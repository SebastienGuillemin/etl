#!/bin/sh

mvn clean install

if [ "$#" -eq 1 ]
then
    mvn exec:java -Dexec.args="$1" -f ./pom.xml
else
    mvn exec:java -f ./pom.xml
fi