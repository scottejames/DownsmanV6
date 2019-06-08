#!/bin/sh
export AWS_REGION="us-east-1"
dynamodb-admin &

docker run -v ~/tmp/data:/data -p 8000:8000 amazon/dynamodb-local -jar DynamoDBLocal.jar -sharedDb -dbPath /data

