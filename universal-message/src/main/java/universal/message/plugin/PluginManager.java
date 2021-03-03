package universal.message.plugin;

/**
 * scan all slices which implements the @Plugin interface, and collect them into the collection.
 * 
 * @author FrankNPC
 *
 */
public interface PluginManager {

	public boolean lockUserAccess(Long userid);
	
	public void unlockUserAccess();
	
	public void configMessagePlugins(String[] callings, String[] parameters);

	public MessagePlugin[] pollMessagePlugins();
	
	public UserPlugin[] pollUserPlugins();
	
	public PluginPlugin[] pollPluginPlugins();
	
	public void destroy();
	
}
