#! /bin/bash

bash -c './postgres_conf.sh'
cd ../../back-end/dira/
bash -c './gradlew run'