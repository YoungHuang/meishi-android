package meishi.service;

import java.sql.SQLException;

import android.util.Log;

import meishi.db.DaoSupport;
import meishi.domain.Order;
import meishi.network.NetworkService;
import meishi.utils.ResponseCode;

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
			try {
				String respone = NetworkService.postForObject(url, order, String.class);
				if (!"success".equals(respone)) {
					code = ResponseCode.FAILED;
				}
			} catch (Exception e) {
				Log.e(TAG, "doInBackground", e);
				code = ResponseCode.NETWORK_ERROR;
			}
			
			return null;
		}
	}
}
