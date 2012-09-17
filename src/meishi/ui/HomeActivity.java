package meishi.ui;

import java.util.List;

import meishi.MainApplication;
import meishi.db.HotAreaService;
import meishi.domain.HotArea;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

public class HomeActivity extends Activity implements View.OnClickListener, OnItemClickListener {
	private static final String TAG = "HomeActivity";
	
	private HotAreaService hotAreaService;
	
	private ListView hotAreaListView;
	
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
		hotAreaListView = (ListView) this.findViewById(R.id.hotAreaList);
		List<HotArea> hotAreaList = hotAreaService.findAll();
		if (hotAreaList != null) {
			
		} else {
			
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
		
	}
}
