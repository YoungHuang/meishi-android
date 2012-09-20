package meishi.db.base;

import java.util.Date;

import meishi.domain.Shop;
import meishi.service.AreaService;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLiteHelper extends SQLiteOpenHelper {
	private final static String DATABASE_NAME = "meishi.db";
	private final static int DATABASE_VERSION = 1;

	private static SQLiteHelper instance;
	private static Context context;
	
	private SQLiteHelper() {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public static SQLiteHelper getInstance() {
		if (instance != null)
			return instance;
		
		synchronized(SQLiteHelper.class) {
			instance = new SQLiteHelper();
			return instance;
		}
	}
	
	public static void setContext(Context context) {
		SQLiteHelper.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		AreaService.onCreate(db);
		
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
