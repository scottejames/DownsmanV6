#!/bin/sh
. ./env.sh
docker run -v /Users/scottejames/.aws/credentials  -it -p 80:8080 scott/downsman:$DM_VERSION

