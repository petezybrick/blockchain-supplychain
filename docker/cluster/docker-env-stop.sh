#!/bin/bash
docker-compose --file docker-compose-zeppelin.yml stop
docker-compose --file docker-compose-spark.yml stop
docker-compose --file docker-compose-presto-hive.yml stop
docker-compose --file docker-compose-web.yml stop
docker-compose --file docker-compose-mysql.yml stop
docker-compose --file docker-compose-cassandra.yml stop

sleep 1
docker ps
