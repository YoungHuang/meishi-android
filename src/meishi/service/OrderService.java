package meishi.service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import meishi.db.DaoSupport;
import meishi.domain.Order;
import meishi.network.NetworkService;
import meishi.network.ResponseException;
import meishi.network.ResponseMessage;
import android.util.Log;

import com.google.gson.reflect.TypeToken;

public class OrderService extends DaoSupport<Order, Integer> {
	private static final String URL_SUBMIT_ORDER = NetworkService.hostUrl + "/order/submit";
	private static final String URL_LIST_ORDER = NetworkService.hostUrl + "/order/list";

	public OrderService() throws SQLException {
		super(Order.class);
	}

	public void submitOrder(Order order, AsyncTaskCallBack<Void> callBack) {
		new OrderAsyncTask(callBack).execute(order);
	}
	
	public void loadOrderList(Integer offset, Integer max, AsyncTaskCallBack<List<Order>> callBack) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("offset", offset.toString());
		params.put("max", max.toString());
		new LoadOrderListAsyncTask(callBack).execute(params);
	}

	private class OrderAsyncTask extends BaseAsyncTask<Order, Void, Void> {
		public OrderAsyncTask(AsyncTaskCallBack<Void> callBack) {
			this.callBack = callBack;
		}

		@Override
		protected Void doInBackground(Order... params) {
			try {
				Order order = NetworkService.postForObject(URL_SUBMIT_ORDER, params[0], Order.class);
				create(order);
			} catch (IOException e) {
				Log.e(TAG, "doInBackground", e);
				responseMessage = new ResponseMessage("Network error!");
				responseMessage.setErrorCode(ResponseMessage.NETWORK_ERROR);
			} catch (ResponseException e) {
				Log.e(TAG, "doInBackground", e);
				responseMessage = e.getResponseMessage();
			} catch (SQLException e) {
				Log.e(TAG, "doInBackground", e);
				responseMessage = new ResponseMessage("Database error!");
				responseMessage.setErrorCode(ResponseMessage.DB_ERROR);
			}

			return null;
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
}
