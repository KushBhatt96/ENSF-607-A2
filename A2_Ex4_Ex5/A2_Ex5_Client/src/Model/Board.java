package Model;


/**
 * The Board class creates a board, marks the board, and checks to see if the game is over.
 * The board has dimensions of 3 x 3 cells.
 * @author Kush Bhatt
 * @version 1.0
 * @since February 10, 2020
 */
public class Board implements Constants {
	/**
	 * Simple multi-array of chars to keep track of the marks on the board
	 */
	private char theBoard[][];
	/**
	 * The number of marks made on the board
	 */
	private int markCount;

	/**
	 * Constructs a Board object and assigns dimensions of 3 x 3 to the char array with empty spaces.
	 */
	public Board() {
		markCount = 0;
		theBoard = new char[3][];
		for (int i = 0; i < 3; i++) {
			theBoard[i] = new char[3];
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		}
	}

	
	/**
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public char getMark(int row, int col) {
		return theBoard[row][col];
	}

	/**
	 * Checks to see if the board is full of marks.
	 * @return
	 */
	public boolean isFull() {
		return markCount == 9;
	}

	/**
	 * Calls checkWinner method to see if player X has won the game.
	 * @return
	 */
	public boolean xWins() {
		if (checkWinner(LETTER_X) == 1)
			return true;
		else
			return false;
	}

	/**
	 * Calls checkWinner method to see if player O has won the game.
	 * @return
	 */
	public boolean oWins() {
		if (checkWinner(LETTER_X) == 1)
			return true;
		else
			return false;
	}

	/**
	 * Responsible for the game board visual. The visual will update as more moves have
	 * been made by the players. For each space, getMark will be called to check if 
	 * the position has a mark on it. 
	 */
	public void display() {
		displayColumnHeaders();
		addHyphens();
		for (int row = 0; row < 3; row++) {
			addSpaces();
			System.out.print("    row " + row + ' ');
			for (int col = 0; col < 3; col++)
				System.out.print("|  " + getMark(row, col) + "  ");
			System.out.println("|");
			addSpaces();
			addHyphens();
		}
	}

	/**
	 * Adds a character X or O to the game board char array based on given row and column 
	 * as specified by the player.
	 * @param row row chosen by player
	 * @param col column chosen by player
	 * @param mark player mark
	 */
	public void addMark(int row, int col, char mark) {
		
		theBoard[row][col] = mark;
		markCount++;
	}

	/**
	 * Clears the game board char array back to full of whitespace characters
	 */
	public void clear() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		markCount = 0;
	}

	/**
	 * Checks to see if player with specified mark has won the game or not.
	 * @param mark Mark of one of the players, X or O
	 * @return
	 */
	int checkWinner(char mark) {
		int row, col;
		int result = 0;

		for (row = 0; result == 0 && row < 3; row++) {
			int row_result = 1;
			for (col = 0; row_result == 1 && col < 3; col++)
				if (theBoard[row][col] != mark)
					row_result = 0;
			if (row_result != 0)
				result = 1;
		}

		
		for (col = 0; result == 0 && col < 3; col++) {
			int col_result = 1;
			for (row = 0; col_result != 0 && row < 3; row++)
				if (theBoard[row][col] != mark)
					col_result = 0;
			if (col_result != 0)
				result = 1;
		}

		if (result == 0) {
			int diag1Result = 1;
			for (row = 0; diag1Result != 0 && row < 3; row++)
				if (theBoard[row][row] != mark)
					diag1Result = 0;
			if (diag1Result != 0)
				result = 1;
		}
		if (result == 0) {
			int diag2Result = 1;
			for (row = 0; diag2Result != 0 && row < 3; row++)
				if (theBoard[row][3 - 1 - row] != mark)
					diag2Result = 0;
			if (diag2Result != 0)
				result = 1;
		}
		return result;
	}

	/**
	 * Displays the column headers, i.e. col 0, col 1, col 2, as part of the game board visuals
	 */
	void displayColumnHeaders() {
		System.out.print("          ");
		for (int j = 0; j < 3; j++)
			System.out.print("|col " + j);
		System.out.println();
	}

	/**
	 * Adds a row of hyphens as part of the game board visuals
	 */
	void addHyphens() {
		System.out.print("          ");
		for (int j = 0; j < 3; j++)
			System.out.print("+-----");
		System.out.println("+");
	}

	/**
	 * Adds a row of spaces as part of the game board visuals
	 */
	void addSpaces() {
		System.out.print("          ");
		for (int j = 0; j < 3; j++)
			System.out.print("|     ");
		System.out.println("|");
	}
}
