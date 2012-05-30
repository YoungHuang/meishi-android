package meishi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import meishi.domain.Shop;
import meishi.utils.NetUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;


public class SearchShopListFromNetTask extends Task {
	private static final String TAG = "SearchShopListFromNetTask";
	private List<Shop> shopList = new ArrayList<Shop>();
	private static final String url = NetUtils.hostUrl + "/shop/search";
	private Map<String, String> params;

	public SearchShopListFromNetTask(int offset, int maxResult, RefreshCallBack callBack) {
		params = new HashMap<String, String>();
		params.put("offset", String.valueOf(offset));
		params.put("max", String.valueOf(maxResult));
		this.callBack = callBack;
	}
	
	public SearchShopListFromNetTask(Map<String, String> params, int offset, int maxResult, RefreshCallBack callBack) {
		this.params = params;
		params.put("offset", String.valueOf(offset));
		params.put("max", String.valueOf(maxResult));
		this.callBack = callBack;
	}
	
	public SearchShopListFromNetTask(String keywords, int offset, int maxResult, RefreshCallBack callBack) {
		params = new HashMap<String, String>();
		params.put("keywords", keywords);
		params.put("offset", String.valueOf(offset));
		params.put("max", String.valueOf(maxResult));
		this.callBack = callBack;
	}
	
	@Override
	public void execute() {
		try {
			byte[] data = NetUtils.sendGetRequest(url, params, "UTF-8");
			String json = new String(data, "UTF-8");
			JSONArray jsonarray = new JSONArray(json);
			for (int i = 0; i < jsonarray.length(); i++) {
				JSONObject jsonobject = jsonarray.getJSONObject(i);
				Shop shop = new Shop();
				shop.stringToBean(jsonobject.toString());
				shopList.add(shop);
			}
		} catch (Exception e) {
			Log.w(TAG, "searchShopListFromNet() exception: ", e);
		}
	}

	@Override
	public void refresh() {
		callBack.refresh(shopList);
	}
}
