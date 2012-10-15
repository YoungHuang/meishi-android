package meishi.service;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import meishi.db.DaoSupport;
import meishi.domain.Shop;
import meishi.network.NetworkService;
import meishi.utils.ResponseCode;

import com.google.gson.reflect.TypeToken;

public class ShopService extends DaoSupport<Shop, Integer> {
	private static final String url = NetworkService.hostUrl + "/shop/list";

	public ShopService() throws SQLException {
		super(Shop.class);
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
