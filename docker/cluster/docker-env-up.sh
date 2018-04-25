#!/bin/bash

docker-compose --file docker-compose-mysql.yml up -d
docker-compose --file docker-compose-cassandra.yml up -d
docker-compose --file docker-compose-web.yml up -d

sleep 1
# docker ps
