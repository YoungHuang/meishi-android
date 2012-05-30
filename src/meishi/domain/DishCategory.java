package meishi.domain;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * 菜单类别
 * @author yhuang
 *
 */
public class DishCategory {
	private static final String TAG = "DishCategory";
	
	private Integer id;
	/** 菜单类别名 **/
	private String name;
	/** 菜单列表 **/
	private List<Dish> dishList;
	/** 菜品数量 **/
	private int count;
	
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

	public List<Dish> getDishList() {
		return dishList;
	}

	public void setDishList(List<Dish> dishList) {
		this.dishList = dishList;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public String beanToString() {
		JSONObject object = new JSONObject();
		try {
			object.put("name", name);
		} catch (JSONException e) {
			Log.d(TAG, "beanToString() exception", e);
		}
		
		return object.toString();
	}
}
