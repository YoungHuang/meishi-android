package meishi.ui;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {
	private static final String TAG = "MainActivity";

	private static final String TAB_HOME = "TabHome";
	private static final String TAB_COLLECT = "TabCollect";
	private static final String TAB_USER = "TabUser";
	
	TabHost tabHost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main_activity);
		
		tabHost = this.getTabHost();
		// 创建tab页
		TabSpec tab1 = tabHost.newTabSpec(TAB_HOME).setIndicator(TAB_HOME);
		tab1.setContent(new Intent(this, HomeActivity.class));
		tabHost.addTab(tab1);
		
		TabSpec tab3 = tabHost.newTabSpec(TAB_COLLECT).setIndicator(TAB_COLLECT);
		tab3.setContent(new Intent(this, CollectActivity.class));
		tabHost.addTab(tab3);
		
		TabSpec tab4 = tabHost.newTabSpec(TAB_USER).setIndicator(TAB_USER);
		tab4.setContent(new Intent(this, UserHomeActivity.class));
		tabHost.addTab(tab4);
		
		tabHost.setCurrentTabByTag(TAB_HOME);
		
		RadioGroup radioGroup = (RadioGroup) this.findViewById(R.id.main_radio);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch(checkedId) {
				case R.id.radio_button1: // Home
					tabHost.setCurrentTabByTag(TAB_HOME);
					break;
					
				case R.id.radio_button2: // Nearby
					tabHost.setCurrentTabByTag(TAB_COLLECT);
					break;
					
				case R.id.radio_button3: // Collection
					tabHost.setCurrentTabByTag(TAB_COLLECT);
					break;
					
				case R.id.radio_button4: // Personal center
					tabHost.setCurrentTabByTag(TAB_USER);
					break;
					
				case R.id.radio_button5: // Settings
					tabHost.setCurrentTabByTag(TAB_COLLECT);
					break;
				}
			}
		});
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		tabHost.setCurrentTabByTag(TAB_HOME);
	}
}
