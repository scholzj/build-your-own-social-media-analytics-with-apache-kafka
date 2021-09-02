package cz.scholz.wordcloud.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.scholz.wordcloud.model.twitter.Tweet;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.jboss.logging.Logger;

import java.io.IOException;

public class TweetSerde  implements Serde<Tweet> {
    private static final Logger LOG = Logger.getLogger(TweetSerde.class);

    final ObjectMapper mapper;

    private final TweetSerializer serializer;
    private final TweetDeserializer deserializer;

    public TweetSerde() {
        mapper = new ObjectMapper();
        serializer = new TweetSerializer();
        deserializer = new TweetDeserializer();
    }

    @Override
    public Serializer<Tweet> serializer() {
        return serializer;
    }

    @Override
    public Deserializer<Tweet> deserializer() {
        return deserializer;
    }

    final class TweetSerializer implements Serializer<Tweet> {
        @Override
        public byte[] serialize(String topic, Tweet data) {
            try {
                return mapper.writeValueAsBytes(data);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to serialize tweet", e);
            }
        }
    }

    final class TweetDeserializer implements Deserializer<Tweet> {
        @Override
        public Tweet deserialize(String topic, byte[] data) {
            try {
                return mapper.readValue(data, Tweet.class);
            } catch (IOException e) {
                LOG.errorv("Failed to deserialize Tweet from JSON: {0}", new String(data));
                throw new RuntimeException("Failed to deserialize tweet", e);
            }
        }
    }
}
