package universal.message.plugin;

import java.io.Serializable;

import universal.message.Message;

public interface EventCallback{

	public void pollMessage(Message<Serializable> message);

}
