#!/bin/sh
. ./env.sh

aws dynamodb list-tables --region="us-east-2"

