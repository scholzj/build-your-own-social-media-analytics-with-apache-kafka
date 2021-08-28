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
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.Serialized;
import org.apache.kafka.streams.kstream.SlidingWindows;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.Stores;
import org.apache.kafka.streams.state.WindowBytesStoreSupplier;
import twitter4j.Status;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class TopologyProducer {
    static final List<String> IGNORED_WORDS = List.of("https");
    static final String TWEETS_TOPIC = "scholzj-timeline";
    static final String WORD_CLOUD_STORE = "word-cloud-store";
    static final String WORD_CLOUD_TOPIC = "word-cloud-topic";
    static final String WINDOWED_WORD_CLOUD_STORE = "word-cloud-store";

    static final String WEATHER_STATIONS_STORE = "weather-stations-store";

    static final String WEATHER_STATIONS_TOPIC = "weather-stations";
    static final String TEMPERATURE_VALUES_TOPIC = "temperature-values";
    static final String TEMPERATURES_AGGREGATED_TOPIC = "temperatures-aggregated";

    @Produces
    public Topology buildTopology() {
        final TwitterStatusSerde twitterStatusSerde = new TwitterStatusSerde();
        //final ObjectMapperSerde<Status> twitterStatusSerde = new ObjectMapperSerde<>(Status.class);
        final ObjectMapperSerde<TimelineKey> timelineKeySerde = new ObjectMapperSerde<>(TimelineKey.class);
        final KeyValueBytesStoreSupplier storeSupplier = Stores.persistentKeyValueStore(WORD_CLOUD_STORE);
        //final WindowBytesStoreSupplier windowedStoreSupplier = Stores.persistentTimestampedWindowStore(WINDOWED_WORD_CLOUD_STORE, Duration.ofDays(1), Duration.ofHours(1), false);
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

        /*groupedByWord.windowedBy(SlidingWindows.withTimeDifferenceAndGrace(Duration.ofHours(1), Duration.ZERO))
                .count(Materialized.<String, Long>as(windowedStoreSupplier)
                        .withKeySerde(Serdes.String())
                        .withValueSerde(Serdes.Long()));*/

        return builder.build();
    }
}
