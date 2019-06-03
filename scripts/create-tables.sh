#!/bin/sh
. ./env.sh
java -classpath $CLASSPATH:$DM_CLASSES  com.scottejames.downsman.services.CreateDynamoTables
