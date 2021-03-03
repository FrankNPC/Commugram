package org.commugram.plugins.relay;

import java.io.Serializable;

import org.commugram.datacenter.MessageInMemoryManager;

import universal.message.Message;
import universal.message.Plugin;
import universal.message.User;
import universal.message.plugin.MessagePlugin;

/**
 * just for official example, will be upgraded to cache component and persist to
 * database later. you can implement you own instance.
 * 
 * @author FrankNPC
 *
 */
public class DistributeMessagePlugin implements MessagePlugin {

	private static MessageInMemoryManager messageInMemoryManager = new MessageInMemoryManager();

	public static int ERROR_MESSAGE_EMPTY =				-MESSAGE - 1;
	public static int ERROR_MESSAGE_USER_NOT_MATCH = 	-MESSAGE - 2;
	public static int ERROR_MESSAGE_POLL_FAIL = 		-MESSAGE - 4;
	public static int ERROR_MESSAGE_COUNT_FAIL = 		-MESSAGE - 5;

	@Override
	public Message<Serializable> send(Message<Serializable> lastStackMessage, User user,
			Message<Serializable> message) {
		Message<Serializable> returnMessage = new Message<Serializable>();
		returnMessage.setType(MESSAGE);

		message = messageInMemoryManager.send(message);

		return returnMessage;
	}

	@Override
	public Message<Serializable> send(Message<Serializable> lastStackMessage, User user,
			Message<Serializable>[] messages) {
		Message<Serializable> returnMessage = new Message<Serializable>();
		returnMessage.setType(MESSAGE);

		for (int i = 0; i < messages.length; i += 1) {
			messages[i] = messageInMemoryManager.send(messages[i]);
		}
		returnMessage.setContent(messages);
		return returnMessage;
	}

	@Override
	public Message<Serializable> poll(Message<Serializable> lastStackMessage, User user, long startid, int time,
			int count) {
		Message<Serializable> returnMessage = new Message<Serializable>();
		returnMessage.setType(MESSAGE);

		Message<Serializable>[] messages = messageInMemoryManager.poll(user, startid, time, count);

		if (messages == null) {
			returnMessage.setType(ERROR_MESSAGE_POLL_FAIL);
			return returnMessage;
		}

		returnMessage.setContent(messages);

		return returnMessage;
	}

	@Override
	public Message<Serializable> poll(Message<Serializable> lastStackMessage, User user) {
		Message<Serializable> returnMessage = new Message<Serializable>();
		returnMessage.setType(MESSAGE);

		Message<Serializable>[] messages = messageInMemoryManager.poll(user, 0, 0, 0);

		if (messages == null) {
			returnMessage.setType(ERROR_MESSAGE_POLL_FAIL);
			return returnMessage;
		}

		returnMessage.setContent(messages);

		return returnMessage;
	}

	@Override
	public Message<Serializable> count(Message<Serializable> lastStackMessage, User user) {
		Message<Serializable> returnMessage = new Message<Serializable>();
		returnMessage.setType(MESSAGE);

		int count = messageInMemoryManager.queryAvaliableMessageCount(user);

		if (count < 0) {
			returnMessage.setType(ERROR_MESSAGE_COUNT_FAIL);
			return returnMessage;
		}

		returnMessage.setContent(count);

		return returnMessage;
	}

	private Plugin plugin;

	@Override
	public Plugin get() {
		if (plugin != null) {
			return plugin;
		}
		plugin = new Plugin();
		plugin.setCalling(this.getClass().getTypeName());

		return plugin;
	}

}
