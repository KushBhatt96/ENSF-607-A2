import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class DateClient {
    private Socket aSocket;
    private PrintWriter socketOut;
    private BufferedReader socketIn;
    private BufferedReader stdIn;

    public DateClient(String serverName, int portNumber) {
        try {
            aSocket = new Socket(serverName, portNumber);
            stdIn = new BufferedReader(new InputStreamReader(System.in));
            socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
            socketOut = new PrintWriter(aSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println("\nPlease check that the server is running first");
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        DateClient aClient = new DateClient("localhost", 9090);
        aClient.communicate();
    }

    public void communicate() {
        String line = "";
        String response = "";

        while (!line.equals("QUIT")) {
            try {
                System.out.println("\nPlease select an option (DATE/TIME)");
                line = stdIn.readLine(); // read line from the user (standard console)
                socketOut.println(line); // Send the line to the server
                response = socketIn.readLine(); // read response from the server
                if (response != null)
                    System.out.println(response); // print the response
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            stdIn.close();
            socketIn.close();
            socketOut.close();
        } catch (IOException e) {
            e.getStackTrace();
        }

    }

}
