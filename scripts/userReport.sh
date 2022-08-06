#!/bin/sh

. ./env.sh
export DM_DEV=False

java -classpath $CLASSPATH:$DM_CLASSES -Dlog4j.configuration=/Users/scottejames/IdeaProjects/DownsmanV6/target/classes/log4j.properties com.scottejames.downsman.reports.UserReport
