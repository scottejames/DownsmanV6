#!/bin/sh
. ./env.sh
dynamodb-admin
docker run -p 8000:8000 amazon/dynamodb-local -jar DynamoDBLocal.jar 
