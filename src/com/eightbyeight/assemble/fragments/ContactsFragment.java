package com.eightbyeight.assemble.fragments;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.eightbyeight.assemble.R;
import com.eightbyeight.assemble.adapters.AddNewContactsActivity;
import com.eightbyeight.assemble.adapters.Contacts;
import com.eightbyeight.assemble.adapters.CustomListAdapter;

public class ContactsFragment extends ListFragment{
    ArrayList<Contacts> testContacts = null;
    OnArticleSelectedListener mListener;
    
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
        //Grab the list of contacts.
        ObjectInput in;
        File outFile = new File(Environment.getExternalStorageDirectory(), "someRandom.data");
        try {
            FileInputStream fileIn = new FileInputStream(outFile);
            in = new ObjectInputStream(fileIn);
            testContacts = (ArrayList<Contacts>) in.readObject();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        } 
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
        
        setListAdapter(new CustomListAdapter(getActivity(),
                R.layout.contacts_fragment_layout, testContacts));
        
       this.getListView().setClickable(true);
    }

    
    public interface OnArticleSelectedListener{
        public void onArticleSelected(Uri articleUri);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if(position ==0){
            System.out.println("add NEW CONTACT");
            Intent intent = new Intent(getActivity(),AddNewContactsActivity.class);
            startActivity(intent);
//            Fragment addNewContactsFragment = new AddNewContactsFragment();
//            FragmentTransaction transaction = getFragmentManager().beginTransaction();
//            transaction.replace(((ViewGroup)getView().getParent()).getId(), addNewContactsFragment);
//            transaction.addToBackStack(null);
//            
//            transaction.commit();
        }
    }


}
