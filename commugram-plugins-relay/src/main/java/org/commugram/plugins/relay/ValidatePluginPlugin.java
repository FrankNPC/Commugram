package org.commugram.plugins.relay;

import java.io.Serializable;

import org.commugram.datacenter.PluginInMemoryManager;
import org.commugram.datacenter.UserInMemoryManager;

import universal.message.Message;
import universal.message.Plugin;
import universal.message.User;
import universal.message.plugin.MessagePlugin;
import universal.message.plugin.PluginPlugin;

/**
 * 
 * just for official example, will be upgraded to cache component and persist to database later.
 * you can implement you own instance.
 * 
 * @author FrankNPC
 *
 */
public class ValidatePluginPlugin implements PluginPlugin {

	private static PluginInMemoryManager pluginInMemoryManager = new PluginInMemoryManager();
	
	private static UserInMemoryManager userInMemoryManager = new UserInMemoryManager();

	@Override
	public Message<Serializable> configMessagePlugins(Message<Serializable> lastStackMessage, User user, String[] callings, String[] parameters) {
    	Message<Serializable> message = new Message<Serializable>();
		message.setType(PLUGIN);
		
    	if (user.getLogintoken()==null) {
    		message.setType(ValidateUserPlugin.ERROR_USER_TOKEN);
            return message;
    	}

    	User getUser = userInMemoryManager.getUserByLogintoken(user.getLogintoken());
    	if (getUser==null) {
    		message.setType(ValidateUserPlugin.ERROR_USER_NOT_EXIST);
            return message;
    	}
    	
    	pluginInMemoryManager.configMessagePlugins(callings, parameters);
		
		return message;
	}

	@Override
	public Message<Serializable> pollMessagePlugins(Message<Serializable> lastStackMessage, User user) {
    	Message<Serializable> message = new Message<Serializable>();
		message.setType(PLUGIN);
		
    	if (user.getLogintoken()==null) {
    		message.setType(ValidateUserPlugin.ERROR_USER_TOKEN);
            return message;
    	}

    	User getUser = userInMemoryManager.getUserByLogintoken(user.getLogintoken());
    	if (getUser==null) {
    		message.setType(ValidateUserPlugin.ERROR_USER_NOT_EXIST);
            return message;
    	}
    	
    	MessagePlugin[] messagePlugins = pluginInMemoryManager.pollMessagePlugins();
    	Plugin[] plugins = new Plugin[messagePlugins.length];
    	for(int i=0; i<plugins.length; i+=1) {
    		plugins[i] = messagePlugins[i].get();
    	}
    	message.setContent(plugins);
		return message;
	}

}
