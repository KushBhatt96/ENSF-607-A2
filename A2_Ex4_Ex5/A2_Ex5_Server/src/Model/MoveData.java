package Model;

import java.io.Serializable;

/**
 * MoveData is a serializable object that contains information about each move that a player makes.
 * The MoveData object is passed and received from the server to get move information from opponent.
 * @author Instructor
 * @version 1.0
 * @since February 10, 2020
 */
public class MoveData implements Serializable{
	
	/**
	 * serialVersionUID for the serializable object
	 */
	private static final long serialVersionUID = 1;
	
	/**
	 * Indicates the mark of the player who just made their move.
	 */
	private char oldMark; 
	
	/**
	 * Indicates the mark of the player who is going next.
	 */
	public char newMark;
	
	/**
	 * Integer corresponding to code on a switch board based on opponents move
	 */
	private int makeUpdate;
	
	/**
	 * Name of opponent who just made their move
	 */
	private String name;   
	
	/**
	 * Overloaded constructor
	 * @param name
	 */
	public MoveData(String name) {
		this.name = name;
	}

	/**
	 * Overloaded constructor
	 * @param makeUpdate
	 */
	public MoveData(int makeUpdate) {
		this.makeUpdate = makeUpdate;
	}
	
	/**
	 * Overloaded constructor
	 * @param oldMark
	 * @param newMark
	 * @param name
	 * @param makeUpdate
	 */
	public MoveData(char oldMark, char newMark, String name, int makeUpdate) {
		this.oldMark = oldMark;
		this.newMark = newMark;
		this.name = name;
		this.makeUpdate = makeUpdate;
	}
	
	public char getOldMark() {
		return oldMark;
	}
	public void setOldMark(char oldMark) {
		this.oldMark = oldMark;
	}
	
	
	public char getNewMark() {
		return newMark;
	}
	public void setNewMark(char newMark) {
		this.newMark = newMark;
	}
	
	
	public int getMakeUpdate() {
		return makeUpdate;
	}
	public void setMakeUpdate(int makeUpdate) {
		this.makeUpdate = makeUpdate;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
