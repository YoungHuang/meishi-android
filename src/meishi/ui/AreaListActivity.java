package meishi.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class AreaListActivity extends Activity {
	private static final String TAG = "AreaListActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_area_list);
	}
}
