package universal.message.plugin;

import java.io.Serializable;

import universal.message.Message;
import universal.message.Plugin;
import universal.message.User;

/**
 * The portal will do the best to pass the values without any intercept.
 * any inherits will be expected to be set with values that but are not guaranteed.
 * 
 * @author FrankNPC
 *
 */
public interface UserPlugin extends Constant {

	/**
	 * will be called, then the return will be set with parameters.
	 */
	public Plugin getPlugin();

	public Message<Serializable> get(Message<Serializable> lastStackMessage, User user);

	/**
	 * registor user
	 * 
	 * @param lastStackMessage the message from last stack
	 * @param user the user who call the function
	 * @return
	 */
	public Message<Serializable> register(Message<Serializable> lastStackMessage, User user);

	/**
	 * login user
	 * 
	 * @param lastStackMessage the message from last stack
	 * @param user the user who call the function
	 * @return
	 */
	public Message<Serializable> login(Message<Serializable> lastStackMessage, User user);

	/**
	 * modify user's information
	 * 
	 * @param lastStackMessage the message from last stack
	 * @param user the user who call the function
	 * @return
	 */
	public Message<Serializable> modify(Message<Serializable> lastStackMessage, User user);

	/**
	 * remove the target user
	 * 
	 * @param lastStackMessage the message from last stack
	 * @param user the user who call the function
	 * @return
	 */
	public Message<Serializable> remove(Message<Serializable> lastStackMessage, User user);
	
	/**
	 * reset the login for the target user
	 * 
	 * @param lastStackMessage the message from last stack
	 * @param user the user who call the function
	 * @return
	 */
	public Message<Serializable> reset(Message<Serializable> lastStackMessage, User user);

}
