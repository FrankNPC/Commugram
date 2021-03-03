package org.commugram.relay.service;

import java.io.Serializable;

import javax.annotation.Resource;

import org.commugram.relay.service.base.MessageService;
import org.springframework.stereotype.Component;

import universal.message.Message;
import universal.message.User;
import universal.message.plugin.Constant;
import universal.message.plugin.MessagePlugin;
import universal.message.plugin.PluginManager;

@Component
public class MessageServiceImpl implements MessageService {

    @Resource(name="commugramPluginManager")
	private PluginManager commugramPluginManager;
    
	@Override
	public Message<Serializable> send(User user, Message<Serializable> message) {
		Message<Serializable> pluginMessage = new Message<Serializable>();
		pluginMessage.setType(Constant.MESSAGE);
		pluginMessage.setTargetid(user.getUserid());
		
		try {
			if (!commugramPluginManager.lockUserAccess(user.getUserid())) {
				pluginMessage.setType(Constant.LOCKED_ACCESS);
				return pluginMessage;
			}
			MessagePlugin[] messagePlugins = commugramPluginManager.pollMessagePlugins();
			for(MessagePlugin messagePlugin : messagePlugins) {
				pluginMessage = messagePlugin.send(pluginMessage, user, message);
				if (pluginMessage!=null&&pluginMessage.getType()!=Constant.SYSTEM) {return pluginMessage;}
			}
		}finally {
			commugramPluginManager.unlockUserAccess();
		}
		
		return pluginMessage;
	}

	@Override
	public Message<Serializable> send(User user, Message<Serializable>[] messages) {
		Message<Serializable> pluginMessage = new Message<Serializable>();
		pluginMessage.setType(Constant.MESSAGE);
		pluginMessage.setTargetid(user.getUserid());

		try {
			if (!commugramPluginManager.lockUserAccess(user.getUserid())) {
				pluginMessage.setType(Constant.LOCKED_ACCESS);
				return pluginMessage;
			}
			MessagePlugin[] messagePlugins = commugramPluginManager.pollMessagePlugins();
			for(MessagePlugin messagePlugin : messagePlugins) {
				pluginMessage = messagePlugin.send(pluginMessage, user, messages);
				if (pluginMessage!=null&&pluginMessage.getType()!=Constant.SYSTEM) {return pluginMessage;}
			}

		}finally {
			commugramPluginManager.unlockUserAccess();
		}

		return pluginMessage;
	}
	
	@Override
	public Message<Serializable> poll(User user) {
		Message<Serializable> pluginMessage = new Message<Serializable>();
		pluginMessage.setType(Constant.MESSAGE);
		pluginMessage.setTargetid(user.getUserid());

		try {
			if (!commugramPluginManager.lockUserAccess(user.getUserid())) {
				pluginMessage.setType(Constant.LOCKED_ACCESS);
				return pluginMessage;
			}
			MessagePlugin[] messagePlugins = commugramPluginManager.pollMessagePlugins();
			for(MessagePlugin messagePlugin : messagePlugins) {
				pluginMessage = messagePlugin.poll(pluginMessage, user);
				if (pluginMessage!=null&&pluginMessage.getType()!=Constant.SYSTEM) {return pluginMessage;}
			}

		}finally {
			commugramPluginManager.unlockUserAccess();
		}

		return pluginMessage;
	}

	@Override
	public Message<Serializable> poll(User user, long startid, int time, int count) {
		Message<Serializable> pluginMessage = new Message<Serializable>();
		pluginMessage.setType(Constant.MESSAGE);
		pluginMessage.setTargetid(user.getUserid());

		try {
			if (!commugramPluginManager.lockUserAccess(user.getUserid())) {
				pluginMessage.setType(Constant.LOCKED_ACCESS);
				return pluginMessage;
			}
			MessagePlugin[] messagePlugins = commugramPluginManager.pollMessagePlugins();
			for(MessagePlugin messagePlugin : messagePlugins) {
				pluginMessage = messagePlugin.poll(pluginMessage, user, startid, time, count);
				if (pluginMessage!=null&&pluginMessage.getType()!=Constant.SYSTEM) {return pluginMessage;}
			}

		}finally {
			commugramPluginManager.unlockUserAccess();
		}

		return pluginMessage;
	}

	@Override
	public Message<Serializable> count(User user) {
		Message<Serializable> pluginMessage = new Message<Serializable>();
		pluginMessage.setType(Constant.MESSAGE);
		pluginMessage.setTargetid(user.getUserid());

		try {
			if (!commugramPluginManager.lockUserAccess(user.getUserid())) {
				pluginMessage.setType(Constant.LOCKED_ACCESS);
				return pluginMessage;
			}
			MessagePlugin[] messagePlugins = commugramPluginManager.pollMessagePlugins();
			for(MessagePlugin messagePlugin : messagePlugins) {
				pluginMessage = messagePlugin.count(pluginMessage, user);
				if (pluginMessage!=null&&pluginMessage.getType()!=Constant.SYSTEM) {return pluginMessage;}
			}

		}finally {
			commugramPluginManager.unlockUserAccess();
		}
		
		return pluginMessage;
	}


}
