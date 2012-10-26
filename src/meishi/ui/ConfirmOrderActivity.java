package meishi.ui;

import java.sql.SQLException;
import java.util.List;

import meishi.MainApplication;
import meishi.domain.Dish;
import meishi.domain.Order;
import meishi.domain.OrderItem;
import meishi.service.AsyncTaskCallBack;
import meishi.service.OrderService;
import meishi.utils.GlobalData;
import meishi.utils.ResponseCode;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 订单确认
 * @author yhuang
 *
 */
public class ConfirmOrderActivity extends Activity implements OnClickListener {
	private static final String TAG = "ConfirmOrderActivity";
	
	private OrderService orderService;
	
	private Order order;
	private TextView totalAmountView;
	private EditText noteText;
	private Button peopleButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.confirm_order_activity);
		
		initVariables();
		
		order = GlobalData.order;
		totalAmountView = (TextView) this.findViewById(R.id.totalAmount);
		totalAmountView.setText("共" + order.getTotalAmount() + "元");
		TextView descView = (TextView) this.findViewById(R.id.desc);
		descView.setText("每餐加收8元外送费");
		noteText = (EditText) this.findViewById(R.id.note);
		Button backButton = (Button) this.findViewById(R.id.back);
		Button nextButton = (Button) this.findViewById(R.id.next);
		backButton.setOnClickListener(this);
		nextButton.setOnClickListener(this);
		
		peopleButton = (Button) this.findViewById(R.id.people);
		Button plus = (Button) this.findViewById(R.id.plus);
		Button minus = (Button) this.findViewById(R.id.minus);
		plus.setOnClickListener(this);
		minus.setOnClickListener(this);
		
		ListView listView = (ListView) this.findViewById(R.id.order_list);
		ListAdapter listAdapter = new ListAdapter(this, order.getOrderItemList());
		listView.setAdapter(listAdapter);
	}
	
	private void initVariables() {
		MainApplication application = (MainApplication) getApplicationContext();
		orderService = application.getOrderService();
	}
	
	@Override
	public void onClick(View v) {
		Integer people = order.getPeople();
		switch(v.getId()) { 
		case R.id.back: // 返回
			this.finish();
			
			break;
		case R.id.next: // 下一步
			order.setDescription(noteText.getText().toString());
			try {
				orderService.create(order);
			} catch (SQLException e) {
				Log.e(TAG, "database error", e);
				return;
			}
			orderService.submitOrder(order, new AsyncTaskCallBack<Void>() {
				@Override
				public void onSuccess(Void t) {
					Toast.makeText(ConfirmOrderActivity.this, "submit order success", Toast.LENGTH_LONG).show();
				}

				@Override
				public void onError(ResponseCode code) {
					Toast.makeText(ConfirmOrderActivity.this, "submit order error", Toast.LENGTH_LONG).show();
				}
			});
//			Task task = new SubmitOrderTask(new RefreshCallBack() {
//				@Override
//				public void refresh(Object... params) {
//					String result = (String) params[0];
//					Toast.makeText(ConfirmOrderActivity.this, result, Toast.LENGTH_LONG).show();
//				}
//			});
//			MainService.newTask(task);
			break;
		case R.id.plus: // 增加人数
			people++;
			order.setPeople(people);
			peopleButton.setText(people.toString());
			
			break;
		case R.id.minus: // 减少人数
			if (people != 0)
				people--;
			order.setPeople(people);
			peopleButton.setText(people.toString());
			
			break;
		}
	}
	
	class ListAdapter extends BaseAdapter {
		private List<OrderItem> dishList;
		private Context context;
		
		public ListAdapter(Context context, List<OrderItem> dishList) {
			this.context = context;
			this.dishList = dishList;
		}

		@Override
		public int getCount() {
			return dishList.size();
		}

		@Override
		public Object getItem(int position) {
			return dishList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return dishList.get(position).getDish().getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Log.d(TAG, "getView");
			
			View view = null;
			final Dish dish = dishList.get(position).getDish();
			view = LayoutInflater.from(context).inflate(R.layout.confirm_order_listview, null);
			TextView dishName = (TextView) view.findViewById(R.id.dishName);
			dishName.setText(dish.getName());
			TextView price= (TextView) view.findViewById(R.id.price);
			price.setText(dish.getPrice().toString());
			
			TextView minus = (TextView) view.findViewById(R.id.minus);
			TextView plus = (TextView) view.findViewById(R.id.plus);
			final TextView countView = (TextView) view.findViewById(R.id.count);
			final OrderItem orderDish = dishList.get(position);
			countView.setText(orderDish.getCount().toString());
			
			View.OnClickListener listener = new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Integer count = Integer.valueOf(countView.getText().toString());
					Double totalAmount = order.getTotalAmount();
					Integer totalCount = order.getTotalCount();
					
					switch(v.getId()) {
					case R.id.minus:
						if (count == 0)
							break;
						
						count--;
						totalCount--;
						totalAmount -= dish.getPrice();
						
						break;
					case R.id.plus:
						count++;
						totalCount++;
						totalAmount += dish.getPrice();
						
						break;
					}
					
					orderDish.setCount(count);
					countView.setText(count.toString());
					order.setTotalAmount(totalAmount);
					order.setTotalCount(totalCount);
					totalAmountView.setText("共" + totalAmount + "元");
				}
			};
			minus.setOnClickListener(listener);
			plus.setOnClickListener(listener);
			
			return view;
		}
	}
}
