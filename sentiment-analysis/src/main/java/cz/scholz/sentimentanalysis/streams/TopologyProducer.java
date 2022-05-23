package cz.scholz.sentimentanalysis.streams;

import ai.djl.Application;
import ai.djl.MalformedModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ModelZoo;
import ai.djl.translate.TranslateException;
import cz.scholz.sentimentanalysis.model.TweetSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

@ApplicationScoped
public class TopologyProducer {
    private static final Logger LOG = Logger.getLogger(TopologyProducer.class);

    private static final String SOURCE_TOPIC = "twitter-search";
    private static final String TARGET_TOPIC = "twitter-alerts";

    private static Predictor<String, Classifications> predictor;

    @Produces
    public Topology buildTopology() {
        final TweetSerde tweetSerde = new TweetSerde();
        try {
            Criteria<String, Classifications> criteria = Criteria.builder()
                    .optApplication(Application.NLP.SENTIMENT_ANALYSIS)
                    .setTypes(String.class, Classifications.class)
                    .build();
            predictor = ModelZoo.loadModel(criteria).newPredictor();
        } catch (IOException | ModelNotFoundException | MalformedModelException e) {
            LOG.error("Failed to load model", e);
            throw new RuntimeException("Failed to load model", e);
        }

        final StreamsBuilder builder = new StreamsBuilder();

        builder.stream(SOURCE_TOPIC, Consumed.with(Serdes.ByteArray(), tweetSerde))
                .flatMapValues(value -> {
                    if (value.getRetweetedStatus() != null)  {
                        // We ignore retweets => we do not want alert for every retweet
                        return List.of();
                    } else {
                        String tweet = value.getText();

                        try {
                            Classifications classifications = predictor.predict(tweet);

                            String statusUrl = "https://twitter.com/" + value.getUser().getScreenName() + "/status/" + value.getId();
                            String alert = String.format("The following tweet was classified as %s with %2.2f%% probability: %s",
                                    classifications.best().getClassName().toLowerCase(Locale.ENGLISH),
                                    classifications.best().getProbability() * 100,
                                    statusUrl);

                            // We care nly about strong results where probability is > 50%
                            if (classifications.best().getProbability() > 0.50) {
                                LOG.infov("Tweeting: {0}", alert);
                                return List.of(alert);
                            } else {
                                LOG.infov("Not tweeting: {0}", alert);
                                return List.of();
                            }
                        } catch (TranslateException e) {
                            LOG.errorv("Failed to classify the tweet {0}", value);
                            return List.of();
                        }
                    }
                })
                .peek((key, value) -> LOG.infov("{0}", value))
                .to(TARGET_TOPIC, Produced.with(Serdes.ByteArray(), Serdes.String()));

        return builder.build();
    }
}
