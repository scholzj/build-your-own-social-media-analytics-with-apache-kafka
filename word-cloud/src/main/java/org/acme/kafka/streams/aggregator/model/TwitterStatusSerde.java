package org.acme.kafka.streams.aggregator.model;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;

import java.nio.charset.StandardCharsets;

public class TwitterStatusSerde implements Serde<Status> {
    @Override
    public Serializer<Status> serializer() {
        return new TwitterStatusSerializer();
    }

    @Override
    public Deserializer<Status> deserializer() {
        return new TwitterStatusDeserializer();
    }

    class TwitterStatusSerializer implements Serializer<Status> {
        @Override
        public byte[] serialize(String topic, Status data) {
            return TwitterObjectFactory.getRawJSON(data).getBytes(StandardCharsets.UTF_8);
        }
    }

    class TwitterStatusDeserializer implements Deserializer<Status> {
        @Override
        public Status deserialize(String topic, byte[] data) {
            try {
                return TwitterObjectFactory.createStatus(new String(data));
            } catch (TwitterException e) {
                throw new RuntimeException("Failed to deserialize Twitter status", e);
            }
        }
    }
}
