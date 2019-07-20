#!/bin/sh

. ./env.sh
(cd ..;docker build -f scripts/Dockerfile -t scott/downsman:$DM_VERSION .)

docker tag scott/downsman:$DM_VERSION scottejames/downsman:$DM_VERSION
docker push scottejames/downsman:$DM_VERSION

