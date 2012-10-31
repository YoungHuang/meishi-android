package meishi.ui;

import android.os.Bundle;
import android.util.Log;

public class AccountActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);
	}
}
