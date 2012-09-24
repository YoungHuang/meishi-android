package meishi.ui;

import java.util.List;

import meishi.domain.Shop;
import meishi.service.MainService;
import meishi.service.RefreshCallBack;
import meishi.service.SearchShopListFromNetTask;
import meishi.service.Task;
import meishi.utils.ShopListAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

public class StartActivity extends Activity implements View.OnClickListener, OnItemClickListener {
	private static final String TAG = "StartActivity";
	
	private static final int MAX_RESULT = 5;
	
	private ListView shopListView;
	private ShopListAdapter shopListAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_activity);
		
		// 搜索栏
		EditText keywords = (EditText) this.findViewById(R.id.keywords);
		keywords.setOnClickListener(this);
		
		initShopList();
	}

	// 显示附近商铺列表
	private void initShopList() {
		shopListView = (ListView) this.findViewById(R.id.shopList);
		Task task = new SearchShopListFromNetTask(0, MAX_RESULT, new RefreshCallBack() {
			@Override
			public void refresh(Object... params) {
				List<Shop> shopList = (List<Shop>) params[0];
				shopListAdapter = new ShopListAdapter(StartActivity.this, shopList);
				shopListView.setAdapter(shopListAdapter);
				
				shopListView.setOnItemClickListener(StartActivity.this);
			}
		});
		MainService.newTask(task);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.keywords: // 搜索栏
			Intent intent = new Intent(this, ShopSearchActivity2.class);
			this.startActivity(intent);
			break;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (id == -1) {// 更多
			Task task = new SearchShopListFromNetTask(shopListAdapter.getCount() - 1, MAX_RESULT, new RefreshCallBack() {
				@Override
				public void refresh(Object... params) {
					List<Shop> shopList = (List<Shop>) params[0];
					shopListAdapter.addMoreItems(shopList);
				}
			});
			MainService.newTask(task);
		} else {// 显示店铺首页
			Intent intent = new Intent(this, ShopDetailActivity.class);
			Shop shop = (Shop) shopListAdapter.getItem(position);
			ShopDetailActivity.shop = shop;
			Log.d(TAG, String.valueOf(shopListAdapter.getItemId(position)));
			this.startActivity(intent);
		}
	}
}
