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

/**
 * Creates a header/separator for the contacts, so that users can be ordered in sections
 * @author shil
 *
 */
public class CustomListAdapter extends ArrayAdapter<AssembleContacts>{
    Context context;
    int layoutResourceId;   
    ArrayList<AssembleContacts> contacts= null;
    
    //TODO look into making this more adaptable so we're not constantly creating new objects in ContactsFragment
    public CustomListAdapter(Context context, int layoutResourceId, ArrayList<AssembleContacts> contacts) {
        super(context, layoutResourceId, contacts);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.contacts = contacts;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ContactsHolder holder = null;
        AssembleContacts contact = contacts.get(position);
        if(row == null)
        {
            boolean needSeparator = false;
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
           
            holder = new ContactsHolder();
            //Get both views in our layout to hide/unhide
            holder.separator =(TextView)row.findViewById(R.id.separator);
            holder.txtView = (TextView)row.findViewById(R.id.contacts_item);
            
            //First position, we need a header
            if(position == 0){
                needSeparator = true;
            }else if (position != 0){
                //If we've encountered a new letter in the beginning, we need a new header
                if (!contacts.get(position-1).getName().substring(0, 1).equalsIgnoreCase(contact.getName().substring(0,1))){
                    needSeparator = true;
                }
            }
            
            //If we need a header, we make the header/separator
            if(needSeparator){
                holder.separator.setText(contact.getName().substring(0,1).toUpperCase());
                holder.txtView.setText(contact.getName());
            //Otherwise hide it.
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
