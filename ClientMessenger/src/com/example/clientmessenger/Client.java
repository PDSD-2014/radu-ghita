package com.example.clientmessenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;



public class Client
{
	private String serverMessage;
	private String serverIP;
	private String username;
	private int serverPort;
	private int isRunning = 0;
	private OnReceive listener;
	
	PrintWriter outputStream;
	BufferedReader inputStream;
	Socket socket;
	
	/*Constructor*/
	public Client(String serverIP, int serverPort, String username)
	{
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.username = username;
		this.listener = null;
	}
	
	public Client(String serverIP, String serverPort, String username)
	{
		this.serverIP = serverIP;
		this.serverPort = Integer.parseInt(serverPort);
		this.username = username;
	}
	
	/* setters*/
	public void setListener(OnReceive listener) {
		this.listener = listener;
	}
	public void setServerMessage(String msg)
	{
		this.serverMessage = msg;
	}
	
	public void setServerIP(String ip)
	{
		this.serverIP = ip;
	}
	
	public void setServerPort(int port)
	{
		this.serverPort = port;
	}
	
	/* getters */
	public String getServerMessage()
	{
		return this.serverMessage;
	}
	
	public String getServerIP()
	{
		return this.serverIP;
	}
	
	public int getServerPort()
	{
		return this.serverPort;
	}
	
	public Socket getSocket()
	{
		return this.socket;
	}
	
	public BufferedReader getInputStream()
	{
		return this.inputStream;
	}
	
	public PrintWriter getOutputStream()
	{
		return this.outputStream;
	}
	
	public void connect() throws UnknownHostException, IOException
	{
		socket = new Socket(serverIP, serverPort);
		inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		outputStream = new PrintWriter(socket.getOutputStream());
		
		if(socket.isConnected()) {
			this.send(username);
			Log.d("DEBUG", "Connected!");
		}
	}
	
	public void send(String message)
	{
		if(socket != null && socket.isConnected())
		{
			outputStream.print(message + "\r\n");
			outputStream.flush();
		}
	}
	
	public void listen() throws IOException
	{
		isRunning = 1;
		
		while(isRunning != 0) {
			String msg = inputStream.readLine();
			
			if(msg != null && listener != null) {	
				Tokenizer newTok = new Tokenizer(msg);
				if(newTok.isClientList() || newTok.isNewUser())
					for(int i=1; i<newTok.split.size(); i++)
						Conference.list.add(newTok.split.get(i));
				else
				if(newTok.isExitingUser())
					Conference.list.remove(newTok.split.get(1));
				else
					listener.msgReceived(msg);
				
				Log.d("Received!!!!!!!!!!!!!!", msg);
			}
		}
	}
	
	
	public void disconnect() throws IOException
	{
		inputStream.close();
		outputStream.close();
		socket.close();
		
		inputStream = null;
		outputStream = null;
		socket = null;
	}

	public interface OnReceive {
		public void msgReceived(String msg);
	}
}
