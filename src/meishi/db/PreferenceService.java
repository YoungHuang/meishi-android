package meishi.db;

import meishi.domain.City;
import meishi.service.CityService;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * 配置信息
 * @author yhuang
 *
 */
public class PreferenceService {
	private final static String PREFERENCES_NAME = "Preferences";
	private final static String CITY_NAME = "CityName";
	private final static String COOKIE = "Cookie";
	
	private CityService cityService;
	private SharedPreferences preferences;
	
	private City city;
	private String cookie;
	
	public PreferenceService(Context context, CityService cityService) {
		preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
		this.cityService = cityService;
	}
	
	public void setCity(City city) {
		this.city = city;
		preferences.edit().putString(CITY_NAME, city.getName()).commit();
	}
	
	public City getCity() {
		if (city == null) {
			String name = preferences.getString(CITY_NAME, null);
			city = cityService.findByName(name);
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
}
