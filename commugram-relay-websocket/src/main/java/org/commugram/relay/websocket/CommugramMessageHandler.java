package org.commugram.relay.websocket;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import universal.message.Message;
import universal.message.plugin.Constant;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;

@Component
public class CommugramMessageHandler extends AbstractWebSocketHandler {

	private static Map<Long, WebSocketSession> idSessionMap = Collections.synchronizedMap(new HashMap<Long, WebSocketSession>());

	private static final String SESSION_USER_ID = CommugramMessageHandler.class.getTypeName()+".SESSION.USER_ID";
	
	@Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		idSessionMap.remove(session.getAttributes().get(SESSION_USER_ID));
    }
	
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close();
        }
		idSessionMap.remove(session.getAttributes().get(SESSION_USER_ID));
    }


//  @Autowired
//	CommugramMessageHandler commugramMessageHandler;
//	if (returnMessage.getType()==Constant.USER) {
//    	commugramMessageHandler.closeWebSocketSession(user.getUserid());
//	}
    public void closeWebSocketSession(Long userid) {
    	try {
    		WebSocketSession session = idSessionMap.remove(userid);
    		if (session!=null&&session.isOpen()) {
        		session.close();
    		}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
//  @Autowired
//	CommugramMessageHandler commugramMessageHandler;
//	for(Message<Serializable> message : messages) {
//    	commugramMessageHandler.notifyMessage(message.getTargetid());
//	}
    public void notifyMessage(Long userid) {
    	WebSocketSession session = idSessionMap.get(userid);
    	try {
    		ObjectMapper mapper = new ObjectMapper();
        	Message<Serializable> message = new Message<Serializable>();
        	message.setType(Constant.MESSAGE);
            TextMessage returnMessage = new TextMessage(mapper.writeValueAsString(message));
			session.sendMessage(returnMessage);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		super.handleTextMessage(session, message);		
	}

	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
		
	}

	protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
		
	}

}
