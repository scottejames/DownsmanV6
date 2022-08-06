#!/bin/sh

. ./env.sh
export DM_DEV=False

java -classpath $CLASSPATH:$DM_CLASSES com.scottejames.downsman.reports.CheckinReport

mv /tmp/Checkin-Report-*.csv /Users/scottejames/Google\ Drive/Downsman/Downsman\ 2022/SignupReport/

