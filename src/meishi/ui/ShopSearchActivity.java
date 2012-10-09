package meishi.ui;

import java.util.List;

import meishi.MainApplication;
import meishi.db.PreferenceService;
import meishi.domain.District;
import meishi.domain.Shop;
import meishi.service.AsyncTaskCallBack;
import meishi.service.DistrictService;
import meishi.service.ShopService;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ShopSearchActivity extends Activity {
	private static final String TAG = "ShopSearchActivity";
	
	private PreferenceService preferenceService;
	private ShopService shopService;
	private DistrictService districtService;
	
	private District district;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_search);
		
		int districtId = getIntent().getIntExtra("districtId", -1);
		if (districtId != -1) {
			district = districtService.find(districtId);
		}
		
		initVariables();
		
		initShopList();
	}
	
	private void initVariables() {
		MainApplication application = (MainApplication) getApplicationContext();
		preferenceService = application.getPreferenceService();
		shopService = application.getShopService();
		districtService = application.getDistrictService();
	}
	
	private void initShopList() {
		final ListView shopListView = (ListView) this.findViewById(R.id.shopList);
		final LinearLayout loadingLayout = (LinearLayout) findViewById(R.id.loadingLayout);
		
		shopService.loadShopList(district.getCityId(), district.getId(), null, null, 0, 5, new AsyncTaskCallBack<List<Shop>>() {
			@Override
			public void refresh(List<Shop> shopList) {
				if (shopList == null)
					return;
				
				loadingLayout.setVisibility(View.GONE);
				shopListView.setVisibility(View.VISIBLE);
			}
		});
	}
}
