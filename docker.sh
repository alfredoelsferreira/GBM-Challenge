#!/usr/bin/env bash

# Don't execute this, these are the commands that have been executed in the tutorial, just as resource.
# To run the application use Docker compose.
docker run -d \
    --name demo-mysql \
    -e MYSQL_ROOT_PASSWORD=p4SSW0rd \
    -e MYSQL_DATABASE=degbmo \
    -e MYSQL_USER=root \
    -e MYSQL_PASSWORD=root \
    mysql:latest
docker run -d \
    --name gbm-challenge \
    --link demo-mysql:mysql \
    -p 8080:8080 \
    gbm/gbm-challenge
