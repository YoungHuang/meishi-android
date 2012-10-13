package meishi.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class SearchActivity extends Activity implements View.OnClickListener {
	private static final String TAG = "SearchActivity";

	private EditText keywordsEditText;
	private String keywords;
	private ListView historyListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		keywordsEditText = (EditText) this.findViewById(R.id.keywords);
		Button searchButton = (Button) this.findViewById(R.id.search);
		searchButton.setOnClickListener(this);
		
		historyListView = (ListView) this.findViewById(R.id.searchHistory);
		
		Button clearButton = (Button) this.findViewById(R.id.clear);
		clearButton.setOnClickListener(this);
		
		init();
	}
	
	private void init() {
		// 搜索关键字历史列表
//		Cursor cursor = SearchHistoryDao.getAllSearchHistoryCursor();
//		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.shop_search_keywords, cursor,
//					new String[]{"keywords"}, new int[]{R.id.keywords});
//		historyListView.setAdapter(adapter);
//		historyListView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				keywords = ((TextView) view.findViewById(R.id.keywords)).getText().toString();
//				search();
//			}
//			
//		});
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.search:
			keywords = keywordsEditText.getText().toString();
//			SearchHistoryDao.saveSearchHistory(keywords);
			search();
			break;
			
		case R.id.clear:
//			SearchHistoryDao.clearAllSearchHistory();
			break;
		}
	}
	
	// 搜索
	private void search() {
		Intent intent = new Intent(this, ShopSearchActivity.class);
		intent.putExtra("keywords", keywords);
		startActivity(intent);
	}
}
