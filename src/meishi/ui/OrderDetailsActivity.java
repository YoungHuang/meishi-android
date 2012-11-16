package meishi.ui;

import meishi.MainApplication;
import meishi.adapter.BaseListViewAdapter;
import meishi.domain.Order;
import meishi.domain.OrderItem;
import meishi.service.OrderService;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class OrderDetailsActivity extends BaseActivity {
	public static Order order;
	
	private OrderService orderService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		setContentView(R.layout.activity_order_details);

		initVariables();

		initView();
	}

	private void initVariables() {
		MainApplication application = MainApplication.getInstance();
		orderService = application.getOrderService();
	}

	private void initView() {
		
	}
	
	private class OrderItemListAdapter extends BaseListViewAdapter<OrderItem> {
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
