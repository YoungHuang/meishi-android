package meishi.persistence;

import java.util.Date;

import meishi.domain.Address;
import meishi.domain.Shop;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
	private final static String DATABASE_NAME = "meishi.db";
	private final static int DATABASE_VERSION = 1;

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table recentlyHistory(shopid integer primary key autoincrement, content text not null, date long);");
		for (int i = 0; i < 10; i++) {
			Shop shop = new Shop();
			shop.setId(i);
			shop.setName(String.valueOf(i));
			shop.setRating(0.5f + i);
			shop.setPhone("5555");
			shop.setStartPrice(20d);
			shop.setCity("nanjing");
			String sql = "insert into recentlyHistory(content, date) values(?, ?)";
			String content = shop.beanToString();
			db.execSQL(sql, new Object[] {content, new Date().getTime()});
		}
		
		db.execSQL("create table searchHistory(_id integer primary key autoincrement, keywords text not null, date long);");
		String sql = "insert into searchHistory(keywords, date) values(?, ?)";
		db.execSQL(sql, new Object[] {"肯德基", new Date().getTime()});
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
}
