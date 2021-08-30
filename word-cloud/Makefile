PROJECT_NAME=build-your-own-social-media-analytics-with-apache-kafka-word-cloud

all: java_build docker_build docker_push
build: java_build
clean: java_clean

include ../Makefile.docker

include ../Makefile.java

.PHONY: build clean
