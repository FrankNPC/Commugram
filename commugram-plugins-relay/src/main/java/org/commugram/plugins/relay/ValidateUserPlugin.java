package org.commugram.plugins.relay;

import java.io.Serializable;
import java.util.UUID;

import org.commugram.datacenter.UserInMemoryManager;

import universal.message.Message;
import universal.message.Plugin;
import universal.message.User;
import universal.message.plugin.UserPlugin;

/**
 * 
 * just for official example, will be upgraded to cache component and persist to database later.
 * you can implement you own instance.
 * 
 * @author FrankNPC
 *
 */
public class ValidateUserPlugin implements UserPlugin {

	public static int ERROR_UID_EXIST = 			-UID -1;
	public static int ERROR_UID_NOT_EXIST = 		-UID -2;

	public static int ERROR_USER_LOGINNAME = 		-USER-1;
	public static int ERROR_USER_LOGINPASSWORD =	-USER-2;
	public static int ERROR_USER_EXIST  =	 		-USER-3;
	public static int ERROR_USER_NOT_EXIST  =		-USER-4;
	public static int ERROR_USER_TOKEN  =			-USER-5;
	public static int ERROR_USER_NEW_FAIL  =	 	-USER-6;

	private static UserInMemoryManager userInMemoryManager = new UserInMemoryManager();
	
	@Override
	public Message<Serializable> register(Message<Serializable> lastStackMessage, User user) {
    	Message<Serializable> message = new Message<Serializable>();
		message.setType(USER);
		
    	if (user.getLoginname()==null||user.getLoginname().isEmpty()) {
    		message.setType(ERROR_USER_LOGINNAME);
            return message;
    	}
    	if (user.getLoginpassword()==null||user.getLoginpassword().isEmpty()) {
    		message.setType(ERROR_USER_LOGINPASSWORD);
            return message;
    	}

    	user.setLogintoken(user.getLoginname()+"#"+System.currentTimeMillis()+"#"+UUID.randomUUID().toString());
    	user = userInMemoryManager.createUser(user);
    	if (user==null) {
    		message.setType(ERROR_USER_NEW_FAIL);
            return message;
    	}
		
		message.setContent(user);
		return message;
	}

	@Override
	public Message<Serializable> login(Message<Serializable> lastStackMessage, User user) {
    	Message<Serializable> message = new Message<Serializable>();
		message.setType(USER);
		
    	if (user.getLoginname()==null||user.getLoginname().isEmpty()) {
    		message.setType(ERROR_USER_LOGINNAME);
            return message;
    	}
    	if (user.getLoginpassword()==null||user.getLoginpassword().isEmpty()) {
    		message.setType(ERROR_USER_LOGINPASSWORD);
            return message;
    	}

    	User getUser = userInMemoryManager.getUserByLoginname(user.getLoginname());
    	if (getUser==null) {
    		message.setType(ERROR_USER_NOT_EXIST);
            return message;
    	}
    	
    	if (!user.getLoginpassword().equals(getUser.getLoginpassword())) {
    		message.setType(ERROR_USER_LOGINPASSWORD);
            return message;
    	}

		message.setContent(getUser);
		return message;
	}
	
	@Override
	public Message<Serializable> modify(Message<Serializable> lastStackMessage, User user) {
    	Message<Serializable> message = new Message<Serializable>();
		message.setType(USER);
		
    	if (user.getLoginname()==null||user.getLoginname().isEmpty()) {
    		message.setType(ERROR_USER_EXIST);
            return message;
    	}

    	User getUser = userInMemoryManager.getUserByLogintoken(user.getLogintoken());
    	if (getUser==null) {
    		message.setType(ERROR_USER_NOT_EXIST);
            return message;
    	}

    	if (!user.getLoginname().equals(getUser.getLoginname())) {
    		message.setType(ERROR_USER_LOGINNAME);
            return message;
    	}
    	if (user.getLoginpassword()!=null&&!user.getLoginpassword().isEmpty()) {
        	getUser.setLoginpassword(user.getLoginpassword());
    	}
		userInMemoryManager.saveUser(getUser);

		message.setContent(user);
		return message;
	}

	@Override
	public Message<Serializable> remove(Message<Serializable> lastStackMessage, User user) {
    	Message<Serializable> message = new Message<Serializable>();
		message.setType(USER);
		
    	if (user.getLogintoken()==null||user.getLogintoken().isEmpty()) {
    		message.setType(ERROR_USER_EXIST);
            return message;
    	}

    	User removeUser = userInMemoryManager.removeUser(user);
    	if (removeUser==null) {
    		message.setType(ERROR_USER_NOT_EXIST);
            return message;
    	}
    	
		message.setContent(removeUser);
		return message;
	}

	@Override
	public Message<Serializable> reset(Message<Serializable> lastStackMessage, User user) {
    	Message<Serializable> message = new Message<Serializable>();
		message.setType(USER);
		
    	if (user.getLogintoken()==null||user.getLogintoken().isEmpty()) {
    		message.setType(ERROR_USER_EXIST);
            return message;
    	}

    	User getUser = userInMemoryManager.getUserByLogintoken(user.getLogintoken());
    	if (getUser==null) {
    		message.setType(ERROR_USER_NOT_EXIST);
            return message;
    	}

    	getUser.setLogintoken(getUser.getLoginname()+"#"+System.currentTimeMillis()+"#"+UUID.randomUUID().toString());
		userInMemoryManager.resetUser(user, getUser);

		message.setContent(getUser);
		return message;
	}

	@Override
	public Message<Serializable> get(Message<Serializable> lastStackMessage, User user) {
    	Message<Serializable> message = new Message<Serializable>();
		message.setType(USER);

    	if (user.getUserid()!=null) {
        	User getUser = userInMemoryManager.getUserByUserid(user.getUserid());
        	if (getUser!=null) {
	    		message.setContent(getUser);
	    		return message;
        	}
    	}
    	
    	if (user.getLogintoken()!=null) {
        	User getUser = userInMemoryManager.getUserByLogintoken(user.getLogintoken());
        	if (getUser!=null) {
        		message.setContent(getUser);
        		return message;
        	}
    	}

    	if (user.getLoginname()!=null) {
        	User getUser = userInMemoryManager.getUserByLoginname(user.getLoginname());
        	if (getUser!=null) {
	    		message.setContent(getUser);
	    		return message;
        	}
    	}

		message.setType(ERROR_USER_NOT_EXIST);
		return message;
	}
	
	private Plugin plugin;
	
	@Override
	public Plugin getPlugin() {
		if (plugin!=null) {return plugin;}
		plugin = new Plugin();
		plugin.setCalling(this.getClass().getTypeName());
		
		return plugin;
	}
	
}
