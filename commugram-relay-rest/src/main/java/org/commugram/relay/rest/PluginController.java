package org.commugram.relay.rest;


import java.io.IOException;
import java.io.Serializable;

import org.commugram.relay.service.base.PluginService;
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
@RequestMapping("plugin")
@CrossOrigin
public class PluginController {

    @Autowired
    PluginService pluginService;

    @Autowired
	UserService userService;

    @RequestMapping("config")
	public String config(
			@RequestParam(value = "callback", defaultValue = "") String callback,
			@RequestParam(value = "logintoken", defaultValue = "") String logintoken,
			@RequestParam(value = "callings", defaultValue = "") String callingStrings,
			@RequestParam(value = "parameters", defaultValue = "") String parameterStrings
						) {
    	User user = new User();
    	
    	Message<Serializable> returnMessage = userService.get(user);
		ObjectMapper mapper = new ObjectMapper();
		
    	if (Constant.USER==returnMessage.getType()) {
    		user = (User) returnMessage.getContent();
	    	user.setLogintoken(logintoken);
	    	
			try {
				String[] callings = mapper.readValue(callingStrings, String[].class);
				String[] parameters = mapper.readValue(parameterStrings, String[].class);
		    	returnMessage = pluginService.configMessagePlugins(user, callings, parameters);
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
			@RequestParam(value = "logintoken", defaultValue = "") String logintoken
						) {
    	User user = new User();
    	
    	Message<Serializable> returnMessage = userService.get(user);
		ObjectMapper mapper = new ObjectMapper();
		
    	if (Constant.USER==returnMessage.getType()) {
    		user = (User) returnMessage.getContent();
	    	user.setLogintoken(logintoken);
	    	returnMessage = pluginService.pollMessagePlugins(user);
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
