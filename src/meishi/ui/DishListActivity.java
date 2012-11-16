package meishi.ui;

import java.util.ArrayList;
import java.util.List;

import meishi.MainApplication;
import meishi.adapter.BaseListViewAdapter;
import meishi.domain.Dish;
import meishi.domain.DishCategory;
import meishi.domain.Order;
import meishi.domain.OrderItem;
import meishi.domain.Shop;
import meishi.network.ResponseMessage;
import meishi.service.AsyncTaskCallBack;
import meishi.service.DishCategoryService;
import meishi.service.DishService;
import meishi.utils.GlobalData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class DishListActivity extends BaseActivity implements OnClickListener {
	private DishCategoryService dishCategoryService;
	private DishService dishService;

	public Order order;
	public static Shop shop;
	private DishCategory dishCategory;

	private ListView dishCategoryListView;
	private DishCategoryListAdapter dishCategoryListAdapter;
	private ListView dishListView;
	private DishListAdapter dishListAdapter;
	private View footerView;
	private LinearLayout moreExceptionLayout;

	private TextView totalCountView;
	private TextView totalAmountView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dish_list);

		initVariables();

		initView();

		initDishCategorys();

		initDishes();

		Button backButton = (Button) this.findViewById(R.id.back);
		Button nextButton = (Button) this.findViewById(R.id.next);
		backButton.setOnClickListener(this);
		nextButton.setOnClickListener(this);
	}

	private void initVariables() {
		MainApplication application = (MainApplication) getApplicationContext();
		dishCategoryService = application.getDishCategoryService();
		dishService = application.getDishService();
	}

	private void initView() {
		totalCountView = (TextView) findViewById(R.id.totalCount);
		totalAmountView = (TextView) findViewById(R.id.totalAmount);

		if (GlobalData.order == null) {
			GlobalData.order = new Order();
			order = GlobalData.order;
			order.setShop(shop);
		}

		totalCountView.setText(order.getTotalCount());
		totalAmountView.setText(order.getTotalAmount().toString());
	}

	private void initDishCategorys() {
		dishCategoryListView = (ListView) findViewById(R.id.dishCategoryList);
		dishCategoryListAdapter = new DishCategoryListAdapter();
		dishCategoryListView.setAdapter(dishCategoryListAdapter);

		loadDishCategorys();
	}

	private void loadDishCategorys() {
		dishCategoryService.loadDishCategorys(ShopDetailActivity.shop.getId(),
				new AsyncTaskCallBack<List<DishCategory>>() {
					@Override
					public void onSuccess(List<DishCategory> dishCategoryList) {
						dishCategoryListAdapter.addMoreItems(dishCategoryList);
						dishCategory = DishCategory.findDefualt(dishCategoryList);
						loadDishes(0, MAX_RESULT);
					}

					@Override
					public void onError(ResponseMessage responseMessage) {
						// Error
					}
				});

		dishCategoryListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				dishCategory = (DishCategory) dishCategoryListAdapter.getItem(position);
				loadDishes(0, MAX_RESULT);
			}
		});
	}

	private void initDishes() {
		dishListView = (ListView) findViewById(R.id.dishList);
		footerView = LayoutInflater.from(DishListActivity.this).inflate(R.layout.list_item_more, null);
		dishListView.addFooterView(footerView);

		moreExceptionLayout = (LinearLayout) footerView.findViewById(R.id.more_exception_layout);

		dishListAdapter = new DishListAdapter();
		dishListView.setAdapter(dishListAdapter);
		dishListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Dish dish = (Dish) dishListAdapter.getItem(position);
				OrderItem orderItem = order.findOrderItem(dish.getId());
				if (orderItem == null) {
					orderItem = new OrderItem();
					orderItem.setDish(dish);
					orderItem.setCount(0);
					order.addOrderItem(orderItem);
					order.setTotalCount(order.getTotalCount() + 1);
					order.setTotalAmount(order.getTotalAmount() + dish.getPrice());
				} else {
					order.deleteOrderItem(orderItem);
					order.setTotalCount(order.getTotalCount() - 1);
					order.setTotalAmount(order.getTotalAmount() - dish.getPrice());
				}

				totalCountView.setText(order.getTotalCount());
				totalAmountView.setText(order.getTotalAmount().toString());
			}
		});

		dishListView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
					loadDishes(dishListAdapter.getCount(), MAX_RESULT);
				}
			}
		});
	}

	private void loadDishes(Integer offset, Integer max) {
		final LinearLayout moreLoadLayout = (LinearLayout) footerView.findViewById(R.id.more_load_layout);

		dishService.loadDishes(dishCategory.getId(), offset, max, new AsyncTaskCallBack<List<Dish>>() {
			@Override
			public void onSuccess(List<Dish> dishList) {
				dishListAdapter.addMoreItems(dishList);
				moreLoadLayout.setVisibility(View.VISIBLE);
				moreExceptionLayout.setVisibility(View.GONE);
			}

			@Override
			public void onError(ResponseMessage responseMessage) {
				moreLoadLayout.setVisibility(View.GONE);
				moreExceptionLayout.setVisibility(View.VISIBLE);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			this.finish();
			break;
		case R.id.next:
			Intent intent = new Intent(this, ConfirmOrderActivity.class);
			this.startActivity(intent);
			break;
		}

	}

	private class DishCategoryListAdapter extends BaseListViewAdapter<DishCategory> {
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(DishListActivity.this)
						.inflate(R.layout.list_item_dish_category, null);
				holder = new Holder();
				holder.categoryName = (TextView) convertView.findViewById(R.id.categoryName);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			DishCategory dishCategory = itemList.get(position);
			holder.categoryName.setText(dishCategory.getName());
			if (dishCategory.isDefaul()) {
				// TODO
			}

			return convertView;
		}

		private class Holder {
			TextView categoryName;
		}
	}

	private class DishListAdapter extends BaseListViewAdapter<Dish> {
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(DishListActivity.this).inflate(R.layout.list_item_dish, null);
				holder = new Holder();
				holder.dishName = (TextView) convertView.findViewById(R.id.dishName);
				holder.count = (TextView) convertView.findViewById(R.id.count);
				holder.price = (TextView) convertView.findViewById(R.id.price);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			Dish dish = itemList.get(position);
			holder.dishName.setText(dish.getName());
			holder.price.setText(dish.getPrice().toString());
			OrderItem orderItem = order.findOrderItem(dish.getId());
			if (orderItem != null) {
				holder.count.setVisibility(View.VISIBLE);
				holder.count.setText(orderItem.getCount());
			} else {
				holder.count.setVisibility(View.INVISIBLE);
			}

			return convertView;
		}

		private class Holder {
			TextView dishName;
			TextView count;
			TextView price;
		}
	}
}
