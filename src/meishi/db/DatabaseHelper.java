package meishi.db;

import java.sql.SQLException;

import meishi.domain.Area;
import meishi.domain.City;
import meishi.domain.Dish;
import meishi.domain.DishCategory;
import meishi.domain.District;
import meishi.domain.HotArea;
import meishi.domain.Shop;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	private static final String TAG = "DatabaseHelper";

	private final static String DATABASE_NAME = "meishi.db";
	private final static int DATABASE_VERSION = 1;

	private static Context context;
	private static DatabaseHelper databaseHelper = null;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public static DatabaseHelper getHelper() {
		if (databaseHelper != null) {
			return databaseHelper;
		}
		synchronized (DatabaseHelper.class) {
			if (databaseHelper == null) {
				databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
			}
			return databaseHelper;
		}
	}

	public static void setContext(Context context) {
		DatabaseHelper.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, City.class);
			TableUtils.createTable(connectionSource, HotArea.class);
			TableUtils.createTable(connectionSource, District.class);
			TableUtils.createTable(connectionSource, Area.class);
			TableUtils.createTable(connectionSource, Dish.class);
			TableUtils.createTable(connectionSource, DishCategory.class);
			TableUtils.createTable(connectionSource, Shop.class);
		} catch (SQLException e) {
			Log.e(TAG, "Unable to create datbases", e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
		// TODO Auto-generated method stub

	}
}
