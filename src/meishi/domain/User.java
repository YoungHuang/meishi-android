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
	private Address address;
	/** 电话 **/
	private String phone;
	
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
	
	public Address getAddress() {
		return address;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
