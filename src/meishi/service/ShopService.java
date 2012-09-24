package meishi.service;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import meishi.db.base.DaoSupport;
import meishi.domain.Shop;

public class ShopService extends DaoSupport<Shop> {
	private static final String TAG = "ShopService";

	public static void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "createTable");
	}
}
