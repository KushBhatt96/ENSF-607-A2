package View;

/*
ENSF 607 - Winter 2020 - Lab 02 - Exercise 6
Kush Bhatt & Matthew Vanderwey 2019-11-15
 */

import Model.Client;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

/**
 * @author Kush Bhatt & Matthew Vanderwey
 * @version 2.0
 * @since 2020-02-07
 * <p>
 * This class creates the main View in the client management system
 * for Lab 08.
 * <p>
 * Please note that I built this GUI using the Swing UI Designer in IntelliJ IDEA,
 * which creates the final JFrame using the attached XML file: "ClientManagementView.form".
 * <p>
 * Unfortunately, because of this the program will not compile outside of IntelliJ IDEA.
 * Please run the provided JAR file, or PDF image, to see the final JFrame as I designed it.
 * <p>
 * Thank you!
 * MV
 */
public class ClientManagementView extends JFrame {
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    private JPanel mainPanel;
    private JPanel hostPanel;
    private JPanel searchPanel;
    private JPanel clientPanel;
    private JPanel searchClientPanel;
    private JTextField clientIDTextField;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField addressTextField;
    private JTextField postalCodeTF;
    private JTextField phoneNumTF;
    private JComboBox<String> clientTypeComboBox;
    private JLabel clientIDLabel;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel addressLabel;
    private JLabel postalCodeLabel;
    private JLabel phoneNumberLabel;
    private JLabel clientTypeLabel;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton clearButton;
    private JPanel clientButtonPanel;
    private JRadioButton clientIDRadioButton;
    private JRadioButton lastNameRadioButton;
    private JRadioButton clientTypeRadioButton;
    private JTextField searchParamTextField;
    private JButton searchButton;
    private JButton clearSearchButton;
    private JLabel selectSearchLabel;
    private JLabel searchParamLabel;
    private JList<Client> searchResultsList;
    private JScrollPane searchResultScroller;
    private ButtonGroup clientTypeRBMenu;
    private DefaultListModel<Client> searchResultsListModel;


    /**
     * Constructor for the main View.
     * <p>
     * Please note that this is abbreviated because this form was created by the
     * IntelliJ IDEA Swing UI form builder.
     */
    public ClientManagementView() {
        super("Client Management System");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        searchResultsListModel = new DefaultListModel<>();
        searchResultsList.setModel(searchResultsListModel);

        // Set search as the default button on enter/return
        JRootPane rootPane = SwingUtilities.getRootPane(searchButton);
        rootPane.setDefaultButton(searchButton);

        // ActionListener for the "Clear Search" button
        clearSearchButton.addActionListener(e -> clearSearch());
        // ActionListener for the "Clear" button
        clearButton.addActionListener(e -> clearClientInfo());

    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    // Getters and Setters

    public void setClientTypeComboBox(String clientType) {
        if (clientType.equalsIgnoreCase("R"))
            this.clientTypeComboBox.setSelectedIndex(0);
        else if (clientType.equalsIgnoreCase("C"))
            this.clientTypeComboBox.setSelectedIndex(1);
        else
            JOptionPane.showMessageDialog(this, "Invalid Client Type: " + clientType);
    }

    public int getClientIDTextField() {
        if (!clientIDTextField.getText().equals(""))
            return Integer.parseInt(clientIDTextField.getText());
        else
            return 0;
    }

    public void setClientIDTextField(int id) {
        String s = String.format("%04d", id);
        this.clientIDTextField.setText(s);
    }

    public String getFirstNameTextField() {
        return firstNameTextField.getText();
    }

    public void setFirstNameTextField(String firstName) {
        this.firstNameTextField.setText(firstName);
    }

    public String getLastNameTextField() {
        return lastNameTextField.getText();
    }

    public void setLastNameTextField(String lastName) {
        this.lastNameTextField.setText(lastName);
    }

    public String getAddressTextField() {
        return addressTextField.getText();
    }

    public void setAddressTextField(String address) {
        this.addressTextField.setText(address);
    }

    public String getPostalCodeTF() {
        return postalCodeTF.getText();
    }

    public void setPostalCodeTF(String postalCode) {
        this.postalCodeTF.setText(postalCode);
    }

    public String getPhoneNumTF() {
        return phoneNumTF.getText();
    }

    public void setPhoneNumTF(String phoneNum) {
        this.phoneNumTF.setText(phoneNum);
    }

    public String getClientTypeSelected() {
        return (String) clientTypeComboBox.getSelectedItem();
    }

    public String getSearchParamTextField() {
        return searchParamTextField.getText();
    }

    public Client getSearchResultsSelected() {
        return searchResultsList.getSelectedValue();
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Public Methods

    /**
     * Adds a new Client to the search results lists model.
     *
     * @param c new Client object to add to the model
     */
    public void appendSearchResultsList(Client c) {
        searchResultsListModel.addElement(c);
    }


    /**
     * Add a WindowListener to the main View.
     *
     * @param wl WindowListener
     */
    public void addMainWindowListener(WindowListener wl) {
        this.addWindowListener(wl);
    }

    /**
     * Add a ListSelectionListener to the Search Results List
     *
     * @param e ListSelection Listener
     */
    public void addSearchResultsListener(ListSelectionListener e) {
        searchResultsList.addListSelectionListener(e);
    }

    /**
     * Add an ActionListener to the "Search" button.
     *
     * @param e ActionListener
     */
    public void addSearchButtonListener(ActionListener e) {
        searchButton.addActionListener(e);
    }

    /**
     * Add an ActionListener to the "Save" button.
     *
     * @param e ActionListener
     */
    public void addSaveButtonListener(ActionListener e) {
        saveButton.addActionListener(e);
    }

    /**
     * Add an ActionListener to the "Delete" button.
     *
     * @param e Action Listener
     */
    public void addDeleteButtonListener(ActionListener e) {
        deleteButton.addActionListener(e);
    }

    /**
     * Public wrapper for the clearClientInfo method.
     */
    public void deleteClientInfo() {
        clearClientInfo();
    }

    /**
     * Public wrapper for the clearSearch method.
     */
    public void deleteSearchResults() {
        clearSearch();
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    // Helper Methods

    private boolean isClientIDSelected() {
        return clientIDRadioButton.isSelected();
    }

    private boolean isLastNameSelected() {
        return lastNameRadioButton.isSelected();
    }

    private boolean isClientTypeSelected() {
        return clientTypeRadioButton.isSelected();
    }

    /**
     * This method clears all the fields in the
     * "Client Information" panel.
     */
    private void clearClientInfo() {
        clientIDTextField.setText("");
        firstNameTextField.setText("");
        lastNameTextField.setText("");
        addressTextField.setText("");
        postalCodeTF.setText("");
        phoneNumTF.setText("");
        clearSearch();
    }

    /**
     * Clear all the elements from the search results list.
     */
    private void clearSearch() {
        searchResultsListModel.removeAllElements();
    }

    public String getClientRadioButton() {
        if (isClientIDSelected()) {
            return "ClientID";
        } else if (isLastNameSelected()) {
            return "LastName";
        } else if (isClientTypeSelected()) {
            return "ClientType";
        } else {
            JOptionPane.showMessageDialog(this,
                    "Please Select a type of search using the radio buttons");
        }
        return null;
    }
}



