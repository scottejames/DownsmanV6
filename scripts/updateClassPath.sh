#!/bin/sh
(cd ../;mvn dependency:build-classpath -Dmdep.outputFile=scripts/ClassPath)
