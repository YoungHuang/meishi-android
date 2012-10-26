package meishi.ui;

import java.util.ArrayList;
import java.util.List;

import meishi.MainApplication;
import meishi.domain.DishCategory;
import meishi.domain.Order;
import meishi.service.AsyncTaskCallBack;
import meishi.service.DishCategoryService;
import meishi.utils.ResponseCode;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DishListActivity extends BaseActivity {
	private DishCategoryService dishCategoryService;

	public static Order order;
	
	private DishCategoryListAdapter dishCategoryListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dish_list);

		initVariables();

		initDishCategorys();

		TextView totalCountView = (TextView) findViewById(R.id.totalCount);
		TextView totalAmountView = (TextView) findViewById(R.id.totalAmount);
		ListView dishCategoryListView = (ListView) findViewById(R.id.dishCategoryList);
		ListView dishListView = (ListView) findViewById(R.id.dishList);
	}

	private void initVariables() {
		MainApplication application = (MainApplication) getApplicationContext();
		dishCategoryService = application.getDishCategoryService();
	}

	private void initDishCategorys() {
		ListView dishCategoryListView = (ListView) findViewById(R.id.dishCategoryList);
		dishCategoryListAdapter = new DishCategoryListAdapter();
		dishCategoryListView.setAdapter(dishCategoryListAdapter);

		dishCategoryService.loadDishCategorys(ShopDetailActivity.shop.getId(), new AsyncTaskCallBack<List<DishCategory>>() {
			@Override
			public void onSuccess(List<DishCategory> dishCategoryList) {
				dishCategoryListAdapter.addMoreItems(dishCategoryList);
			}

			@Override
			public void onError(ResponseCode code) {
				// Error
			}
		});
		
		dishCategoryListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private class DishCategoryListAdapter extends BaseAdapter {
		List<DishCategory> dishCategoryList;

		public DishCategoryListAdapter() {
			dishCategoryList = new ArrayList<DishCategory>();
		}

		@Override
		public int getCount() {
			return dishCategoryList.size();
		}

		@Override
		public Object getItem(int position) {
			return dishCategoryList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

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
			DishCategory dishCategory = dishCategoryList.get(position);
			holder.categoryName.setText(dishCategory.getName());

			return convertView;
		}
		
		public void addMoreItems(List<DishCategory> dishCategorys) {
			dishCategoryList.addAll(dishCategorys);
			this.notifyDataSetChanged();
		}

		private class Holder {
			TextView categoryName;
		}
	}

	private class DishListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
