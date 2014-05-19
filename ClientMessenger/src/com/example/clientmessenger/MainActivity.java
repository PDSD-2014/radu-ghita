package com.example.clientmessenger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;


public class MainActivity extends Activity {

	protected Button connect;
	protected EditText serverIP;
	protected EditText serverPort;
	protected EditText username;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		serverIP = (EditText)findViewById(R.id.EditText1);
		serverPort = (EditText)findViewById(R.id.EditText2);
		connect = (Button)findViewById(R.id.button1);
		username = (EditText)findViewById(R.id.EditText3);
		
		username.setOnEditorActionListener(new OnEditorActionListener()
		{

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				Intent intent = new Intent(getBaseContext(), Conference.class);
				
				// pass server and port to next activity
				intent.putExtra("serverIP", serverIP.getText().toString());
				intent.putExtra("serverPort", serverPort.getText().toString());
				intent.putExtra("username", username.getText().toString());
			
				startActivity(intent);
				return true;
			}
			
		});
		
		connect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), Conference.class);
				
				// pass server and port to next activity
				intent.putExtra("serverIP", serverIP.getText().toString());
				intent.putExtra("serverPort", serverPort.getText().toString());
				intent.putExtra("username", username.getText().toString());
				startActivity(intent);
			}
			
		});
	}
}
