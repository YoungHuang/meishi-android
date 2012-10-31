package meishi.service;

import java.io.IOException;
import java.sql.SQLException;

import meishi.db.DaoSupport;
import meishi.domain.Order;
import meishi.network.NetworkService;
import meishi.network.ResponseException;
import meishi.network.ResponseMessage;
import android.util.Log;

public class OrderService extends DaoSupport<Order, Integer> {
	private static final String url = NetworkService.hostUrl + "/order/save";

	public OrderService() throws SQLException {
		super(Order.class);
	}

	public void submitOrder(Order order, AsyncTaskCallBack<Void> callBack) {
		new OrderAsyncTask(callBack).execute(order);
	}

	private class OrderAsyncTask extends BaseAsyncTask<Order, Void, Void> {
		public OrderAsyncTask(AsyncTaskCallBack<Void> callBack) {
			this.callBack = callBack;
		}

		@Override
		protected Void doInBackground(Order... params) {
			Order order = params[0];
			String respone;
			try {
				respone = NetworkService.postForObject(url, order, String.class);
				if (!"success".equals(respone)) {
					responseMessage = new ResponseMessage("Submit order failed!");
					responseMessage.setErrorCode(ResponseMessage.FAILED);
				}
			} catch (IOException e) {
				Log.e(TAG, "doInBackground", e);
				responseMessage = new ResponseMessage("Network error!");
				responseMessage.setErrorCode(ResponseMessage.NETWORK_ERROR);
			} catch (ResponseException e) {
				Log.e(TAG, "doInBackground", e);
				responseMessage = e.getResponseMessage();
			}

			return null;
		}
	}
}
