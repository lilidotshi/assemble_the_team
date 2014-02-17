package com.eightbyeight.assemble.filehandlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import android.os.Environment;
import android.util.Log;

import com.eightbyeight.assemble.adapters.AssembleContacts;

//TODO perhaps making this async would be better. Please to consider
/**
 * FileHandler is a singleton that handles all file reading and writing
 * so that the application doesn't have to constantly open a file
 * @author shil
 *
 */
public class FileHandler {
    
    private static FileHandler fileHandler  = null;
    
    //TODO: HashTable might be easier, especially or easy access to certain contacts
    //Loads a list of contacts
    ArrayList<AssembleContacts> file;
    
    //Create initial file
    private FileHandler(){
        file = initialize();
    }
    
    //Singleton so we don't unnecessarily access the file
    /**
     * Gets a single instance of the file
     * @return fileHandler
     */
    public static FileHandler getInstance(){
        if (fileHandler == null){
            fileHandler = new FileHandler();
        }
        
        return fileHandler;
        
    }    
    
    /**
     * Initializes the singleton the first time. It access the data we stored
     * @return testContacts
     */
    @SuppressWarnings("unchecked")
    private static ArrayList<AssembleContacts> initialize(){
        ObjectInput in;
        ArrayList<AssembleContacts> testContacts; 
        File outFile = new File(Environment.getExternalStorageDirectory(), "assemblecontacts.data");
        try {
            FileInputStream fileIn = new FileInputStream(outFile);
            in = new ObjectInputStream(fileIn);
            
            //Because we're the ones who write, if they corrupt the data, it'll throw an exception
            testContacts = (ArrayList<AssembleContacts>) in.readObject();
            in.close();
        } catch (Exception e) {
            Log.w("ASSEMBLE warning!", "Save file not found, creating new one");
            return new ArrayList<AssembleContacts>();
        }
        
        //If we don't have any data, just return a new object
        if (testContacts == null){
            return new ArrayList<AssembleContacts>();
        }

        return testContacts;
        
    }
    
    /**
     * Method to return the contacts of the singleton.
     * @return file
     */
    public ArrayList<AssembleContacts> getContacts(){
        return file;
    }
    
    /**
     * Add a new contact, and sort it. 
     * @param newContact
     */
    //TODO: Consider a more efficient method of sorting since we are inserting into a sorted array
    public void add(AssembleContacts newContact){
        file.add(newContact);
        Collections.sort(file);
    }
    
    //TODO name comparisons will be changed to phone number later.
    public void remove(int position){
        file.remove(position);
    }
    
    /**
     * Save the current contacts data
     */
    public void saveContacts(){
        if (isExternalStorageWritable()){
            Log.d("ASSEMBLE!", "External is writeable");           
            File outFile = new File(Environment.getExternalStorageDirectory(), "assemblecontacts.data");
            try {
                ObjectOutput out = new ObjectOutputStream(new FileOutputStream(outFile));
                out.writeObject(file);
                out.close();
                Log.d("ASSMEBLE!","Contacts written");
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } 
        }
    }
    
    public void savePastAssembles(){
        //TODO After moving all this to the server, start saving log info
    }
    
    
    /**
     * Method to check whether or not there's the ability to write to the SD card
     * @return boolean
     */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /** Checks if external storage is available to at least read 
     * @return boolean
     */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
            Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }


}
