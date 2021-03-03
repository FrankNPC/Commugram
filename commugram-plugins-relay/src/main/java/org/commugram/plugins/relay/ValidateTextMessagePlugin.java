package org.commugram.plugins.relay;

import java.io.Serializable;

import universal.message.Message;
import universal.message.Plugin;
import universal.message.User;
import universal.message.plugin.MessagePlugin;

/**
 * just for official example, will be upgraded to cache component and persist to database later.
 * you can implement you own instance.
 * 
 * @author FrankNPC
 *
 */
public class ValidateTextMessagePlugin implements MessagePlugin {
	
	public static int ERROR_MESSAGE_OVER_LENGTH =			-MESSAGE-6;

	@Override
	public Message<Serializable> send(Message<Serializable> lastStackMessage, User user, Message<Serializable> message) {
		if (message.getType()!=MESSAGE_TEXT) { return null; }
		
		if (message.getContent().toString().length()>1024) {
        	Message<Serializable> returnMessage = new Message<Serializable>();
    		returnMessage.setType(ERROR_MESSAGE_OVER_LENGTH);
            return returnMessage;
		}
		return null;
	}

	@Override
	public Message<Serializable> send(Message<Serializable> lastStackMessage, User user, Message<Serializable>[] messages) {
    	for(Message<Serializable> message : messages) {
    		if (message.getType()!=MESSAGE_TEXT) { continue; }
    		if (message.getContent().toString().length()>1024) {
            	Message<Serializable> returnMessage = new Message<Serializable>();
        		returnMessage.setType(ERROR_MESSAGE_OVER_LENGTH);
        		return returnMessage;
    		}
    	}
		return null;
	}

	@Override
	public Message<Serializable> poll(Message<Serializable> lastStackMessage, User user, long startid, int time, int count) {
		return null;
	}

	@Override
	public Message<Serializable> poll(Message<Serializable> lastStackMessage, User user) {
		return null;
	}

	@Override
	public Message<Serializable> count(Message<Serializable> lastStackMessage, User user) {
		return null;
	}

	private Plugin plugin;
	
	@Override
	public Plugin get() {
		if (plugin!=null) {return plugin;}
		plugin = new Plugin();
		plugin.setCalling(this.getClass().getTypeName());
		return plugin;
	}
	
}
