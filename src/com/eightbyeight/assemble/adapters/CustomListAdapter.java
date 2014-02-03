package com.eightbyeight.assemble.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.eightbyeight.assemble.R;

public class CustomListAdapter extends ArrayAdapter<Contacts>{
    Context context;
    int layoutResourceId;   
    ArrayList<Contacts> contacts= null;
    
    public CustomListAdapter(Context context, int layoutResourceId, ArrayList<Contacts> contacts) {
        super(context, layoutResourceId, contacts);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.contacts = contacts;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ContactsHolder holder = null;
        Contacts contact = contacts.get(position);
        if(row == null)
        {
            boolean needSeparator = false;
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
           
            holder = new ContactsHolder();
            holder.separator =(TextView)row.findViewById(R.id.separator);
            holder.txtView = (TextView)row.findViewById(R.id.contacts_item);
            if(position == 0 && !contact.getName().equals("+Add New Contact")){
                needSeparator = true;
            }else if (position != 0){
                if (!contacts.get(position-1).getName().substring(0, 1).equals(contact.getName().substring(0,1))){
                    needSeparator = true;
                }
            }
            
            if(needSeparator){
                holder.separator.setText(contact.getName().substring(0,1).toUpperCase());
                holder.txtView.setText(contact.getName());
            }else{
                holder.separator.setVisibility(TextView.GONE);
                holder.txtView.setText(contact.getName());
            }
           
            row.setTag(holder);
        }
        else
        {
            holder = (ContactsHolder)row.getTag();
        }
       
        return row;
    }

    static class ContactsHolder{
        TextView separator;
        TextView txtView;
    }
}
