package cz.scholz.sentimentanalysis.streams;

import cz.scholz.sentimentanalysis.model.Aggregation;
import cz.scholz.sentimentanalysis.model.TweetSerde;
import io.quarkus.kafka.client.serialization.ObjectMapperSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.Materialized;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class TopologyProducer {
    private static final Logger LOG = Logger.getLogger(TopologyProducer.class);

    private static final String SOURCE_TOPIC = "twitter-timeline";

    @Produces
    public Topology buildTopology() {
        final TweetSerde tweetSerde = new TweetSerde();
        final ObjectMapperSerde<Aggregation> aggregationSerde = new ObjectMapperSerde<>(Aggregation.class);
        final StreamsBuilder builder = new StreamsBuilder();

        builder.stream(SOURCE_TOPIC, Consumed.with(Serdes.ByteArray(), tweetSerde))
                .filter((key, value) -> value.getRetweetedStatus() == null)
                .groupBy((key, value) -> 1, Grouped.with(Serdes.Integer(), tweetSerde))
                .aggregate(
                        Aggregation::new,
                        (aggKey, newValue, oldValue) -> oldValue.aggregate(newValue),
                        Materialized.with(Serdes.Integer(), aggregationSerde)
                )
                .toStream()
                .peek((key, value) -> LOG.infov("{0}", value));

        return builder.build();
    }
}
