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
	private static final String url = NetworkService.hostUrl + "/order/submit";

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
			try {
				Order order = NetworkService.postForObject(url, params[0], Order.class);
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
}
