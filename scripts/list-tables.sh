#!/bin/sh
. ./env.sh

aws dynamodb list-tables --endpoint-url http://localhost:8000 --region="eu-west-2"

