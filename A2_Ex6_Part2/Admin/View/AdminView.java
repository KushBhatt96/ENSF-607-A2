package View;

/*
ENSF 607 - Winter 2020 - Lab 02 - Exercise 6
Kush Bhatt & Matthew Vanderwey 2019-11-15
 */

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * @author Kush Bhatt & Matthew Vanderwey
 * @version 2.0
 * @since 2020-02-07
 * <p>
 * This class creates the main View in the client management system
 * for Lab 08.
 * <p>
 * Please note that I built this GUI using the Swing UI Designer in IntelliJ IDEA,
 * which creates the final JFrame using the attached XML file: "AdminView.form".
 * <p>
 * Unfortunately, because of this the program will not compile outside of IntelliJ IDEA.
 * Please run the provided JAR file, or PDF image, to see the final JFrame as I designed it.
 * <p>
 * Thank you!
 * MV
 */
public class AdminView extends JFrame {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 400;
    private JPanel mainPanel;
    private JPanel hostPanel;
    private JPanel filePanel;
    private JRadioButton backupRadioButton;
    private JRadioButton restoreRadioButton;
    private JButton goButton;

    private JLabel selectOpLabel;
    private JLabel subTitle;
    private JLabel mainTitle;
    private ButtonGroup operationRBMenu;

    private SetupDBDialog setupDBDialog;
    private DBStatusDialog dbStatusDialog;

    /**
     * Constructor for the main View.
     * <p>
     * Please note that this is abbreviated because this form was created by the
     * IntelliJ IDEA Swing UI form builder.
     */
    public AdminView() {
        super("Client Management System");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Set Go as the default button
        JRootPane rootPane = SwingUtilities.getRootPane(goButton);
        rootPane.setDefaultButton(goButton);

        // Create a new SetupDBDialog
        setupDBDialog = new SetupDBDialog();

        // Create a new DBStatusDialog
        dbStatusDialog = new DBStatusDialog(setupDBDialog);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    // Getters and Setters

    public String getUrl() {
        return setupDBDialog.getUrlTF();
    }

    public String getUserName() {
        return setupDBDialog.getUserNameTF();
    }

    public String getPassword() {
        return setupDBDialog.getPasswordTF();
    }

    public String getDBName() {
        return setupDBDialog.getDbNameTF();
    }

    public String getTableName() {
        return setupDBDialog.getTableNameTF();
    }

    public String getFileName() {
        return setupDBDialog.getFileNameTF();
    }

    public void setSetupDBDialogVisible(boolean set) {
        setupDBDialog.setVisible(set);
    }

    public void setDBStatusDialogVisible(boolean set) {
        dbStatusDialog.setVisible(set);
    }

    public void disposeSetupDBDialog() {
        setupDBDialog.dispose();
    }

    public void disposeDBStatusDialog() {
        dbStatusDialog.dispose();
    }

    public void setdBTF(String dataBase) {
        dbStatusDialog.setdBTF(dataBase);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Public Methods

    /**
     * Add ActionListener to the "Connect" button on the SetupDBDialog.
     *
     * @param e ActionListener
     */
    public void addConnectButtonListener(ActionListener e) {
        setupDBDialog.addConnectButtonListener(e);
    }

    /**
     * Add ActionListener to the "Cancel" button on the SetupDBDialog.
     *
     * @param e ActionListener
     */
    public void addCancelButtonListener(ActionListener e) {
        setupDBDialog.addCancelButtonListener(e);
    }

    /**
     * Add ActionListener to the "Proceed" button on the DBStatusDialog.
     *
     * @param e ActionListener
     */
    public void addProceedActionListener(ActionListener e) {
        dbStatusDialog.addProceedActionListener(e);
    }

    /**
     * Add an ActionListener to the "Go!" button.
     *
     * @param e ActionListener
     */
    public void addGoButtonListener(ActionListener e) {
        goButton.addActionListener(e);
    }

    /**
     *
     * @return true if Backup radio button is selected
     */
    public boolean isBackupSelected() {
        return backupRadioButton.isSelected();
    }

    /**
     *
     * @return true if Restore radio button is selected
     */
    public boolean isRestoreSelected() {
        return restoreRadioButton.isSelected();
    }

}



