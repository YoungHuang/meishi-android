package meishi.ui;

import java.util.List;

import meishi.MainApplication;
import meishi.adapter.BaseListViewAdapter;
import meishi.db.PreferenceService;
import meishi.domain.City;
import meishi.network.ResponseMessage;
import meishi.service.AsyncTaskCallBack;
import meishi.service.CityService;
import meishi.utils.LocationUtils;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class CitySelectActivity extends BaseActivity implements OnItemClickListener, OnClickListener {
	private PreferenceService preferenceService;
	private CityService cityService;
	
	private HotCityListAdapter adapter;
	
	private City city;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_city_select);
		
		initVariables();
		
		initView();
		
		loadHotCities();
	}
	
	private void initVariables() {
		MainApplication application = (MainApplication) getApplicationContext();
		preferenceService = application.getPreferenceService();
		cityService = application.getCityService();
	}
	
	private void initView() {
		final TextView gpsCityView = (TextView) findViewById(R.id.gpsCity);
		LocationUtils.getInstance().getLocation(new LocationUtils.LocationCallBack() {
			@Override
			public void onError() {
				gpsCityView.setText("GPS get city failed");
			}
			
			@Override
			public void onComplete(String name) {
				gpsCityView.setText(name);
				city = cityService.findByName(name);
			}
		});
		gpsCityView.setOnClickListener(this);
		
		ListView hotCityListView  = (ListView) findViewById(R.id.hotCityList);
		adapter = new HotCityListAdapter();
		hotCityListView.setAdapter(adapter);
		hotCityListView.setOnItemClickListener(this);
		
		final TextView loadingMessage = (TextView) findViewById(R.id.loadingMessage);
		final TextView retryButton = (TextView) findViewById(R.id.retryButton);
		
		retryButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadingMessage.setVisibility(View.VISIBLE);
				retryButton.setVisibility(View.GONE);
				loadHotCities();
			}
		});
	}
	
	private void loadHotCities() {
		final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
		progressBar.setVisibility(View.VISIBLE);
		
		cityService.loadHotCities(new AsyncTaskCallBack<List<City>>() {
			@Override
			public void onSuccess(List<City> cityList) {
				LinearLayout loadingLayout = (LinearLayout) findViewById(R.id.loadingLayout);
				loadingLayout.setVisibility(View.GONE);
				ListView hotCityListView  = (ListView) findViewById(R.id.hotCityList);
				hotCityListView.setVisibility(View.VISIBLE);
				if (cityList != null) {
					adapter.addMoreItems(cityList);
				}
			}

			@Override
			public void onError(ResponseMessage responseMessage) {
				TextView loadingMessage = (TextView) findViewById(R.id.loadingMessage);
				TextView retryButton = (TextView) findViewById(R.id.retryButton);
				progressBar.setVisibility(View.GONE);
				loadingMessage.setVisibility(View.GONE);
				retryButton.setVisibility(View.VISIBLE);
			}
		});
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		city = (City) adapter.getItem(position);
		switchCity();
	}
	
	@Override
	public void onClick(View v) {
		if (city != null) {
			switchCity();
		}
	}

	
	private void switchCity() {
		if (city == null) {
			return;
		}
		City oldcity = preferenceService.getCity();
		boolean changed = false;
		if (oldcity == null || !oldcity.getId().equals(city.getId())) {
			changed = true;
		}
		preferenceService.setCity(city);
		Intent intent = new Intent();
		intent.putExtra("CityChanged", changed);
		this.setResult(Activity.RESULT_OK, intent);
		finish();
	}


	private class HotCityListAdapter extends BaseListViewAdapter<City> {
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(CitySelectActivity.this).inflate(R.layout.list_item_city, null);
				holder = new Holder();
				holder.name = (TextView) convertView.findViewById(R.id.name);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			City city = itemList.get(position);
			holder.name.setText(city.getName());

			return convertView;
		}
		
		private class Holder {
			TextView name;
		}
	}
}
