package meishi.ui;

import java.util.List;

import meishi.domain.Dish;
import meishi.domain.Order;
import meishi.domain.OrderItem;
import meishi.domain.Shop;
import meishi.utils.GlobalData;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DishActivity extends Activity implements OnClickListener, OnItemClickListener {
	private static final String TAG = "DishActivity";
	private static final int MAX_RESULT = 5;
	
	private TextView totalView;
	public static Shop shop;
	private Order order;
	private GridView gridView;
	private GridViewAdapter gridViewAdapter;
	private long dishCategoryId = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dish_activity);
		
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText(shop.getName());
		
		totalView = (TextView) this.findViewById(R.id.total);
		Button selectButton = (Button) this.findViewById(R.id.selectButton);
		Button backButton = (Button) this.findViewById(R.id.back);
		Button nextButton = (Button) this.findViewById(R.id.next);
		selectButton.setOnClickListener(this);
		backButton.setOnClickListener(this);
		nextButton.setOnClickListener(this);
		
		order = new Order();
		GlobalData.order = order;
		order.setShop(shop);
		
		initDishList();
	}

	// 显示菜单列表
	private void initDishList() {
		gridView = (GridView) this.findViewById(R.id.gridView);
//		Task task = new GetDishListFromNetTask(dishCategoryId, 0, MAX_RESULT, new RefreshCallBack() {
//			@Override
//			public void refresh(Object... params) {
//				List<Dish> dishList = (List<Dish>) params[0];
//				gridViewAdapter = new GridViewAdapter(DishActivity.this, dishList);
//				gridView.setAdapter(gridViewAdapter);
//				
//				gridView.setOnItemClickListener(DishActivity.this);
//			}
//		});
//		MainService.newTask(task);
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) { 
		case R.id.selectButton: // 选择菜单类别
			
			break;
		case R.id.back: // 返回
			this.finish();
			
			break;
		case R.id.next: // 下一步
			Intent intent = new Intent(this, ConfirmOrderActivity.class);
			this.startActivity(intent);
			
			break;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
//		if (id == -1) {// 更多
//			Task task = new GetDishListFromNetTask(dishCategoryId, gridViewAdapter.getCount() - 1, MAX_RESULT, new RefreshCallBack() {
//				@Override
//				public void refresh(Object... params) {
//					List<Dish> dishList = (List<Dish>) params[0];
//					gridViewAdapter.addMoreItems(dishList);
//				}
//			});
//			MainService.newTask(task);
//		} else {// 显示菜单详情
//			
//		}
	}
	
	class GridViewAdapter extends BaseAdapter {
		private List<Dish> dishList;
		private Context context;

		public GridViewAdapter(Context context, List<Dish> dishList) {
			this.context = context;
			this.dishList = dishList;
		}
		
		@Override
		public int getCount() {
			return dishList.size() + 1;
		}

		@Override
		public Object getItem(int position) {
			return dishList.get(position);
		}

		@Override
		public long getItemId(int position) {
			if (position == getCount() - 1) {
				return -1;
			} else {
				return dishList.get(position).getId();
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			if (position == getCount() - 1) { // 更多
				view = LayoutInflater.from(context).inflate(R.layout.dish_activity_item, null);
				LinearLayout moreLayout = (LinearLayout) view.findViewById(R.id.moreLayout);
				moreLayout.setVisibility(View.VISIBLE);
			} else {
				final Dish dish = dishList.get(position);
				view = LayoutInflater.from(context).inflate(R.layout.dish_activity_item, null);
				// 菜名
				TextView dishName = (TextView) view.findViewById(R.id.dishName);
				dishName.setText(dish.getName());
				// 价格
				TextView price= (TextView) view.findViewById(R.id.price);
				price.setText(dish.getPrice().toString());
				
				TextView minus = (TextView) view.findViewById(R.id.minus);
				TextView plus = (TextView) view.findViewById(R.id.plus);
				final TextView countView = (TextView) view.findViewById(R.id.count);
				
				OrderItem orderDish = order.findOrderItem(dish.getId());
				if (orderDish != null) {
					countView.setText(orderDish.getCount().toString());
				}
				
				View.OnClickListener listener = new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Integer count = Integer.valueOf(countView.getText().toString());
						Double totalAmount = order.getTotalAmount();
						Integer totalCount = order.getTotalCount();
						OrderItem orderDish = order.findOrderItem(dish.getId());
						switch(v.getId()) {
						case R.id.minus: // 减少一份
							if (count == 0)
								break;
							
							count--;
							totalCount--;
							orderDish.setCount(count);
							totalAmount -= dish.getPrice();
							if (count == 0) { // 删除该OrderDish
								order.deleteOrderItem(orderDish);
							}
							
							break;
						case R.id.plus: // 增加一份
							count++;
							totalCount++;
							totalAmount += dish.getPrice();
							if (count == 1) { // 添加OrderDish
								orderDish = new OrderItem();
								orderDish.setDish(dish);
								order.addOrderItem(orderDish);
							}
							orderDish.setCount(count);
							break;
						}
						countView.setText(count.toString());
						order.setTotalAmount(totalAmount);
						order.setTotalCount(totalCount);
						totalView.setText("已点:" + totalCount + "份 共" + totalAmount + "元");
					}
				};
				minus.setOnClickListener(listener);
				plus.setOnClickListener(listener);
			}
			
			return view;
		}
		
		public void addMoreItems(List<Dish> moreDishList) {
			if (moreDishList.size() != 0) {
				dishList.addAll(moreDishList);
				this.notifyDataSetChanged();
			}
		}
	}
}
