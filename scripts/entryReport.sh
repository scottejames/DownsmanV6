#!/bin/sh

. ./env.sh
export DM_DEV=False

java -classpath $CLASSPATH:$DM_CLASSES com.scottejames.downsman.reports.EntryReport

mv /tmp/Entry-Report-*.csv /Users/scottejames/downsman-reports

