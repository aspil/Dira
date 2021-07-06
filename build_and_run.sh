#!/bin/sh

docker kill $(docker ps -q)

# Build backend image
pushd ./back-end/dira/
./gradlew bootBuildImage
popd

# run docker-compose
docker-compose -f dira_docker.yml up -d --force-recreate
