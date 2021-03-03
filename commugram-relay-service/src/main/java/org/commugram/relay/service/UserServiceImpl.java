package org.commugram.relay.service;

import java.io.Serializable;

import javax.annotation.Resource;

import org.commugram.relay.service.base.UserService;
import org.springframework.stereotype.Service;

import universal.message.Message;
import universal.message.User;
import universal.message.plugin.Constant;
import universal.message.plugin.PluginManager;
import universal.message.plugin.UserPlugin;

@Service
public class UserServiceImpl implements UserService {

    @Resource(name="commugramPluginManager")
	private PluginManager commugramPluginManager;
    
	@Override
	public Message<Serializable> register(User user) {
		Message<Serializable> pluginMessage = new Message<Serializable>();
		pluginMessage.setType(Constant.USER);
		pluginMessage.setTargetid(user.getUserid());

		try {
			if (!commugramPluginManager.lockUserAccess(user.getUserid())) {
				pluginMessage.setType(Constant.LOCKED_ACCESS);
				return pluginMessage;
			}
			UserPlugin[] userPlugins = commugramPluginManager.pollUserPlugins();
			for(UserPlugin userPlugin : userPlugins) {
				pluginMessage = userPlugin.register(pluginMessage, user);
				if (pluginMessage!=null&&pluginMessage.getType()!=Constant.SYSTEM) {return pluginMessage;}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			commugramPluginManager.unlockUserAccess();
		}
		return pluginMessage;
	}

	@Override
	public Message<Serializable> login(User user) {
		Message<Serializable> pluginMessage = new Message<Serializable>();
		pluginMessage.setType(Constant.USER);
		pluginMessage.setTargetid(user.getUserid());

		try {
			if (!commugramPluginManager.lockUserAccess(user.getUserid())) {
				pluginMessage.setType(Constant.LOCKED_ACCESS);
				return pluginMessage;
			}
			UserPlugin[] userPlugins = commugramPluginManager.pollUserPlugins();
			for(UserPlugin userPlugin : userPlugins) {
				pluginMessage = userPlugin.login(pluginMessage, user);
				if (pluginMessage!=null&&pluginMessage.getType()!=Constant.SYSTEM) {return pluginMessage;}
			}
		}finally {
			commugramPluginManager.unlockUserAccess();
		}

		return pluginMessage;
	}

	@Override
	public Message<Serializable> reset(User user) {
		Message<Serializable> pluginMessage = new Message<Serializable>();
		pluginMessage.setType(Constant.USER);
		pluginMessage.setTargetid(user.getUserid());

		try {
			if (!commugramPluginManager.lockUserAccess(user.getUserid())) {
				pluginMessage.setType(Constant.LOCKED_ACCESS);
				return pluginMessage;
			}
			UserPlugin[] userPlugins = commugramPluginManager.pollUserPlugins();
			for(UserPlugin userPlugin : userPlugins) {
				pluginMessage = userPlugin.reset(pluginMessage, user);
				if (pluginMessage!=null&&pluginMessage.getType()!=Constant.SYSTEM) {return pluginMessage;}
			}
		}finally {
			commugramPluginManager.unlockUserAccess();
		}

		return pluginMessage;
	}

	@Override
	public Message<Serializable> modify(User user) {
		Message<Serializable> pluginMessage = new Message<Serializable>();
		pluginMessage.setType(Constant.USER);
		pluginMessage.setTargetid(user.getUserid());

		try {
			if (!commugramPluginManager.lockUserAccess(user.getUserid())) {
				pluginMessage.setType(Constant.LOCKED_ACCESS);
				return pluginMessage;
			}
			UserPlugin[] userPlugins = commugramPluginManager.pollUserPlugins();
			for(UserPlugin userPlugin : userPlugins) {
				pluginMessage = userPlugin.modify(pluginMessage, user);
				if (pluginMessage!=null&&pluginMessage.getType()!=Constant.SYSTEM) {return pluginMessage;}
			}
		}finally {
			commugramPluginManager.unlockUserAccess();
		}

		return pluginMessage;
	}

	@Override
	public Message<Serializable> remove(User user) {
		Message<Serializable> pluginMessage = new Message<Serializable>();
		pluginMessage.setType(Constant.USER);
		pluginMessage.setTargetid(user.getUserid());

		try {
			if (!commugramPluginManager.lockUserAccess(user.getUserid())) {
				pluginMessage.setType(Constant.LOCKED_ACCESS);
				return pluginMessage;
			}
			UserPlugin[] userPlugins = commugramPluginManager.pollUserPlugins();
			for(UserPlugin userPlugin : userPlugins) {
				pluginMessage = userPlugin.remove(pluginMessage, user);
				if (pluginMessage!=null&&pluginMessage.getType()!=Constant.SYSTEM) {return pluginMessage;}
			}
		}finally {
			commugramPluginManager.unlockUserAccess();
		}

		return pluginMessage;
	}

	@Override
	public Message<Serializable> get(User user) {
		Message<Serializable> pluginMessage = new Message<Serializable>();
		pluginMessage.setType(Constant.USER);
		pluginMessage.setTargetid(user.getUserid());

		try {
			if (!commugramPluginManager.lockUserAccess(user.getUserid())) {
				pluginMessage.setType(Constant.LOCKED_ACCESS);
				return pluginMessage;
			}
			UserPlugin[] userPlugins = commugramPluginManager.pollUserPlugins();
			for(UserPlugin userPlugin : userPlugins) {
				pluginMessage = userPlugin.get(pluginMessage, user);
				if (pluginMessage!=null&&pluginMessage.getType()!=Constant.SYSTEM) {return pluginMessage;}
			}
		}finally {
			commugramPluginManager.unlockUserAccess();
		}

		return pluginMessage;
	}

}
