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
	public final static Integer STATUS_NEW = 0;
	public final static Integer STATUS_CONFIRM = 1;
	
	private Integer id;
	private Shop shop;
	private Double totalAmount = 0.0;
	private Integer totalCount = 0;
	private Integer people = 0;
	private String description;
	private Integer status = STATUS_NEW;
	private List<OrderItem> orderItemList = new ArrayList<OrderItem>();

	public OrderItem findOrderItem(Integer dishId) {
		for (OrderItem orderItem : orderItemList) {
			if (orderItem.getDish().getId().equals(dishId))
				return orderItem;
		}
		
		return null;
	}

	public void addOrderItem(OrderItem orderItem) {
		orderItemList.add(orderItem);
	}
	
	public void deleteOrderItem(OrderItem orderItem) {
		orderItemList.remove(orderItem);
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
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
