#!/bin/bash
docker-compose --file docker-compose-mysql.yml rm -f
docker-compose --file docker-compose-cassandra.yml rm -f
docker-compose --file docker-compose-web.yml rm -f

sleep 1
docker ps
