package org.commugram.relay.service.base;

import java.io.Serializable;

import universal.message.Message;
import universal.message.User;

/**
 * The services are all just to deliver the packed object to plugins.
 * @author FrankNPC
 *
 */
public interface MessageService {

	public Message<Serializable> send(User user, Message<Serializable> message);
	public Message<Serializable> send(User user, Message<Serializable>[] messageList);
	public Message<Serializable> poll(User user, long startid, int time, int count);
	public Message<Serializable> poll(User user);
	public Message<Serializable> count(User user);
	
}
