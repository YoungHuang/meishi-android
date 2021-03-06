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
	/** 菜品数量 **/
	@DatabaseField
	private int count;
	@DatabaseField
	private boolean defaul;
	@DatabaseField(foreign = true, index = true)
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean isDefaul() {
		return defaul;
	}

	public void setDefaul(boolean defaul) {
		this.defaul = defaul;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
	
	public static DishCategory findDefualt(List<DishCategory> dishCategoryList) {
		for (DishCategory dishCategory : dishCategoryList) {
			if (dishCategory.isDefaul()) {
				return dishCategory;
			}
		}
		
		return null;
	}
}
