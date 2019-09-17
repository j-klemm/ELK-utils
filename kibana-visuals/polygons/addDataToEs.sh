#!/bin/bash
#AUTHOR: J.KLEMM
#Run this script locally to copy data onto ES cluster running in AWS. f data is local then run postNewData.sh.

remoteDataDir=~/kib-vis/data #CHANGE ME TO GRAB DATA FROM A DIFFERENT REMOTE DIR
localDataDir=./localdata  #CHANGE ME TO COPY DATA FROM DIFFERENT LOCAL DIR
masterNodeAddress=swarm-manager-1 #IP address or alias of ElasticSearch data node

scp ./postNewData.sh $masterNodeAddress:$remoteVisualizationDir/ #copy over shell script
scp $localDataDir/* $masterNodeAddress:$remoteDataDir #copy over local data
ssh $masterNodeAddress "sh $remoteVisualizationDir/postNewData.sh" #run the script you copied over
