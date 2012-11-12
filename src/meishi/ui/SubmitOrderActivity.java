package meishi.ui;

import meishi.MainApplication;
import meishi.db.PreferenceService;
import meishi.domain.Order;
import meishi.network.ResponseMessage;
import meishi.service.AsyncTaskCallBack;
import meishi.service.OrderService;
import meishi.utils.GlobalData;
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
		TextView addressView = (TextView) findViewById(R.id.address);
		addressView.setText(order.getAddress());
		TextView modifyAddressView = (TextView) findViewById(R.id.modifyAddress);
		Button submitButton = (Button) findViewById(R.id.submit);

		submitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				orderService.submitOrder(order, new AsyncTaskCallBack<Void>() {
					@Override
					public void onSuccess(Void t) {
						Toast.makeText(SubmitOrderActivity.this, "submit order success", Toast.LENGTH_LONG).show();
					}

					@Override
					public void onError(ResponseMessage responseMessage) {
						Toast.makeText(SubmitOrderActivity.this, "submit order error", Toast.LENGTH_LONG).show();
					}
				});
			}
		});
	}
}
