package meishi.ui;

import java.util.List;

import meishi.domain.Shop;
import meishi.persistence.SearchHistoryDao;
import meishi.service.MainService;
import meishi.service.RefreshCallBack;
import meishi.service.SearchShopListFromNetTask;
import meishi.service.Task;
import meishi.utils.ShopListAdapter;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ShopSearchActivity extends Activity implements View.OnClickListener, OnItemClickListener {
	private static final String TAG = "ShopSearchActivity";

	private EditText keywordsEditText;
	private String keywords;
	private ListView historyListView;
	private ListView resultsListView;
	
	private ShopListAdapter shopListAdapter;
	private static final int MAX_RESULT = 5;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_search_activity);
		
		keywordsEditText = (EditText) this.findViewById(R.id.keywords);
		Button searchButton = (Button) this.findViewById(R.id.search);
		searchButton.setOnClickListener(this);
		
		historyListView = (ListView) this.findViewById(R.id.searchHistory);
		resultsListView = (ListView) this.findViewById(R.id.searchResults);
		
		Button clearButton = (Button) this.findViewById(R.id.clear);
		clearButton.setOnClickListener(this);
		
		init();
	}

	private void init() {
		// 搜索关键字历史列表
		Cursor cursor = SearchHistoryDao.getAllSearchHistoryCursor();
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.shop_search_keywords, cursor,
					new String[]{"keywords"}, new int[]{R.id.keywords});
		historyListView.setAdapter(adapter);
		historyListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				keywords = ((TextView) view.findViewById(R.id.keywords)).getText().toString();
				search();
			}
			
		});
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.search:
			keywords = keywordsEditText.getText().toString();
			SearchHistoryDao.saveSearchHistory(keywords);
			search();
			break;
			
		case R.id.clear:
			SearchHistoryDao.clearAllSearchHistory();
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (id == -1) {// 更多
			Task task = new SearchShopListFromNetTask(keywords, shopListAdapter.getCount() - 1, MAX_RESULT, new RefreshCallBack() {
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
			intent.putExtra("shopid", shop.getId());
			Log.d(TAG, String.valueOf(shopListAdapter.getItemId(position)));
			this.startActivity(intent);
		}
	}
	
	// 搜索
	private void search() {
		Task task = new SearchShopListFromNetTask(keywords, 0, MAX_RESULT, new RefreshCallBack() {
			@Override
			public void refresh(Object... params) {
				List<Shop> shopList = (List<Shop>) params[0];
				shopListAdapter = new ShopListAdapter(ShopSearchActivity.this, shopList);
				resultsListView.setAdapter(shopListAdapter);
				resultsListView.setVisibility(View.VISIBLE);
				historyListView.setVisibility(View.GONE);
				
				resultsListView.setOnItemClickListener(ShopSearchActivity.this);
			}
		});
		MainService.newTask(task);
	}
}
