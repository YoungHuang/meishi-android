package meishi.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import meishi.db.base.SQLiteHelper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SearchHistoryDao {
	private static final String TAG = "SearchHistoryDB";
	private static final long LIMIT = 15;
	
	public static void saveSearchHistory(String keywords) {
		Log.d(TAG, "saveSearchHistory");
		
		SQLiteHelper dbHelper = DBManager.getDBHelper();
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String sql = "insert into searchHistory(content, date) values(?, ?)";
		db.execSQL(sql, new Object[] {keywords, new Date().getTime()});
		
		checkLimit();
	}
	
	public static List<String> getAllSearchHistory() {
		SQLiteHelper dbHelper = DBManager.getDBHelper();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String sql = "select * from searchHistory";
		Cursor cursor = db.rawQuery(sql, null);
		List<String> searchHistoryList = new ArrayList<String>();
		while (cursor.moveToNext()) {
			String keywords = cursor.getString(cursor.getColumnIndex("keywords"));
			searchHistoryList.add(keywords);
		}
		cursor.close();
		
		return searchHistoryList;
	}
	
	public static Cursor getAllSearchHistoryCursor() {
		SQLiteHelper dbHelper = DBManager.getDBHelper();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String sql = "select * from searchHistory";
		Cursor cursor = db.rawQuery(sql, null);
		
		return cursor;
	}
	
	public static void clearAllSearchHistory() {
		SQLiteHelper dbHelper = DBManager.getDBHelper();
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String sql = "delete from searchHistory";
		db.execSQL(sql);
	}
	
	public static void checkLimit() {
		SQLiteHelper dbHelper = DBManager.getDBHelper();
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String sql = "select count(*) from searchHistory";
		Cursor cursor = db.rawQuery(sql, null);
		cursor.moveToFirst();
		long count = cursor.getLong(0);
		cursor.close();
		
		if (count > LIMIT) {
			sql = "select _id from searchHistory";
			cursor = db.rawQuery(sql, null);
			String deleteIds = "";
			while (cursor.moveToNext()) {
				deleteIds += cursor.getString(0) + ",";
			}
			deleteIds = deleteIds.substring(0, deleteIds.length() - 1);
			
			sql = "delete from searchHistory where _id in(" + deleteIds + ")";
			db.execSQL(sql);
			cursor.close();
		}
	}
}
