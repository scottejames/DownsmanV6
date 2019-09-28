#!/bin/sh

docker build -t scott/downsmanclosed:1.0  .
docker scott/downsmanclosed:1.0 scottejames/downsmanclosed:1.0
docker push scottejames/downsmanclosed:1.0
