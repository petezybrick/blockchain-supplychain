#!/bin/bash
docker-compose --file docker-compose-mysql.yml rm -f
docker-compose --file docker-compose-cassandra.yml rm -f
docker-compose --file docker-compose-web.yml rm -f
docker-compose --file docker-compose-presto-hive.yml rm -f
docker-compose --file docker-compose-spark.yml rm -f
docker-compose --file docker-compose-zeppelin.yml rm -f

sleep 1
docker ps
