package org.commugram.relay.service;

import static org.junit.Assert.assertEquals;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.commugram.relay.service.base.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import universal.message.Message;
import universal.message.User;
import universal.message.plugin.Constant;

/**
 * Unit test for user.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-commugram-beans.xml"})
public class UserTest {

    @Autowired
    UserService userService;

    @Test
    public void testUser() {
    	testRegister();
    	testLogin();
    	testReset();
    	testModify();
    	testRemove();
    }

    public void testRegister() {
        System.out.println("---test register user BEGAIN---");

        User targetuser = new User();
        targetuser.setLoginname("aaaaaaaa");
        try {
			targetuser.setLoginpassword(EncryptTools.getHexString(
										MessageDigest.getInstance("SHA1").digest((""+System.currentTimeMillis()).getBytes())));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        Message<Serializable> returnMessage = userService.register(targetuser);
        
        assertEquals(true, returnMessage.getType()==Constant.USER);
        assertEquals(true, returnMessage.getContent()!=null);
        System.out.println(returnMessage);

        Message<Serializable> messageRepeat = userService.register(targetuser);

        assertEquals(true, messageRepeat.getType()!=Constant.USER);
        assertEquals(true, messageRepeat.getContent()==null);
        System.out.println(messageRepeat);

        System.out.println("---test register user END---");
    }

    public void testLogin() {
        System.out.println("---test login user BEGAIN---");

        User targetuser = new User();
        targetuser.setLoginname("aaaaaaaaa");
        try {
			targetuser.setLoginpassword(EncryptTools.getHexString(
										MessageDigest.getInstance("SHA1").digest((""+System.currentTimeMillis()).getBytes())));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        Message<Serializable> returnMessage = userService.register(targetuser);

        assertEquals(true, returnMessage.getType()==Constant.USER);
        assertEquals(true, returnMessage.getContent()!=null);
        System.out.println(returnMessage);

        Message<Serializable> loginMessage = userService.login(targetuser);
        
        assertEquals(true, loginMessage.getType()==Constant.USER);
        assertEquals(true, ((User)loginMessage.getContent()).getLogintoken()!=null);
        
        System.out.println(loginMessage);
        
        System.out.println("---test login user END---");
    }

    public void testReset() {
        System.out.println("---test reset user BEGAIN---");

        User targetuser = new User();
        targetuser.setLoginname(UUID.randomUUID().toString());
        try {
			targetuser.setLoginpassword(EncryptTools.getHexString(
										MessageDigest.getInstance("SHA1").digest((""+System.currentTimeMillis()).getBytes())));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        Message<Serializable> returnMessage = userService.register(targetuser);
        
        assertEquals(true, returnMessage.getType()==Constant.USER);
        assertEquals(true, returnMessage.getContent()!=null);
        System.out.println(returnMessage);

        try {
			targetuser.setLoginpassword(EncryptTools.getHexString(
										MessageDigest.getInstance("SHA1").digest((""+System.currentTimeMillis()).getBytes())));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        Message<Serializable> resetMessage = userService.reset(targetuser);
        
        assertEquals(true, resetMessage.getType()==Constant.USER);
        assertEquals(true, ((User)resetMessage.getContent()).getLoginpassword().equals(targetuser.getLoginpassword()));
        System.out.println(resetMessage);

        targetuser.setLoginname(UUID.randomUUID().toString());
        targetuser.setLoginpassword(UUID.randomUUID().toString());
        targetuser.setLogintoken(UUID.randomUUID().toString());
        resetMessage = userService.modify(targetuser);

        assertEquals(true, resetMessage.getType()!=Constant.USER);
        System.out.println(resetMessage);
        System.out.println(targetuser);
        
        System.out.println("---test reset user END---");
    }
    
    public void testModify() {
        System.out.println("---test modify user BEGAIN---");

        User targetuser = new User();
        targetuser.setLoginname(UUID.randomUUID().toString());
        try {
			targetuser.setLoginpassword(EncryptTools.getHexString(
										MessageDigest.getInstance("SHA1").digest((""+System.currentTimeMillis()).getBytes())));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        Message<Serializable> returnMessage = userService.register(targetuser);
        
        assertEquals(true, returnMessage.getType()==Constant.USER);
        assertEquals(true, returnMessage.getContent()!=null);
        System.out.println(returnMessage);

        try {
			targetuser.setLoginpassword(EncryptTools.getHexString(
										MessageDigest.getInstance("SHA1").digest((""+System.currentTimeMillis()).getBytes())));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        Message<Serializable> resetMessage = userService.modify(targetuser);
        
        assertEquals(true, resetMessage.getType()==Constant.USER);
        assertEquals(true, ((User)resetMessage.getContent()).getLoginpassword().equals(targetuser.getLoginpassword()));
        System.out.println(resetMessage);

        targetuser.setLoginname(UUID.randomUUID().toString());
        targetuser.setLoginpassword(UUID.randomUUID().toString());
        targetuser.setLogintoken(UUID.randomUUID().toString());
        resetMessage = userService.modify(targetuser);
        
        assertEquals(true, resetMessage.getType()!=Constant.USER);
        System.out.println(resetMessage);
        System.out.println(targetuser);
        
        System.out.println("---test modify user END---");
    }

    public void testRemove() {
        System.out.println("---test remove user BEGAIN---");

        User targetuser = new User();
        targetuser.setLoginname(UUID.randomUUID().toString());
        try {
			targetuser.setLoginpassword(EncryptTools.getHexString(
										MessageDigest.getInstance("SHA1").digest((""+System.currentTimeMillis()).getBytes())));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        Message<Serializable> returnMessage = userService.register(targetuser);
        
        assertEquals(true, returnMessage.getType()==Constant.USER);
        assertEquals(true, returnMessage.getContent()!=null);
        System.out.println(returnMessage);

        Message<Serializable> removeMessage = userService.remove(targetuser);
        
        assertEquals(true, removeMessage.getType()==Constant.USER);
        System.out.println(removeMessage);

        Message<Serializable> modifyMessage = userService.modify(targetuser);
        
        assertEquals(true, modifyMessage.getType()!=Constant.USER);
        System.out.println(modifyMessage);

        targetuser.setLogintoken(UUID.randomUUID().toString());
        removeMessage = userService.remove(targetuser);
        
        assertEquals(true, removeMessage.getType()!=Constant.USER);
        System.out.println(removeMessage);
        
        System.out.println("---test remove user END---");
    }
	
}
