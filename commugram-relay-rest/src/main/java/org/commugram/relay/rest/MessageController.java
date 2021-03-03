package org.commugram.relay.rest;


import java.io.IOException;
import java.io.Serializable;

import org.commugram.relay.service.base.MessageService;
import org.commugram.relay.service.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import universal.message.Message;
import universal.message.User;
import universal.message.plugin.Constant;

/**
 * The controller is to deliver the packed object to plugins.
 * no intercept, no validate.
 * @author FrankNPC
 *
 */
@RestController
@RequestMapping("message")
@CrossOrigin
public class MessageController {

    @Autowired
	MessageService messageService;

    @Autowired
	UserService userService;

//	public Message<Serializable> sent(User user, Message<Serializable> message);
//	public Message<Serializable> sent(User user, Message[] messageList);
//	public Message<Serializable> poll(User user, int count);
//	public Message<Serializable> poll(User user);
//	public Message<Serializable> count(User user);

    @RequestMapping("send")
	public String send(
			@RequestParam(value = "callback", defaultValue = "") String callback,
			@RequestParam(value = "logintoken", defaultValue = "") String logintoken,
			@RequestParam(value = "messagestrings", defaultValue = "") String messagestrings
						) {
		ObjectMapper mapper = new ObjectMapper();
		
    	User user = new User();
    	user.setLogintoken(logintoken);
    	Message<Serializable> returnMessage = userService.get(user);
    	
    	if (Constant.USER==returnMessage.getType()) {
    		user = (User) returnMessage.getContent();
	    	user.setLogintoken(logintoken);

			try {
		         Message<Serializable>[] messages = mapper.readValue(messagestrings, 
		                                               new TypeReference<Message<String>[]>() {});
	        	returnMessage = messageService.send(user, messages);
			} catch (IOException e) {
				returnMessage.setType(Constant.ERROR_PARSE);
				e.printStackTrace();
			}
    	}
    	
        try {
        	if (callback.isEmpty()) {
        		return mapper.writeValueAsString(returnMessage);
        	}else {
    			return callback+"("+mapper.writeValueAsString(returnMessage)+")";
        	}
		} catch (JsonProcessingException e) {
			returnMessage = new Message<Serializable>();
			returnMessage.setType(Constant.ERROR_PARSE);
			e.printStackTrace();
		}
        return returnMessage.toString();
    }

    @RequestMapping("poll")
	public String poll(
			@RequestParam(value = "callback", defaultValue = "") String callback,
			@RequestParam(value = "logintoken", defaultValue = "") String logintoken,
			@RequestParam(value = "startid", defaultValue = "0") String startidstring,
			@RequestParam(value = "time", defaultValue = "0") String timestring,
			@RequestParam(value = "count", defaultValue = "0") String countstring
						) {
		ObjectMapper mapper = new ObjectMapper();
		
    	User user = new User();
    	user.setLogintoken(logintoken);
    	Message<Serializable> returnMessage = userService.get(user);
    	
    	if (Constant.USER==returnMessage.getType()) {
    		user = (User) returnMessage.getContent();
	    	user.setLogintoken(logintoken);

	        try {
		    	long startid = Long.parseLong(startidstring);
		    	int time = Integer.parseInt(timestring);
		    	int count = Integer.parseInt(countstring);
		    	
	    		returnMessage = messageService.poll(user, startid, time, count);
			} catch (NumberFormatException e) {
				returnMessage = new Message<Serializable>();
				returnMessage.setType(Constant.ERROR_PARSE);
				e.printStackTrace();
			}
    	}

        try {
        	if (callback.isEmpty()) {
        		return mapper.writeValueAsString(returnMessage);
        	}else {
    			return callback+"("+mapper.writeValueAsString(returnMessage)+")";
        	}
		} catch (JsonProcessingException e) {
			returnMessage = new Message<Serializable>();
			returnMessage.setType(Constant.ERROR_PARSE);
			e.printStackTrace();
		}
        return returnMessage.toString();
    }

    @RequestMapping("count")
	public String count(
			@RequestParam(value = "callback", defaultValue = "") String callback,
			@RequestParam(value = "logintoken", defaultValue = "") String logintoken
						) {
		ObjectMapper mapper = new ObjectMapper();
		
    	User user = new User();
    	user.setLogintoken(logintoken);
    	Message<Serializable> returnMessage = userService.get(user);
    	
    	if (Constant.USER==returnMessage.getType()) {
    		user = (User) returnMessage.getContent();
	    	user.setLogintoken(logintoken);
    		returnMessage = messageService.count(user);
    	}

        try {
        	if (callback.isEmpty()) {
        		return mapper.writeValueAsString(returnMessage);
        	}else {
    			return callback+"("+mapper.writeValueAsString(returnMessage)+")";
        	}
		} catch (JsonProcessingException e) {
			returnMessage = new Message<Serializable>();
			returnMessage.setType(Constant.ERROR_PARSE);
			e.printStackTrace();
		}
        return returnMessage.toString();
    }
    
    

}
