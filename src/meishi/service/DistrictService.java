package meishi.service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import meishi.db.DaoSupport;
import meishi.domain.Area;
import meishi.domain.District;
import meishi.network.NetworkService;
import meishi.network.ResponseException;
import meishi.network.ResponseMessage;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.reflect.TypeToken;

public class DistrictService extends DaoSupport<District, Integer> {
	private static final String districtUrl = NetworkService.hostUrl + "/district/list";
	private static final String areaUrl = NetworkService.hostUrl + "/area/list";

	private AreaService areaService;

	public DistrictService(AreaService areaService) throws SQLException {
		super(District.class);
		this.areaService = areaService;
	}

	public static void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "createTable");
	}

	public List<District> findAllByCityId(Integer cityId) {
		// TODO
		return null;
	}

	public void saveList(List<District> districtList) {
		if (districtList != null) {
			for (District district : districtList) {
				try {
					create(district);
				} catch (SQLException e) {
					Log.e(TAG, "saveList() exception", e);
				}
			}
		}
	}

	public void loadAllByCityId(Integer cityId, AsyncTaskCallBack<List<District>> callBack) {
		List<District> districtList = findAllByCityId(cityId);

		if (districtList == null) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("cityId", cityId.toString());
			new DistrictAsyncTask(callBack).execute(params);
		} else {
			callBack.onSuccess(districtList);
		}
	}

	private class DistrictAsyncTask extends BaseAsyncTask<Map<String, String>, Void, List<District>> {
		public DistrictAsyncTask(AsyncTaskCallBack<List<District>> callBack) {
			this.callBack = callBack;
		}

		@Override
		protected List<District> doInBackground(Map<String, String>... params) {
			List<District> districtList = null;
			try {
				// Loading district list from Net
				Type type = new TypeToken<List<District>>() {}.getType();

				districtList = NetworkService.getForList(districtUrl, type, params[0]);

				saveList(districtList);

				// Loading area list from Net
				type = new TypeToken<List<Area>>() { }.getType();
				List<Area> areaList = null;
				areaList = NetworkService.getForList(areaUrl, type, params[0]);
				areaService.saveList(areaList);
			} catch (IOException e) {
				Log.e(TAG, "doInBackground", e);
				responseMessage = new ResponseMessage("Network error!");
				responseMessage.setErrorCode(ResponseMessage.NETWORK_ERROR);
			} catch (ResponseException e) {
				Log.e(TAG, "doInBackground", e);
				responseMessage = e.getResponseMessage();
			}
			
			return districtList;
		}
	}
}
