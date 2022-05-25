package cz.scholz.tagcloud.streams;

import cz.scholz.tagcloud.model.TweetSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.Stores;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class TopologyProducer {
    static final String TAG_CLOUD_STORE = "tag-cloud-store";

    @ConfigProperty(name = "quarkus.kafka-streams.topics")
    String tweetsTopic;

    @Produces
    public Topology buildTopology() {
        final TweetSerde tweetSerde = new TweetSerde();
        final KeyValueBytesStoreSupplier storeSupplier = Stores.persistentKeyValueStore(TAG_CLOUD_STORE);

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
                .filter((key, value) -> value != null && (value.startsWith("#") || value.startsWith("@")))
                .mapValues(TopologyProducer::stripSpecialCharactersFromTheEnd)
                .filter((key, value) -> !value.isEmpty())
                .groupBy((key, value) -> value, Grouped.with(Serdes.String(), Serdes.String()))
                .count(Materialized.<String, Long>as(storeSupplier)
                        .withKeySerde(Serdes.String())
                        .withValueSerde(Serdes.Long()));

        return builder.build();
    }

    /**
     * Strips special characters from the end of the word. This removes things such as ":" or "." etc.
     *
     * @param word  The word which should be striped
     *
     * @return      The striped word
     */
    static String stripSpecialCharactersFromTheEnd(String word)  {
        return word.replaceAll("\\W+$", "");
    }
}
