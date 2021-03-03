package org.commugram.relay.service;

import java.io.Serializable;

import javax.annotation.Resource;

import org.commugram.relay.service.base.PluginService;
import org.springframework.stereotype.Service;

import universal.message.Message;
import universal.message.User;
import universal.message.plugin.Constant;
import universal.message.plugin.PluginManager;
import universal.message.plugin.PluginPlugin;

@Service
public class PluginServiceImpl implements PluginService {

    @Resource(name="commugramPluginManager")
	private PluginManager commugramPluginManager;
    
	@Override
	public Message<Serializable> configMessagePlugins(User user, String[] callings, String[] parameters) {
		Message<Serializable> pluginMessage = new Message<Serializable>();
		pluginMessage.setType(Constant.PLUGIN);
		pluginMessage.setTargetid(user.getUserid());

		try {
			if (!commugramPluginManager.lockUserAccess(user.getUserid())) {
				pluginMessage.setType(Constant.LOCKED_ACCESS);
				return pluginMessage;
			}
			PluginPlugin[] pluginPlugins = commugramPluginManager.pollPluginPlugins();
			for(PluginPlugin pluginPlugin : pluginPlugins) {
				pluginMessage = pluginPlugin.configMessagePlugins(pluginMessage, user, callings, parameters);
				if (pluginMessage!=null&&pluginMessage.getType()!=Constant.SYSTEM) {return pluginMessage;}
			}
		}finally {
			commugramPluginManager.unlockUserAccess();
		}

		return pluginMessage;
	}

	@Override
	public Message<Serializable> pollMessagePlugins(User user) {
		Message<Serializable> pluginMessage = new Message<Serializable>();
		pluginMessage.setType(Constant.PLUGIN);
		pluginMessage.setTargetid(user.getUserid());

		try {
			if (!commugramPluginManager.lockUserAccess(user.getUserid())) {
				pluginMessage.setType(Constant.LOCKED_ACCESS);
				return pluginMessage;
			}
			PluginPlugin[] pluginPlugins = commugramPluginManager.pollPluginPlugins();
			for(PluginPlugin pluginPlugin : pluginPlugins) {
				pluginMessage = pluginPlugin.pollMessagePlugins(pluginMessage, user);
				if (pluginMessage!=null&&pluginMessage.getType()!=Constant.SYSTEM) {return pluginMessage;}
			}
		}finally {
			commugramPluginManager.unlockUserAccess();
		}

		return pluginMessage;
	}

}
