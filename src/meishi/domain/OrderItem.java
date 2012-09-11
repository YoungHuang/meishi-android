package meishi.domain;

/**
 * 订单项
 * @author yhuang
 *
 */
public class OrderItem {
	private Dish dish;
	private Integer count = 0;

	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
