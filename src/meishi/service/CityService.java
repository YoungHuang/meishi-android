package meishi.service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;

import meishi.db.DaoSupport;
import meishi.domain.City;
import meishi.network.NetworkService;
import meishi.network.ResponseException;
import meishi.network.ResponseMessage;
import android.util.Log;

import com.google.gson.reflect.TypeToken;

public class CityService extends DaoSupport<City, Integer> {
	private static final String url = NetworkService.hostUrl + "/city/hotCityList";
	
	public CityService() throws SQLException {
		super(City.class);
	}
	
	public City findByName(String name) {
		City city = null;
		try {
			List<City> cityList = queryForEq("name", name);
			if (cityList != null && cityList.size() > 0) {
				city = cityList.get(0);
			}
		} catch (SQLException e) {
			Log.e(TAG, "findByName() exception", e);
		}
		
		return city;
	}
	
	public void saveList(List<City> hotCityList) {
		if (hotCityList != null) {
			for (City city : hotCityList) {
				try {
					create(city);
				} catch (SQLException e) {
					Log.e(TAG, "saveList() exception", e);
				}
			}
		}
	}
	
	public void loadHotCities(AsyncTaskCallBack<List<City>> callBack) {
		List<City> cityList = null;
		try {
			cityList = queryForAll();
		} catch (SQLException e) {
			Log.e(TAG, "loadHotCities() exception", e);
		}
		
		if (cityList == null || cityList.isEmpty()) {
			new LoadHotCitiesAsyncTask(callBack).execute();
		} else {
			callBack.onSuccess(cityList);
		}
	}
	
	private class LoadHotCitiesAsyncTask extends BaseAsyncTask<Void, Void, List<City>> {
		public LoadHotCitiesAsyncTask(AsyncTaskCallBack<List<City>> callBack) {
			this.callBack = callBack;
		}

		@Override
		protected List<City> doInBackground(Void... params) {
			Type type = new TypeToken<List<City>>() { }.getType();
			List<City> hotCityList = null;
			try {
				hotCityList = NetworkService.getForList(url, type, null);
			} catch (IOException e) {
				Log.e(TAG, "doInBackground", e);
				responseMessage = new ResponseMessage("Network error!");
				responseMessage.setErrorCode(ResponseMessage.NETWORK_ERROR);
			} catch (ResponseException e) {
				Log.e(TAG, "doInBackground", e);
				responseMessage = e.getResponseMessage();
			}
			saveList(hotCityList);

			return hotCityList;
		}
	}
}
