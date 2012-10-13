package meishi.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


/**
 * 菜单
 * @author yonghuang
 *
 */
@DatabaseTable
public class Dish {
	@DatabaseField(id = true)
	private Integer id;
	/** 菜名 **/
	@DatabaseField
	private String name;
	/** 价格 **/
	@DatabaseField
	private Double price;
	/** 描述 **/
	@DatabaseField
	private String description;
	@DatabaseField(foreign = true)
	private DishCategory dishCategory;
	
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

	public DishCategory getDishCategory() {
		return dishCategory;
	}

	public void setDishCategory(DishCategory dishCategory) {
		this.dishCategory = dishCategory;
	}
}
