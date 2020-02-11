package Control;

/*
ENSF 607 - Winter 2020 - Lab 02 - Exercise 6
Kush Bhatt & Matthew Vanderwey 2019-11-15
 */

import Model.Client;
import Model.DBManager;
import View.ClientManagementView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is the Controller Class for the client management system.
 * <p>
 * This class controls the flow of information between the Model and View.
 *
 * @author Kush Bhatt & Matthew Vanderwey
 * @version 2.0
 * @since 2020-02-07
 */
public class ClientManController {
    /**
     * View
     */
    private ClientManagementView cmv;

    /**
     * Model
     */
    private DBManager dbm;

    /**
     * Currently selected Client
     */
    private Client selected;

    /**
     * Parametric constructor.
     *
     * @param cmv ClientManagement (View)
     * @param dbm Database Manager (Model)
     */
    public ClientManController(ClientManagementView cmv, DBManager dbm) {
        this.cmv = cmv;
        this.dbm = dbm;

        // Add a window listener for the close window action
        cmv.addMainWindowListener(new MainWindowCloseListener());

        // Add ActionListeners to the buttons on the Main View
        cmv.addSearchButtonListener(new SearchListener());
        cmv.addSaveButtonListener(new SaveListener());
        cmv.addDeleteButtonListener(new DeleteListener());

        // Add a ActionListener to the search results JList
        cmv.addSearchResultsListener(new SearchResultsListener());

        // Check that the Database is running correctly
        try {
            if (!dbm.isSetupOK()) {
                JOptionPane.showMessageDialog(cmv,"The database is not configured correctly" +
                        "\nPlease run the Admin App", "Database Setup Error",
                        JOptionPane.WARNING_MESSAGE);
                System.exit(-8);
            } else if(!dbm.isMySQLServerUp()) {
                JOptionPane.showMessageDialog(cmv,"The MySQL server is down" +
                                "\nPlease run the Admin App", "Database Setup Error",
                        JOptionPane.WARNING_MESSAGE);
                System.exit(-9);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Helper Methods (User Input Validation)

    /**
     * Validates the attributes of a client object.
     *
     * @param checkClient Client object to check
     * @return True if all parameters are valid, otherwise false
     */
    private boolean checkClientInfo(Client checkClient, ClientManagementView cmv) {
        if (checkClient.getFirstName().equals("")) {
            JOptionPane.showMessageDialog(cmv,
                    "First name cannot be blank!");
            return false;
        }

        if (checkClient.getLastName().equals("")) {
            JOptionPane.showMessageDialog(cmv,
                    "Last name cannot be blank!");
            return false;
        }

        if (checkClient.getAddress().equals("")) {
            JOptionPane.showMessageDialog(cmv,
                    "Address cannot be blank!");
            return false;
        }

        if (checkClient.getFirstName().length() > 20) {
            JOptionPane.showMessageDialog(cmv,
                    "First name is too long!\n" +
                            "\nMax 20 characters");
            return false;
        }

        if (checkClient.getLastName().length() > 20) {
            JOptionPane.showMessageDialog(cmv,
                    "Last name is too long!\n" +
                            "\nMax 20 Characters");
            return false;
        }

        if (checkClient.getAddress().length() > 50) {
            JOptionPane.showMessageDialog(cmv,
                    "Address is too long!\n" +
                            "\nMax 50 Characters");
            return false;
        }

        if (!checkPostalCode(checkClient.getPostalCode())) {
            JOptionPane.showMessageDialog(cmv,
                    "Invalid Postal Code!\n" +
                            "\nA Canadian postal code must:\n" +
                            "\tBe in the format A1A 1A1, where A is a letter adn 1 is a digit\n" +
                            "\tHave a space separating the third and forth characters\n" +
                            "\tNOT include the letters D, F, I, O, Q or U\n" +
                            "\tNOT start with either W or Z");
            return false;
        }

        if (!checkPhoneNumber(checkClient.getPhoneNum())) {
            JOptionPane.showMessageDialog(cmv,
                    "Invalid Phone Number!\n" +
                            "\nMust be in the format of: 111-111-1111");
            return false;
        }

        // If all above tests pass, return true
        return true;
    }

    /**
     * Validates a canadian postal code based on the following parameters:
     * -Is in the format A1A 1A1, where A is a letter and 1 is a digit
     * -A space separates the third and fourth characters
     * -Does not include the letters D, F, I, O, Q or U
     * -The first position does not make use of the letters W or Z
     *
     * @param postalCode Postal code to validate
     * @return True if valid postal code, otherwise false
     */
    private boolean checkPostalCode(String postalCode) {
        String regex = "^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] ?[0-9][A-Z][0-9]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(postalCode);
        return matcher.matches();
    }

    /**
     * Validates a phone number based on the format 111-111-1111
     *
     * @param phoneNum String to validate
     * @return True if valid, otherwise false
     */
    private boolean checkPhoneNumber(String phoneNum) {
        String regex = "^?([0-9]{3})?[-]?([0-9]{3})[-]?([0-9]{4})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNum);
        return matcher.matches();
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Listeners

    /**
     * WindowListener for the main View confirms that the user wants to exit,
     * and makes sure that the database connection is closed properly.
     */
    private class MainWindowCloseListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            int confirm = JOptionPane.showOptionDialog(cmv,
                    "Are You Sure You Want to Close The Application?",
                    "Exit Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    null,
                    JOptionPane.NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    /**
     * ActionListener for the Search button.
     */
    private class SearchListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            updateSearchResults();
        }
    }

    /**
     * Updates the search results pain.
     */
    private void updateSearchResults() {
        // Clear the previous search results
        cmv.deleteSearchResults();

        ArrayList<Client> searchResults = new ArrayList<>();
        try {
            searchResults = dbm.searchResults(
                    cmv.getClientRadioButton(),
                    cmv.getSearchParamTextField());
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }

        if (searchResults.isEmpty()) {
            JOptionPane.showMessageDialog(cmv,
                    "No Records Found!\n\nPlease double check your selected Search Type\n" +
                            "and Search Parameter!");
        }

        for (Client c : searchResults) {
            cmv.appendSearchResultsList(c);
        }
    }

    /**
     * ActionListener for the Search button.
     */
    private class SearchResultsListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            selected = cmv.getSearchResultsSelected();
            if (selected != null) {
                cmv.setClientIDTextField(selected.getID());
                cmv.setFirstNameTextField(selected.getFirstName());
                cmv.setLastNameTextField(selected.getLastName());
                cmv.setAddressTextField(selected.getAddress());
                cmv.setPostalCodeTF(selected.getPostalCode());
                cmv.setPhoneNumTF(selected.getPhoneNum());
                cmv.setClientTypeComboBox(selected.getClientType());
            }
        }
    }

    /**
     * ActionListener for the Save button
     */
    private class SaveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Create a new client from the info in the View
            Client modClient = new Client(
                    cmv.getClientIDTextField(),
                    cmv.getFirstNameTextField(),
                    cmv.getLastNameTextField(),
                    cmv.getAddressTextField(),
                    cmv.getPostalCodeTF(),
                    cmv.getPhoneNumTF(),
                    cmv.getClientTypeSelected());

            // Validate the information provided
            boolean validClientInfo = checkClientInfo(modClient, cmv);

            if (validClientInfo) {
                // If this is a new client, assign them a new client number
                if (modClient.getID() == 0) {
                    try {
                        modClient.setId(dbm.getNextClientID());
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    // Check if the record has changed in the database since we searched for it.
                    try {
                        if (selected != null && dbm.isChanged(selected)) {
                            int confirm = JOptionPane.showOptionDialog(cmv,
                                    "The record for Client ID: " + selected.getID() +
                                            " has changed since it was selected." +
                                            "\nDo you wish wish to still update this record?" +
                                            "\n\nIf not, please review the updated search results.",
                                    "Client Info Update Confirmation",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    null,
                                    JOptionPane.NO_OPTION);
                            if (confirm != 0) {
                                updateSearchResults();
                                return;
                            }
                        } else if (!dbm.isChanged(modClient)) {
                            JOptionPane.showMessageDialog(cmv, "The current record in the database for Client ID: "
                                    + modClient.getID() + " is identical to the current form." +
                                    "\n\nNo changes will be made to the client's record.");
                            return;
                        }
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                    // Confirm that we want to change an existing client
                    int confirm = JOptionPane.showOptionDialog(cmv,
                            "Are You Sure You Want to Update Client ID: " +
                                    modClient.getID() + "?",
                            "Client Info Update Confirmation",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            null,
                            JOptionPane.NO_OPTION);
                    if (confirm != 0) return;
                }

                try {
                    dbm.updateClient(modClient);
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }

                JOptionPane.showMessageDialog(cmv, "Client ID: " +
                        modClient.getID() + " Info Added/Updated!");

                // Clear the displays
                cmv.deleteClientInfo();
                cmv.deleteSearchResults();
            }
        }
    }

    /**
     * ActionListener for the Delete button
     */
    private class DeleteListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int clientID = cmv.getClientIDTextField();

            if (clientID == 0) {
                JOptionPane.showMessageDialog(cmv,
                        "Please first search for an existing client!");
                return;
            }

            int confirm = JOptionPane.showOptionDialog(cmv,
                    "Are you sure that you want to remove\nclient ID: " +
                            clientID + "\nfrom the database?",
                    "Delete Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    null,
                    JOptionPane.NO_OPTION);

            if (confirm == 0) {
                try {
                    dbm.deleteClient(clientID);
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
                cmv.deleteClientInfo();
                cmv.deleteSearchResults();
            }
        }
    }

}
