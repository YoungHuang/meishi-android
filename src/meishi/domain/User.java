package meishi.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 用户
 * @author yhuang
 *
 */
@DatabaseTable
public class User {
	@DatabaseField(id = true)
	private Integer id;
	/** 姓名 **/
	@DatabaseField
	private String name;
	/** 地址 **/
	@DatabaseField
	private String address;
	/** 电话 **/
	@DatabaseField
	private String phone;
	@DatabaseField
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
