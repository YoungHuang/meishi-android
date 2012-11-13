package meishi.ui;

import meishi.MainApplication;
import meishi.db.PreferenceService;
import meishi.domain.User;
import meishi.network.ResponseMessage;
import meishi.service.BaseAsyncTaskCallBack;
import meishi.service.UserService;
import meishi.utils.UserData;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class UserHomeActivity extends BaseActivity {
	private PreferenceService preferenceService;
	private UserService userService;

	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		setContentView(R.layout.activity_user_home);

		initVariables();

		user = preferenceService.getUser();
		if (user == null) {
			startActivity(UserActivity.class);
		} else {
			loadUserData();
		}
	}

	private void initVariables() {
		MainApplication application = MainApplication.getInstance();
		preferenceService = application.getPreferenceService();
		userService = application.getUserService();
	}

	@Override
	protected void onResume() {
		super.onResume();
		loadUserData();
	}

	private void loadUserData() {
		userService.loadUserData(new BaseAsyncTaskCallBack<UserData>(UserHomeActivity.this) {
			@Override
			public void onSuccess(UserData userData) {
				
			}

			@Override
			protected void onOtherError(ResponseMessage responseMessage) {
				Toast.makeText(UserHomeActivity.this, "load user data error", Toast.LENGTH_LONG).show();
			}
		});
	}
}
