package meishi.service;

import meishi.domain.Order;
import meishi.network.NetworkService;
import meishi.utils.GlobalData;
import meishi.utils.NetUtils;

import org.json.JSONObject;

import android.util.Log;


public class SubmitOrderTask extends Task {
	private static final String TAG = "SubmitOrderTask";
	
	private static final String url = NetUtils.submitOrderUrl;
	
	private String result;
	
	public SubmitOrderTask(RefreshCallBack callBack) {
		this.callBack = callBack;
	}

	@Override
	public void execute() {
		Order order = GlobalData.order;
//		String json = order.beanToString();
		try {
//			byte[] data = NetUtils.sendPostRequest(url, json, NetUtils.enc);
//			String jsonString = new String(data, NetUtils.enc);
//			JSONObject object = new JSONObject(jsonString);
//			result = object.getString("result");
			
			result = NetworkService.postForObject(url, order, String.class);
		} catch (Exception e) {
			Log.w(TAG, "searchShopListFromNet() exception: ", e);
		}
	}

	@Override
	public void refresh() {
		callBack.refresh(result);
	}
}
