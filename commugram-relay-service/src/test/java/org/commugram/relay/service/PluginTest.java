package org.commugram.relay.service;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

import static org.junit.Assert.assertEquals;

/**
 * Unit test for user.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-commugram-beans.xml"})
public class PluginTest {

    @Autowired
    UserService userService;

    @Autowired
    PluginService pluginService;
    
    @Test
    public void testUser() {
    	pollMessagePlugins();
    	configMessagePlugins();
    }

    
    public void pollMessagePlugins() {
        System.out.println("---test pollMessagePlugins BEGAIN---");

        User aUser = new User();
        aUser.setLoginname("pollMessagePlugins");
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

        Message<Serializable> returnMessage = pluginService.pollMessagePlugins(aUser);

        assertEquals(true, returnMessage.getType()==Constant.PLUGIN);
        System.out.println(returnMessage);
        
        Plugin[] plugins = (Plugin[]) returnMessage.getContent();
        for(Plugin plugin : plugins) {
            assertEquals(true, plugin!=null);
            System.out.println(plugin);
        }
        System.out.println("---test pollMessagePlugins END---");
    }
    

    public void configMessagePlugins() {
        System.out.println("---test configMessagePlugins BEGAIN---");

        User aUser = new User();
        aUser.setLoginname("configMessagePlugins");
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
        System.out.println(aMessage);

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
        
        System.out.println("---test configMessagePlugins END---");
    }
	
}
