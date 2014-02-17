package com.eightbyeight.assemble.activities;

/**
 * Activity that launches from add new contact to pick users from your contacts
 * @author shil
 */

/*TODO: Server implementation will be phone number based, so it will only pull users that have installed the app and have
  an account tied to a phone number */
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.eightbyeight.assemble.R;
import com.eightbyeight.assemble.adapters.AssembleContacts;
import com.eightbyeight.assemble.filehandlers.FileHandler;

public class AddNewContactsActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor>, OnItemClickListener{
    //Currently does not use, but might need to later to narrow results

//    @SuppressLint("InlinedApi")
//    private static final String SELECTION =
//            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
//            Contacts.DISPLAY_NAME_PRIMARY + " LIKE ?" :
//            Contacts.DISPLAY_NAME + " LIKE ?";

    @SuppressLint("InlinedApi")
    private static final String[] PROJECTION =
            {
                Contacts._ID,
                Contacts.LOOKUP_KEY,
                Build.VERSION.SDK_INT
                        >= Build.VERSION_CODES.HONEYCOMB ?
                        Contacts.DISPLAY_NAME_PRIMARY :
                        Contacts.DISPLAY_NAME
            };
    // The column index for the _ID column
    private static final int CONTACT_ID_INDEX = 0;
    // The column index for the LOOKUP_KEY column
    private static final int LOOKUP_KEY_INDEX = 1;

    /*
     * Defines an array that contains column names to move from
     * the Cursor to the ListView.
     */
    @SuppressLint("InlinedApi")
    private final static String[] FROM_COLUMNS = {
            Build.VERSION.SDK_INT
                    >= Build.VERSION_CODES.HONEYCOMB ?
                    Contacts.DISPLAY_NAME_PRIMARY :
                    Contacts.DISPLAY_NAME
    };
    /*
     * Defines an array that contains resource ids for the layout views
     * that get the Cursor column contents. The id is pre-defined in
     * the Android framework, so it is prefaced with "android.R.id"
     */
    private final static int[] TO_IDS = {
           R.id.texty
    };
    // Define global mutable variables
    // Define a ListView object
    ListView mContactsList;
    // Define variables for the contact the user selects
    // The contact's _ID value
    long mContactId;
    // The contact's LOOKUP_KEY
    String mContactKey;
    // A content URI for the selected contact
    Uri mContactUri;
    // An adapter that binds the result Cursor to the ListView
    private SimpleCursorAdapter mCursorAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contacts_fragment);
        
        // Gets the ListView from the View list of the parent activity
        mContactsList = (ListView)findViewById(R.id.phone_contacts);
  
        // Gets a CursorAdapter
        mCursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.add_contacts_text,
                null,
                FROM_COLUMNS, TO_IDS,
                0);
        // Sets the adapter for the ListView
        mContactsList.setAdapter(mCursorAdapter);

        // Set the item click listener to be the current fragment.
        mContactsList.setAdapter(mCursorAdapter);
        mContactsList.setOnItemClickListener(this);
//        // Initializes the loader
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View item, int position, long rowID) {
        
        ContentResolver cr = getContentResolver();
        // Get the Cursor
        Cursor cursor = ((CursorAdapter) parent.getAdapter()).getCursor();
        // Move to the selected contact
        cursor.moveToPosition(position);
        // Get the _ID value
        mContactId = cursor.getLong(CONTACT_ID_INDEX);
        // Get the selected LOOKUP KEY
        mContactKey = cursor.getString(LOOKUP_KEY_INDEX);
        // Create the contact's content Uri
        mContactUri = Contacts.getLookupUri(mContactId, mContactKey);
        
        //TODO Will be removing all this in favor of a server based content provider.
        Cursor phones = cr.query(Phone.CONTENT_URI,null,Phone.CONTACT_ID + " = " + mContactId, null, null);
        
        /*
         * You can use mContactUri as the content URI for retrieving
         * the details for a contact.
         */
        AssembleContacts newContact = new AssembleContacts(((TextView)item).getText().toString());
        while(phones.moveToNext()){
            String number = phones.getString(phones.getColumnIndex(Phone.NUMBER));
            
            newContact.addPhoneNumber(number);
//            int type = phones.getInt(phones.getColumnIndex(Phone.TYPE));
//            switch(type){
//            case Phone.TYPE_HOME:
//                System.out.println()
//            }
        }
        FileHandler testContacts = FileHandler.getInstance();
        
        if(!testContacts.getContacts().contains(newContact)){
            testContacts.add(newContact);
            testContacts.saveContacts();
        }
        
        finish();

    }

    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {

        // TODO: I might want to add a "quick search" that narrows based on search text.
        // Starts the query
        return new CursorLoader(
                this,
                Contacts.CONTENT_URI,
                PROJECTION,
                null,
                null,
                Contacts.DISPLAY_NAME + " ASC"
        );

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Put the result Cursor in the adapter for the ListView
        
        mCursorAdapter.swapCursor(cursor);

        
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Delete the reference to the existing Cursor
        mCursorAdapter.swapCursor(null);
        
    }


}
