package com.eightbyeight.assemble;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;

import com.eightbyeight.assemble.fragments.AssembleFragment;
import com.eightbyeight.assemble.fragments.ContactsFragment;
import com.eightbyeight.assemble.fragments.ContactsFragment.OnArticleSelectedListener;
import com.eightbyeight.assemble.fragments.PastAssemblesFragment;
import com.eightbyeight.assemble.listeners.TabListener;

/**
 * AssembleMain is the main activity that launches when ASSEMBLE! starts. Constructs the three main tabs
 * and handles any data being passed through each
 * @author shil
 *
 */
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

    //TODO: Options menu for alerts in the future after server set up.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.assemble_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onArticleSelected(Uri articleUri) {
        //TODO: Currently unused, as there's no feedback coming from any of the fragments yet, consider removing
    }
    
}
