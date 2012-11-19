package meishi.ui;

import java.util.List;

import meishi.MainApplication;
import meishi.adapter.BaseListViewAdapter;
import meishi.domain.Order;
import meishi.domain.OrderItem;
import meishi.service.BaseAsyncTaskCallBack;
import meishi.service.OrderService;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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
		ListView orderListView = (ListView) findViewById(R.id.orderList);

		final OrderItemListAdapter orderItemListAdapter = new OrderItemListAdapter();
		orderListView.setAdapter(orderItemListAdapter);

		orderService.loadOrderItemList(order.getId(), new BaseAsyncTaskCallBack<List<OrderItem>>(
				OrderDetailsActivity.this) {
			@Override
			public void onSuccess(List<OrderItem> orderItemList) {
				orderItemListAdapter.addMoreItems(orderItemList);
			}
		});
	}

	private class OrderItemListAdapter extends BaseListViewAdapter<OrderItem> {
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(OrderDetailsActivity.this)
						.inflate(R.layout.list_item_orderitem, null);
				holder = new Holder();
				holder.dishName = (TextView) convertView.findViewById(R.id.dishName);
				holder.price = (TextView) convertView.findViewById(R.id.price);
				holder.count = (TextView) convertView.findViewById(R.id.count);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			OrderItem orderItem = itemList.get(position);
			holder.dishName.setText("name: " + orderItem.getDish().getName());
			holder.price.setText("price: " + orderItem.getAmount().toString());
			holder.count.setText(orderItem.getCount());

			return convertView;
		}

		private class Holder {
			TextView dishName;
			TextView price;
			TextView count;
		}
	}
}
