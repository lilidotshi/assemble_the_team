package com.eightbyeight.assemble;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;

import com.eightbyeight.assemble.adapters.Contacts;
import com.eightbyeight.assemble.fragments.AddNewContactsFragment;
import com.eightbyeight.assemble.fragments.AssembleFragment;
import com.eightbyeight.assemble.fragments.ContactsFragment;
import com.eightbyeight.assemble.fragments.ContactsFragment.OnArticleSelectedListener;
import com.eightbyeight.assemble.fragments.PastAssemblesFragment;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AssembleMain extends Activity implements OnArticleSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assemble_main);
        // setup action bar for tabs
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);
        ArrayList<Contacts> testContacts = new ArrayList<Contacts>();

        // Populate list with our static array of titles.
        testContacts.add(new Contacts("+Add a new friend", Contacts.REGULAR));  
        testContacts.add(new Contacts("Julie Secret Wang", Contacts.REGULAR));
        testContacts.add(new Contacts("Lane Standige", Contacts.REGULAR));
        testContacts.add(new Contacts("Lili Shi", Contacts.REGULAR));
        testContacts.add(new Contacts("Wyndham Juneau", Contacts.REGULAR));
        if (isExternalStorageWritable()){
            System.out.println("*************** YES");
        }
        File outFile = new File(Environment.getExternalStorageDirectory(), "someRandom.data");
        try {
            ObjectOutput out = new ObjectOutputStream(new FileOutputStream(outFile));
            out.writeObject(testContacts);
            out.close();
            System.out.println("Object written");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        //Main tab
        Tab tab = actionBar.newTab()
                           .setText(R.string.assemble_button_text)
                           .setTabListener(new TabListener<AssembleFragment>(
                                   this, "assemble", AssembleFragment.class));
        actionBar.addTab(tab);

        //Contacts Tab
        tab = actionBar.newTab()
                       .setText(R.string.contacts_action_bar_text)
                       .setTabListener(new TabListener<ContactsFragment>(
                               this, "contacts", ContactsFragment.class));
        
        actionBar.addTab(tab);
        
        //Past Assembles Tab
        tab = actionBar.newTab()
                       .setText(R.string.pastevents_action_bar_text)
                       .setTabListener(new TabListener<PastAssemblesFragment>(
                               this, "pastassembles", PastAssemblesFragment.class));
        actionBar.addTab(tab);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.assemble_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
            Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    @Override
    public void onArticleSelected(Uri articleUri) {
        System.out.println("HERP DERP");
    }

}
