package org.acme.kafka.streams.aggregator.streams;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.errors.InvalidStateStoreException;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.jboss.logging.Logger;

@ApplicationScoped
public class InteractiveQueries {
    private static final Logger LOG = Logger.getLogger(InteractiveQueries.class);

    @Inject
    KafkaStreams streams;

    public Map<String, Long> getAllTimeHigh(int count)   {
        LOG.infov("Finding top {0} words", count);

        //TreeSet<KeyValue<String, Long>> topResults = new TreeSet<>(Comparator.comparing(keyValue -> keyValue.value));
        TreeSet<KeyValue<String, Long>> topResults = new TreeSet<>((w1, w2) -> w1.value > w2.value ?  1 : -1);
        KeyValueIterator<String, Long> all = getWordCloudStore().all();

        while (all.hasNext())   {
            KeyValue next = all.next();
            //LOG.infov("Handling word {0} with count {1}", next.key, next.value);
            topResults.add(next);

            if (topResults.size() > count) {
                topResults.pollFirst();
            }
        }

        return topResults.stream().collect(Collectors.toMap(keyValue -> keyValue.key, keyValue -> keyValue.value));
    }

    private ReadOnlyKeyValueStore<String, Long> getWordCloudStore() {
        while (true) {
            try {
                return streams.store(StoreQueryParameters.fromNameAndType(TopologyProducer.WORD_CLOUD_STORE, QueryableStoreTypes.keyValueStore()));
            } catch (InvalidStateStoreException e) {
                // store not ready yet
            }
        }
    }

    public List<PipelineMetadata> getMetaData() {
        return streams.allMetadataForStore(TopologyProducer.WORD_CLOUD_STORE)
                .stream()
                .map(m -> new PipelineMetadata(
                        m.hostInfo().host() + ":" + m.hostInfo().port(),
                        m.topicPartitions()
                                .stream()
                                .map(TopicPartition::toString)
                                .collect(Collectors.toSet())))
                .collect(Collectors.toList());
    }

    /*public GetWeatherStationDataResult getWeatherStationData(int id) {
        KeyQueryMetadata metadata = streams.queryMetadataForKey(
                TopologyProducer.WEATHER_STATIONS_STORE,
                id,
                Serdes.Integer().serializer());

        if (metadata == null || metadata == KeyQueryMetadata.NOT_AVAILABLE) {
            LOG.warnv("Found no metadata for key {0}", id);
            return GetWeatherStationDataResult.notFound();
        } else if (metadata.activeHost().host().equals(HostName.getQualifiedHostName())) {
            LOG.infov("Found data for key {0} locally", id);
            Aggregation result = getWeatherStationStore().get(id);

            if (result != null) {
                return GetWeatherStationDataResult.found(WeatherStationData.from(result));
            } else {
                return GetWeatherStationDataResult.notFound();
            }
        } else {
            LOG.infov("Found data for key {0} on remote host {1}:{2}", id, metadata.activeHost().host(), metadata.activeHost().port());
            return GetWeatherStationDataResult.foundRemotely(metadata.activeHost().host(), metadata.activeHost().port());
        }
    }



    private ReadOnlyWindowStore<String, Long> getWindowedWordCloudStore() {
        while (true) {
            try {
                return streams.store(StoreQueryParameters.fromNameAndType(TopologyProducer.WINDOWED_WORD_CLOUD_STORE, QueryableStoreTypes.windowStore()));
            } catch (InvalidStateStoreException e) {
                // ignore, store not ready yet
            }
        }
    }*/
}
