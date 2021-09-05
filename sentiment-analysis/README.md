# Sentiment Analysis

This project uses Quarkus, Kafka Streams and Deep Java Library to analyze sentiment of tweets received from Camel Kafka Connector.
It reads tweets from one topic, decides whether they are _positive_ or _negative_ and if they are above 90%, it forwards them to another topic where they can be picked up by another appliction.

## Build

Run `make all` to build the container image and push it to a registry.
You can use the environment variables `DOCKER_REGISTRY`, `DOCKER_ORG` and `DOCKER_TAG` to configure the registry where the image will be pushed.