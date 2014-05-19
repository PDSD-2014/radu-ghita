import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class Client extends Thread {
	private Socket clientSocket;
	private final Client[] threads;
	private DataInputStream in;
	private PrintStream out;
	private String clientName;

	public Client(Socket clientSocket, Client[] threads) throws Exception {
		this.clientSocket = clientSocket;
	    this.threads = threads;
	    this.in = new DataInputStream(this.clientSocket.getInputStream());
	    this.out = new PrintStream(this.clientSocket.getOutputStream());
	    this.clientName = "#";
	}

	public void run() {
		int maxClientNo = this.threads.length;
		
	    try {
	      // registering client
	      String name = in.readLine().trim();
	      System.out.println("Received name: " + name); 
	
	      out.println("Welcome <" + name
	          + "> !\nTo exit the chatroom enter /quit in a new line.");
	      
	      synchronized (this) {
	        for (int i = 0; i < maxClientNo; i++) {
	          if (threads[i] != null && threads[i] == this) {
	            clientName = "@" + name;
	            threads[i].out.println("ClientList: " + Tokenizer.buildString(Server.clientNames));
	            break;
	          }
	        }
	        
	        Server.clientNames.add(name);
	        
	        for (int i = 0; i < maxClientNo; i++) {
	          if (threads[i] != null && threads[i] != this) {
	            threads[i].out.println("*** User <" + name
	                + "> has joined the chatroom ! ***");
	            threads[i].out.println("NewUser: " + name);
	          }
	        }
	      }
	      
	      // starting the conversation
	      while (true) {
	        String line = in.readLine();
	        System.out.println("Received msg: " + line);
	        
	        // quit message
	        if (line.startsWith("/quit"))
	          break;
	        
	        // private message
	        if (line.startsWith("@")) {
	          String[] words = line.split("\\s", 2);
	          if (words.length > 1 && words[1] != null) {
	            words[1] = words[1].trim();
	            if (!words[1].isEmpty()) {
	              synchronized (this) {
	                for (int i = 0; i < maxClientNo; i++) {
	                  if (threads[i] != null && threads[i] != this
	                      && threads[i].clientName != null
	                      && threads[i].clientName.equals(words[0])) {
	                    threads[i].out.println("[p]<" + name + "> " + words[1]);
	                    this.out.println("[p]<" + name + "> " + words[1]);
	                    break;
	                  }
	                }
	              }
	            }
	          }
	        } 
	        else {
	          // public message
	          synchronized (this) {
	            for (int i = 0; i < maxClientNo; i++) {
	              if (threads[i] != null && threads[i].clientName != null) {
	                threads[i].out.println("<" + name + "> " + line);
	              }
	            }
	          }
	        }
	      }
	      
	      // current user exited
	      synchronized (this) {
	        for (int i = 0; i < maxClientNo; i++) {
	          if (threads[i] != null && threads[i] != this
	              && threads[i].clientName != null) {
	            threads[i].out.println("*** User <" + name
	                + "> has left the chatroom ! ***");
	            threads[i].out.println("ExitingUser: " + name);
	          }
	        }
	        Server.clientNames = Tokenizer.removeName(Server.clientNames, name);
	        for(int i=0; i<Server.clientNames.size(); i++)
	        	System.out.println(Server.clientNames.get(i));
	      }
	      
	      out.println("*** Goodbye <" + name + "> ! ***");
	      
	      synchronized (this) {
	        for (int i = 0; i < maxClientNo; i++) {
	          if (threads[i] == this) {
	        	  threads[i] = null;
	          }
	        }
	      }
	      
	      //closing streams
	      in.close();
	      out.close();
	      clientSocket.close();
	    } 
	    catch (IOException e) {
	    	System.err.println(e);
	    }
	}
}
