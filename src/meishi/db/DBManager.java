package meishi.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import meishi.db.base.SQLiteHelper;
import meishi.domain.Shop;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class DBManager {
	private static final String TAG = "DBManager";
	
	private static SQLiteHelper dbHelper;
	private static DBManager dbManager;
	
	private DBManager(Context context) {
//		dbHelper = new SQLiteHelper(context);
	}
	
	public static DBManager open(Context context) {
		if (dbManager == null) {
			dbManager = new DBManager(context);
		}
		
		return dbManager;
	}
	
	public static DBManager getDBManager() {
		return dbManager;
	}
	
	public static SQLiteHelper getDBHelper() {
		return dbHelper;
	}
	
	public void saveRecentlyHistory(Shop shop) {
		Log.d(TAG, "saveRecentlyHistory");
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String sql = "insert into recentlyHistory(content, date) values(?, ?)";
		String content = shop.beanToString();
		db.execSQL(sql, new Object[] {content, new Date().getTime()});
	}
	
	public Shop findRecentlyHistory(Integer id) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String sql = "select * from recentlyHistory where shopid=?";
		Cursor cursor = db.rawQuery(sql, new String[] {id.toString()});
		Shop shop = null;
		Log.d(TAG, "" + cursor.getCount());
		if (cursor.moveToFirst()) {
			shop = new Shop();
			shop.stringToBean(cursor.getString(cursor.getColumnIndex("content")));
		}
		cursor.close();
		
		return shop;
	}
	
	public List<Shop> getScrollRecentlyHistory(Integer offset, Integer maxResult) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String sql = "select * from recentlyHistory limit ?,?";
		Cursor cursor = db.rawQuery(sql, new String[] {offset.toString(), maxResult.toString()});
		List<Shop> shopList = new ArrayList<Shop>();
		Shop shop = null;
		while (cursor.moveToNext()) {
			shop = new Shop();
			shop.stringToBean(cursor.getString(cursor.getColumnIndex("content")));
			shopList.add(shop);
		}
		cursor.close();
		
		return shopList;
	}
	
	public void clearRecentlyHistory() {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String sql = "delete from recentlyHistory";
		db.execSQL(sql);
	}
}
