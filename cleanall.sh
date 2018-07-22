#!/bin/bash
mvn -f bcsc-common/pom.xml clean
mvn -f bcsc-api/pom.xml clean
mvn -f bcsc-apiclient/pom.xml clean
mvn -f bcsc-service/pom.xml clean
mvn -f bcsc-sim/pom.xml clean
mvn -f bcsc-webapi/pom.xml clean
mvn -f bcsc-webui/pom.xml clean
mvn -f bcsc-test/pom.xml clean
