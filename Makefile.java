# Makefile.java contains the shared tasks for building Java applications. This file is
# included into the Makefile files which contain some Java sources which should be build
# (E.g. cluster-controller etc.).
#

NATIVE_DOCKER_BUILD = ""

UNAME_S := $(shell uname -s)
ifeq ($(UNAME_S),Darwin)
	NATIVE_DOCKER_BUILD = "-Dquarkus.native.container-build=true"
endif

java_build:
	echo "Building JAR file ..."
	mvn package -Pnative $(NATIVE_DOCKER_BUILD)

java_clean:
	echo "Cleaning Maven build ..."
	mvn clean
