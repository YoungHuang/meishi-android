package meishi.ui;

import meishi.MainApplication;
import meishi.network.ResponseMessage;
import meishi.service.AsyncTaskCallBack;
import meishi.service.UserService;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UserActivity extends BaseActivity implements OnClickListener {
	private UserService userService;
	
	private TextView nameTxt;
	private TextView passwordTxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		
		initVariables();
		
		nameTxt = (TextView) findViewById(R.id.name);
		passwordTxt = (TextView) findViewById(R.id.password);
		Button loginBtn = (Button) findViewById(R.id.login);
		Button registerBtn = (Button) findViewById(R.id.register);
		loginBtn.setOnClickListener(this);
		registerBtn.setOnClickListener(this);
	}
	
	private void initVariables() {
		MainApplication application = (MainApplication) getApplicationContext();
		userService = application.getUserService();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.login:
			login();
			break;
		case R.id.register:
			
			break;
		}
	}

	private void login() {
		String name = nameTxt.getText().toString();
		String password = passwordTxt.getText().toString();
		userService.login(name, password, new AsyncTaskCallBack<Void>() {
			@Override
			public void onSuccess(Void t) {
				startActivity(UserHomeActivity.class);
			}

			@Override
			public void onError(ResponseMessage responseMessage) {
				Toast.makeText(UserActivity.this, "Login failed!", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
