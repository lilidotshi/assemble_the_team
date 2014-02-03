package com.eightbyeight.assemble.adapters;

import java.io.Serializable;

public class Contacts implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -6238616209103537739L;

    public static final int REGULAR = 0;
    public static final int SEPARATOR = 1;
    private String name;
    private int type;
    
    public Contacts(String name, int type){
        this.name = name;
        this.type = type;
    }   
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    public void setType(int type){
        this.type = type;
    }
    public int getType(){
        return type;
    }
}
