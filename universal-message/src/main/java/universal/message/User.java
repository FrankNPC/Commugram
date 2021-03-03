package universal.message;

/**
 * user information.
 * for transfer data as protocol only.
 * 
 * @author FrankNPC
 *
 */
public class User implements java.io.Serializable{
	
	private static final long serialVersionUID = -4839212448150067425L;

	public User() {}
	
	protected Long userid;
	
	/**
	 * for check the user login.
	 */
	protected String loginname;
	
	/**
	 * password.
	 * should not be plain text. it can be MD5 code or something else.
	 */
	protected String loginpassword;

	/**
	 * for check the login
	 */
	protected String logintoken;
	
	public String toString() {
		return "{'userid': "
				+(userid==null?0:userid)+", 'loginname': '"
				+(loginname==null?"":loginname)+"', 'loginpassword': '"
				+(loginpassword==null?"":loginpassword)+"', 'logintoken': '"
				+(logintoken==null?"":logintoken)+"'}";
	}
	
	public String getLoginpassword() {
		return loginpassword;
	}

	public void setLoginpassword(String loginpassword) {
		this.loginpassword = loginpassword;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getLogintoken() {
		return logintoken;
	}

	public void setLogintoken(String logintoken) {
		this.logintoken = logintoken;
	}
	
	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

}
