package universal.message.plugin;


import java.io.Serializable;

import universal.message.Message;
import universal.message.User;

/**
 * The portal will do the best to pass the values without any intercept.
 * any inherits will be expected to be set with values that but are not guaranteed.
 * 
 * @author FrankNPC
 *
 */
public interface PluginPlugin extends Constant {

	/**
	 * config user
	 * 
	 * @param lastStackMessage<Serializable> the message from last stack.
	 * @param user the user who call the function.
	 * @param callings the class package and name.
	 * @param parameters the parameters for the MessagePlugin's sub class that may use to process user data.
	 * @return 
	 */
	public Message<Serializable> configMessagePlugins(Message<Serializable> lastStackMessage, User user, String[] callings, String[] parameters);

	/**
	 * config user
	 * 
	 * @param lastStackMessage<Serializable> the message from last stack.
	 * @param user the user who call the function.
	 * @return the plugin correspond to MessagePlugin
	 */
	public Message<Serializable> pollMessagePlugins(Message<Serializable> lastStackMessage, User user);

}
