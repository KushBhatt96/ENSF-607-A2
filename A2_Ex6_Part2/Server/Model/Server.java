package Model;

/*
ENSF 607 - Winter 2020 - Lab 02 - Exercise 6
Kush Bhatt & Matthew Vanderwey 2019-11-15
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class enables a DataBase Manager for the client
 * management system.
 *
 * @author Kush Bhatt & Matthew Vanderwey
 * @version 2.0
 * @since 2020-02-07
 */
public class Server implements DBManager {
    private DataLogger dataLogger;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private String connectionInfo, login, password;
    private String serverPath, DBName, tableName, fileName;
    private boolean dbConnectOK, setTableOK, tableFillOK;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor

    public Server() {
        // Setup the server data logger
        dataLogger = new DataLogger("message_log.txt", "yyyy/MM/dd HH:mm:ss");

        // Initially set all setup validation flags to false
        dbConnectOK = false;
        setTableOK = false;
        tableFillOK = false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Getters

    @Override
    public String getConnectionInfo() {
        return connectionInfo;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public String getTableName() {
        return this.tableName;
    }

    @Override
    public int getNextClientID() {
        return Client.generateClientID();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Public Methods

    /**
     * Sets up a new connection base on the provided MySQL Connection details.
     *
     * @param connectionInfo String: URL for MySQL Server
     * @param login          String: MySQL user name
     * @param password       String: Password for given user name
     */
    @Override
    public void setupConnection(String connectionInfo, String login, String password,
                                String DBName, String tableName, String fileName) {

        // Log that the admin has logged in
        dataLogger.writeToMessageLog("Admin Logged In");

        // Establish connection details from the Admin
        this.connectionInfo = connectionInfo;
        this.serverPath = connectionInfo;
        this.login = login;
        this.password = password;
        this.DBName = DBName;
        this.tableName = tableName;
        this.fileName = fileName;

        makeConnection(connectionInfo, login, password);
    }

    /**
     * Creates a new DB at the JDBC connection, unless the DB already exists, and then it updates
     * the DB Connection existing DataBase.
     */
    @Override
    public void createDB() {
        try {
            if (connection != null) {
                if (!isDBExist()) {
                    String newDB = "CREATE DATABASE " + DBName;
                    preparedStatement = connection.prepareStatement(newDB);
                    preparedStatement.executeUpdate();
                }
                if (!dbConnectOK)
                    connection = updateConnection(connectionInfo, DBName);
                dbConnectOK = true;
                dataLogger.writeToMessageLog("Database " + DBName + " created");
            }
        } catch (SQLException sqlex) {
            dataLogger.writeToMessageLog("Failed to create new DataBase" + DBName);
            sqlex.printStackTrace();

        } catch (Exception e) {
            dataLogger.writeToMessageLog("Something has gone terribly wrong while creating the DB");
            e.printStackTrace();
        }
    }

    /**
     * Adds a new table to the DB to hold all the client info, unless the table already exists.
     */
    @Override
    public void createTable() {
        String sql = "CREATE TABLE " + tableName + " (" +
                "id INT(4) NOT NULL, " +
                "firstname VARCHAR(20) NOT NULL, " +
                "lastname VARCHAR(20) NOT NULL, " +
                "address VARCHAR(50) NOT NULL, " +
                "postalCod CHAR(7) NOT NULL, " +
                "phoneNumber CHAR(12) NOT NULL, " +
                "clientType CHAR(1) NOT NULL, " +
                "PRIMARY KEY ( id ))";
        try {
            if (connection != null) {
                if (!isTableExist()) {
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.executeUpdate();
                }
                setTableOK = true;
                dataLogger.writeToMessageLog("Table " + tableName + " created");
            }
        } catch (SQLException e) {
            dataLogger.writeToMessageLog("Failed to create new table" + tableName);
            e.printStackTrace();
        }
    }

    /**
     * Populates the client table in the database from the provided text file.
     */
    @Override
    public void fillTable() {
        try {
            if (connection != null) {
                if (isTableEmpty()) {
                    Scanner sc = new Scanner(new FileReader(fileName));
                    while (sc.hasNext()) {
                        String[] clientInfo = sc.nextLine().split(";");
                        addClient(new Client(
                                Client.generateClientID(), // Create a new unique client ID number
                                clientInfo[0],      // First Name
                                clientInfo[1],      // Last Name
                                clientInfo[2],      // Address
                                clientInfo[3],      // Postal Code
                                clientInfo[4],      // Phone Number
                                clientInfo[5]));    // Client Type (R or C)
                    }
                    sc.close();
                }
                tableFillOK = true;
                dataLogger.writeToMessageLog("Table " + tableName + " populated");
            }
        } catch (FileNotFoundException e) {
            dataLogger.writeToMessageLog("File " + fileName + " Not Found!");
        } catch (Exception e) {
            dataLogger.writeToMessageLog("Something has gone terribly wrong while filling the table");
            e.printStackTrace();
        }
    }

    /**
     * Removes the table (and its contents) from the database.
     */
    @Override
    public void removeTable() {
        String sql = "DROP TABLE " + tableName;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            tableFillOK = setTableOK = false;
            Client.resetBaseID();
            dataLogger.writeToMessageLog("Remove Table: " + tableName);
        } catch (SQLException e) {
            dataLogger.writeToMessageLog("Failed to Remove Table: " + tableName);
            e.printStackTrace();
        }
    }

    /**
     * Writes the current contents of the database table to a file
     */
    @Override
    public boolean backupTable() {
        try {
            if (connection != null) {
                if (!isTableEmpty()) {
                    FileWriter fw = new FileWriter(fileName, false);
                    ArrayList<Client> clients = getClients("All", "All");
                    for (Client c : clients) {
                        fw.append(c.getFirstName() + ";" +
                                c.getLastName() + ";" +
                                c.getAddress() + ";" +
                                c.getPostalCode() + ";" +
                                c.getPhoneNum() + ";" +
                                c.getClientType() + "\n");
                    }
                    fw.flush();
                    fw.close();
                    dataLogger.writeToMessageLog("Client table backed-up by Admin");
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            dataLogger.writeToMessageLog("Error writing backup file.");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Adds a new client to the exist table.
     *
     * @param client new Client object.
     */
    @Override
    public void addClient(Client client) {
        String sql = "INSERT INTO " + tableName +
                "(id, firstname, lastname, address, postalCod, phoneNumber, clientType)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, client.getID());
            preparedStatement.setString(2, client.getFirstName());
            preparedStatement.setString(3, client.getLastName());
            preparedStatement.setString(4, client.getAddress());
            preparedStatement.setString(5, client.getPostalCode());
            preparedStatement.setString(6, client.getPhoneNum());
            preparedStatement.setString(7, client.getClientType());
            preparedStatement.executeUpdate();
            dataLogger.writeToMessageLog("Added Client: " + client + " to table: " + tableName);
        } catch (SQLException e) {
            dataLogger.writeToMessageLog("Failed to add " + client + " to table: " + tableName);
            e.printStackTrace();
        }
    }

    /**
     * Updates a clients info, or creates a new one if they do not already exist.
     *
     * @param client Client object
     */
    @Override
    public void updateClient(Client client) {
        String sql = null;

        if (isExistingClient(client.getID())) {
            sql = "UPDATE " + tableName + " SET " +
                    "firstname=?, lastname=?, address=?, postalCod=?, phoneNumber=?, clientType=? " +
                    "WHERE id=?";
        } else {
            addClient(client);
        }

        if (sql != null) {
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(7, client.getID());
                preparedStatement.setString(1, client.getFirstName());
                preparedStatement.setString(2, client.getLastName());
                preparedStatement.setString(3, client.getAddress());
                preparedStatement.setString(4, client.getPostalCode());
                preparedStatement.setString(5, client.getPhoneNum());
                preparedStatement.setString(6, client.getClientType());
                preparedStatement.executeUpdate();
                dataLogger.writeToMessageLog("Updated client " + client + " in table: " + tableName);
            } catch (SQLException e) {
                dataLogger.writeToMessageLog("Failed to update " + client + " in table: " + tableName);
                e.printStackTrace();
            }
        }
    }

    /**
     * Drops a client from the table.
     *
     * @param clientID Unique client ID number of the client to drop.
     */
    @Override
    public void deleteClient(int clientID) {
        if (isExistingClient(clientID)) {
            String sql = "DELETE FROM " + tableName + " WHERE id=?";
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, clientID);
                preparedStatement.executeUpdate();
                dataLogger.writeToMessageLog("Dropped client ID: " + clientID + " in table " + tableName);
            } catch (SQLException e) {
                dataLogger.writeToMessageLog("Failed to drop client ID: " + clientID + " in table " + tableName);
                e.printStackTrace();
            }
        }
    }

    /**
     * Determines whether a client record has changed in the database since it was last retrieved.
     *
     * @param client record in RAM / Stack
     * @return true if the client record has been updated by another user in the database,
     * otherwise false.
     */
    @Override
    public boolean isChanged(Client client) {
        try {
            String sql = "SELECT * FROM " + tableName + " WHERE id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, client.getID());
            ResultSet result = preparedStatement.executeQuery();

            Client current = null;

            if (result.next()) {
                current = new Client(
                        result.getInt("id"),
                        result.getString("firstname"),
                        result.getString("lastname"),
                        result.getString("address"),
                        result.getString("postalCod"),
                        result.getString("phoneNumber"),
                        result.getString("clientType")
                );
            }
            result.close();
            if (current == null) return true;
            if (current.equals(client)) return false;
        } catch (SQLException e) {
            dataLogger.writeToMessageLog("Database connection error while checking record status.");
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Adds the database table to the search results window (list model)
     */
    @Override
    public ArrayList<Client> searchResults(String selection, String searchParam) {
        return getClients(selection, searchParam);
    }

    /**
     * Verify that database setup has been completed successfully.
     *
     * @return true if it has, otherwise false
     */
    @Override
    public boolean isSetupOK() {
        dataLogger.writeToMessageLog("Client logged into system");
        // Check if the database has already been setup
        if (!(dbConnectOK && setTableOK && tableFillOK)) {
            dataLogger.writeToMessageLog("Database connection not yet configured");
            return false;
        }
        dataLogger.writeToMessageLog("Database checks OK");
        return true;
    }

    /**
     * Checks if the MySQL server is up
     *
     * @return true if it is, otherwise false
     */
    @Override
    public boolean isMySQLServerUp() {
        return isDBExist();
    }

    /**
     * Resets the MySQL DB connection if it has failed while the app server is still running
     */
    @Override
    public void restartDB() {
        try {
            if (!connection.isValid(3)) {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
                connection = resetConnection();
                dbConnectOK = setTableOK = tableFillOK = false;
                makeConnection(serverPath, login, password);
                dataLogger.writeToMessageLog("Connection to MySQL host reset");
            }
        } catch (SQLException e) {
            dataLogger.writeToMessageLog("Failed To Properly Close DataBase Connection!");
            e.printStackTrace();
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    // Helper Methods

    /**
     * @param connectionInfo URL of the MySQL Databse
     * @param login          Usually "root" or "admin"
     * @param password       Your MySQL DB user password
     */
    private void makeConnection(String connectionInfo, String login, String password) {
        try {
            // This is the package location for the MySQL driver that this application uses
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(connectionInfo, login, password);
            dataLogger.writeToMessageLog("Connected to: " + connectionInfo);
        } catch (SQLException | ClassNotFoundException sqlEx) {
            dataLogger.writeToMessageLog("Cannot connect to: " + connectionInfo);
            sqlEx.printStackTrace();
        } catch (Exception catchall) {
            dataLogger.writeToMessageLog("Something has gone terribly wrong...");
            catchall.printStackTrace();
        }
    }

    /***
     * Returns an ArrayList of Clients based on selection type and search parameters
     * @param selection "ClientID", "LastName" or "ClientType", see setupSearchPS()
     * @param searchParam parameters for the search, based on the selection method
     * @return ArrayList of Client objects
     */
    private ArrayList<Client> getClients(String selection, String searchParam) {
        ArrayList<Client> searchResults = new ArrayList<>();
        try {
            if (setupSearchPS(selection, searchParam)) {
                ResultSet clients = preparedStatement.executeQuery();
                if (clients.next()) {
                    do {
                        searchResults.add(new Client(
                                clients.getInt("id"),
                                clients.getString("firstname"),
                                clients.getString("lastname"),
                                clients.getString("address"),
                                clients.getString("postalCod"),
                                clients.getString("phoneNumber"),
                                clients.getString("clientType"))
                        );
                    } while (clients.next());
                }
                clients.close();
            } else {
                dataLogger.writeToMessageLog("Invalid Prepared Statement when trying to get client records");
            }
        } catch (SQLException e) {
            dataLogger.writeToMessageLog("SQL error while trying to get client records");
            e.printStackTrace();
        }
        return searchResults;
    }

    /**
     * Check if the database already exists.
     *
     * @return true if it does, otherwise false.
     */
    private boolean isDBExist() {
        try {
            ResultSet resultSet = connection.getMetaData().getCatalogs();
            while (resultSet.next()) {
                String catalogs = resultSet.getString(1);
                if (DBName.equalsIgnoreCase(catalogs)) {
                    return true;
                }
            }
        } catch (SQLException sqlEx) {
            dataLogger.writeToMessageLog("DataBase Connection Error while searching for " + DBName);
            sqlEx.printStackTrace();
        }
        return false;
    }

    /**
     * Check if the table already exists in the database.
     *
     * @return true if it does, otherwise false.
     */
    private boolean isTableExist() {
        try {
            ResultSet resultSet = connection.getMetaData().getTables(
                    null, null, "%", null);

            while (resultSet.next()) {
                String tables = resultSet.getString(3);
                if (tableName.equalsIgnoreCase(tables)) {
                    return true;
                }
            }
        } catch (SQLException sqlEx) {
            dataLogger.writeToMessageLog("DataBase Connection Error while checking if table exists");
            sqlEx.printStackTrace();
        }
        return false;
    }

    /**
     * Checks if the existing table is empty
     *
     * @return true if it is, otherwise false.
     */
    private boolean isTableEmpty() {
        try {
            String sql = "SELECT * FROM " + tableName;
            preparedStatement = connection.prepareStatement(sql);
            ResultSet clients = preparedStatement.executeQuery();
            if (clients.next()) {
                clients.close();
                return false;
            }
            clients.close();

        } catch (SQLException sqlEx) {
            dataLogger.writeToMessageLog("DataBase Connection Error while checking if table is empty");
            sqlEx.printStackTrace();
        }
        return true;
    }

    /**
     * Check if this is an existing client.
     *
     * @param clientID Unique client ID.
     * @return true if this is an existing client, otherwise false.
     */
    private boolean isExistingClient(int clientID) {
        String sql = "SELECT * FROM " + tableName + " WHERE ID=?";
        ResultSet client;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, clientID);
            client = preparedStatement.executeQuery();
            if (client.next()) {
                client.close();
                return true;
            }
            client.close();
        } catch (SQLException e) {
            dataLogger.writeToMessageLog("DataBase Connection Error while checking for existing client");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates the MySQL server connection details once a new DB is created.
     *
     * @param serverPath   URL of the mySQL server.
     * @param dataBaseName Name of the database schema.
     * @return Updated connection to the database schema.
     * @throws SQLException To be caught by the calling method.
     */
    private Connection updateConnection(String serverPath, String dataBaseName) throws SQLException {
        connectionInfo = serverPath + "/" + dataBaseName;
        return DriverManager.getConnection(connectionInfo, login, password);
    }

    /**
     * Resets the DB connection
     *
     * @return original connection path
     * @throws SQLException to be caught by calling method
     */
    private Connection resetConnection() throws SQLException {
        connectionInfo = serverPath;
        return DriverManager.getConnection(connectionInfo, login, password);
    }

    /**
     * Sets up the PreparedStatement for a search of the database.
     *
     * @return false if missing search selection type and/or search parameters.
     * @throws SQLException To be handles by the calling method.
     */
    private boolean setupSearchPS(String selection, String searchParam) throws SQLException {
        String sql;
        if (searchParam.length() != 0) {
            if (selection.equalsIgnoreCase("ClientID")) {
                int clientID = 0;
                try {
                    clientID = Integer.parseInt(searchParam);
                } catch (NumberFormatException numEx) {
                    dataLogger.writeToMessageLog("Invalid client ID number in search parameters");
                }
                if (clientID != 0) {
                    sql = "SELECT * FROM " + tableName + " WHERE id=?";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, Integer.parseInt(searchParam));
                    return true;
                }
            } else if (selection.equalsIgnoreCase("LastName")) {
                sql = "SELECT * FROM " + tableName + " WHERE lastname=?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, searchParam);
                return true;
            } else if (selection.equalsIgnoreCase("ClientType")) {
                sql = "SELECT * FROM " + tableName + " WHERE clientType=?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, searchParam);
                return true;
            } else if (selection.equalsIgnoreCase("All")) {
                sql = "SELECT * FROM " + tableName;
                preparedStatement = connection.prepareStatement(sql);
                return true;
            } else {
                dataLogger.writeToMessageLog("Invalid Client Type Provided in search parameters");
                return false;
            }
        }
        return false;
    }

}
