package meishi;

import meishi.db.AreaService;
import meishi.db.HotAreaService;
import meishi.db.base.SQLiteHelper;
import android.app.Application;

public class MainApplication extends Application {
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
		areaService = new AreaService();
		hotAreaService = new HotAreaService();
	}
	
	public AreaService getAreaService() {
		return areaService;
	}
	
	public HotAreaService getHotAreaService() {
		return hotAreaService;
	}
}
