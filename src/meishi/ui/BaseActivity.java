package meishi.ui;

import android.app.Activity;
import android.content.Intent;

public class BaseActivity extends Activity {
	protected static String TAG;
	
	protected static int MAX_RESULT = 10;

	public BaseActivity() {
		TAG = this.getClass().getName();
	}
	
	protected void startActivity(Class<?> clazz) {
		Intent intent = new Intent(this, clazz);
		startActivity(intent);
	}
}
