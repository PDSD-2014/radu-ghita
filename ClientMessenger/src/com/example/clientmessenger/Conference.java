package com.example.clientmessenger;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class Conference extends Activity {

	private ListenTask lt;
	private ListView messageList;
	public static ListAdapter listAdapter;
	private Client newClient;
	private EditText editT;
	private Button send;
	private Spinner spinner1;
	public static ArrayList<String> messages;
	ArrayAdapter<String> dataAdapter;
	static List<String> list = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conference);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String serverIP = bundle.getString("serverIP");
		String serverPort = bundle.getString("serverPort");
		String username = bundle.getString("username");
		
		editT = (EditText) findViewById(R.id.editText);
		send = (Button)findViewById(R.id.send_button);
		spinner1 = (Spinner)findViewById(R.id.spinner1);
		
		messages = new ArrayList<String>();
		messageList = (ListView)findViewById(R.id.list);
		listAdapter = new ListAdapter(this, messages);
		messageList.setAdapter(listAdapter);
		
		newClient = new Client(serverIP, serverPort, username);
		
		lt = new ListenTask(newClient);
		lt.execute("");
		
		dataAdapter = new ArrayAdapter<String>(this,
		android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(dataAdapter);
		spinner1.setOnItemSelectedListener(new YourItemSelectedListener());


		
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editT.getText().toString();
                newClient.send(message);
                
 
                //refresh the list
                listAdapter.notifyDataSetChanged();
                editT.setText("");
            }
        });
	}
	
	public class YourItemSelectedListener implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	        String selected = parent.getItemAtPosition(pos).toString();
	        Log.d("spinner item", selected);
	        editT.setText("@" + selected + "");
	    }
	    
	    public void onItemClick(AdapterView<?> adapter, View view, int pos,
	            long id) {
	        // TODO Auto-generated method stub

	        String data= spinner1.getItemAtPosition(pos).toString();
	        Log.d("spinner item", data);
	        editT.setText("@" + data + "");
	    }

	    public void onNothingSelected(AdapterView parent) {
	        // Do nothing.
	    }
	}
	
}
