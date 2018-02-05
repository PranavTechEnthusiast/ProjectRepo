package org.newbieApps.messenger.exception;

import javax.xml.bind.annotation.XmlRootElement;

/*@XmlRootElement should be used in case you need 
 * to send exception object in json/xml format in response. 
 * To achieve this a no arg constructor of the class is mandatory.s*/
public class DataNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5132724899894157774L;
    
	/*public DataNotFoundException(){
		
	}*/
	
	public DataNotFoundException(String message){
    	super(message);
    } 
}
