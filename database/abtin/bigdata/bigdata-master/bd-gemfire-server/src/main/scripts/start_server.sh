#!/usr/bin/env bash

# todo: add parameter checking

cwd=`pwd`

cp=$cwd/lib/bd-gemfire-server-dependencies-@deployVersion@.jar

appProperties="-Dapp.props=$cwd/config/application-$2.properties"

gfsh -e "start server --name=server$1 --use-cluster-configuration=false --log-level=config --locators=127.0.0.1[10334] --bind-address=localhost --classpath=$cp --J=$appProperties --spring-xml-location=file://$cwd/config/server-cache.xml"
