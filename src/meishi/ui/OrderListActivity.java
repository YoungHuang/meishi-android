package meishi.ui;

import java.util.List;

import meishi.MainApplication;
import meishi.adapter.BaseListViewAdapter;
import meishi.domain.Order;
import meishi.network.ResponseMessage;
import meishi.service.BaseAsyncTaskCallBack;
import meishi.service.OrderService;
import meishi.ui.PaginatedListView.OnLoadMoreDataListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class OrderListActivity extends BaseActivity implements OnItemClickListener {
	private OrderService orderService;

	private PaginatedListView orderListView;
	private OrderListAdapter orderListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		setContentView(R.layout.activity_order_list);

		initVariables();

		initView();
	}

	private void initVariables() {
		MainApplication application = MainApplication.getInstance();
		orderService = application.getOrderService();
	}

	private void initView() {
		orderListView = (PaginatedListView) findViewById(R.id.orderList);
		orderListView.enablePagination();
		orderListAdapter = new OrderListAdapter();
		orderListView.setAdapter(orderListAdapter);
		orderListView.setOnItemClickListener(this);
		
		orderListView.setOnLoadMoreDataListener(new OnLoadMoreDataListener() {
			@Override
			public void loadMoreData() {
				loadOrderList();
			}
		});
		loadOrderList();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Order order = (Order) orderListAdapter.getItem(position);
		OrderDetailsActivity.order = order;
		startActivity(OrderDetailsActivity.class);
	}

	private void loadOrderList() {
		orderService.loadOrderList(orderListAdapter.getCount(), MAX_RESULT, new BaseAsyncTaskCallBack<List<Order>>(
				OrderListActivity.this) {
			@Override
			public void onSuccess(List<Order> orderList) {
				if (orderList != null && orderList.size() != 0) {
					orderListAdapter.addMoreItems(orderList);
					orderListView.onLoadSuccess();
				} else {
					orderListView.onLoadComplete();
				}
			}

			@Override
			protected void onOtherError(ResponseMessage responseMessage) {
				super.onOtherError(responseMessage);
				orderListView.onLoadError();
			}
		});
	}

	private class OrderListAdapter extends BaseListViewAdapter<Order> {
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(OrderListActivity.this).inflate(R.layout.list_item_order, null);
				holder = new Holder();
				holder.shopName = (TextView) convertView.findViewById(R.id.shopName);
				holder.orderDesc = (TextView) convertView.findViewById(R.id.orderDesc);
				holder.orderStatus = (TextView) convertView.findViewById(R.id.orderStatus);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			Order order = itemList.get(position);
			holder.shopName.setText(order.getShop().getName());
			holder.orderDesc.setText("共" + order.getTotalCount() + "份，" + order.getTotalAmount() + "元");
			holder.orderStatus.setText(Order.STATUS[order.getStatus()]);

			return convertView;
		}

		private class Holder {
			TextView shopName;
			TextView orderDesc;
			TextView orderStatus;
		}
	}
}
