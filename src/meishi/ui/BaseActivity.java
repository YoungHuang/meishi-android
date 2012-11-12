package meishi.ui;

import android.app.Activity;
import android.content.Intent;

public class BaseActivity extends Activity {
	protected static String TAG;

	public BaseActivity() {
		TAG = this.getClass().getName();
	}
	
	protected void startActivity(Class<?> clazz) {
		Intent intent = new Intent(this, clazz);
		startActivity(intent);
	}
}
