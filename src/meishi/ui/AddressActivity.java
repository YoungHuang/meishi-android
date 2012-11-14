package meishi.ui;

import meishi.MainApplication;
import meishi.db.PreferenceService;
import meishi.domain.User;
import meishi.service.BaseAsyncTaskCallBack;
import meishi.service.UserService;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddressActivity extends BaseActivity implements OnClickListener {
	private PreferenceService preferenceService;
	private UserService userService;
	
	private TextView addressTxt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		setContentView(R.layout.activity_address);
		
		initVariables();
		
		initView();
	}
	
	private void initVariables() {
		MainApplication application = MainApplication.getInstance();
		preferenceService = application.getPreferenceService();
		userService = application.getUserService();
	}
	
	private void initView() {
		addressTxt = (TextView) findViewById(R.id.address);
		User user = preferenceService.getUser();
		addressTxt.setText(user.getAddress());
		
		Button saveBtn = (Button) findViewById(R.id.save);
		Button cancelBtn = (Button) findViewById(R.id.cancel);
		
		saveBtn.setOnClickListener(this);
		cancelBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.save:
			User user = preferenceService.getUser();
			user.setAddress(addressTxt.getText().toString());
			userService.updateUser(user, new BaseAsyncTaskCallBack<Void>(AddressActivity.this) {
				@Override
				public void onSuccess(Void t) {
					Toast.makeText(AddressActivity.this, "update user successful!", Toast.LENGTH_SHORT).show();
					finish();
				}
			});
			break;
		case R.id.cancel:
			finish();
			break;
		}
	}
}
