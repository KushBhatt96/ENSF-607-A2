package Control;

/*
ENSF 607 - Winter 2020 - Lab 02 - Exercise 6
Kush Bhatt & Matthew Vanderwey 2020-02-07
 */

import Model.DBManager;
import Model.Server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * This is the Controller class for the app server
 */
public class ServerController {

    public static void main(String[] args) {
        try {
            // Instantiate a new Database Controller (Server)
            Server server = new Server();

            // Generate a remote stub for the Server (Database Controller)
            DBManager stub = (DBManager) UnicastRemoteObject.exportObject(server,0);

            // Bind the remote object's stub in the RMI Registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("DBManager", stub);

            // Print to server console that the server is ready
            System.out.println("-------------------------");
            System.out.println("Server Ready...");
            System.out.println("-------------------------");

        } catch (RemoteException | AlreadyBoundException e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

}
