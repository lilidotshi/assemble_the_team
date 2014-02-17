package com.eightbyeight.assemble.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.eightbyeight.assemble.R;
import com.eightbyeight.assemble.activities.AddNewContactsActivity;
import com.eightbyeight.assemble.adapters.CustomListAdapter;
import com.eightbyeight.assemble.filehandlers.FileHandler;

/**
 * ContactsFragment is where all the user's friends are managed.
 * @author shil
 *
 */
public class ContactsFragment extends Fragment implements OnItemClickListener, OnClickListener, OnItemLongClickListener{
    FileHandler testContacts = FileHandler.getInstance();
    OnArticleSelectedListener mListener;
    ListView friends;
    TextView addNew;
    
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try {
            mListener = (OnArticleSelectedListener)activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.contacts_fragment, container, false);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       
        addNew = (TextView) getActivity().findViewById(R.id.add_a_new_friend);
        addNew.setOnClickListener(this);
        friends = (ListView) getActivity().findViewById(R.id.friends);
        friends.setAdapter(new CustomListAdapter(getActivity(),
                R.layout.contacts_fragment_layout, testContacts.getContacts()));
        friends.setOnItemClickListener(this);
        friends.setOnItemLongClickListener(this);
        friends.setClickable(true);
    }

    
    public interface OnArticleSelectedListener{
        public void onArticleSelected(Uri articleUri);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
        TextView t = (TextView) ((LinearLayout)item).findViewById(R.id.contacts_item);
        System.out.println("Position: " + position);
        System.out.println("I clicked on " + t.getText());
    }
    
    @Override
    public void onClick(View arg0) {
        Intent intent = new Intent(getActivity(),AddNewContactsActivity.class);
        getActivity().startActivity(intent);
    }
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View item, int position,
            long id) {
        final TextView t = (TextView) ((LinearLayout)item).findViewById(R.id.contacts_item);
        final int ps = position;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String confirmDelete = getActivity().getString(R.string.confirm_delete) + " " + t.getText();
        builder.setMessage(confirmDelete)
               .setTitle(R.string.confirm_delete_title);
        // Add the buttons
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       testContacts.remove(ps);
                       testContacts.saveContacts();
                       
                       CustomListAdapter adapter = new CustomListAdapter(getActivity(),
                               R.layout.contacts_fragment_layout, testContacts.getContacts());
                       friends.setAdapter(adapter);
                   }
               });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                   }
               });

        AlertDialog dialog = builder.create();
        dialog.show();
        return false;
    }

    @Override 
    public void onResume(){
        super.onResume();
        CustomListAdapter adapter = new CustomListAdapter(getActivity(),
                R.layout.contacts_fragment_layout, testContacts.getContacts());        
        friends.setAdapter(adapter);
    }

}
