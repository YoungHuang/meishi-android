package meishi.service;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import meishi.db.DaoSupport;
import meishi.domain.HotArea;
import meishi.network.NetworkService;
import meishi.utils.ResponseCode;

import android.util.Log;

import com.google.gson.reflect.TypeToken;

public class HotAreaService extends DaoSupport<HotArea, Integer> {
	private static final String TAG = "HotAreaService";
	
	private static final String url = NetworkService.hostUrl + "/hotArea/list";

	public HotAreaService() throws SQLException {
		super(HotArea.class);
	}
	
	public List<HotArea> findAllByCityId(Integer cityId) throws SQLException {
		return queryForEq("city_id", cityId);
	}
	
	public void saveList(List<HotArea> hotAreaList) {
		if (hotAreaList != null) {
			for (HotArea hotArea : hotAreaList) {
				try {
					create(hotArea);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void loadAll(Integer cityId, AsyncTaskCallBack<List<HotArea>> callBack) {
		List<HotArea> hotAreaList = null;
		try {
			hotAreaList = findAllByCityId(cityId);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (hotAreaList == null) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("cityId", cityId.toString());
			new HotAreaAsyncTask(callBack).execute(params);
		} else {
			callBack.onSuccess(hotAreaList);
		}
	}

	private class HotAreaAsyncTask extends BaseAsyncTask<Map<String, String>, Void, List<HotArea>> {
		public HotAreaAsyncTask(AsyncTaskCallBack<List<HotArea>> callBack) {
			this.callBack = callBack;
		}

		@Override
		protected List<HotArea> doInBackground(Map<String, String>... params) {
			Type type = new TypeToken<List<HotArea>>(){}.getType();
			List<HotArea> hotAreaList = null;
			try {
				hotAreaList = NetworkService.getForList(url, type, params[0]);
				saveList(hotAreaList);
			} catch (Exception e) {
				Log.e(TAG, "doInBackground", e);
				code = ResponseCode.NETWORK_ERROR;
			}
			
			return hotAreaList;
		}
	}
}
