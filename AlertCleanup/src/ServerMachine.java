
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author sonnguyen
 */
public class ServerMachine {

    public static int countClient = 0;
    public CentralComputer centralComputer;

    public ServerMachine(int port) {
	centralComputer = new CentralComputer();

	try {
	    ServerSocket server = new ServerSocket(port);
	    System.out.println("Server starts running.......");
	    Socket client;
	    while (true) {
		client = server.accept();
		System.out.println("Client " + ++countClient);
		DefaultClient handleClient = new DefaultClient(client, centralComputer);
		handleClient.start();
	    }
	} catch (IOException ex) {
	    Logger.getLogger(ServerMachine.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

}
