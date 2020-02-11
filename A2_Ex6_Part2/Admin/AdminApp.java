/*
ENSF 607 - Winter 2020 - Lab 02 - Exercise 6
Kush Bhatt & Matthew Vanderwey 2019-11-15
 */

import Control.AdminController;
import Model.DBManager;
import View.AdminView;

import javax.swing.*;
import java.awt.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * App class for Admins using our Client Management platform
 */
public class AdminApp {
    public static void main(String[] args) {
        // Configure Swing components in the event dispatch thread
        EventQueue.invokeLater(AdminApp::run);
    }

    public static void run() {
        try {
            Registry registry = LocateRegistry.getRegistry("99.79.63.33");
            DBManager dbm = (DBManager) registry.lookup("DBManager");

            AdminView adminView = new AdminView();
            new AdminController(adminView, dbm);
            adminView.setVisible(true);

        } catch (RemoteException | NotBoundException e) {
            System.err.println("Client exception: " + e.toString());
            JOptionPane.showMessageDialog(null,"App Server Down!" +
                            "\n\n Please contact Matthew Vanderwey (matthew.vanderwey@ucalgary.ca)\n",
                    "App Server Failure!", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
