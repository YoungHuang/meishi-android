package meishi.service;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import meishi.db.base.DaoSupport;
import meishi.domain.City;

public class CityService extends DaoSupport<City> {
	private static final String TAG = "CityService";

	public static void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "createTable");
	}
	
	public City findByName(String name) {
		// TODO
		return null;
	}
}
