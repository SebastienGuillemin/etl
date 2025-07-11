#!/bin/sh
mvn clean install

if [ "$#" -eq 1 ]; then
    if [ "$1" = "stups_evaluation_quantitative" ]; then
        echo "STUPS quantitative evaluation"
        l=100
        for i in $(seq 1 9);
        do
            mvn exec:java -Dexec.args="$1 $l" -f ./pom.xml
            l=$((l * 2))
        done

    elif [ "$1" = "stups_evaluation_qualitative" ]; then
        echo "STUPS qualitative evaluation"
        mvn exec:java -Dexec.args="$1" -f ./pom.xml
    fi
else
    mvn exec:java -f ./pom.xml
fi