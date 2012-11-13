package meishi.ui;

import java.util.ArrayList;
import java.util.List;

import meishi.MainApplication;
import meishi.db.PreferenceService;
import meishi.domain.Area;
import meishi.domain.City;
import meishi.domain.HotArea;
import meishi.network.ResponseMessage;
import meishi.service.AreaService;
import meishi.service.AsyncTaskCallBack;
import meishi.service.HotAreaService;
import android.app.Activity;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HomeActivity extends Activity implements View.OnClickListener, OnItemClickListener {
	private static final String TAG = "HomeActivity";

	private PreferenceService preferenceService;
	private HotAreaService hotAreaService;
	private AreaService areaService;

	private HotAreaAdapter adapter;
	private ListView hotAreaListView;
	
	private City city;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);

		initVariables();

		EditText keywords = (EditText) this.findViewById(R.id.keywords);
		keywords.setOnClickListener(this);

		initHotAreaList();
	}

	private void initVariables() {
		MainApplication application = (MainApplication) getApplicationContext();
		hotAreaService = application.getHotAreaService();
		preferenceService = application.getPreferenceService();
		areaService = application.getAreaService();
		
		city = preferenceService.getCity();
	}

	private void initHotAreaList() {
		hotAreaListView = (ListView) findViewById(R.id.hotAreaList);
		adapter = new HotAreaAdapter();
		hotAreaListView.setAdapter(adapter);
		hotAreaListView.setOnItemClickListener(this);

		loadInitData();
		
		final TextView loadingMessage = (TextView) findViewById(R.id.loadingMessage);
		final TextView retryButton = (TextView) findViewById(R.id.retryButton);
		
		retryButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadingMessage.setVisibility(View.VISIBLE);
				retryButton.setVisibility(View.GONE);
				loadInitData();
			}
		});
	}

	private void loadInitData() {
		final LinearLayout loadingLayout = (LinearLayout) findViewById(R.id.loadingLayout);
		final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
		final TextView loadingMessage = (TextView) findViewById(R.id.loadingMessage);
		final TextView retryButton = (TextView) findViewById(R.id.retryButton);
		progressBar.setVisibility(View.VISIBLE);
		
		hotAreaService.loadAll(city.getId(), new AsyncTaskCallBack<List<HotArea>>() {
			@Override
			public void onSuccess(List<HotArea> hotAreaList) {
				loadingLayout.setVisibility(View.GONE);
				hotAreaListView.setVisibility(View.VISIBLE);
				HotArea moreArea = new HotArea();
				moreArea.setName("more areas");
				hotAreaList.add(moreArea);
				
				adapter.addMoreItems(hotAreaList);
			}

			@Override
			public void onError(ResponseMessage responseMessage) {
				progressBar.setVisibility(View.GONE);
				loadingMessage.setVisibility(View.GONE);
				retryButton.setVisibility(View.VISIBLE);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.keywords:
			Intent intent = new Intent(this, SearchActivity.class);
			this.startActivity(intent);
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		HotArea hotArea = (HotArea) adapter.getItem(position);
		if (hotArea.getCity() == null) {
			Intent intent = new Intent(this, AreaListActivity.class);
			this.startActivity(intent);
		} else {
			Area area = areaService.findByName(hotArea.getName());
			Intent intent = new Intent(this, ShopSearchActivity.class);
			intent.putExtra("areaId", area.getId());
			startActivity(intent);
		}
	}

	private class HotAreaAdapter extends BaseAdapter {
		List<HotArea> hotAreaList;

		public HotAreaAdapter() {
			hotAreaList = new ArrayList<HotArea>();
		}
		
		@Override
		public int getCount() {
			return hotAreaList.size();
		}

		@Override
		public Object getItem(int position) {
			return hotAreaList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(HomeActivity.this).inflate(R.layout.list_item_hot_area, null);
				holder = new Holder();
				holder.name = (TextView) convertView.findViewById(R.id.name);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			HotArea hotArea = hotAreaList.get(position);
			holder.name.setText(hotArea.getName());

			return convertView;
		}

		public void addMoreItems(List<HotArea> hotAreas) {
			hotAreaList.addAll(hotAreas);
			this.notifyDataSetChanged();
		}

		private class Holder {
			TextView name;
		}
	}
}
