package meishi.domain;

import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 餐厅
 * @author yhuang
 *
 */
@DatabaseTable
public class Shop {
	@DatabaseField(id = true)
	private Integer id;
	/** 餐厅名 **/
	@DatabaseField
	private String name;
	/** 地址 **/
	@DatabaseField
	private String province;
	@DatabaseField
	private String city;
	@DatabaseField
	private String district;
	@DatabaseField
	private String road;
	/** 纬度 **/
	@DatabaseField
	private Double latitude;
	/** 经度 **/
	@DatabaseField
	private Double longitude;
	/** 评价 **/
	@DatabaseField
	private float rating;
	/** 评论数 **/
	@DatabaseField
	private Integer commentCount;
	/** 电话 **/
	@DatabaseField
	private String phone;
	/** 起送金额 **/
	@DatabaseField
	private Double startPrice;
	/** 简介 **/
	@DatabaseField
	private String description;
	/** 菜单目录列表 **/
	private List<DishCategory> dishCategories;

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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getRoad() {
		return road;
	}

	public void setRoad(String road) {
		this.road = road;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}
	
	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Double getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(Double startPrice) {
		this.startPrice = startPrice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<DishCategory> getDishCategories() {
		return dishCategories;
	}

	public void setDishCategories(List<DishCategory> dishCategories) {
		this.dishCategories = dishCategories;
	}
}
