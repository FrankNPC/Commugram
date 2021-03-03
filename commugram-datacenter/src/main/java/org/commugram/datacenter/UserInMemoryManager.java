package org.commugram.datacenter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import universal.message.UID;
import universal.message.User;

public class UserInMemoryManager {

	public static final Map<Long, User> useridMap = new HashMap<Long, User>();
	private static final Map<String, User> userLoginnameMap = new HashMap<String, User>();
	private static final Map<String, User> userLogintokenMap = new HashMap<String, User>();

	private static final Map<Long, UID> activeUIDMap = new HashMap<Long, UID>();
//	private static final AtomicLong counter = new AtomicLong();

	private static final Lock lock = new ReentrantLock();
	private static final Condition condition = lock.newCondition();
	
	public static final int RegisterRate = 1000;
	private static long counter = 0;
	
	public UID createUID(UID uid) {
		long id = 0;
    	try {
            if (!lock.tryLock()) {
            	return null;
            }
			condition.await(RegisterRate, TimeUnit.MILLISECONDS);
			id = counter+=1;
			condition.signalAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
	    	lock.unlock();
		}
        
		if (uid==null) {
			uid = new UID();
			uid.setId(id);
		}else {
			uid = activeUIDMap.get(uid.getId());
			if (uid==null) {
				uid = new UID();
				uid.setId(id);
			}else if (uid.getCreatetime()>0){
				return null;
			}
		}
		uid.setCreatetime((int) (System.currentTimeMillis()/1000));
		return uid;
	}
	
	public UID removeUID(UID uid) {
		uid = activeUIDMap.get(uid.getId());
		if (uid!=null) {
			uid.setCreatetime(0);
			uid = activeUIDMap.remove(uid.getId());
		}
		return uid;
	}
	
	public User createUser(User user) {
		if (user==null) {
			user = new User();
		}
		if (userLoginnameMap.containsKey(user.getLoginname())
				||userLogintokenMap.containsKey(user.getLogintoken())
				) {
			return null;
		}
		UID uid = createUID(null);
		if (uid==null){
			return null;
		}
		
		user.setUserid(uid.getId());

		useridMap.put(user.getUserid(), user);
		userLoginnameMap.put(user.getLoginname(), user);
		userLogintokenMap.put(user.getLogintoken(), user);
		
		return user;
	}

	public User getUserByUserid(Long userid) {
		if (userid==null) {
			return null;
		}
		User loginUser = useridMap.get(userid);
		if (loginUser!=null) {
			if (!userLoginnameMap.containsKey(loginUser.getLoginname())) {
				userLoginnameMap.put(loginUser.getLoginname(), loginUser);
			}
			if (!userLogintokenMap.containsKey(loginUser.getLogintoken())) {
				userLogintokenMap.put(loginUser.getLogintoken(), loginUser);
			}
			
			return loginUser;
		}
		return null;
	}

	public User getUserByLogintoken(String logintoken) {
		if (logintoken==null) {
			return null;
		}
		User loginUser = userLogintokenMap.get(logintoken);
		if (loginUser!=null) {
			if (!useridMap.containsKey(loginUser.getUserid())) {
				useridMap.put(loginUser.getUserid(), loginUser);
			}
			if (!userLoginnameMap.containsKey(loginUser.getLoginname())) {
				userLoginnameMap.put(loginUser.getLoginname(), loginUser);
			}
			return loginUser;
		}
		return null;
	}

	public User getUserByLoginname(String loginname) {
		if (loginname==null) {
			return null;
		}
		User loginUser = userLoginnameMap.get(loginname);
		if (loginUser!=null) {
			if (!useridMap.containsKey(loginUser.getUserid())) {
				useridMap.put(loginUser.getUserid(), loginUser);
			}
			if (!userLogintokenMap.containsKey(loginUser.getLogintoken())) {
				userLogintokenMap.put(loginUser.getLogintoken(), loginUser);
			}
			return loginUser;
		}
		return null;
	}

	public User saveUser(User user) {
		if (user==null) {
			return null;
		}
		User loginUser = userLogintokenMap.get(user.getLogintoken());
		if (loginUser!=null) {
			if (!useridMap.containsKey(loginUser.getUserid())) {
				useridMap.put(loginUser.getUserid(), loginUser);
			}
			if (!userLoginnameMap.containsKey(loginUser.getLoginname())) {
				userLoginnameMap.put(loginUser.getLoginname(), loginUser);
			}
			return loginUser;
		}
		return null;
	}
	
	public User resetUser(User fromUser, User targetUser) {
		if (fromUser==null) {
			return null;
		}

		userLogintokenMap.put(targetUser.getLogintoken(), targetUser);
		userLoginnameMap.put(targetUser.getLoginname(), targetUser);
		useridMap.put(targetUser.getUserid(), targetUser);
		userLogintokenMap.remove(fromUser.getLogintoken());
		return targetUser;
	}
	
	public User removeUser(User user) {
		if (user==null) {
			return null;
		}
		User getUser = userLogintokenMap.get(user.getLogintoken());
		if (getUser!=null) {
			useridMap.remove(getUser.getUserid());
			userLoginnameMap.remove(getUser.getLoginname());
			userLogintokenMap.remove(getUser.getLogintoken());
			UID uid = new UID();
			uid.setId(user.getUserid());
			removeUID(uid);
		}
		return getUser;
	}

}
