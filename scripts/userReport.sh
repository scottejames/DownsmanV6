#!/bin/sh

. ./env.sh
export DM_DEV=False

java -Dlog4j.debug -classpath $CLASSPATH:$DM_CLASSES:. com.scottejames.downsman.reports.UserReport
