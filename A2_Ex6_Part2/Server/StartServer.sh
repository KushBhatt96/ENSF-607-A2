#!/bin/bash

rmiregistry &
sleep 1
javac ./Control/ServerController.java
sleep 1
# This script will start the server on your local machine
java -cp .:./Control/mysql-connector-java-5.1.48-bin.jar -Djava.rmi.server.codebase=file: Control.ServerController &
