package meishi.domain;

/**
 * 订单项
 * 
 * @author yhuang
 * 
 */
public class OrderItem {
	private Dish dish;
	private Double amount = 0.0;
	private Integer count = 0;

	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
