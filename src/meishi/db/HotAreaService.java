package meishi.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import meishi.db.base.DaoSupport;
import meishi.domain.HotArea;

public class HotAreaService extends DaoSupport<HotArea> {
	private static final String TAG = "HotAreaService";

	public static void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "createTable");
	}
}
