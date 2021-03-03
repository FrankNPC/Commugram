package org.commugram.plugins.relay;

import java.io.Serializable;

import org.commugram.datacenter.UserInMemoryManager;

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
public class ValidateMessagePlugin implements MessagePlugin {
	
	private static UserInMemoryManager userInMemoryManager = new UserInMemoryManager();

	public static int ERROR_MESSAGE_EMPTY =					-MESSAGE-1;
	public static int ERROR_MESSAGE_USER_NOT_MATCH =		-MESSAGE-2;

	@Override
	public Message<Serializable> send(Message<Serializable> lastStackMessage, User user, Message<Serializable> message) {
    	if (message==null||message.getContent()==null||message.getType()<0) {
        	Message<Serializable> returnMessage = new Message<Serializable>();
    		returnMessage.setType(ERROR_MESSAGE_EMPTY);
            return returnMessage;
    	}

    	User getUser = userInMemoryManager.getUserByLogintoken(user.getLogintoken());
    	if (getUser==null) {
        	Message<Serializable> returnMessage = new Message<Serializable>();
    		returnMessage.setType(ValidateUserPlugin.ERROR_USER_NOT_EXIST);
            return returnMessage;
    	}
    	if (!getUser.getUserid().equals(message.getUserid())) {
        	Message<Serializable> returnMessage = new Message<Serializable>();
    		returnMessage.setType(ERROR_MESSAGE_USER_NOT_MATCH);
            return returnMessage;
    	}
    	
    	User getTargetUser = userInMemoryManager.getUserByUserid(message.getTargetid());
    	if (getTargetUser==null) {
        	Message<Serializable> returnMessage = new Message<Serializable>();
    		returnMessage.setType(ValidateUserPlugin.ERROR_USER_NOT_EXIST);
            return returnMessage;
    	}
    	
		return null;
	}

	@Override
	public Message<Serializable> send(Message<Serializable> lastStackMessage, User user, Message<Serializable>[] messages) {
    	if (messages==null||messages==null||messages.length==0) {
        	Message<Serializable> returnMessage = new Message<Serializable>();
    		returnMessage.setType(ERROR_MESSAGE_EMPTY);
            return returnMessage;
    	}

    	User getUser = userInMemoryManager.getUserByLogintoken(user.getLogintoken());
    	if (getUser==null) {
        	Message<Serializable> returnMessage = new Message<Serializable>();
    		returnMessage.setType(ValidateUserPlugin.ERROR_USER_NOT_EXIST);
            return returnMessage;
    	}
    	
    	for(Message<Serializable> message : messages) {
        	if (message==null||message.getContent()==null||message.getType()<0) {
        		continue;
        	}
        	
        	if (!getUser.getUserid().equals(message.getUserid())) {
            	Message<Serializable> returnMessage = new Message<Serializable>();
        		returnMessage.setType(ERROR_MESSAGE_USER_NOT_MATCH);
                return returnMessage;
        	}
        	User getTargetUser = userInMemoryManager.getUserByUserid(message.getTargetid());
        	if (getTargetUser==null) {
            	Message<Serializable> returnMessage = new Message<Serializable>();
        		returnMessage.setType(ValidateUserPlugin.ERROR_USER_NOT_EXIST);
                return returnMessage;
        	}
    	}
    	
		return null;
	}

	@Override
	public Message<Serializable> poll(Message<Serializable> lastStackMessage, User user, long startid, int time, int count) {
    	User getUser = userInMemoryManager.getUserByLogintoken(user.getLogintoken());
    	if (getUser==null) {
        	Message<Serializable> returnMessage = new Message<Serializable>();
    		returnMessage.setType(ValidateUserPlugin.ERROR_USER_NOT_EXIST);
            return returnMessage;
    	}
    	if (!getUser.getUserid().equals(user.getUserid())) {
        	Message<Serializable> returnMessage = new Message<Serializable>();
    		returnMessage.setType(ERROR_MESSAGE_USER_NOT_MATCH);
            return returnMessage;
    	}
    	
    	User getTargetUser = userInMemoryManager.getUserByUserid(user.getUserid());
    	if (getTargetUser==null) {
        	Message<Serializable> returnMessage = new Message<Serializable>();
    		returnMessage.setType(ValidateUserPlugin.ERROR_USER_NOT_EXIST);
            return returnMessage;
    	}
    	
		return null;
	}

	@Override
	public Message<Serializable> poll(Message<Serializable> lastStackMessage, User user) {
    	User getUser = userInMemoryManager.getUserByLogintoken(user.getLogintoken());
    	if (getUser==null) {
        	Message<Serializable> returnMessage = new Message<Serializable>();
    		returnMessage.setType(ValidateUserPlugin.ERROR_USER_NOT_EXIST);
            return returnMessage;
    	}
    	if (!getUser.getUserid().equals(user.getUserid())) {
        	Message<Serializable> returnMessage = new Message<Serializable>();
    		returnMessage.setType(ERROR_MESSAGE_USER_NOT_MATCH);
            return returnMessage;
    	}
    	
    	User getTargetUser = userInMemoryManager.getUserByUserid(user.getUserid());
    	if (getTargetUser==null) {
        	Message<Serializable> returnMessage = new Message<Serializable>();
    		returnMessage.setType(ValidateUserPlugin.ERROR_USER_NOT_EXIST);
            return returnMessage;
    	}
    	
		return null;
	}
	
	@Override
	public Message<Serializable> count(Message<Serializable> lastStackMessage, User user) {

    	User getUser = userInMemoryManager.getUserByLogintoken(user.getLogintoken());
    	if (getUser==null) {
        	Message<Serializable> returnMessage = new Message<Serializable>();
    		returnMessage.setType(ValidateUserPlugin.ERROR_USER_NOT_EXIST);
            return returnMessage;
    	}
    	if (!getUser.getUserid().equals(user.getUserid())) {
        	Message<Serializable> returnMessage = new Message<Serializable>();
    		returnMessage.setType(ERROR_MESSAGE_USER_NOT_MATCH);
            return returnMessage;
    	}
    	
    	User getTargetUser = userInMemoryManager.getUserByUserid(user.getUserid());
    	if (getTargetUser==null) {
        	Message<Serializable> returnMessage = new Message<Serializable>();
    		returnMessage.setType(ValidateUserPlugin.ERROR_USER_NOT_EXIST);
            return returnMessage;
    	}
    	
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
