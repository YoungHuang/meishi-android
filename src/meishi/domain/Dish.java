package meishi.domain;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * 菜单
 * @author yonghuang
 *
 */
public class Dish {
	private static final String TAG = "Dish";
	
	private Integer id;
	/** 菜名 **/
	private String name;
	/** 价格 **/
	private Double price;
	/** 描述 **/
	private String description;
	
	public Dish() {
		
	}
	
	public Dish(Integer id, String name, Double price, String description) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
	}

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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void stringToBean(String str) {
		try {
			JSONObject object = new JSONObject(str);
			id = object.getInt("id");
			name = object.getString("name");
			price = object.getDouble("price");
		} catch (JSONException e) {
			Log.d(TAG, "stringToBean() exception", e);
		}
	}
}
