# Tag Cloud

This project uses Quarkus and Kafka Streams to analyze tweets received from Camel Kafka Connector.
It finds all hashtags and mentions and counts their occurrences.
The results are presented using HTTP API and using the Echarts Word Cloud chart.

## Build

Run `make all` to build the container image and push it to a registry.
You can use the environment variables `DOCKER_REGISTRY`, `DOCKER_ORG` and `DOCKER_TAG` to configure the registry where the image will be pushed.