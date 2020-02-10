/*
ENSF 607 - Winter 2020 - Lab 02 - Exercise 6
Kush Bhatt & Matthew Vanderwey 2020-02-07
 */

import Control.ClientManController;
import Model.DBManager;
import View.ClientManagementView;

import java.awt.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * This class acts as the main application for this client management system.
 */
public class ClientManApp {
    public static void main(String[] args) {
        // Configure Swing components in the event dispatch thread
        EventQueue.invokeLater(ClientManApp::run);
    }

    /**
     * Sets up the Model, View and Controller.
     */
    private static void run() {
        try {
            Registry registry = LocateRegistry.getRegistry("99.79.63.33");
            DBManager dbm = (DBManager) registry.lookup("DBManager");

            ClientManagementView cmv = new ClientManagementView();
            new ClientManController(cmv, dbm);
            cmv.setVisible(true);

        } catch (RemoteException | NotBoundException e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

}
