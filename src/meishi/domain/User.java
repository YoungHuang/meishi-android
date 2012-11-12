package meishi.domain;

/**
 * 用户
 * @author yhuang
 *
 */
public class User {
	private Integer id;
	/** 姓名 **/
	private String name;
	/** 地址 **/
	private String address;
	/** 电话 **/
	private String phone;
	private String cookie;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
}
