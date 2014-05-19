import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

public class Server {
	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	private static final int maxClientNo = Runtime.getRuntime().availableProcessors();
	private static final Client[] threads = new Client[maxClientNo];
	public static ArrayList<String> clientNames = new ArrayList<String>();
	
	public static void main(String[] args) throws Exception {
		Options opts = new Options();			
		opts.addOption("p", "port", true, "server port on which to listen for incoming connections");
		
		CommandLineParser parser = new GnuParser();
		CommandLine cmd = parser.parse(opts, args);

		if (!cmd.hasOption("port")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("java -jar MessengerServer ", opts);
			return;
		}
		
		int port = Integer.parseInt(cmd.getOptionValue("port"));
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Listening for incoming connections on: " + 
					serverSocket.getLocalSocketAddress().toString() + " ...");
		} 
		catch (IOException e) {
			System.err.println(e);
		}
		
	    while (true) {
	    	try {
	    		clientSocket = serverSocket.accept();
	    		int i = 0;
	    		for (i = 0; i < maxClientNo; i++) {
	    			if (threads[i] == null) {
	    				System.out.println("Client " + 
	    						clientSocket.getInetAddress().toString() + " connected !");
	    				(threads[i] = new Client(clientSocket, threads)).start();
	    				break;
	    			}
	    		}
	    		if (i == maxClientNo) {
	    			PrintStream os = new PrintStream(clientSocket.getOutputStream());
	    			os.println("Server busy, please try again later.");
	    			os.close();
	    			clientSocket.close();
	    		}
	        } 
	    	catch (IOException e) {
	          System.err.println(e);
	    	}
	    }
	}
}
