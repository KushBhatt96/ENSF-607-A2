package View;

/*
ENSF 607 - Winter 2020 - Lab 02 - Exercise 6
Kush Bhatt & Matthew Vanderwey 2019-11-15
 */

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * This dialog provides a confirmation of the database setup
 * and also allows the user to go back and modify the setup.
 * <p>
 * Please that this dialog was created using the IntelliJ IDEA GUI form builder,
 * and therefore cannot be compiled outside of IntelliJ IDEA.
 *
 * @author Kush Bhatt & Matthew Vanderwey
 * @version 1.2
 * @since 2020-02-08
 */
public class DBStatusDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonProceed;
    private JTextField dBTF;

    //////////////////////////////////////////////////////////////////
    // Constructor

    DBStatusDialog(JDialog owner) {
        super(owner, true);
        setContentPane(contentPane);
        pack();
        setResizable(false);
        getRootPane().setDefaultButton(buttonProceed);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

    /////////////////////////////////////////////////////////////////
    // Setters

    void setdBTF(String dataBase) {
        dBTF.setText(dataBase);
    }


    //////////////////////////////////////////////////////////////////
    // Add Action Listeners

    /**
     * Add ActionListener to the "Proceed" button.
     *
     * @param e ActionListener
     */
    void addProceedActionListener(ActionListener e) {
        buttonProceed.addActionListener(e);
    }

}
