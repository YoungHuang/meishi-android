package meishi;

import meishi.db.PreferenceService;
import meishi.db.base.SQLiteHelper;
import meishi.service.AreaService;
import meishi.service.CityService;
import meishi.service.HotAreaService;
import android.app.Application;

public class MainApplication extends Application {
	private PreferenceService preferenceService;
	private CityService cityService;
	private AreaService areaService;
	private HotAreaService hotAreaService;

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
		hotAreaService = new HotAreaService();
		preferenceService = new PreferenceService(this, cityService);
	}
	
	public CityService getCityService() {
		return cityService;
	}
	
	public AreaService getAreaService() {
		return areaService;
	}
	
	public HotAreaService getHotAreaService() {
		return hotAreaService;
	}
}
