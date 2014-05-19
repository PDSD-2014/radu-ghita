package com.example.clientmessenger;

import java.io.IOException;

import android.os.AsyncTask;

public class ListenTask extends AsyncTask<String,String,Void>{

	private Client client;
	
	public ListenTask(Client client) {
		this.client = client;
		this.client.setListener(new Client.OnReceive() {
			
			@Override
			public void msgReceived(String msg) {
				publishProgress(msg);
			}
		});
	}
	
	@Override
	protected Void doInBackground(String... params) {	
		try {
			client.connect();
			client.listen();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(String... params)
	{
		super.onProgressUpdate(params);
		Conference.messages.add(params[0]);
		Conference.listAdapter.notifyDataSetChanged();
	}
}	
	