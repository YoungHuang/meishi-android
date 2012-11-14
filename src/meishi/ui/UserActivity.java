package meishi.ui;

import meishi.MainApplication;
import meishi.domain.User;
import meishi.network.ResponseMessage;
import meishi.service.AsyncTaskCallBack;
import meishi.service.UserService;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserActivity extends BaseActivity implements OnClickListener {
	public static final String ACTION = "Action";
	public static final int ACTION_LOGIN = 0;
	public static final int ACTION_REGISTER = 1;
	
	private int action = ACTION_LOGIN;
	
	private UserService userService;
	
	private RelativeLayout loginLayout;
	private RelativeLayout registerLayout;
	private TextView nameTxt;
	private TextView passwordTxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		
		initVariables();
		
		action = this.getIntent().getIntExtra(ACTION, ACTION_LOGIN);
		loginLayout = (RelativeLayout) findViewById(R.id.loginLayout);
		registerLayout = (RelativeLayout) findViewById(R.id.registerLayout);
		if (action == ACTION_REGISTER) {
			loginLayout.setVisibility(View.GONE);
			registerLayout.setVisibility(View.VISIBLE);
		}
		
		nameTxt = (TextView) findViewById(R.id.name);
		passwordTxt = (TextView) findViewById(R.id.password);
		Button loginBtn = (Button) findViewById(R.id.login);
		Button registerBtn = (Button) findViewById(R.id.register);
		Button registerConfirmBtn = (Button) findViewById(R.id.registerConfirm);
		loginBtn.setOnClickListener(this);
		registerBtn.setOnClickListener(this);
		registerConfirmBtn.setOnClickListener(this);
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
			showRegisterView();
			break;
		case R.id.registerConfirm:
			register();
			break;
		}
	}

	private void login() {
		String name = nameTxt.getText().toString();
		String password = passwordTxt.getText().toString();
		userService.login(name, password, new AsyncTaskCallBack<Void>() {
			@Override
			public void onSuccess(Void t) {
				finish();
			}

			@Override
			public void onError(ResponseMessage responseMessage) {
				Toast.makeText(UserActivity.this, "Login failed!", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void showRegisterView() {
		loginLayout.setVisibility(View.GONE);
		registerLayout.setVisibility(View.VISIBLE);
	}
	
	private void register() {
		TextView nameTxt = (TextView) findViewById(R.id.newName);
		TextView passwordTxt = (TextView) findViewById(R.id.newPassword);
		TextView passwordConfirmTxt = (TextView) findViewById(R.id.newPassword2);
		String name = nameTxt.getText().toString();
		String password = passwordTxt.getText().toString();
		String password2 = passwordConfirmTxt.getText().toString();
		if (name == null || "".equals(name)) {
			Toast.makeText(UserActivity.this, "Name can not be blank", Toast.LENGTH_SHORT).show();
			return;
		}
		if (password == null || "".equals(password) || password2 == null || "".equals(password2)) {
			Toast.makeText(UserActivity.this, "Password can not be blank", Toast.LENGTH_SHORT).show();
			return;
		}
		if (!password.equals(password2)) {
			Toast.makeText(UserActivity.this, "Passwords not equal", Toast.LENGTH_SHORT).show();
			return;
		}
		
		userService.register(name, password, new AsyncTaskCallBack<User>() {
			@Override
			public void onSuccess(User user) {
				Toast.makeText(UserActivity.this, "Register successful!", Toast.LENGTH_SHORT).show();
				finish();
			}

			@Override
			public void onError(ResponseMessage responseMessage) {
				Toast.makeText(UserActivity.this, responseMessage.getErrorMessage(), Toast.LENGTH_SHORT).show();
			}
		});
	}
}
