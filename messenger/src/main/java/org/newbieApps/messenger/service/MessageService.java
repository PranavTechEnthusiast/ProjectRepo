package org.newbieApps.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.newbieApps.messenger.database.DatabaseClass;
import org.newbieApps.messenger.exception.DataNotFoundException;
import org.newbieApps.messenger.model.Message;

public class MessageService {
	private Map<Long,Message> messages=DatabaseClass.getMessages();
	
	public MessageService(){
		messages.put(1L,new Message(1l,"HI","Pranav"));
		messages.put(2L,new Message(2l,"Hello","Pranav"));
	}
	public List<Message> getAllMessages(){
		/*Message m1 = new Message(1l,"HI","Pranav");
		Message m2 = new Message(2l,"Hello","Pranav");
		Message m3 = new Message(3l,"Good Morning","Pranav");
		
		
		List<Message> messages= new ArrayList<>();
		messages.add(m1);
		messages.add(m2);
		messages.add(m3);*/
		
		return new ArrayList<Message>(messages.values());
	}
	
	public Message getMessage(long id){
		Message message= messages.get(id);
		if(message==null){
			throw new DataNotFoundException("Message with ID="+id+" not found.");
		}
		return message;
	}
	
	public Message addMessage(Message message){
		message.setId(messages.size()+1);
		messages.put(message.getId(),message);
		return message;
	}
	
	public Message updateMessage(Message message){
		if(message.getId() <= 0){
			return null;
		}
		messages.put(message.getId(),message);
		return message;
	}
	
	public Message removeMessage(long id){
		return messages.remove(id);
		
	}
	
	public List<Message> getAllMsgsForYear(int year){
		List<Message> messagesForYear = new ArrayList<Message>();
		Calendar cal = Calendar.getInstance();
		for(Message message:messages.values()){
			cal.setTime(message.getCreated());
			if(cal.get(Calendar.YEAR) == year){
				messagesForYear.add(message);
			}
		}
		return messagesForYear;
	}
	
	public List<Message> getAllMsgsPaginated(int start,int size){
		List<Message> messagesPaginated = new ArrayList<Message>(messages.values());
		int lastIndex=start+size;
		if(lastIndex>messagesPaginated.size()) return new ArrayList<Message>();
		return messagesPaginated.subList(start,lastIndex);
	}
}
