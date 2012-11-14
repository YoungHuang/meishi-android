package meishi.ui;

import meishi.MainApplication;
import meishi.db.PreferenceService;
import meishi.domain.User;
import meishi.service.BaseAsyncTaskCallBack;
import meishi.service.UserService;
import meishi.utils.UserData;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UserHomeActivity extends BaseActivity implements OnClickListener {
	private PreferenceService preferenceService;
	private UserService userService;

	private User user;
	
	private TextView newOrderCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		setContentView(R.layout.activity_user_home);

		initVariables();

		initView();
		
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
	
	private void initView() {
		newOrderCount = (TextView) findViewById(R.id.newOrderCount);
		RelativeLayout orderLayout = (RelativeLayout) findViewById(R.id.orderLayout);
		RelativeLayout addressLayout = (RelativeLayout) findViewById(R.id.addressLayout);
		orderLayout.setOnClickListener(this);
		addressLayout.setOnClickListener(this);
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
				newOrderCount.setText("(" + userData.getNewOrderCount() + ")");
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.orderLayout:
			startActivity(OrderListActivity.class);
			break;
		case R.id.addressLayout:
			startActivity(AddressActivity.class);
			break;
		}
	}
}
