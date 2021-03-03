package org.commugram.relay.service.base;

import java.io.Serializable;

import universal.message.Message;
import universal.message.User;

/**
 * The services are all just to deliver the packed object to plugins.
 * @author FrankNPC
 *
 */
public interface UserService {

	public Message<Serializable> register(User user);
	public Message<Serializable> login(User user);
	public Message<Serializable> reset(User user);
	public Message<Serializable> modify(User user);
	public Message<Serializable> remove(User user);
	public Message<Serializable> get(User user);
	
}
