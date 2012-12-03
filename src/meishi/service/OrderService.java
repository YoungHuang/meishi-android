package meishi.service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import meishi.domain.Order;
import meishi.domain.OrderItem;
import meishi.network.NetworkService;
import meishi.network.ResponseException;
import meishi.network.ResponseMessage;
import android.util.Log;

import com.google.gson.reflect.TypeToken;

public class OrderService {
	private static final String TAG = "OrderService";
	private static final String URL_SUBMIT_ORDER = NetworkService.hostUrl + "/order/submit";
	private static final String URL_LIST_ORDER = NetworkService.hostUrl + "/order/list";
	private static final String URL_LIST_ORDERITEM = NetworkService.hostUrl + "/order/items";

	public void submitOrder(Order order, AsyncTaskCallBack<Order> callBack) {
		new OrderAsyncTask(callBack).execute(order);
	}
	
	public void loadOrderList(Integer offset, Integer max, AsyncTaskCallBack<List<Order>> callBack) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("offset", offset.toString());
		params.put("max", max.toString());
		new LoadOrderListAsyncTask(callBack).execute(params);
	}
	
	public void loadOrderItemList(Integer orderId, AsyncTaskCallBack<List<OrderItem>> callBack) {
		new LoadOrderItemListAsyncTask(callBack).execute(orderId);
	}

	private class OrderAsyncTask extends BaseAsyncTask<Order, Void, Order> {
		public OrderAsyncTask(AsyncTaskCallBack<Order> callBack) {
			this.callBack = callBack;
		}

		@Override
		protected Order doInBackground(Order... params) {
			Order order = null;
			try {
				order = NetworkService.postForObject(URL_SUBMIT_ORDER, params[0], Order.class);
			} catch (IOException e) {
				Log.e(TAG, "doInBackground", e);
				responseMessage = new ResponseMessage("Network error!");
				responseMessage.setErrorCode(ResponseMessage.NETWORK_ERROR);
			} catch (ResponseException e) {
				Log.e(TAG, "doInBackground", e);
				responseMessage = e.getResponseMessage();
			}

			return order;
		}
	}
	
	private class LoadOrderListAsyncTask extends BaseAsyncTask<Map<String, String>, Void, List<Order>> {
		public LoadOrderListAsyncTask(AsyncTaskCallBack<List<Order>> callBack) {
			this.callBack = callBack;
		}

		@Override
		protected List<Order> doInBackground(Map<String, String>... params) {
			Type type = new TypeToken<List<Order>>() {}.getType();
			List<Order> orderList = null;
			try {
				orderList = NetworkService.getForList(URL_LIST_ORDER, type, params[0]);
			} catch (IOException e) {
				Log.e(TAG, "doInBackground", e);
				responseMessage = new ResponseMessage("Network error!");
				responseMessage.setErrorCode(ResponseMessage.NETWORK_ERROR);
			} catch (ResponseException e) {
				Log.e(TAG, "doInBackground", e);
				responseMessage = e.getResponseMessage();
			}

			return orderList;
		}
	}
	
	private class LoadOrderItemListAsyncTask extends BaseAsyncTask<Integer, Void, List<OrderItem>> {
		public LoadOrderItemListAsyncTask(AsyncTaskCallBack<List<OrderItem>> callBack) {
			this.callBack = callBack;
		}

		@Override
		protected List<OrderItem> doInBackground(Integer... params) {
			Type type = new TypeToken<List<OrderItem>>() {}.getType();
			List<OrderItem> orderItemList = null;
			String url = URL_LIST_ORDERITEM + "/" + params[0];
			try {
				orderItemList = NetworkService.getForList(url, type, null);
			} catch (IOException e) {
				Log.e(TAG, "doInBackground", e);
				responseMessage = new ResponseMessage("Network error!");
				responseMessage.setErrorCode(ResponseMessage.NETWORK_ERROR);
			} catch (ResponseException e) {
				Log.e(TAG, "doInBackground", e);
				responseMessage = e.getResponseMessage();
			}

			return orderItemList;
		}
	}
}
