#!/bin/sh

mvn clean install

if [ "$#" -eq 1 ]
then
    mvn exec:java -Dexec.args="$1"
else
    mvn exec:java
fi