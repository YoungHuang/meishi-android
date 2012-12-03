package meishi.ui;

import java.util.List;

import meishi.MainApplication;
import meishi.adapter.BaseListViewAdapter;
import meishi.db.PreferenceService;
import meishi.domain.City;
import meishi.network.ResponseMessage;
import meishi.service.AsyncTaskCallBack;
import meishi.service.CityService;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;


public class CitySelectActivity extends BaseActivity implements OnItemClickListener {
	private PreferenceService preferenceService;
	private CityService cityService;
	
	private HotCityListAdapter adapter;
	
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
		ListView hotCityListView  = (ListView) findViewById(R.id.hotCityList);
		adapter = new HotCityListAdapter();
		hotCityListView.setAdapter(adapter);
		hotCityListView.setOnItemClickListener(this);
	}
	
	private void loadHotCities() {
		cityService.loadHotCities(new AsyncTaskCallBack<List<City>>() {
			@Override
			public void onSuccess(List<City> cityList) {
				if (cityList != null) {
					adapter.addMoreItems(cityList);
				}
			}

			@Override
			public void onError(ResponseMessage responseMessage) {
				showShortToast("Load hot cities error!");
			}
		});
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
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
