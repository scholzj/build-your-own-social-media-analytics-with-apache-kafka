package org.acme.kafka.streams.aggregator.streams;

import io.quarkus.kafka.client.serialization.ObjectMapperSerde;
import org.acme.kafka.streams.aggregator.model.TimelineKey;
import org.acme.kafka.streams.aggregator.model.TwitterStatusSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Suppressed;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.Stores;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class TopologyProducer {
    static final List<String> IGNORED_WORDS = List.of("https");
    static final String TWEETS_TOPIC = "scholzj-timeline";
    static final String WORD_CLOUD_STORE = "word-cloud-store";
    static final String LATEST_WORD_CLOUD_STORE = "latest-word-cloud-store";

    @Produces
    public Topology buildTopology() {
        final TwitterStatusSerde twitterStatusSerde = new TwitterStatusSerde();
        //final ObjectMapperSerde<Status> twitterStatusSerde = new ObjectMapperSerde<>(Status.class);
        final ObjectMapperSerde<TimelineKey> timelineKeySerde = new ObjectMapperSerde<>(TimelineKey.class);
        final KeyValueBytesStoreSupplier storeSupplier = Stores.persistentKeyValueStore(WORD_CLOUD_STORE);
        final KeyValueBytesStoreSupplier latestStoreSupplier = Stores.persistentKeyValueStore(LATEST_WORD_CLOUD_STORE);
        //final WindowBytesStoreSupplier latestStoreSupplier = Stores.persistentTimestampedWindowStore(WINDOWED_WORD_CLOUD_STORE, Duration.ofDays(1), Duration.ofHours(1), false);
        final StreamsBuilder builder = new StreamsBuilder();

        final KGroupedStream<String, String> groupedByWord = builder.stream(TWEETS_TOPIC, Consumed.with(timelineKeySerde, twitterStatusSerde))
                .flatMapValues(value -> {
                    if (value.isRetweet())  {
                        return List.of(value.getRetweetedStatus().getText());
                    } else if (value.getQuotedStatus() != null) {
                        return List.of(value.getRetweetedStatus().getText(), value.getText());
                    } else {
                        return List.of(value.getText());
                    }
                })
                .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
                .filter((key, value) -> value.length() > 4 && !IGNORED_WORDS.contains(value))
                //.groupBy((key, value) -> key.type + "@" + value);
                .groupBy((key, value) -> value, Grouped.with(Serdes.String(), Serdes.String()));

        groupedByWord.count(Materialized.<String, Long>as(storeSupplier)
                        .withKeySerde(Serdes.String())
                        .withValueSerde(Serdes.Long()));

        groupedByWord.windowedBy(TimeWindows.of(Duration.ofHours(1)).advanceBy(Duration.ofMinutes(1)).grace(Duration.ofMinutes(1)))
                .count(Materialized.with(Serdes.String(), Serdes.Long()))
                .suppress(Suppressed.untilWindowCloses(Suppressed.BufferConfig.unbounded()))
                .toStream((key, value) -> {
                    return key.key();
                })
                //.toStream()
                //.groupBy((key, value) -> key.key(), Grouped.with(WindowedSerdes.timeWindowedSerdeFrom(String.class), Serdes.String()));
                //.groupBy((key, value) -> key.key(), Grouped.with(Serdes.String(), Serdes.String()))
                .groupByKey(Grouped.with(Serdes.String(), Serdes.Long()))
                .aggregate(
                        () -> 0L,
                        (aggKey, newValue, aggValue) -> newValue,
                        Materialized.<String, Long>as(latestStoreSupplier)
                                .withKeySerde(Serdes.String())
                                .withValueSerde(Serdes.Long())
                );
                /*.count(Materialized.<String, Long>as(latestStoreSupplier)
                        .withKeySerde(Serdes.String())
                        .withValueSerde(Serdes.Long()));*/

        return builder.build();
    }
}
