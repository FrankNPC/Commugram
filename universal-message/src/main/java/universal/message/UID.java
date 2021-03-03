package universal.message;

/**
 * for creating and storing the universal unique ID.
 * for transfer data as protocol only.
 * 
 * @TODO: the data center is obligated to clean the data belong the former user, or notify other data center to do so when the create time is set to 0.
 * 
 * @author FrankNPC
 *
 */
public class UID implements java.io.Serializable{
	
	private static final long serialVersionUID = -4832212428150067425L;
	
	public UID() {}
	
	/**
	 * Universal unique ID for user.
	 * each service provider could build your unique id center, so you can hold all user data.
	 */
	private Long id;

	public static final int CLEAR_UID = 0;
	
	/**
	 * save the created time.
	 *     0: has released, can be assigned for another user.
	 * Non-0: currently occupied by user.
	 */
	private Integer createtime;
	
	public String toString() {
		return "{'id': "+id+", 'createtime': "+createtime+"}";
	}


	public Integer getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Integer createtime) {
		this.createtime = createtime;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

}
