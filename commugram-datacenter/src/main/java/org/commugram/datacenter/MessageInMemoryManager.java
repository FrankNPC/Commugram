package org.commugram.datacenter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import universal.message.Message;
import universal.message.User;

public class MessageInMemoryManager {

	private static final Map<Long, List<Message<Serializable>>> activeMessageMap = new HashMap<Long, List<Message<Serializable>>>();

	private static final AtomicInteger counter = new AtomicInteger();

	public Message<Serializable> send(Message<Serializable> message) {
		if (message.getId()==null||message.getId()==0) {
			message.setId(counter.incrementAndGet());
		}
		if (message.getCreatetime()==null||message.getCreatetime()==0) {
			message.setCreatetime((int) (System.currentTimeMillis()/1000));
		}
		List<Message<Serializable>> messageList = activeMessageMap.get(message.getUserid());
		if (messageList==null) {
			messageList = new ArrayList<Message<Serializable>>();
			activeMessageMap.put(message.getUserid(), messageList);
		}
		messageList.add(message);
		
		if (!message.getUserid().equals(message.getTargetid())) {
			messageList = activeMessageMap.get(message.getTargetid());
			if (messageList==null) {
				messageList = new ArrayList<Message<Serializable>>();
				activeMessageMap.put(message.getTargetid(), messageList);
			}
			messageList.add(message);
		}
		
		return message;
	}

	public int queryAvaliableMessageCount(User user) {
		List<Message<Serializable>> messageList = activeMessageMap.get(user.getUserid());
		if (messageList==null) {
			return 0;
		}
		
		return messageList.size();
	}
	
	public Message<Serializable>[] poll(User user, long startid, int time,int count) {
		List<Message<Serializable>> messageList = activeMessageMap.get(user.getUserid());
		if (messageList==null||messageList.isEmpty()) {
			return new Message[0];
		}

		int size = messageList.size(),start=size-1;
		count = count<1?Integer.MAX_VALUE:count;
		count = size<count?size:count;
		for(int i=0; i<count; i+=1,start-=1) {
			Message<Serializable> message = messageList.get(start);
			if (message.getId()<=startid||message.getCreatetime()<time) {start+=1;break;}
		}
		start=start<0?0:start;
		if (size<=0||start>=size) {
			return new Message[0];
		}
		messageList = messageList.subList(start, size);
		return messageList.toArray(new Message[size-start]);
	}
	
}
