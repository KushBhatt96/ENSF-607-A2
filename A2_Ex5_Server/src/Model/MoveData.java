package Model;

import java.io.Serializable;

public class MoveData implements Serializable{
	
	private static final long serialVersionUID = 1;
	private char oldMark;    //indicates mark of opponent player who just made a move
	public char newMark;    //indicates the mark of the current player whose turn it is 
	private int makeUpdate;     //integer corresponding to code on a switch board based on opponents move
	private String name;   //name of opponent who just made their move
	
	
	public MoveData(String name) {
		this.name = name;
	}
	
	public MoveData(int makeUpdate) {
		this.makeUpdate = makeUpdate;
	}
	
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
