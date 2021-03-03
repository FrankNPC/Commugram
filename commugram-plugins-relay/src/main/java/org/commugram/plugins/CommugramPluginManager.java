package org.commugram.plugins;


import org.commugram.datacenter.PluginInMemoryManager;


/**
 * 
 * @author FrankNPC
 *
 */
public class CommugramPluginManager extends PluginInMemoryManager {
	static {
		PluginInMemoryManager.registerPlugin(org.commugram.plugins.relay.ValidateMessagePlugin.class);
		PluginInMemoryManager.registerPlugin(org.commugram.plugins.relay.ValidateTextMessagePlugin.class);
		PluginInMemoryManager.registerPlugin(org.commugram.plugins.relay.DistributeMessagePlugin.class);
		PluginInMemoryManager.registerPlugin(org.commugram.plugins.relay.ValidatePluginPlugin.class);
		PluginInMemoryManager.registerPlugin(org.commugram.plugins.relay.ValidateUserPlugin.class);
	}
	
}
