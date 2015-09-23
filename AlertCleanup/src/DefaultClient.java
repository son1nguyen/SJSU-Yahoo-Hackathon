
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
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
public class DefaultClient extends Thread {

    private Socket clientSocket;
    private CentralComputer centralComputer;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private static LinkedList<String> linkedList = new LinkedList<>();
    private static Lock lock = new ReentrantLock();

    public DefaultClient(Socket clientSocket, CentralComputer centralComputer) {
	this.clientSocket = clientSocket;
	this.centralComputer = centralComputer;
    }

    @Override
    public void run() {
	if (establishConnection()) {
	    int count = 0;
	    try {
		while (servingClient()) {
		}
		System.out.println(count + " has been received");
		clientSocket.close();
	    } catch (Exception e) {
		System.err.println("Having problem with serving client");
	    }
	    //System.err.println(--ServerMachine.countClient);
	    System.out.println(linkedList.size());
	    //System.out.println(centralComputer.errorMessages.size());
	}
    }

    /* This method establishes a connection to client including inputStream and
     * outputStream*/
    private boolean establishConnection() {
	try {
	    inputStream = new ObjectInputStream(clientSocket.getInputStream());
	    outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
	    //System.out.println("Connection to a client is established");
	    return true;
	} catch (Exception e) {
	    System.err.println("Error creating InputStream and OutputStream");
	    return false;
	}
    }

    private boolean servingClient() {
	try {
	    String message = (String) inputStream.readObject();
	    if (message.equalsIgnoreCase("exit")) {
		return false;
	    } else {
		//addToList(message);
		lock.lock();
		linkedList.add(message);

		lock.unlock();
		return true;
	    }

	    //centralComputer.reportError(message);
	} catch (Exception e) {
	    System.err.println(e);
	    return false;
	}
    }
}
