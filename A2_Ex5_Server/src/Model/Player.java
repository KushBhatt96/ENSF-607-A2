package Model;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class represents one of the players participating in the game.
 * Each player has a name, game board, opponent, and mark (either X or O).
 * @author Kush Bhatt
 * @version 1.0
 * @since October 5, 2019
 */
public class Player implements Constants{

	/**
	 * Name of the player
	 */
	private String name;

	/**
	 * Game board the player will play on
	 */
	/**
	 * The opponent Player object
	 */
	private Player opponent;
	/**
	 * The mark this player will be using
	 */
	private char mark;
	private Socket aSocket;
	private ObjectInputStream socketIn;


	private ObjectOutputStream socketOut;
	/**
	 * Constructs the player and gives him/her a name and a mark.
	 * @param name Name of the player
	 * @param mark Mark, either X or O
	 */
	public Player(Socket aSocket) {
		this.aSocket = aSocket;
		try {
			socketOut = new ObjectOutputStream(aSocket.getOutputStream());
			socketIn = new ObjectInputStream(aSocket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void retrieveName() {     //Obtain each players name as they input it in the greeting box
		try {
			MoveData moveIn = (MoveData) socketIn.readObject();
			name = moveIn.getName();
			if(name != null) {
			System.out.println("Welcome " + name);
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initiate() {
		MoveData moveOut = new MoveData('X', mark, opponent.getName(), 0);
		try {
			socketOut.writeObject(moveOut);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The Play method checks the board first to see if anyone has won, or if the 
	 * game has ended in a tie. If not, it will proceed to allow a player to make a move
	 * by called makeMove method.
	 */

	
	
	public void play(Board theBoard) {
		try {
			//reading the current players input
			MoveData moveIn = (MoveData) socketIn.readObject();
			if(moveIn.getMakeUpdate()!=13) {
			//marking the current players input on the server board
			markTheBoard(moveIn, theBoard);
			
			//check for win --> if yes send appropriate switch case no, else proceed
			if (checkWin(theBoard)) {
				MoveData moveOut = new MoveData(11);
				moveOut.setName(getName());
				moveOut.setNewMark(opponent.getMark());
				opponent.sendMove(moveOut);
				sendMove(moveOut);
			}
			else if(checkTie(theBoard)) {
				MoveData moveOut = new MoveData(12);
				moveOut.setNewMark(opponent.getMark());
				opponent.sendMove(moveOut);
				sendMove(moveOut);
			}
			else {
			//This sends messages to both clients indicating whose turn is next
			MoveData moveOut = new MoveData(10);
			moveOut.setName(opponent.getName());
			moveOut.setNewMark(opponent.getMark());
			opponent.sendMove(moveOut);
			sendMove(moveOut);
			}
			//This sends an object to the waiting client and marks his/her board
			moveIn.setOldMark(mark);
			opponent.sendMove(moveIn);
			
			//This passes on the play to the other player
			opponent.play(theBoard);
			}
			else {
				MoveData moveOut = new MoveData(13);
				opponent.sendMove(moveOut);
			}
			
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void markTheBoard(MoveData moveIn, Board theBoard) {
		int makeUpdate = moveIn.getMakeUpdate();
		switch(makeUpdate) {
		case 1:   
			theBoard.addMark(0, 0, mark);
			theBoard.display();
			break;
		case 2:   
			theBoard.addMark(0, 1, mark);
			theBoard.display();
			break;
		case 3:   
			theBoard.addMark(0, 2, mark);
			theBoard.display();
			break;
		case 4:   
			theBoard.addMark(1, 0, mark);
			theBoard.display();
			break;
		case 5:   
			theBoard.addMark(1, 1, mark);
			theBoard.display();
			break;
		case 6:   
			theBoard.addMark(1, 2, mark);
			theBoard.display();
			break;
		case 7:   
			theBoard.addMark(2, 0, mark);
			theBoard.display();
			break;
		case 8:   
			theBoard.addMark(2, 1, mark);
			theBoard.display();
			break;
		case 9:   
			theBoard.addMark(2, 2, mark);
			theBoard.display();
			break;
		}
	}
	
	
	public void sendMove(MoveData moveOut) {
		try {
			socketOut.writeObject(moveOut);
			socketOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * The makeMove method will prompt a player to choose a row and a column in 
	 * order to make a move. After each move, the board will be displayed.
	 */
	public boolean checkWin(Board theBoard) {
		if(theBoard.checkWinner(mark)==1) {
			return true;
		}
		return false;
	}
	
	public boolean checkTie(Board theBoard) {
		if(theBoard.isFull()) {
			return true;
		}
		return false;
	}
	
	public void setOpponent(Player opponent) {
		this.opponent = opponent;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public char getMark() {
		return mark;
	}

	public void setMark(char mark) {
		this.mark = mark;
	}

	
}
