package meishi.service;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import meishi.db.DaoSupport;
import meishi.domain.Area;
import meishi.domain.District;
import meishi.network.NetworkService;
import meishi.utils.ResponseCode;
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
			// Loading district list from Net
			Type type = new TypeToken<List<District>>(){}.getType();
			List<District> districtList = null;
			try {
				districtList = NetworkService.getForList(districtUrl, type, params[0]);
				saveList(districtList);
			} catch (Exception e) {
				code = ResponseCode.NETWORK_ERROR;
			}
			
			// Loading area list from Net
			type = new TypeToken<List<Area>>(){}.getType();
			List<Area> areaList = null;
			try {
				areaList = NetworkService.getForList(areaUrl, type, params[0]);
				areaService.saveList(areaList);
			} catch (Exception e) {
				code = ResponseCode.NETWORK_ERROR;
			}
			
			return districtList;
		}
	}
}
