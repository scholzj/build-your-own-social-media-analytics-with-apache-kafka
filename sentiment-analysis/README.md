# Sentiment Analysis

This project uses Quarkus, Kafka Streams and Deep Java Library to analyze sentiment of tweets received from Camel Kafka Connector.


It splits them into words and counts the occurrences.
The results are presented using HTTP API and using the Echarts Word Cloud chart.

## Build

Run `make all` to build the container image and push it to a registry.
You can use the environment variables `DOCKER_REGISTRY`, `DOCKER_ORG` and `DOCKER_TAG` to configure the registry where the image will be pushed.