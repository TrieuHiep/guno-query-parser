#!/bin/sh
TARGET_HOME=./release
ACTIVE_PROFILE=prod
JMX_PARAM="-Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false"
echo $JMX_PARAM
rm -rf release/ &&
mvn clean &&
mvn package -Dmaven.test.skip=true &&
exec java -Xmx2000m -Xms312m -Dspring.profiles.active=$ACTIVE_PROFILE -Dlog4j2.formatMsgNoLookups=true -cp conf/:$TARGET_HOME/libs/*:$TARGET_HOME/guno-query-parser-1.0.0.jar vn.guno.GunoQueryParserApplication "$@" & echo $! > pid