import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Kush Bhatt and Matthew Vanderway
 * @version 1.0
 * January 29, 2019
 * Server class in the simple client-server application
 */
public class Server {
	
	private Socket aSocket;
	private ServerSocket serverSocket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	
	/**
	 * Server constructor that initializes a new ServerSocket object with a port number.
	 */
	public Server() {
		try {
			serverSocket = new ServerSocket(8099);
		}catch (IOException e){
			System.out.println("Unable to create server socket object." + e.getMessage());
		}
	}
	
	/**
	 * Receives and stores a string input from the client. If string does not equal to null,
	 * the method checks if the string is a palindrome and returns a response to the client
	 * accordingly.
	 */
	public void checkPalindrome() {
		String line = null;
		while(true) {
			try {
				line = socketIn.readLine();
				if(line == null) {
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
				System.out.println("Unable to read in the line." + e.getMessage());
			}
		}
	}
	
	
	public static void main (String [] args) throws IOException{
		Server myServer = new Server();
		try {
			//here we are accepting the connection from a client
			myServer.aSocket = myServer.serverSocket.accept();
			System.out.println("Server is now running.");
			myServer.socketIn = new BufferedReader(new InputStreamReader(myServer.aSocket.getInputStream()));
			myServer.socketOut = new PrintWriter((myServer.aSocket.getOutputStream()), true);
			
			//next we can call a checkPalindrome function
			myServer.checkPalindrome();
			
			//lastly we will close the input and output streams
			myServer.socketIn.close();
			myServer.socketOut.close();
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
