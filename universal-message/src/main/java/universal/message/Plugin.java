package universal.message;

/**
 * the setting for a plugin.
 * for transfer data as protocol only.
 * 
 * @author FrankNPC
 *
 */
public class Plugin implements java.io.Serializable{
	
	private static final long serialVersionUID = -4839212448150067425L;

	public Plugin() {}
	
	protected Long id;

	/**
	 * owned user id
	 */
	protected Long userid;
	
	/**
	 * name, only support plain text.
	 */
	protected String name;
	
	/**
	 * description, should support at least HTML.
	 */
	protected String description;

	/**
	 * the url of the home page.
	 */
	protected String homepage;
	
	/**
	 * telephone number.
	 */
	protected String contact;

	/**
	 * email.
	 */
	protected String email;

	/**
	 * will call the basic method of the plugins.
	 * should be unique package and class name in the system.
	 */
	protected String calling;
	
	/**
	 * prepared for the user setup the parameters.
	 */
	protected String parameters;
	
	public static final int CLIENT = -2;
	public static final int CLIENT_LOGIN = -1;
	public static final int SERVER_LOGIN = 1;
	public static final int SERVER =  2;
	
	/**
	 * -2: for client without login.
	 * -1: for client with login.
	 *  1: for server with login.
	 *  2: for server without login.
	 */
	protected Integer type;

	public String toString() {
		return "{'id':"
				+id+",'userid': "
				+userid+", 'name': '"
				+name+"', 'description': '"
				+description+"', 'homepage': '"
				+homepage+"', 'contact': '"
				+contact+"', 'email': '"
				+email+"', 'type': "
				+type+", 'calling': '"
				+calling+"', 'parameters': '"
				+parameters+"'}";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCalling() {
		return calling;
	}

	public void setCalling(String calling) {
		this.calling = calling;
	}

}
