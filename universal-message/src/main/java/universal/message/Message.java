package universal.message;

import java.io.Serializable;

/**
 * messages between client and server.
 * for transfer data as protocol only.
 * 
 * @author FrankNPC
 *
 */
public class Message<T extends Serializable> implements Serializable{
	
	private static final long serialVersionUID = -4839212448150067425L;
	
	public Message() {}

	/**
	 * defined message type
	 * lesser than 0 is kept for negative use. Generally for error.
	 * greater than 0 is kept for positive use.
	 */
	protected Integer type;
	
	/**
	 * id. just for storage.
	 * 
	 */
	protected Integer id;

	/**
	 * the owner.
	 */
	protected Long userid;
	
	/**
	 * the user will sent to.
	 */
	protected Long targetid;
	
	/**
	 * create time.
	 */
	protected Integer createtime;
	
	/**
	 * the content
	 * depends on the @type, it would be html/json/plain or various type.
	 */
	protected T content;

	public String toString() {
		return "{'type': "
				+(type==null?0:type)+", 'targetid': "
				+(targetid==null?0:targetid)+", 'id': "
				+(id==null?0:id)+", 'userid': "
				+(userid==null?0:userid)+", 'createtime': "
				+(createtime==null?0:createtime)+", 'content': '"
				+(content==null?"":content.toString())+"'}";
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Integer getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Integer createtime) {
		this.createtime = createtime;
	}

	public Long getTargetid() {
		return targetid;
	}

	public void setTargetid(Long targetid) {
		this.targetid = targetid;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

}
