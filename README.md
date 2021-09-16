# Build your own social media analytics with Apache Kafka

This repository contains the demo for a conference talk with the same name.
You can also have a look at the [slides](https://docs.google.com/presentation/d/18bmiZagwrAe8fnuuyBs45l1U5OHIGwK9pjLALpcC23E/edit?usp=sharing).

## Prerequisites

1) Install the Strimzi operator.
   The demo is currently using Strimzi 0.25.0, but it should work also with newer versions.
   If needed, follow the documentation at [https://strimzi.io](https://strimzi.io).

2) Create a Kubernetes Secret with credentials for your container registry.
   It should follow the usual Kubernetes format:
   ```yaml
   apiVersion: v1
   kind: Secret
   metadata:
     name: docker-credentials
   type: kubernetes.io/dockerconfigjson
   data:
     .dockerconfigjson: Cg==
   ```

3) Register for the Twitter API and create a Kubernetes Secret with the Twitter credentials in the following format:
   ```yaml
   apiVersion: v1
   kind: Secret
   metadata:
     name: twitter-credentials
   type: Opaque
   data:
     consumerKey: Cg==
     consumerSecret: Cg==
     accessToken: Cg==
     accessTokenSecret: Cg==
   ```

4) Deploy the Kafka cluster:
   ```
   kubectl apply -f 01-kafka-yaml
   ```

5) Once Kafka cluster is ready, deploy the Kafka Connect cluster which will also download the Camel Kafka Connectors for Twitter
   ```
   kubectl apply -f 02-connect-yaml
   ```

## Analyzing our own Twitter timeline

1) Deploy the Camel Twitter Timeline connector
   ```
   kubectl apply -f 10-timeline.yaml
   ```
   That should create a topic `twitter-timeline` and start sending the twitter statuses to this topic.
   You can use `kafkacat` to check them:
   ```
   kafkacat -C -b <brokerAddress> -o beginning -t twitter-timeline | jq .text
   ```

2) Deploy the Word Cloud and Tag Cloud applications:
   ```
   kubectl apply -f 11-timeline-word-cloud.yaml
   kubectl apply -f 12-timeline-tag-cloud.yaml
   ```
   They create Ingress resources to be able to access their API and UI.
   If needed, you might need to customize the Ingress or replace it with Route etc.
   The source code for both applications is part of the repository.
   You should see a word cloud similar to this:
   ![Word Cloud](assets/word-cloud.png)

## Doing a sentiment analysis of a search result

1) Deploy the Camel Twitter Search connector
   ```
   kubectl apply -f 20-search.yaml
   ```
   That should create a topic `twitter-search` and start sending the twitter statuses to this topic.
   You can change the search term in the connector configuration (YAML file)
   You can use `kafkacat` to check them:
   ```
   kafkacat -C -b <brokerAddress> -o beginning -t twitter-search | jq .text
   ```

2) Deploy the Camel Twitter DM connector
   ```
   kubectl apply -f 21-alerts.yaml
   ```
   That should create a topic `twitter-alerts` and consume it.
   When a message is sent to this topic, it will be forwarded as a direct message to the account specified in `.spec.config` in `camel.sink.path.user`.
   Update this to your Twitter screen name (username) before deploying the connector.
   You can use `kafkacat` to check them:
   ```
   kafkacat -C -b <brokerAddress> -o beginning -t twitter-alerts | jq .text
   ```

3) Deploy the Sentiment Analysis applications:
   ```
   kubectl apply -f 22-sentiment-analysis.yaml
   ```
   It will read the tweets found by the search connector and do a sentiment analysis of them.
   If they are positive or negative on more than 90%, it will forward them to the alert topic.
   The DM connector will pick them up from this topic and send them as DMs to your Twitter account.
   ![Sentiment Analysis](assets/sentiment-analysis.png)

## Doing ad-hoc analysis

1) Open the `ad-hoc` project in an IDE.
   Check out the source code and configure it in `./src/main/resources/application.properties`.
   If you want, change the code to prepare a new experiment.
   Once ready, run the code using `mvn quarkus:dev` and watch the output.

## Other examples

You can use also other examples and play with them.
The files `90-kafka-search.yaml`, `91-strimzi-search.yaml`, and `92-avfc-search.yaml` contain some other example searches as well.

## Useful commands

These commands might be useful when playing with the demo:

### Reseting the streams applications:

1) Stop the application

2) Reset the application context
   ```
   bin/kafka-streams-application-reset.sh --bootstrap-servers <brokerAddress> --application-id <applicationId> --execute
   ```

3) Reset the offset
   ```
   hacking/kafka/bin/kafka-consumer-groups.sh --bootstrap-server <brokerAddress> --group <applicationId> --topic <sourceTopic> --to-earliest --reset-offsets --execute
   ```

### Reading the tweets with `kafkacat`

```
kafkacat -G <groupId> -C -b <brokerAddress> -o beginning -t <topic> | jq .text
```

You can also pipe the output to `jq` to pretty-print the JSON and use `jq` to for example extract the status message:

```
kafkacat -G <groupId> -C -b <brokerAddress> -o beginning -t <topic> | jq .text
```