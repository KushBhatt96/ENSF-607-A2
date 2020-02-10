start rmiregistry
javac .\Control\ServerController.java
REM The command below will start the server on your local machine
java -cp .;.\Control\mysql-connector-java-5.1.48-bin.jar -Djava.rmi.server.codebase=file: Control.ServerController