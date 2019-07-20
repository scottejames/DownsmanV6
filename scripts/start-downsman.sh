#!/bin/sh
. ./env.sh
docker run -v /Users/scottejames/tmp/letsencrypt:/etc/letsencrypt -v /Users/scottejames/.aws/credentials  -it -p 443:8443 -p 80:8080 scott/downsman:$DM_VERSION

