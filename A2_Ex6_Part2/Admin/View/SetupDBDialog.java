package View;

/*
ENSF 607 - Winter 2020 - Lab 02 - Exercise 6
Kush Bhatt & Matthew Vanderwey 2019-11-15
 */

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * This dialog is used to setup the MySQL database connection.
 * <p>
 * Please that this dialog was created using the IntelliJ IDEA GUI form builder,
 * and therefore cannot be compiled outside of IntelliJ IDEA.
 * <p>
 * This includes:
 * URL path of the server to use
 * MySQL user login and password
 * Database schema name to use
 * Name of the client table to use
 * File name for the initial client data text file
 *
 * @author Kush Bhatt & Matthew Vanderwey
 * @version 1.2
 * @since 2020-02-08
 */
public class SetupDBDialog extends JDialog {

    private JButton connectButton;
    private JButton cancelButton;
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel buttonPanel;
    private JTextField urlTF;
    private JTextField userNameTF;
    private JTextField passwordTF;
    private JPanel connectionPanel;
    private JPanel DBPanel;
    private JLabel dbNameLabel;
    private JTextField dbNameTF;
    private JTextField tableNameTF;
    private JLabel talbeNameLabel;
    private JTextField fileNameTF;

    ////////////////////////////////////////////////////////////////////
    // Constructor

    SetupDBDialog() {
        setModal(true);
        setContentPane(mainPanel);
        pack();
        setResizable(false);
        getRootPane().setDefaultButton(connectButton);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

    }

    ///////////////////////////////////////////////////////////////////
    // Getters

    String getUrlTF() {
        return urlTF.getText();
    }

    String getUserNameTF() {
        return userNameTF.getText();
    }

    String getPasswordTF() {
        return passwordTF.getText();
    }

    String getDbNameTF() {
        return dbNameTF.getText();
    }

    String getTableNameTF() {
        return tableNameTF.getText();
    }

    String getFileNameTF() {
        return fileNameTF.getText();
    }

    ////////////////////////////////////////////////////////////////////
    // Add Action Listeners

    /**
     * Adds an ActionListener for the Connect button.
     *
     * @param listenForConnectButton ActionListener
     */
    void addConnectButtonListener(ActionListener listenForConnectButton) {
        connectButton.addActionListener(listenForConnectButton);
    }

    /**
     * Add an ActionListener to the "Cancel" Button.
     *
     * @param listenForCancelButton ActionListener
     */
    void addCancelButtonListener(ActionListener listenForCancelButton) {
        cancelButton.addActionListener(listenForCancelButton);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
