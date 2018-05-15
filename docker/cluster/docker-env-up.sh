#!/bin/bash

docker-compose --file docker-compose-mysql.yml up -d --remove-orphans
docker-compose --file docker-compose-cassandra.yml up -d --remove-orphans
docker-compose --file docker-compose-web.yml up -d --remove-orphans

sleep 1
# docker ps
