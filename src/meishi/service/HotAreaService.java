package meishi.service;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import meishi.db.base.DaoSupport;
import meishi.domain.HotArea;
import meishi.network.NetworkService;
import meishi.utils.ResponseCode;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.reflect.TypeToken;

public class HotAreaService extends DaoSupport<HotArea> {
	private static final String TAG = "HotAreaService";
	
	private static final String url = NetworkService.hostUrl + "/hotArea/list";

	public static void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "createTable");
	}
	
	public List<HotArea> findAllByCityId(Integer cityId) {
		// TODO
		return null;
	}
	
	public void saveList(List<HotArea> hotAreaList) {
		if (hotAreaList != null) {
			for (HotArea hotArea : hotAreaList) {
				save(hotArea);
			}
		}
	}

	public void loadAll(Integer cityId, AsyncTaskCallBack<List<HotArea>> callBack) {
		List<HotArea> hotAreaList = findAllByCityId(cityId);

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
				code = ResponseCode.NETWORK_ERROR;
			}
			
			return hotAreaList;
		}
	}
}
