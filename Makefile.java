# Makefile.java contains the shared tasks for building Java applications. This file is
# included into the Makefile files which contain some Java sources which should be build
# (E.g. cluster-controller etc.).
#

UNAME_S := $(shell uname -s)
ifeq ($(UNAME_S),Darwin)
	NATIVE_BUILD?=-Pnative -Dquarkus.native.container-build=true
else
	NATIVE_BUILD?=-Pnative
endif

java_build:
	echo "Building JAR file ..."
	mvn $(NATIVE_BUILD) package

java_clean:
	echo "Cleaning Maven build ..."
	mvn clean
