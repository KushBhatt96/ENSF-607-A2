package Model;

//STUDENTS SHOULD ADD CLASS COMMENTS, METHOD COMMENTS, FIELD COMMENTS 

/**
 * The Game class inherits characters from the Constants class which are then used 
 * in the game itself. Game class also contains the main method. This application
 * runs a TIC-TAC-TOE game with 2 players. 
 * 
 * @author Kush
 * @version 1.0
 * @since February 10, 2020
 */
public class Game implements Constants, Runnable {

	/**
	 * Represents the board where the game will be played
	 */
	private Board theBoard;
	private Player xPlayer;
	private Player oPlayer;
	/**
	 * Constructs a Game object with the creation of a new game board. 
	 */
    public Game(Player xPlayer, Player oPlayer) {
        theBoard  = new Board();
        this.xPlayer = xPlayer;
        this.oPlayer = oPlayer;
        xPlayer.setOpponent(oPlayer);
		oPlayer.setOpponent(xPlayer);
	}
    
	@Override
	public void run() {
	xPlayer.initiate();
	oPlayer.initiate();
	System.out.println("The game will now begin!");
	xPlayer.play(theBoard);
	}

	public Player getXPlayer() {
		return xPlayer;
	}

	public void setXPlayer(Player xPlayer) {
		this.xPlayer = xPlayer;
	}

	public Player getOPlayer() {
		return oPlayer;
	}

	public void setOPlayer(Player oPlayer) {
		this.oPlayer = oPlayer;
	}
    
}
