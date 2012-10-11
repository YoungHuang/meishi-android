package meishi.service;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

import meishi.db.base.DaoSupport;
import meishi.domain.HotArea;
import meishi.domain.Shop;
import meishi.network.NetworkService;
import meishi.utils.ResponseCode;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ShopService extends DaoSupport<Shop> {
	private static final String TAG = "ShopService";
	
	private static final String url = NetworkService.hostUrl + "/shop/list";

	public static void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "createTable");
	}
	
	public void loadShopList(Integer cityId, Integer districtId, Integer areaId, String keywords, Integer offset, Integer max, AsyncTaskCallBack<List<Shop>> callBack) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("offset", offset.toString());
		params.put("max", max.toString());
		params.put("cityId", cityId.toString());
		params.put("districtId", districtId.toString());
		params.put("areaId", areaId.toString());
		
		new ShopListAsyncTask(callBack).execute(params);
	}
	
	private class ShopListAsyncTask extends BaseAsyncTask<Map<String, String>, Void, List<Shop>> {
		public ShopListAsyncTask(AsyncTaskCallBack<List<Shop>> callBack) {
			this.callBack = callBack;
		}

		@Override
		protected List<Shop> doInBackground(Map<String, String>... params) {
			// Loading shop list from Net
			Type type = new TypeToken<List<Shop>>(){}.getType();
			List<Shop> shopList = null;
			try {
				shopList = NetworkService.getForList(url, type, params[0]);
			} catch (Exception e) {
				code = ResponseCode.NETWORK_ERROR;
			}
			
			return shopList;
		}
	}
}
