package View;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Model.Constants;

/**
 * boardView acts as the GUI manager for the program. It initializes the JFrame with all of its components and also 
 * any dialog boxes and events.
 * @author Kush Bhatt
 * @version 1.0
 * @Since February 10, 2020
 */
public class BoardView extends JFrame implements Constants {
	
	private JButton [][] theBoard = new JButton [3][3];
	
	private JLabel msgWindow = new JLabel("Message Window");
	private JLabel player = new JLabel("Player");
	private JLabel userName = new JLabel("User Name");
	
	private JTextArea infoArea = new JTextArea("Game information will be shown here: ");
	
	private JTextField playerText = new JTextField();
	private JTextField userNameText = new JTextField();
	
	private JTextField playerName = new JTextField();
	
	public BoardView() {
		setTitle("Tic Tac Toe");
		setSize(1000,700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(400, 300));
		JPanel westPanel = new JPanel();
		westPanel.setLayout(null);                     // To fully customize the layout, we must specify that we aren't using one
	
		int y = 20;
		for (int i = 0; i < 3;i++) {
			int x = 20;
			for(int j = 0; j < 3; j++) {
				theBoard[i][j]= new JButton(" ");
				theBoard[i][j].setEnabled(false);
				theBoard[i][j].setBounds(x,y,50,50);
				westPanel.add(theBoard[i][j]);
				theBoard[i][j].setSize(50, 50);
				x+=70;
			}
			y+=70;
		}
		westPanel.add(msgWindow);
		msgWindow.setBounds(350,0, 150,20);
		westPanel.add(player);
		player.setBounds(20,250,100,20);
		westPanel.add(userName);
		userName.setBounds(20,300,150,20);
		
		westPanel.add(playerText);
		playerText.setBounds(75,250,75,25);
		playerText.setEditable(false);
		westPanel.add(userNameText);
		userNameText.setBounds(100,300,150,25);
		userNameText.setEditable(false);

		westPanel.add(infoArea);
		infoArea.setBounds(250,30,400,100);
		infoArea.setEditable(false);
		
		add(westPanel);
		setVisible(true);
	
	}
	

//PLAYER INFORMATION SETUP -----------------------------------------------------------------------------------	
	
	/**
	 * playerName takes in the name of the player.
	 * @param playerChar
	 * @return
	 */
	public String playerName() {
		playerName.setText("");
		Object [] fields = {
				" Player Name: ", playerName,
		};
		try {
		int optionSelected = JOptionPane.showConfirmDialog(null,fields, "Enter Player Name: ", JOptionPane.OK_CANCEL_OPTION,
															JOptionPane.QUESTION_MESSAGE);
		  if (optionSelected == JOptionPane.OK_OPTION) {  
			  if(playerName.getText().equals("")) {
				  return playerName();
			  }
			  else {
			  return playerName.getText();
			  }
		  }
		  else if(optionSelected == JOptionPane.CANCEL_OPTION) {
			  return null;
		  }
		}
		catch(Exception e) {
			System.out.println("Could not get name.");
			return null;
		}
		return null;
	}
	
// Functions used by Referee to Control the Game------------------------------------------------------------------------
	
	/**
	 * Get text from the information text area.
	 * @param infoText
	 */
	public void setInfoAreaText(String infoText) {
		infoArea.setText(infoText);
	}
	
	/**
	 * Set the mark of each player.
	 * @param passedPlayerChar
	 */
	public void setLabelPlayer(char passedPlayerChar) {
		playerText.setText(String.valueOf(passedPlayerChar));
	}
	
	/**
	 * Get the mark of each player.
	 * @return
	 */
	public String getLabelPlayer() {
		return playerText.getText();
	}
	
	/**
	 * Set user name of each player in the textfield.
	 * @param passedUserName
	 */
	public void setUserName(String passedUserName) {
		userNameText.setText(passedUserName);
	}
	
	/**
	 * Show a dialog box asking for the type of player.
	 * @param name
	 * @param NUMBER_OF_TYPES
	 * @return
	 */
	public int CreatePlayer(String name, int NUMBER_OF_TYPES) {
		int playerType = Integer.parseInt(JOptionPane.showInputDialog("\nWhat type of player is " + name + "?\n" +
														"  1: Human\n" + "  2: Random Player\n"
														+ "  3: Blocking Player\n" + "  4: Smart Player\n"
				                                         +"Please enter a number in the range 1-" + NUMBER_OF_TYPES + ": "));
		return playerType;
	}
	
	/**
	 * Set the mark on the button that is clicked. 
	 * @param mark
	 * @param row
	 * @param col
	 */
	public void setButtonMark(char mark, int row, int col) {
		theBoard[row][col].setText(String.valueOf(mark));
	}
	
	/**
	 * Check if a spot is already taken.
	 */
	public void spotAlreadyTaken() {
		JOptionPane.showMessageDialog(this,"Spot already taken!");
	}
	
	/**
	 * Check if it is your turn or not
	 */
	public void notYourTurn() {
		JOptionPane.showMessageDialog(this,"It isn't your turn yet!");
	}
	
	/**
	 * Check if the opponent has quit
	 */
	public void opponentHasQuit() {
		JOptionPane.showMessageDialog(this,"Your opponent has quit the game.");
	}
	
	/**
	 * Get the mark that exists on a button or blank string.
	 * @param row
	 * @param col
	 * @return
	 */
	public String getButtonMark(int row, int col) {
		return theBoard[row][col].getText(); 
	}
	
	/**
	 * Disable all of the buttons.
	 */
	public void disableButtons() {
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				theBoard[i][j].setEnabled(false);
			}
		}
	}
	
	/**
	 * Enable all of the buttons.
	 */
	public void enableButtons() {
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				theBoard[i][j].setEnabled(true);
			}
		}
	}
	

//HUMAN PLAYER BUTTONS--------------------------------------------------------------
	
	/**
	 * Methods for registering GUI components to ActionListeners.
	 * @param listenForBtnPress
	 */
	public void addButton1(ActionListener listenForBtnPress) {
		theBoard[0][0].addActionListener(listenForBtnPress);
	}
	
	public void addButton2(ActionListener listenForBtnPress) {
		theBoard[0][1].addActionListener(listenForBtnPress);
	}
	
	public void addButton3(ActionListener listenForBtnPress) {
		theBoard[0][2].addActionListener(listenForBtnPress);
	}
	
	public void addButton4(ActionListener listenForBtnPress) {
		theBoard[1][0].addActionListener(listenForBtnPress);
	}

	public void addButton5(ActionListener listenForBtnPress) {
		theBoard[1][1].addActionListener(listenForBtnPress);
	}

	public void addButton6(ActionListener listenForBtnPress) {
		theBoard[1][2].addActionListener(listenForBtnPress);
	}
	
	public void addButton7(ActionListener listenForBtnPress) {
		theBoard[2][0].addActionListener(listenForBtnPress);
	}
	
	public void addButton8(ActionListener listenForBtnPress) {
		theBoard[2][1].addActionListener(listenForBtnPress);
	}
	
	public void addButton9(ActionListener listenForBtnPress) {
		theBoard[2][2].addActionListener(listenForBtnPress);
	}
	
}
