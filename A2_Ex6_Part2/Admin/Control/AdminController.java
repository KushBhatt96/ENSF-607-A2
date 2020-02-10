package Control;

/*
ENSF 607 - Winter 2020 - Lab 02 - Exercise 6
Kush Bhatt & Matthew Vanderwey 2019-11-15
 */

import Model.DBManager;
import View.AdminView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class AdminController {

    private AdminView adminView;
    private DBManager dbm;

    public AdminController(AdminView adminView, DBManager dbm) {
        this.adminView = adminView;
        this.dbm = dbm;

        adminView.addGoButtonListener(new GoListener());

        // Activate a database setup dialog
        adminView.addConnectButtonListener(new ConnectListener());
        adminView.addCancelButtonListener(ae -> System.exit(0));
        adminView.setSetupDBDialogVisible(true);

    }

    /**
     * ActionListener for the Connect button on the DB connect dialog.
     */
    private class ConnectListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Establish the Database Connection
            try {
                dbm.setupConnection(
                        adminView.getUrl(),
                        adminView.getUserName(),
                        adminView.getPassword(),
                        adminView.getDBName(),
                        adminView.getTableName(),
                        adminView.getFileName());
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }

            // Create a new DB, or use the existing one
            try {
                dbm.createDB();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }

            // Create a new table, or use the existing one
            try {
                dbm.createTable();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }

            // Add the clients in clients.txt to the database
            try {
                dbm.fillTable();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }

            try {
                if (!dbm.isSetupOK())
                    JOptionPane.showMessageDialog(adminView,
                            "Database setup has failed!" +
                                    "\nPlease check that you have entered the correct parameters" +
                                    "\nand that your MySQL Server is running!",
                            "Setup Failure", JOptionPane.ERROR_MESSAGE);
                else {
                    // Add Listeners to the DBStatusDialog buttons
                    adminView.addProceedActionListener(ae -> {
                        adminView.disposeSetupDBDialog();
                        adminView.disposeDBStatusDialog();
                    });

                    // Populate the textFields in the DBStatusDialog
                    adminView.setdBTF(dbm.getConnectionInfo());

                    adminView.setDBStatusDialogVisible(true);
                }
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Listener for the "Go!" button
     */
    private class GoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (adminView.isRestoreSelected()) {
                try {
                    dbm.removeTable();
                    dbm.createTable();
                    dbm.fillTable();
                    if (dbm.isSetupOK()) {
                        JOptionPane.showMessageDialog(adminView,
                                "Database successfully restored!",
                                "Database Restored",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(adminView,
                                "Database successfully restored!",
                                "Database Restore Fail",
                                JOptionPane.WARNING_MESSAGE);
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            } else if (adminView.isBackupSelected()) {
                try {
                    if (dbm.backupTable()) {
                        JOptionPane.showMessageDialog(adminView,
                                "Backup successful!",
                                "Backup Status",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(adminView,
                                "Backup failed!",
                                "Backup Failure",
                                JOptionPane.WARNING_MESSAGE);
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
            System.exit(0);
        }
    }

}
