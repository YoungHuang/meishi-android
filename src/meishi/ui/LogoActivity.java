package meishi.ui;

import meishi.network.NetworkService;
import meishi.persistence.DBManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.Toast;

public class LogoActivity extends Activity {
	private static final String TAG = "LogoActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logo_activity);
		
		ImageView imageView = (ImageView) this.findViewById(R.id.logo_bg);
		AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
        alphaAnimation.setDuration(3000);
        imageView.startAnimation(alphaAnimation);
        
        alphaAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				Intent intent = new Intent(LogoActivity.this, StartActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		init();
	}

	private void init() {
		Log.d(TAG, "init");
		
		DBManager.open(this.getApplicationContext());
		
		// 启动MainService
		Intent mainService = new Intent("meishi.service.MainService");
		startService(mainService);
		
		// 检查网络
		if (!NetworkService.isNetworkActive(this)) {
			Toast.makeText(this, "网络连接失败", Toast.LENGTH_SHORT).show();
		}
	}
}
