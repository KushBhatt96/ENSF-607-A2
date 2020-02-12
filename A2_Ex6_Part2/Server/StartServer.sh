#!/bin/bash

rmiregistry &
sleep 1
javac ./Control/ServerController.java
sleep 1
java -cp .:./Control/mysql-connector-java-5.1.48-bin.jar -Djava.rmi.server.hostname=99.79.63.33 -Djava.rmi.server.codebase=file: Control.ServerController &
