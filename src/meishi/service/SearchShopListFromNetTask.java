package meishi.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import meishi.domain.Shop;
import meishi.network.NetworkService;
import android.util.Log;

import com.google.gson.reflect.TypeToken;


public class SearchShopListFromNetTask extends Task {
	private static final String TAG = "SearchShopListFromNetTask";
	private List<Shop> shopList = new ArrayList<Shop>();
	private static final String url = NetworkService.hostUrl + "/shop/search";
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
			Type type = new TypeToken<List<Shop>>(){}.getType();
			List<Shop> shops = NetworkService.getForList(url, type, params);
			if (shops != null) {
				shopList = shops;
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
