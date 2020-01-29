import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	private Socket aSocket;
	private ServerSocket serverSocket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	
	public Server() {
		try {
			serverSocket = new ServerSocket(8099);
		}catch (IOException e){
			System.out.println("Unable to create server socket.");
			e.printStackTrace();
		}
	}
	
	public void checkPalindrome() {
		String line = null;
		while(true) {
			try {
				line = socketIn.readLine().toLowerCase();
				if(line.equals("QUIT")) {
					line = "Good Bye.";
					socketOut.println(line);
					break;
				}
				String reverseString = "";
				for (int i = line.length()-1; i >= 0; i--) {
					reverseString += line.charAt(i);
				}
				if(line.equals(reverseString)) {
					socketOut.println(line + " is a palindrome");
				}
				else {
					socketOut.println(line + " is not a palindrome");
				}
			} catch(IOException e) {
				System.out.println("Unable to check for palindrome.");
				e.printStackTrace();
			}
		}
	}
	
	
	public static void main (String [] args) throws IOException{
		Server myServer = new Server();
		try {
			//here we are accepting the connection from a client
			myServer.aSocket = myServer.serverSocket.accept();
			System.out.println("Connection accepted by server!");
			myServer.socketIn = new BufferedReader(new InputStreamReader(myServer.aSocket.getInputStream()));
			myServer.socketOut = new PrintWriter((myServer.aSocket.getOutputStream()), true);
			
			//next we can call an isPalindrome function
			myServer.checkPalindrome();
			
			//lastly we will close the input and output streams
			myServer.socketIn.close();
			myServer.socketOut.close();
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
