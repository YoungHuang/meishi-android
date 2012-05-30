package meishi.domain;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * 地址
 * 
 * @author yhuang
 * 
 */
public class Address {
	private static final String TAG = "Address";
	
	String province;
	String city;
	String district;
	String road;
	/** 纬度 **/
	private Double latitude;
	/** 经度 **/
	private Double longitude;
	
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getRoad() {
		return road;
	}

	public void setRoad(String road) {
		this.road = road;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public String beanToString() {
		JSONObject object = new JSONObject();
		try {
			object.put("city", city);
		} catch (JSONException e) {
			Log.d(TAG, "beanToString() exception", e);
		}
		
		return object.toString();
	}
	
	public void stringToBean(String str) {
		try {
			Log.d(TAG, str);
			JSONObject object = new JSONObject(str);
			city = object.getString("city");
			
		} catch (JSONException e) {
			Log.d(TAG, "stringToBean() exception", e);
		}
	}

	@Override
	public String toString() {
		return city + district + road;
	}
}
