package org.commugram.relay.service.base;

import java.io.Serializable;

import universal.message.Message;
import universal.message.User;

/**
 * @author FrankNPC
 *
 */
public interface PluginService {
	
	public Message<Serializable> configMessagePlugins(User user, String[] callings, String[] parameters);

	public Message<Serializable> pollMessagePlugins(User user);

}
