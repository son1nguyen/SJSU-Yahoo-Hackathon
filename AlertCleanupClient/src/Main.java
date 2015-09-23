
import java.io.File;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Scanner;
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
public class Main {

    public static void main(String[] args) {

	for (int i = 0; i < 300; i++) {
	    try {
		Thread t = new Thread(new Runnable() {

		    @Override
		    public void run() {
			try {
			    int count = 0;
			    //System.out.println(InetAddress.getLocalHost());
			    File file = new File("final_alert_file.csv");
			    Scanner scanner = new Scanner(file);
			    scanner.nextLine();

			    Socket socket = new Socket(InetAddress.getLocalHost(), 4000);
			    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

			    while (scanner.hasNext()) {
				String line = scanner.nextLine();
				outputStream.writeObject(line);
				++count;
			    }
			    System.out.println(count);
			    System.out.println("Client is closing");
			    outputStream.writeObject("exit");
			    outputStream.close();
			    socket.close();
			} catch (Exception ex) {
			    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
			}
		    }
		});

		Thread.sleep(100);
		System.out.println("Client: " + i);
		t.start();
	    } catch (Exception ex) {
		Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}

    }
}
