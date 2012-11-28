package meishi.db;

import java.sql.SQLException;

import meishi.MainApplication;
import meishi.domain.City;
import meishi.domain.User;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * 配置信息
 * 
 * @author yhuang
 * 
 */
public class PreferenceService {
	private final static String TAG = "PreferenceService";
	
	private final static String PREFERENCES_NAME = "Preferences";
	private final static String CITY_NAME = "CityName";
	private final static String COOKIE = "Cookie";
	private final static String USER_ID = "UserId";

	private static PreferenceService instance;

	private MainApplication application;
	private SharedPreferences preferences;

	private City city;
	private String cookie;
	private User user;

	public PreferenceService() {
		instance = this;
		application = MainApplication.getInstance();
		preferences = application.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
	}
	
	public static PreferenceService getInstance() {
		return instance;
	}

	public void setCity(City city) {
		this.city = city;
		preferences.edit().putString(CITY_NAME, city.getName()).commit();
	}

	public City getCity() {
		if (city == null) {
			String name = preferences.getString(CITY_NAME, null);
			if (name == null) {
				return null;
			}
			city = application.getCityService().findByName(name);
		}

		return city;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
		preferences.edit().putString(COOKIE, cookie).commit();
	}

	public String getCookie() {
		return cookie;
	}

	public void setUser(User user) {
		this.user = user;
		preferences.edit().putInt(USER_ID, user.getId()).commit();
	}

	public User getUser() {
		if (user == null) {
			try {
				user = application.getUserService().queryForId(preferences.getInt(USER_ID, -1));
			} catch (SQLException e) {
				Log.e(TAG, "getUser", e);
			}
		}
		
		return user;
	}
}
