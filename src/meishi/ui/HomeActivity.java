package meishi.ui;

import java.util.List;

import meishi.MainApplication;
import meishi.domain.HotArea;
import meishi.service.AsyncTaskCallBack;
import meishi.service.HotAreaService;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

public class HomeActivity extends Activity implements View.OnClickListener, OnItemClickListener {
	private static final String TAG = "HomeActivity";
	
	private HotAreaService hotAreaService;
	
	private ArrayAdapter<HotArea> adapter;
	private ProgressBar progressBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);
		
		initVariables();
		
		EditText keywords = (EditText) this.findViewById(R.id.keywords);
		keywords.setOnClickListener(this);
		
		initAreaList();
	}

	private void initVariables() {
		MainApplication application = (MainApplication) getApplicationContext();
		hotAreaService = application.getHotAreaService();
	}
	
	private void initAreaList() {
		final ListView hotAreaListView = (ListView) findViewById(R.id.hotAreaList);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		hotAreaListView.setOnItemClickListener(this);
		
		final HotArea moreArea = new HotArea();
		moreArea.setName("more areas");
		
		List<HotArea> hotAreaList = hotAreaService.getAll(new AsyncTaskCallBack<List<HotArea>>() {
			@Override
			public void refresh(List<HotArea> hotAreaList) {
				progressBar.setVisibility(View.GONE);
				hotAreaListView.setVisibility(View.VISIBLE);
				hotAreaList.add(moreArea);
				adapter = new ArrayAdapter<HotArea>(HomeActivity.this, android.R.layout.simple_list_item_1, hotAreaList);
				hotAreaListView.setAdapter(adapter);
			}
		});
		if (hotAreaList != null) {
			progressBar.setVisibility(View.GONE);
			hotAreaListView.setVisibility(View.VISIBLE);
			hotAreaList.add(moreArea);
			adapter = new ArrayAdapter<HotArea>(this, android.R.layout.simple_list_item_1, hotAreaList);
			hotAreaListView.setAdapter(adapter);
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.keywords:
			Intent intent = new Intent(this, ShopSearchActivity.class);
			this.startActivity(intent);
			break;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		HotArea hotArea = adapter.getItem(position);
		if (hotArea.getCity() == null) {
			
		} else {
			
		}
	}
}
