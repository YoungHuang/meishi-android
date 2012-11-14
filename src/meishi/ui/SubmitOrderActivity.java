package meishi.ui;

import meishi.MainApplication;
import meishi.db.PreferenceService;
import meishi.domain.Order;
import meishi.service.BaseAsyncTaskCallBack;
import meishi.service.OrderService;
import meishi.utils.GlobalData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SubmitOrderActivity extends BaseActivity {
	private PreferenceService preferenceService;
	private OrderService orderService;
	private Order order;

	private TextView addressView;
	private TextView modifyAddressView;
	private Button submitButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_submit_order);

		initVariables();

		order = GlobalData.order;
		order.setAddress(preferenceService.getUser().getAddress());
		initView();
	}

	private void initVariables() {
		MainApplication application = (MainApplication) getApplicationContext();
		orderService = application.getOrderService();
		preferenceService = PreferenceService.getInstance();
	}

	private void initView() {
		addressView = (TextView) findViewById(R.id.address);
		addressView.setText(order.getAddress());
		modifyAddressView = (TextView) findViewById(R.id.modifyAddress);
		modifyAddressView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SubmitOrderActivity.this, AddressActivity.class);
				startActivityForResult(intent, 0);
			}
		});
		submitButton = (Button) findViewById(R.id.submit);
		if (order.getAddress() == null || "".equals(order.getAddress())) {
			submitButton.setClickable(false);
		}

		submitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				orderService.submitOrder(order, new BaseAsyncTaskCallBack<Void>(SubmitOrderActivity.this) {
					@Override
					public void onSuccess(Void t) {
						Toast.makeText(SubmitOrderActivity.this, "submit order success", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(SubmitOrderActivity.this, MainActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}
				});
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		addressView.setText(order.getAddress());
		if (order.getAddress() == null || "".equals(order.getAddress())) {
			submitButton.setClickable(true);
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
}
