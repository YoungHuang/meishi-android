package meishi.domain;

import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 菜单类别
 * @author yhuang
 *
 */
@DatabaseTable
public class DishCategory {
	@DatabaseField(id = true)
	private Integer id;
	/** 菜单类别名 **/
	@DatabaseField
	private String name;
	/** 菜单列表 **/
	@DatabaseField
	private List<Dish> dishList;
	/** 菜品数量 **/
	@DatabaseField
	private int count;
	@DatabaseField(foreign = true)
	private Shop shop;
	
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

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
}
