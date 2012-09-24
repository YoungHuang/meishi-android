package meishi.ui;

import meishi.MainApplication;
import meishi.service.ShopService;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class ShopSearchActivity extends Activity {
	private static final String TAG = "ShopSearchActivity";
	
	private ShopService shopService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_search);
		
		initVariables();
	}
	
	private void initVariables() {
		MainApplication application = (MainApplication) getApplicationContext();
		shopService = application.getShopService();
	}
}
