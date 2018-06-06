#!/bin/bash
docker-compose --file docker-compose-mysql.yml start
docker-compose --file docker-compose-cassandra.yml start
docker-compose --file docker-compose-web.yml start
docker-compose --file docker-compose-presto-hive.yml start
docker-compose --file docker-compose-zeppelin.yml start


sleep 1
# docker ps
