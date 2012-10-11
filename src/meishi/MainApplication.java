package meishi;

import meishi.db.PreferenceService;
import meishi.db.base.SQLiteHelper;
import meishi.service.AreaService;
import meishi.service.CityService;
import meishi.service.DistrictService;
import meishi.service.HotAreaService;
import meishi.service.ShopService;
import android.app.Application;

public class MainApplication extends Application {
	private PreferenceService preferenceService;
	private CityService cityService;
	private DistrictService districtService;
	private AreaService areaService;
	private HotAreaService hotAreaService;
	private ShopService shopService;

	@Override
	public void onCreate() {
		super.onCreate();
		// !!!Must initialize database before initialize services which use database
		initDB();
		initVariables();
	}

	private void initDB() {
		SQLiteHelper.setContext(this);
	}
	
	private void initVariables() {
		cityService = new CityService();
		areaService = new AreaService();
		districtService = new DistrictService(areaService);
		hotAreaService = new HotAreaService();
		preferenceService = new PreferenceService(this, cityService);
		shopService = new ShopService();
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
}
