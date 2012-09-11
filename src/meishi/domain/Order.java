package meishi.domain;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * 订单
 * @author yonghuang
 *
 */
public class Order {
	private static final String TAG = "Order";
	
	private Integer id;
	private Shop shop;
	private Double totalAmount = 0.0;
	private Integer totalCount = 0;
	private Integer people = 0;
	private String description;
	private List<OrderItem> orderDishList = new ArrayList<OrderItem>();

	public OrderItem findOrderDish(Integer dishId) {
		for (OrderItem orderDish : orderDishList) {
			if (orderDish.getDish().getId().equals(dishId))
				return orderDish;
		}
		
		return null;
	}

	public void addOrderDish(OrderItem orderDish) {
		orderDishList.add(orderDish);
	}
	
	public void deleteOrderDish(OrderItem orderDish) {
		orderDishList.remove(orderDish);
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getPeople() {
		return people;
	}

	public void setPeople(Integer people) {
		this.people = people;
	}

	public List<OrderItem> getOrderDishList() {
		return orderDishList;
	}

	public void setOrderDishList(List<OrderItem> orderDishList) {
		this.orderDishList = orderDishList;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String beanToString() {
		JSONObject object = new JSONObject();
		try {
			object.put("people", people);
			JSONArray jsonarray = new JSONArray();
			for (OrderItem orderDish : orderDishList) {
				JSONObject obj = new JSONObject();
				obj.put("id", orderDish.getDish().getId());
				obj.put("count", orderDish.getCount());
				jsonarray.put(obj);
			}
			object.put("orderItemList", jsonarray);
		} catch (JSONException e) {
			Log.d(TAG, "beanToString() exception", e);
		}
		
		return object.toString();
	}
}
