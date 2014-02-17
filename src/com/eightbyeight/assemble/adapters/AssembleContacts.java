package com.eightbyeight.assemble.adapters;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * AssembleContacts is the main object that contains all the contact information for each user
 * @author shil
 *
 */
public class AssembleContacts implements Serializable, Comparable<AssembleContacts>{

    /**
     * 
     */
    private static final long serialVersionUID = -6238616209103537739L;

    private String name;
    private ArrayList<String> phoneNumbers;
    
    //TODO: for server use later
    private String userName;
    
    /**
     * Constructor for creating a new contact
     * @param name
     */
    public AssembleContacts(String name){
        this.name = name;
        phoneNumbers = new ArrayList<String>();
    }   
    
    /**
     * Setter for the contact name
     * @param name
     */
    public void setName(String name){
        this.name = name;
    }
    
    /**
     * Getter for the contact name
     * @return name
     */
    public String getName(){
        return name;
    }
    
    /**
     * Setter for the user name
     * @param userName
     */
    public void setUserName(String userName){
        this.userName = userName;
    }
    
    /**
     * Getter for the user name
     * @return userName
     */
    public String getUserName(){
        return userName;
    }
    
    /**
     * Setter for the phone numbers 
     * @param phoneNumbers
     */
    //(Might be deprecated later with a server)
    public void setPhoneNumbers(ArrayList<String>phoneNumbers){
        this.phoneNumbers = phoneNumbers;
    }
    
    /**
     * Setter getter for the phone number 
     * @return
     */
    //(Might be deprecated later with a server)
    public ArrayList<String> getPhoneNumbers(){
        return phoneNumbers;
    }
    
    /**
     * Add a single phone number
     * @param number
     */
    //(Might be deprecated later with a server)
    public void addPhoneNumber(String number){
        phoneNumbers.add(number);
    }

    //Over write the compare to so we can easily call certain java methods
    @Override
    public int compareTo(AssembleContacts val) {
        return name.compareTo(val.getName());
    }
    
    //Overwrite the equals so that we can easily call certain java methods
    @Override
    public boolean equals(Object val) {
        if (!(val instanceof AssembleContacts))
            return false;        
        
        if (val == this)
            return true;
        
        return name.equals(((AssembleContacts)val).getName());
    }
}
