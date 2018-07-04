#!/bin/bash
mvn -f bcsc-common/pom.xml clean install
mvn -f bcsc-api/pom.xml clean install
mvn -f bcsc-apiclient/pom.xml clean install
mvn -f bcsc-service/pom.xml clean install
mvn -f bcsc-sim/pom.xml clean install
mvn -f bcsc-webapi/pom.xml clean install
mvn -f bcsc-webui/pom.xml clean install
mvn -f bcsc-test/pom.xml clean install
