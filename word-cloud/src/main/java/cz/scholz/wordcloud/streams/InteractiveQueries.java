package cz.scholz.wordcloud.streams;

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

    public Map<String, Long> getAllTimeHighest(int count)   {
        LOG.infov("Finding all-time top {0} words", count);
        return getHighestN(count, TopologyProducer.WORD_CLOUD_STORE);
    }

    public Map<String, Long> getLatestHighest(int count)   {
        LOG.infov("Finding latest top {0} words", count);
        return getHighestN(count, TopologyProducer.LATEST_WORD_CLOUD_STORE);
    }

    private Map<String, Long> getHighestN(int count, String storeName)    {
        //noinspection ComparatorMethodParameterNotUsed
        TreeSet<KeyValue<String, Long>> topResults = new TreeSet<>((w1, w2) -> w1.value > w2.value ?  1 : -1);
        KeyValueIterator<String, Long> all = getWordCloudStore(storeName).all();

        while (all.hasNext())   {
            KeyValue<String, Long> next = all.next();
            topResults.add(next);

            if (topResults.size() > count) {
                topResults.pollFirst();
            }
        }

        return topResults.stream().collect(Collectors.toMap(keyValue -> keyValue.key, keyValue -> keyValue.value));
    }

    private ReadOnlyKeyValueStore<String, Long> getWordCloudStore(String storeName) {
        while (true) {
            try {
                return streams.store(StoreQueryParameters.fromNameAndType(storeName, QueryableStoreTypes.keyValueStore()));
            } catch (InvalidStateStoreException e) {
                // store not ready yet
            }
        }
    }

    public List<PipelineMetadata> getAllTimeMetaData() {
        return getMetaData(TopologyProducer.WORD_CLOUD_STORE);
    }

    public List<PipelineMetadata> getLatestMetaData() {
        return getMetaData(TopologyProducer.LATEST_WORD_CLOUD_STORE);
    }

    private List<PipelineMetadata> getMetaData(String storeName) {
        return streams.allMetadataForStore(storeName)
                .stream()
                .map(m -> new PipelineMetadata(
                        m.hostInfo().host() + ":" + m.hostInfo().port(),
                        m.topicPartitions()
                                .stream()
                                .map(TopicPartition::toString)
                                .collect(Collectors.toSet())))
                .collect(Collectors.toList());
    }
}
