package com.example.clientmessenger;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter{
	private ArrayList<String> listItems;
	private LayoutInflater layoutInflater;
	
    public ListAdapter(Context context, ArrayList<String> listItems){
    	this.listItems = listItems; 
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listItems.size();
	}

	@Override
	public Object getItem(int index) {
		// TODO Auto-generated method stub
		return listItems.get(index);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
    public View getView(int position, View view, ViewGroup viewGroup) {
		 
        //check to see if the reused view is null or not, if is not null then reuse it
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_item, null);
        }
 
        //get the string item from the position "position" from array list to put it on the TextView
        String stringItem = listItems.get(position);
        if (stringItem != null) {
 
            TextView itemName = (TextView) view.findViewById(R.id.list_item_text_view);
 
            if (itemName != null) {
                //set the item name on the TextView
                itemName.setText(stringItem);
            }
        }
 
        //this method must return the view corresponding to the data at the specified position.
        return view;
    }
	
}
