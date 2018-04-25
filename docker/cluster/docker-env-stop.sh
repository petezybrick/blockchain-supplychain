#!/bin/bash
docker-compose --file docker-compose-mysql.yml stop
docker-compose --file docker-compose-cassandra.yml stop
docker-compose --file docker-compose-web.yml stop

sleep 1
docker ps
