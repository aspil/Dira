#! /bin/bash

bash -c './postgres_conf.sh'
pushd ../../back-end/dira/
bash -c './gradlew run'
popd
