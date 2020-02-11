package Model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface DBManager extends Remote {
    /**
     *
     * @return Current URL of database MySQL server
     */
    String getConnectionInfo() throws RemoteException;

    /**
     *
     * @return name of database source/backup file
     */
    String getFileName() throws RemoteException;

    /**
     *
     * @return name of current table in use by client app
     */
    String getTableName() throws RemoteException;

    /**
     * Sets up a new connection base on the provided MySQL Connection details.
     *
     * @param connectionInfo String: URL for MySQL Server
     * @param login          String: MySQL user name
     * @param password       String: Password for given user name
     */
    void setupConnection(String connectionInfo, String login, String password,
                         String DBName, String tableName, String fileName) throws RemoteException;

    /**
     * Creates a new DB at the JDBC connection, unless the DB already exists, and then it updates
     * the DB Connection existing DataBase.
     */
    void createDB() throws RemoteException;

    /**
     * Adds a new table to the DB to hold all the client info, unless the table already exists.
     */
    void createTable() throws RemoteException;

    /**
     * Removes the table (and its contents) from the database.
     */
    void removeTable() throws RemoteException;

    /**
     * Populates the client table in the database from the provided text file.
     */
    void fillTable() throws RemoteException;

    /**
     * Writes the current contents of the database table to a file
     */
    boolean backupTable() throws RemoteException;

    /**
     * Adds a new client to the exist table.
     *
     * @param client new Client object.
     */
    void addClient(Client client) throws RemoteException;

    /**
     * Updates a clients info, or creates a new one if they do not already exist.
     *
     * @param client Client object
     */
    void updateClient(Client client) throws RemoteException;

    /**
     * Drops a client from the table.
     *
     * @param clientID Unique client ID number of the client to drop.
     */
    void deleteClient(int clientID) throws RemoteException;

    /**
     * Determines whether a client record has changed in the database since it was last retrieved.
     *
     * @param client record in RAM / Stack
     * @return true if the client record has been updated by another user in the database,
     * otherwise false.
     */
    boolean isChanged(Client client) throws RemoteException;

    /***
     * Used to assign unique client ID numbers
     *
     * @return The next system generated client ID number
     */
    int getNextClientID() throws RemoteException;

    /**
     * Adds the database table to the search results window (list model)
     */
    ArrayList<Client> searchResults(String selection, String searchParam) throws RemoteException;

    /**
     * Verify that database setup has been completed successfully.
     *
     * @return true if it has, otherwise false
     */
    boolean isSetupOK() throws RemoteException;

    /**
     * Checks if the MySQL server is up
     * @return true if it is, otherwise false
     */
    boolean isMySQLServerUp() throws RemoteException;

    /**
     * Public wrapper for the closeConnection helper method.
     */
    void shutdownDB() throws RemoteException;
}
