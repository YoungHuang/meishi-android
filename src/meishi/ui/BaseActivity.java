package meishi.ui;

import android.app.Activity;

public class BaseActivity extends Activity {
	protected static String TAG;

	public BaseActivity() {
		TAG = this.getClass().getName();
	}
}
