package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Model.Board;
import Model.MoveData;
import View.BoardView;

public class ViewController {
	private Board theClientBoard;
	private BoardView boardView;
	private ClientController client;
	
	public ViewController(Board theClientBoard, BoardView boardView, ClientController client) {
		this.theClientBoard = theClientBoard;
		this.boardView = boardView;
		this.client = client;
		boardView.addButton1(new BtnListener1());
		boardView.addButton2(new BtnListener2());
		boardView.addButton3(new BtnListener3());
		boardView.addButton4(new BtnListener4());
		boardView.addButton5(new BtnListener5());
		boardView.addButton6(new BtnListener6());
		boardView.addButton7(new BtnListener7());
		boardView.addButton8(new BtnListener8());
		boardView.addButton9(new BtnListener9());
		
		
		boardView.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if(client.getClientMark()!=client.getCurrentMark()) {
					boardView.notYourTurn();
				}
				else if (JOptionPane.showConfirmDialog(boardView, 
		            "Are you sure you wish to leave the game?", "Close?", 
		            JOptionPane.YES_NO_OPTION,
		            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
		        	client.sendMove(new MoveData(13));
		            System.exit(0);
		        }
		    }
		});
		boardView.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	
	class BtnListener1 implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		if(client.getClientMark()!=client.getCurrentMark()) {
			boardView.notYourTurn();
		}
		else {
		if (boardView.getButtonMark(0, 0).charAt(0) == 'X' || boardView.getButtonMark(0, 0).charAt(0) == 'O' ) {
			boardView.spotAlreadyTaken();
		}
		else {
		boardView.setButtonMark(client.getClientMark(), 0, 0);
		theClientBoard.addMark(0, 0, client.getClientMark());
		theClientBoard.display();
		MoveData move = new MoveData(1);
		client.sendMove(move);
		}
		}
	};
	}
	
	class BtnListener2 implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		if(client.getClientMark()!=client.getCurrentMark()) {
			boardView.notYourTurn();
		}
		else {
		if (boardView.getButtonMark(0, 1).charAt(0) == 'X' || boardView.getButtonMark(0, 1).charAt(0) == 'O' ) {
			boardView.spotAlreadyTaken();
		}
		else {
		boardView.setButtonMark(client.getClientMark(), 0, 1);
		theClientBoard.addMark(0, 1, client.getClientMark());
		theClientBoard.display();
		MoveData move = new MoveData(2);
		client.sendMove(move);
		}
		}
	};
	}
	
	class BtnListener3 implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		if(client.getClientMark()!=client.getCurrentMark()) {
			boardView.notYourTurn();
		}
		else {
		if (boardView.getButtonMark(0, 2).charAt(0) == 'X' || boardView.getButtonMark(0, 2).charAt(0) == 'O' ) {
			boardView.spotAlreadyTaken();
		}
		else {
		boardView.setButtonMark(client.getClientMark(), 0, 2);
		theClientBoard.addMark(0, 2, client.getClientMark());
		theClientBoard.display();
		MoveData move = new MoveData(3);
		client.sendMove(move);
		}
		}
	};
	}
	
	class BtnListener4 implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		if(client.getClientMark()!=client.getCurrentMark()) {
			boardView.notYourTurn();
		}
		else {
		if (boardView.getButtonMark(1, 0).charAt(0) == 'X' || boardView.getButtonMark(1, 0).charAt(0) == 'O' ) {
			boardView.spotAlreadyTaken();
		}
		else {
		boardView.setButtonMark(client.getClientMark(), 1, 0);
		theClientBoard.addMark(1, 0, client.getClientMark());
		theClientBoard.display();
		MoveData move = new MoveData(4);
		client.sendMove(move);
		}
		}
	};
	}
	
	class BtnListener5 implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		if(client.getClientMark()!=client.getCurrentMark()) {
			boardView.notYourTurn();
		}
		else {
		if (boardView.getButtonMark(1, 1).charAt(0) == 'X' || boardView.getButtonMark(1, 1).charAt(0) == 'O' ) {
			boardView.spotAlreadyTaken();
		}
		else {
		boardView.setButtonMark(client.getClientMark(), 1, 1);
		theClientBoard.addMark(1, 1, client.getClientMark());
		theClientBoard.display();
		MoveData move = new MoveData(5);
		client.sendMove(move);
		}
		}
	};
	}
	
	class BtnListener6 implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		if(client.getClientMark()!=client.getCurrentMark()) {
			boardView.notYourTurn();
		}
		else {
		if (boardView.getButtonMark(1, 2).charAt(0) == 'X' || boardView.getButtonMark(1, 2).charAt(0) == 'O' ) {
			boardView.spotAlreadyTaken();
		}
		else {
		boardView.setButtonMark(client.getClientMark(), 1, 2);
		theClientBoard.addMark(1, 2, client.getClientMark());
		theClientBoard.display();
		MoveData move = new MoveData(6);
		client.sendMove(move);
		}
		}
	};
	}
	
	class BtnListener7 implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		if(client.getClientMark()!=client.getCurrentMark()) {
			boardView.notYourTurn();
		}
		else {
		if (boardView.getButtonMark(2, 0).charAt(0) == 'X' || boardView.getButtonMark(2, 0).charAt(0) == 'O' ) {
			boardView.spotAlreadyTaken();
		}
		else {
		boardView.setButtonMark(client.getClientMark(), 2, 0);
		theClientBoard.addMark(2, 0, client.getClientMark());
		theClientBoard.display();
		MoveData move = new MoveData(7);
		client.sendMove(move);
		}
		}
	};
	}
	
	class BtnListener8 implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		if(client.getClientMark()!=client.getCurrentMark()) {
			boardView.notYourTurn();
		}
		else {
		if (boardView.getButtonMark(2, 1).charAt(0) == 'X' || boardView.getButtonMark(2, 1).charAt(0) == 'O' ) {
			boardView.spotAlreadyTaken();
		}
		else {
		boardView.setButtonMark(client.getClientMark(), 2, 1);
		theClientBoard.addMark(2, 1, client.getClientMark());
		theClientBoard.display();
		MoveData move = new MoveData(8);
		client.sendMove(move);
		}
		}
	};
	}
	
	class BtnListener9 implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		if(client.getClientMark()!=client.getCurrentMark()) {
			boardView.notYourTurn();
		}
		else {
		if (boardView.getButtonMark(2, 2).charAt(0) == 'X' || boardView.getButtonMark(2, 2).charAt(0) == 'O' ) {
			boardView.spotAlreadyTaken();
		}
		else {
		boardView.setButtonMark(client.getClientMark(), 2, 2);
		theClientBoard.addMark(2, 2, client.getClientMark());
		theClientBoard.display();
		MoveData move = new MoveData(9);
		client.sendMove(move);
		}
		}
	};
	}
	
}
