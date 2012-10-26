package meishi;

import java.sql.SQLException;

import meishi.db.DatabaseHelper;
import meishi.db.PreferenceService;
import meishi.service.AreaService;
import meishi.service.CityService;
import meishi.service.DishCategoryService;
import meishi.service.DistrictService;
import meishi.service.HotAreaService;
import meishi.service.OrderService;
import meishi.service.ShopService;
import android.app.Application;

public class MainApplication extends Application {
	private PreferenceService preferenceService;
	private CityService cityService;
	private DistrictService districtService;
	private AreaService areaService;
	private HotAreaService hotAreaService;
	private ShopService shopService;
	private OrderService orderService;
	private DishCategoryService dishCategoryService;

	@Override
	public void onCreate() {
		super.onCreate();
		// !!!Must initialize database before initialize services which use database
		initDB();
		initVariables();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		DatabaseHelper.getHelper().close();
	}

	private void initDB() {
		DatabaseHelper.setContext(this);
	}
	
	private void initVariables() {
		try {
			cityService = new CityService();
			areaService = new AreaService();
			districtService = new DistrictService(areaService);
			hotAreaService = new HotAreaService();
			preferenceService = new PreferenceService(this, cityService);
			shopService = new ShopService();
			orderService = new OrderService();
			dishCategoryService = new DishCategoryService();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public PreferenceService getPreferenceService() {
		return preferenceService;
	}
	
	public CityService getCityService() {
		return cityService;
	}
	
	public DistrictService getDistrictService() {
		return districtService;
	}
	
	public AreaService getAreaService() {
		return areaService;
	}
	
	public HotAreaService getHotAreaService() {
		return hotAreaService;
	}
	
	public ShopService getShopService() {
		return shopService;
	}
	
	public OrderService getOrderService() {
		return orderService;
	}
	
	public DishCategoryService getDishCategoryService() {
		return dishCategoryService;
	}
}
