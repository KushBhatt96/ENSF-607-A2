package Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Model.Game;
import Model.MoveData;
import Model.Player;

public class ServerController {
	
	private Socket aSocket;   //a socket object
	private ServerSocket serverSocket;   //server socket object
	private ExecutorService pool;
	
	public ServerController() {
		try {
			serverSocket = new ServerSocket(9898);
			pool = Executors.newCachedThreadPool();
		}catch(IOException e) {
			System.out.println("Error in creating server socket: " + e.getMessage());
		}
	}
	
	public void runServer() {
		try {
			ArrayList<Player> PlayerPair = new ArrayList<Player>(2);
			while(true) {
				aSocket = serverSocket.accept(); 
				System.out.println("Connection accepted by server!");
		        Player player = new Player(aSocket);
		        player.retrieveName();
		        		        
	        	if(player.getName()!=null) {
			        PlayerPair.add(player);
	        	}else {
	        		System.out.println("Sorry could not add. Player was null.");
	        	}

				if(PlayerPair.size() == 2) {
					PlayerPair.get(0).setMark('X');
					PlayerPair.get(1).setMark('O');
					Game theGame = new Game(PlayerPair.get(0), PlayerPair.get(1));
					pool.execute(theGame);
					PlayerPair.clear();
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String [] args) throws IOException{
		ServerController myServer = new ServerController();
		myServer.runServer();
	}
	
}
