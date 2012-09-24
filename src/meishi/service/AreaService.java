package meishi.service;

import java.util.List;

import meishi.db.base.DaoSupport;
import meishi.domain.Area;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AreaService extends DaoSupport<Area> {
	private static final String TAG = "AreaService";

	public static void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "createTable");
	}
	
	public List<Area> findAllByDistrictId(Integer districtId) {
		// TODO
		return null;
	}
	
	public int getCountByDistrictId(Integer districtId) {
		// TODO
		return 0;
	}
}
