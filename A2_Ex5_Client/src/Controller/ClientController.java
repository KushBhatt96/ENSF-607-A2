package Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.UnknownHostException;
import Model.Board;
import Model.MoveData;
import View.BoardView;

public class ClientController {

	private Socket aSocket;
	private ObjectOutputStream socketOut;
	private ObjectInputStream socketIn;
	private char clientMark;
	private String name;
	private char currentMark;
	private static BoardView boardView;

	public ClientController(String serverName, int portNumber) {
		try {
			aSocket = new Socket(serverName, portNumber);
			socketIn = new ObjectInputStream(aSocket.getInputStream());
			socketOut = new ObjectOutputStream(aSocket.getOutputStream());
			socketOut.flush();
		}catch (UnknownHostException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	
	public void getOpponentsMove() {
		while(true) {
		try {
			System.out.println("Waiting for opponent...");
			MoveData move = (MoveData) socketIn.readObject();
			//System.out.println(move.getResponse());
			UpdateGUI(move);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		}
	}
	
	public void UpdateGUI(MoveData move) {
		int makeUpdate = move.getMakeUpdate();
		switch(makeUpdate) {
		case 0:   //corresponds to when opponent player enters their name
			setClientMark(move.getNewMark());
			setCurrentMark(move.getOldMark());
			boardView.setLabelPlayer(clientMark);
			boardView.setInfoAreaText("Player " + move.getName() + " joined the game.");
			if(clientMark == currentMark) {
				boardView.setInfoAreaText("Player " + name + " it is your turn.");
				boardView.enableButtons();
			}else {
				boardView.setInfoAreaText("It is currently player " + move.getName() + "'s turn");
				boardView.enableButtons();
			}
			break;
		case 1:
			boardView.setButtonMark(move.getOldMark(), 0, 0);
			break;
		case 2:
			boardView.setButtonMark(move.getOldMark(), 0, 1);
			break;
		case 3:
			boardView.setButtonMark(move.getOldMark(), 0, 2);
			break;
		case 4:
			boardView.setButtonMark(move.getOldMark(), 1, 0);
			break;
		case 5:
			boardView.setButtonMark(move.getOldMark(), 1, 1);
			break;
		case 6:
			boardView.setButtonMark(move.getOldMark(), 1, 2);
			break;
		case 7:
			boardView.setButtonMark(move.getOldMark(), 2, 0);
			break;
		case 8:
			boardView.setButtonMark(move.getOldMark(), 2, 1);
			break;
		case 9:
			boardView.setButtonMark(move.getOldMark(), 2, 2);
			break;
			
		case 10:
			setCurrentMark(move.getNewMark());
			if (clientMark==currentMark) {
				boardView.setInfoAreaText("Player " + name + " it is your turn.");
			}else {
				boardView.setInfoAreaText("It is currently player " + move.getName() + "'s turn");
			}
			break;
		
		case 11:
			setCurrentMark(move.getNewMark());
			boardView.setInfoAreaText("Player " + move.getName() + " Wins!");
			currentMark = clientMark;
			boardView.disableButtons();
			break;
			
		case 12:
			setCurrentMark(move.getNewMark());
			boardView.setInfoAreaText("Its a tie!");
			currentMark = clientMark;
			boardView.disableButtons();
			break;
		case 13:
			boardView.opponentHasQuit();
			System.exit(0);
			break;
		}
	}
	
	public void sendMove(MoveData move) {
		try {
			socketOut.writeObject(move);
			socketOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMove() {
		name = boardView.playerName();
		MoveData move = new MoveData(name);
		try {
			socketOut.writeObject(move);
			socketOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(name == null) {
			System.exit(0);
		}
		boardView.setUserName(name);
		boardView.setInfoAreaText("Waiting for another player to join the game...");
	}

	
	public static void main (String args []) throws IOException {
		Board theClientBoard  = new Board();
		boardView = new BoardView();
		ClientController client = new ClientController("3.18.104.206", 9898);
		//3.18.104.206
		ViewController viewController = new ViewController(theClientBoard, boardView, client);
		client.sendMove();
		client.getOpponentsMove();
	}



	public char getClientMark() {
		return clientMark;
	}



	public void setClientMark(char clientMark) {
		this.clientMark = clientMark;
	}



	public char getCurrentMark() {
		return currentMark;
	}



	public void setCurrentMark(char currentMark) {
		this.currentMark = currentMark;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}
	
}
