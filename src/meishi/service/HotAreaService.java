package meishi.service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import meishi.db.DaoSupport;
import meishi.domain.HotArea;
import meishi.network.NetworkService;
import meishi.network.ResponseException;
import meishi.network.ResponseMessage;
import android.util.Log;

import com.google.gson.reflect.TypeToken;

public class HotAreaService extends DaoSupport<HotArea, Integer> {
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
					Log.e(TAG, "saveList() exception", e);
				}
			}
		}
	}

	public void loadAll(Integer cityId, AsyncTaskCallBack<List<HotArea>> callBack) {
		List<HotArea> hotAreaList = null;
		try {
			hotAreaList = findAllByCityId(cityId);
		} catch (SQLException e) {
			Log.e(TAG, "loadAll() exception", e);
		}

		if (hotAreaList == null || hotAreaList.isEmpty()) {
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
			Type type = new TypeToken<List<HotArea>>() { }.getType();
			List<HotArea> hotAreaList = null;
			try {
				hotAreaList = NetworkService.getForList(url, type, params[0]);
			} catch (IOException e) {
				Log.e(TAG, "doInBackground", e);
				responseMessage = new ResponseMessage("Network error!");
				responseMessage.setErrorCode(ResponseMessage.NETWORK_ERROR);
			} catch (ResponseException e) {
				Log.e(TAG, "doInBackground", e);
				responseMessage = e.getResponseMessage();
			}
			saveList(hotAreaList);

			return hotAreaList;
		}
	}
}
