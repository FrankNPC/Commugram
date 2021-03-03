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
public interface MessagePlugin extends Constant {
	
	/**
	 * will be called, then the return will be set with parameters.
	 */
	public Plugin get();

	/**
	 * send message to target id
	 * 
	 * @param lastStackMessage<Serializable> the message from last stack
	 * @param user the user who call the function
	 * @param message the message to target id
	 * @return when returned with non-null and non status, the next stack will skip out and response to the client.
	 */
	public Message<Serializable> send(Message<Serializable> lastStackMessage, User user, Message<Serializable> message);

	/**
	 * send multiple messages to multiple target ids
	 * 
	 * @param lastStackMessage<Serializable> the message from last stack
	 * @param user the user who call the function
	 * @param messageList the multiple messages
	 * @return when returned with non-null and non status, the next stack will skip out and response to the client.
	 */
	public Message<Serializable> send(Message<Serializable> lastStackMessage, User user, Message<Serializable>[] messages);

	/**
	 * poll the messages by the conditions.
	 * 
	 * @param lastStackMessage<Serializable> the message from last stack
	 * @param user the user who call the function
	 * @param startid the start id intend to query. If 0, from the first position.
	 * @param time the start time intend to query. If 0, from the first position.
	 * @param count the count for each poll call
	 * @return when returned with non-null and non status, the next stack will skip out and response to the client.
	 *  		message list will be inside the content.
	 */
	public Message<Serializable> poll(Message<Serializable> lastStackMessage, User user, long startid, int time, int count);
	
	/**
	 * poll the messages without the conditions.
	 * 
	 * @param lastStackMessage<Serializable> the message from last stack
	 * @param user the user who call the function
	 * @return when returned with non-null and non status, the next stack will skip out and response to the client.
	 *  		message list will be inside the content.
	 */
	public Message<Serializable> poll(Message<Serializable> lastStackMessage, User user);
	

	/**
	 * count the available messages for client.
	 * 
	 * @param lastStackMessage<Serializable> the message from last stack
	 * @param user the user who call the function
	 * @return 
	 */
	public Message<Serializable> count(Message<Serializable> lastStackMessage, User user);

}
