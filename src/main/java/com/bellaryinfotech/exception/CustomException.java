package com.bellaryinfotech.exception;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

 

/**
 *
 * @author khaja
 */
public class CustomException extends Exception {
    
    private String message=null;
    //Identifies type of exception - specially in case of PL/SQL exceptions, 
    //could be E for handled exceptions and U for unhandled ones
    private String type;
    /**
     * Default Constructor
     */
    public CustomException() {
        super();
    }

    /**
     * Constructor with message
     * @param message
     */
    public CustomException(String message) {
        super(message);
        this.message = message;
    }
 
    /**
     * Constructor with throwable
     * @param cause
     */
    public CustomException(Throwable cause) {
        super(cause);
    }
    
    /**
     * Constructor with message and throwable
     * @param message
     * @param cause
     */
    public CustomException(String message, Throwable cause) {
        super(message,cause,true,false);
        this.message = message;
    }
    
    @Override
    public String toString() {
        return message;
    }
 
    @Override
    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}