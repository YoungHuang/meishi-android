package meishi.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CollectActivity extends Activity implements OnItemClickListener {
	private static final String TAG = "CollectActivity";
	private static final int MAX_RESULT = 5;
//	private DBManager dbManager = DBManager.open(this.getApplicationContext());
	private ListView listView;
//	private ShopListAdapter shopListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.collect_activity);
		
		listView = (ListView) this.findViewById(R.id.collect_list);
//		List<Shop> shopList = dbManager.getScrollRecentlyHistory(0, MAX_RESULT);
//		Log.d(TAG, "shopList size:" + shopList.size());
//		shopListAdapter = new ShopListAdapter(this, shopList);
//		listView.setAdapter(shopListAdapter);
		listView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Log.d(TAG, "onItemClick");
		
		if (id == -1) {// 更多
		} else {// 显示店铺首页
			Intent intent = new Intent(this, ShopDetailActivity.class);
			this.startActivity(intent);
		}
	}
}
