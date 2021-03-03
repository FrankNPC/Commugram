package org.commugram.relay.websocket;


import java.io.Serializable;

import org.commugram.relay.service.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
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
@RequestMapping("user")
@CrossOrigin
public class UserController {

    @Autowired
	UserService userService;

    @Autowired
	CommugramMessageHandler commugramMessageHandler;
    
    //http://localhost:83/register?loginname=xsxs1a&loginpassword=xxsaxsax
    @RequestMapping("register")
	public String register(
			@RequestParam(value = "callback", defaultValue = "") String callback,
			@RequestParam(value = "loginname", defaultValue = "") String loginname,
			@RequestParam(value = "loginpassword", defaultValue = "") String loginpassword
						) {
    	User user = new User();
    	user.setLoginname(loginname);
    	user.setLoginpassword(loginpassword);
    	
    	Message<Serializable> returnMessage = userService.register(user);
		ObjectMapper mapper = new ObjectMapper();

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
    
    //http://localhost:82/login?loginname=xsxs1a&loginpassword=xxsaxsax
    
    @RequestMapping("login")
	public String login(
			@RequestParam(value = "callback", defaultValue = "") String callback,
			@RequestParam(value = "loginname", defaultValue = "") String loginname,
			@RequestParam(value = "loginpassword", defaultValue = "") String loginpassword
						) {
    	User user = new User();
    	user.setLoginname(loginname);
    	user.setLoginpassword(loginpassword);
    	
    	Message<Serializable> returnMessage = userService.login(user);
		ObjectMapper mapper = new ObjectMapper();

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

    //http://localhost:82/reset?logintoken=xsxs1a#1614262028816#ac368cf4-91da-4350-a70f-4a47297300d4
    @RequestMapping("reset")
	public String reset(
			@RequestParam(value = "callback", defaultValue = "") String callback,
			@RequestParam(value = "userid", defaultValue = "") String userid,
			@RequestParam(value = "logintoken", defaultValue = "") String logintoken
						) {
    	User user = new User();
    	user.setUserid(userid==null||userid.isEmpty()?null:Long.parseLong(userid));
    	user.setLogintoken(logintoken);
    	
    	Message<Serializable> returnMessage = userService.reset(user);
		ObjectMapper mapper = new ObjectMapper();

        try {
        	if (returnMessage.getType()==Constant.USER) {
            	commugramMessageHandler.closeWebSocketSession(user.getUserid());
        	}
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

    //http://localhost:82/reset?logintoken=xsxs1a#1614262028816#ac368cf4-91da-4350-a70f-4a47297300d4
    @RequestMapping("modify")
	public String modify(
			@RequestParam(value = "callback", defaultValue = "") String callback,
			@RequestParam(value = "userid", defaultValue = "") String userid,
			@RequestParam(value = "loginname", defaultValue = "") String loginname,
			@RequestParam(value = "loginpassword", defaultValue = "") String loginpassword,
			@RequestParam(value = "logintoken", defaultValue = "") String logintoken
						) {
    	User user = new User();
    	user.setUserid(userid==null||userid.isEmpty()?null:Long.parseLong(userid));
    	user.setLoginname(loginname);
    	user.setLoginpassword(loginpassword);
    	user.setLogintoken(logintoken);
    	
    	Message<Serializable> returnMessage = userService.modify(user);
		ObjectMapper mapper = new ObjectMapper();

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
    
    //http://localhost:82/remove?logintoken=xsxs121a#1614263016865#b492275c-d56c-4170-95b8-3ba6f2b15065
    @RequestMapping("remove")
	public String remove(
			@RequestParam(value = "callback", defaultValue = "") String callback,
			@RequestParam(value = "userid", defaultValue = "") String userid,
			@RequestParam(value = "logintoken", defaultValue = "") String logintoken
						) {
    	User user = new User();
    	user.setUserid(userid==null||userid.isEmpty()?null:Long.parseLong(userid));
    	user.setLogintoken(logintoken);
    	
    	Message<Serializable> returnMessage = userService.remove(user);
		ObjectMapper mapper = new ObjectMapper();

        try {
        	if (returnMessage.getType()==Constant.USER) {
            	commugramMessageHandler.closeWebSocketSession(user.getUserid());
        	}
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
