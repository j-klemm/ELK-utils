#!/bin/bash
#RUN THIS LOCALLY TO POST DATA TO YOUR LOCAL ES INSTANCE, OR RUN addDataToEs.sh TO POST IT REMOTELY

dataDir=/tmp/kib-vis/data #MUST BE FULL PATH. DATA PATH OF LOCAL MACHINE OR PATH ON EC2 IF POSTING REMOTELY.
indexName=geodata

for filename in $dataDir/*.json; do
    echo Processing $filename
    curl -XPOST -H'Content-Type: application/json' localhost:9200/$indexName/_doc -d @$filename
done
