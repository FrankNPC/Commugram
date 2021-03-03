package org.commugram.relay.service;

import static org.junit.Assert.assertEquals;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.commugram.relay.service.base.MessageService;
import org.commugram.relay.service.base.PluginService;
import org.commugram.relay.service.base.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import universal.message.Message;
import universal.message.Plugin;
import universal.message.User;
import universal.message.plugin.Constant;

/**
 * Unit test for user.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-commugram-beans.xml"})
public class MessageTest {

    @Autowired
    UserService userService;

    @Autowired
    PluginService pluginService;
    
    @Autowired
    MessageService messageService;

    @Test
    public void testUser() {
    	testSend();
    	testSendBatch();
    	testPoll();
    	testCount();
//    	testPollByCount();
    }

    
    public void testSend() {
        System.out.println("---test send BEGAIN---");

        User aUser = new User();
        aUser.setLoginname("testSenda");
        try {
        	aUser.setLoginpassword(EncryptTools.getHexString(
										MessageDigest.getInstance("SHA1").digest((""+System.currentTimeMillis()).getBytes())));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        Message<Serializable> aMessage = userService.register(aUser);
        if (aMessage.getType()==Constant.USER) {
            aUser = (User) aMessage.getContent();
        }
        assertEquals(true, aUser!=null);

        User bUser = new User();
        bUser.setLoginname("testSendb");
        try {
        	bUser.setLoginpassword(EncryptTools.getHexString(
										MessageDigest.getInstance("SHA1").digest((""+System.currentTimeMillis()).getBytes())));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        Message<Serializable> bMessage = userService.register(bUser);
        if (bMessage.getType()==Constant.USER) {
        	bUser = (User) bMessage.getContent();
        }
        assertEquals(true, bUser!=null);

        Message<Serializable> returnMessage = pluginService.pollMessagePlugins(aUser);
        
        assertEquals(true, returnMessage.getType()==Constant.PLUGIN);
        System.out.println(returnMessage);
        
        Plugin[] plugins = (Plugin[]) returnMessage.getContent();
        String[] callings = new String[plugins.length];
        String[] parameters = new String[plugins.length];
        for(int i=0; i<plugins.length; i+=1) {
        	callings[i] = parameters[i] = plugins[i].getCalling();
        }
        
        returnMessage = pluginService.configMessagePlugins(aUser, callings, parameters);
        
        assertEquals(true, returnMessage.getType()==Constant.PLUGIN);
        System.out.println(returnMessage);

        returnMessage = pluginService.pollMessagePlugins(aUser);
        
        assertEquals(true, returnMessage.getType()==Constant.PLUGIN);
        System.out.println(returnMessage);
        
        plugins = (Plugin[]) returnMessage.getContent();
        for(Plugin plugin : plugins) {
            assertEquals(true, plugin!=null);
            System.out.println(plugin);
        }
        
        Message<Serializable> sendMessage = new Message<Serializable>();
        sendMessage.setType(Constant.MESSAGE_TEXT);
        sendMessage.setTargetid(bUser.getUserid());
        sendMessage.setUserid(aUser.getUserid());
        sendMessage.setContent("Hello World!!!");
        returnMessage = messageService.send(aUser, sendMessage);

        assertEquals(true, returnMessage.getType()==Constant.MESSAGE);
        System.out.println(returnMessage);

        System.out.println("---test send END---");
    }
    

    public void testSendBatch() {
        System.out.println("---test sendBatch BEGAIN---");

        User aUser = new User();
        aUser.setLoginname("testSendBatcha");
        try {
        	aUser.setLoginpassword(EncryptTools.getHexString(
										MessageDigest.getInstance("SHA1").digest((""+System.currentTimeMillis()).getBytes())));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        Message<Serializable> aMessage = userService.register(aUser);
        if (aMessage.getType()==Constant.USER) {
            aUser = (User) aMessage.getContent();
        }
        assertEquals(true, aUser!=null);

        User bUser = new User();
        bUser.setLoginname("testSendBatchb");
        try {
        	bUser.setLoginpassword(EncryptTools.getHexString(
										MessageDigest.getInstance("SHA1").digest((""+System.currentTimeMillis()).getBytes())));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        Message<Serializable> bMessage = userService.register(bUser);
        if (bMessage.getType()==Constant.USER) {
        	bUser = (User) bMessage.getContent();
        }

        assertEquals(true, bUser!=null);
        
        List<Message<Serializable>> messageList = new ArrayList<Message<Serializable>>();

        Message<Serializable> sendMessage = new Message<Serializable>();
        sendMessage.setType(Constant.MESSAGE_TEXT);
        sendMessage.setUserid(aUser.getUserid());
        sendMessage.setTargetid(bUser.getUserid());
        sendMessage.setContent("Hello b!!!");
        messageList.add(sendMessage);
        
        sendMessage = new Message<Serializable>();
        sendMessage.setType(Constant.MESSAGE_TEXT);
        sendMessage.setUserid(bUser.getUserid());
        sendMessage.setTargetid(aUser.getUserid());
        sendMessage.setContent("Hello a!!!");
        messageList.add(sendMessage);
        
        Message<Serializable> returnMessage = messageService.send(aUser, messageList.toArray(new Message[messageList.size()]));

        assertEquals(true, returnMessage.getType()!=Constant.MESSAGE);
        System.out.println(returnMessage);
        

        messageList = new ArrayList<Message<Serializable>>();

        sendMessage = new Message<Serializable>();
        sendMessage.setType(Constant.MESSAGE_TEXT);
        sendMessage.setUserid(aUser.getUserid());
        sendMessage.setTargetid(bUser.getUserid());
        sendMessage.setContent("Hello a!!!");
        messageList.add(sendMessage);
        
        sendMessage = new Message<Serializable>();
        sendMessage.setType(Constant.MESSAGE_TEXT);
        sendMessage.setUserid(aUser.getUserid());
        sendMessage.setTargetid(bUser.getUserid());
        sendMessage.setContent("Hello a!!!");
        messageList.add(sendMessage);
        
        returnMessage = messageService.send(aUser, messageList.toArray(new Message[messageList.size()]));
        assertEquals(true, returnMessage.getType()==Constant.MESSAGE);
        assertEquals(true, returnMessage.getContent()!=null&&((Message<Serializable>[])returnMessage.getContent()).length>0);
        System.out.println(returnMessage);

        System.out.println("---test sendBatch END---");
    }

    public void testPoll() {
        System.out.println("---test poll BEGAIN---");

        User aUser = new User();
        aUser.setLoginname("testPolla");
        try {
        	aUser.setLoginpassword(EncryptTools.getHexString(
										MessageDigest.getInstance("SHA1").digest((""+System.currentTimeMillis()).getBytes())));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        Message<Serializable> aMessage = userService.register(aUser);
        if (aMessage.getType()==Constant.USER) {
            aUser = (User) aMessage.getContent();
        }
        assertEquals(true, aUser!=null);

        User bUser = new User();
        bUser.setLoginname("testPollb");
        try {
        	bUser.setLoginpassword(EncryptTools.getHexString(
										MessageDigest.getInstance("SHA1").digest((""+System.currentTimeMillis()).getBytes())));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        Message<Serializable> bMessage = userService.register(bUser);
        if (bMessage.getType()==Constant.USER) {
        	bUser = (User) bMessage.getContent();
        }
        assertEquals(true, bUser!=null);

        List<Message<Serializable>> messageList = new ArrayList<Message<Serializable>>();

        Message<Serializable> sendMessage = new Message<Serializable>();
        sendMessage.setType(Constant.MESSAGE_TEXT);
        sendMessage.setUserid(aUser.getUserid());
        sendMessage.setTargetid(bUser.getUserid());
        sendMessage.setContent("Hello b!!!");
        messageList.add(sendMessage);
        
        sendMessage = new Message<Serializable>();
        sendMessage.setType(Constant.MESSAGE_TEXT);
        sendMessage.setUserid(bUser.getUserid());
        sendMessage.setTargetid(aUser.getUserid());
        sendMessage.setContent("Hello a!!!");
        messageList.add(sendMessage);
        
        Message<Serializable> returnMessage = messageService.send(aUser, messageList.toArray(new Message[messageList.size()]));
        
        assertEquals(true, returnMessage.getType()!=Constant.MESSAGE);
        System.out.println(returnMessage);
        

        messageList = new ArrayList<Message<Serializable>>();

        sendMessage = new Message<Serializable>();
        sendMessage.setType(Constant.MESSAGE_TEXT);
        sendMessage.setUserid(aUser.getUserid());
        sendMessage.setTargetid(bUser.getUserid());
        sendMessage.setContent("Hello a!!!");
        messageList.add(sendMessage);
        
        sendMessage = new Message<Serializable>();
        sendMessage.setType(Constant.MESSAGE_TEXT);
        sendMessage.setUserid(aUser.getUserid());
        sendMessage.setTargetid(bUser.getUserid());
        sendMessage.setContent("Hello a!!!");
        messageList.add(sendMessage);

        returnMessage = messageService.send(aUser, messageList.toArray(new Message[messageList.size()]));
        
        assertEquals(true, returnMessage.getType()==Constant.MESSAGE);
        assertEquals(true, returnMessage.getContent()!=null&&((Message<Serializable>[])returnMessage.getContent()).length>0);
        System.out.println(returnMessage);
        
        returnMessage = messageService.poll(bUser);
        assertEquals(true, returnMessage.getType()==Constant.MESSAGE);
        System.out.println(returnMessage);

        System.out.println("---test poll END---");
    }
    
    public void testCount() {
        System.out.println("---test count BEGAIN---");

        User aUser = new User();
        aUser.setLoginname("testCounta");
        try {
        	aUser.setLoginpassword(EncryptTools.getHexString(
										MessageDigest.getInstance("SHA1").digest((""+System.currentTimeMillis()).getBytes())));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        Message<Serializable> aMessage = userService.register(aUser);
        if (aMessage.getType()==Constant.USER) {
            aUser = (User) aMessage.getContent();
        }
        assertEquals(true, aUser!=null);

        User bUser = new User();
        bUser.setLoginname("testCountb");
        try {
        	bUser.setLoginpassword(EncryptTools.getHexString(
										MessageDigest.getInstance("SHA1").digest((""+System.currentTimeMillis()).getBytes())));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        Message<Serializable> bMessage = userService.register(bUser);
        if (bMessage.getType()==Constant.USER) {
        	bUser = (User) bMessage.getContent();
        }
        assertEquals(true, bUser!=null);

        List<Message<Serializable>> messageList = new ArrayList<Message<Serializable>>();

        Message<Serializable> sendMessage = new Message<Serializable>();
        sendMessage.setType(Constant.MESSAGE_TEXT);
        sendMessage.setUserid(aUser.getUserid());
        sendMessage.setTargetid(bUser.getUserid());
        sendMessage.setContent("Hello b!!!");
        messageList.add(sendMessage);
        
        sendMessage = new Message<Serializable>();
        sendMessage.setType(Constant.MESSAGE_TEXT);
        sendMessage.setUserid(bUser.getUserid());
        sendMessage.setTargetid(aUser.getUserid());
        sendMessage.setContent("Hello a!!!");
        messageList.add(sendMessage);
        
        Message<Serializable> returnMessage = messageService.send(aUser, messageList.toArray(new Message[messageList.size()]));
        
        assertEquals(true, returnMessage.getType()!=Constant.MESSAGE);
        System.out.println(returnMessage);

        messageList = new ArrayList<Message<Serializable>>();

        sendMessage = new Message<Serializable>();
        sendMessage.setType(Constant.MESSAGE_TEXT);
        sendMessage.setUserid(aUser.getUserid());
        sendMessage.setTargetid(bUser.getUserid());
        sendMessage.setContent("Hello a!!!");
        messageList.add(sendMessage);
        
        sendMessage = new Message<Serializable>();
        sendMessage.setType(Constant.MESSAGE_TEXT);
        sendMessage.setUserid(aUser.getUserid());
        sendMessage.setTargetid(bUser.getUserid());
        sendMessage.setContent("Hello a!!!");
        messageList.add(sendMessage);

        returnMessage = messageService.send(aUser, messageList.toArray(new Message[messageList.size()]));

        assertEquals(true, returnMessage.getType()==Constant.MESSAGE);
        assertEquals(true, returnMessage.getContent()!=null&&((Message<Serializable>[])returnMessage.getContent()).length>0);
        System.out.println(returnMessage);
        
        returnMessage = messageService.count(bUser);
        
        assertEquals(true, returnMessage.getType()==Constant.MESSAGE);
        System.out.println(returnMessage);

        System.out.println("---test count END---");
    }
	
}
