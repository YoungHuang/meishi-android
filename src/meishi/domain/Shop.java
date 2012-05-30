package meishi.domain;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * 餐厅
 * @author yhuang
 *
 */
public class Shop {
	private static final String TAG = "Shop";
	
	private Integer id;
	/** 餐厅名 **/
	private String name;
	/** 地址 **/
	private Address address;
	/** 评价 **/
	private float rating;
	/** 电话 **/
	private String phone;
	/** 起送金额 **/
	private Double startPrice;
	/** 简介 **/
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
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

	public String beanToString() {
		JSONObject object = new JSONObject();
		try {
			object.put("shopid", id)
				.put("name", name)
				.put("rating", rating)
				.put("phone", phone)
				.put("startPrice", startPrice)
				.put("address", address.beanToString());
		} catch (JSONException e) {
			Log.d(TAG, "beanToString() exception", e);
		}
		
		return object.toString();
	}
	
	public void stringToBean(String str) {
		try {
			JSONObject object = new JSONObject(str);
			id = object.getInt("id");
			name = object.getString("name");
			rating = (float) object.getDouble("rating");
			phone = object.getString("phone");
			startPrice = object.getDouble("startPrice");
			address = new Address();
			address.setCity(object.getString("city"));
//			address.stringToBean(object.getString("address"));
			
		} catch (JSONException e) {
			Log.d(TAG, "stringToBean() exception", e);
		}
	}
}
