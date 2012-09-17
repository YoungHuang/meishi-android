package meishi.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import meishi.db.base.DaoSupport;
import meishi.domain.Area;

public class AreaService extends DaoSupport<Area> {
	private static final String TAG = "AreaService";

	public static void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "createTable");
	}
}
