package com.example.clientmessenger;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Tokenizer {
	public String message;
	public ArrayList<String> split;
	
	public Tokenizer(String message) {
		this.message = message;
		
		this.split = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(this.message, " ");
		while(st.hasMoreTokens())
			this.split.add(st.nextToken());
	}
	
	public boolean isClientList() {
		if(split.contains("ClientList:"))
			return true;
		return false;
	}
	public boolean isNewUser() {
		if(split.contains("NewUser:"))
			return true;
		return false;
	}
	public boolean isExitingUser() {
		if(split.contains("ExitingUser:"))
			return true;
		return false;
	}

}
