start rmiregistry
javac .\Control\ServerController.java
java -cp .;.\Control\mysql-connector-java-5.1.48-bin.jar -Djava.rmi.server.codebase=file: Control.ServerController