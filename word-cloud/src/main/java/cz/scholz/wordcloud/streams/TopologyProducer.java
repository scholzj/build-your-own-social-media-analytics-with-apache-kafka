package cz.scholz.wordcloud.streams;

import cz.scholz.wordcloud.model.TweetSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.Stores;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class TopologyProducer {
    private static final Logger LOG = Logger.getLogger(TopologyProducer.class);

    static final List<String> IGNORED_WORDS = List.of("about", "then", "than", "this", "that", "from", "with", "been",
            "more", "your", "should", "it's", "i've", "have", "will", "here", "come", "some", "when", "that's", "what",
            "like", "just", "they", "their", "many", "which", "give", "them", "first", "today", "time", "last", "live",
            "over", "going", "after", "look", "could", "back", "join", "next");

    static final String WORD_CLOUD_STORE = "word-cloud-store";

    @ConfigProperty(name = "quarkus.kafka-streams.topics")
    String tweetsTopic;

    @Produces
    public Topology buildTopology() {
        final TweetSerde tweetSerde = new TweetSerde();
        final KeyValueBytesStoreSupplier storeSupplier = Stores.persistentKeyValueStore(WORD_CLOUD_STORE);

        final StreamsBuilder builder = new StreamsBuilder();

        builder.stream(tweetsTopic, Consumed.with(Serdes.ByteArray(), tweetSerde))
                .flatMapValues(value -> {
                    if (value.getRetweetedStatus() != null)  {
                        return List.of(value.getRetweetedStatus().getText());
                    } else if (value.getQuotedStatus() != null) {
                        return List.of(value.getQuotedStatus().getText(), value.getText());
                    } else {
                        return List.of(value.getText());
                    }
                })
                .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\s+")))
                .filter((key, value) ->
                        value.length() > 3 // Longer than 3 characters
                                && !IGNORED_WORDS.contains(value) // Not on an ignored words list
                                && !value.startsWith("http://") // Not an HTTP link
                                && !value.startsWith("https://") // Not an HTTPS link
                )
                .mapValues(TopologyProducer::stripSpecialCharacters)
                .filter((key, value) -> !value.isEmpty())
                .groupBy((key, value) -> value, Grouped.with(Serdes.String(), Serdes.String()))
                .count(Materialized.<String, Long>as(storeSupplier)
                        .withKeySerde(Serdes.String())
                        .withValueSerde(Serdes.Long()));

        return builder.build();
    }

    /**
     * Strips special characters from the start or end of the word. This removes things such as ":" or "." etc.
     *
     * @param word  The word which should be striped
     *
     * @return      The striped word
     */
    static String stripSpecialCharacters(String word)  {
        return word.replaceAll("^\\W+", "").replaceAll("\\W+$", "");
    }
}
