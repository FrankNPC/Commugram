package org.commugram.datacenter;

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

import universal.message.plugin.MessagePlugin;
import universal.message.plugin.PluginManager;
import universal.message.plugin.PluginPlugin;
import universal.message.plugin.UserPlugin;

/**
 * scan all plugins which implements the @Plugin interface, and collect them into the collection.
 * 
 * @author FrankNPC
 *
 */
public class PluginInMemoryManager implements PluginManager {

	private static Map<String, Class<MessagePlugin>> messagePluginClassMap = new LinkedHashMap<String, Class<MessagePlugin>>();
	private static Map<String, Class<UserPlugin>> userPluginClassMap = new LinkedHashMap<String, Class<UserPlugin>>();
	private static Map<String, Class<PluginPlugin>> pluginPluginClassMap = new LinkedHashMap<String, Class<PluginPlugin>>();
	
	private static Map<Long, String[]> userMessagePluginsMap = new HashMap<Long, String[]>();

	public static void registerPlugin(Class clazz) {
		if (MessagePlugin.class.isAssignableFrom(clazz)) {
			messagePluginClassMap.put(clazz.getTypeName(), clazz);
		}
		if (UserPlugin.class.isAssignableFrom(clazz)) {
			userPluginClassMap.put(clazz.getTypeName(), clazz);
		}
		if (PluginPlugin.class.isAssignableFrom(clazz)) {
			pluginPluginClassMap.put(clazz.getTypeName(), clazz);
		}
	}
	
	@Override
	public void configMessagePlugins(String[] callings, String[] parameters) {
		String[] callingClasses = messagePluginClassMap.keySet().toArray(new String[messagePluginClassMap.size()]);
		String[] callingAndParameters = new String[callings.length+parameters.length];
		int count = 0;
		for(int i=0,j=0; j<callingAndParameters.length; i+=1,j+=2,count+=1) {
			if (!callingClasses[i].equals(callings[i])) {break;}
			callingAndParameters[j] = callings[i];
			callingAndParameters[j+1] = parameters[i];
		}
		
		if (count!=messagePluginClassMap.size()) {
			callingAndParameters = new String[callingClasses.length+callingClasses.length];

			for(int i=0,j=0; j<callingAndParameters.length; i+=1,j+=2) {
				callingAndParameters[j] = callingClasses[i];
			}
		}
		userMessagePluginsMap.put(userid, callingAndParameters);
	}

	@Override
	public MessagePlugin[] pollMessagePlugins() {
		String[] callingAndParameters = userMessagePluginsMap.get(userid);
		if (callingAndParameters==null) {
			MessagePlugin[] messagePlugins = new MessagePlugin[messagePluginClassMap.size()];
			int i=0;
			for(Class<MessagePlugin> clazz : messagePluginClassMap.values()) {
				try {
					messagePlugins[i++] = clazz.newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return messagePlugins;
		}else {
			MessagePlugin[] messagePlugins = new MessagePlugin[callingAndParameters.length/2];
			for(int i=0,j=0; j<callingAndParameters.length; i+=1,j+=2) {
				Class<MessagePlugin> clazz = messagePluginClassMap.get(callingAndParameters[j]);
				try {
					if (clazz != null) {
						MessagePlugin messagePlugin = clazz.newInstance();
						messagePlugin.get().setParameters(callingAndParameters[j+1]);
						messagePlugins[i] = messagePlugin;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return messagePlugins;
		}
	}

	@Override
	public UserPlugin[] pollUserPlugins() {
		UserPlugin[] userPlugins = new UserPlugin[userPluginClassMap.size()];
		int i=0;
		for(Class<UserPlugin> clazz : userPluginClassMap.values()) {
			try {
				userPlugins[i++] = clazz.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return userPlugins;
	}
	
	@Override
	public PluginPlugin[] pollPluginPlugins() {
		PluginPlugin[] pluginPlugins = new PluginPlugin[pluginPluginClassMap.size()];
		int i=0;
		for(Class<PluginPlugin> clazz : pluginPluginClassMap.values()) {
			try {
				pluginPlugins[i++] = clazz.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return pluginPlugins;
	}
	
	private static Map<Long, Long> lockUserAccessMap = Collections.synchronizedMap(new HashMap<Long, Long>());

	private Long userid;
	
	@Override
	public boolean lockUserAccess(Long userid) {
		this.userid = userid = userid==null?0l:userid;
		Long previous = lockUserAccessMap.put(this.userid, System.currentTimeMillis());

		if (previous!=null&&System.currentTimeMillis()-previous<5000) {
			lockUserAccessMap.put(this.userid, previous);
			return false;
		}
		
		return true;
	}

	@Override
	public void unlockUserAccess() {
		lockUserAccessMap.remove(this.userid);
	}
	
	public void destroy() {

	}

	
}
