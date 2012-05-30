package meishi.ui;

import meishi.domain.Shop;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShopDetailActivity extends Activity implements OnClickListener {
	private static final String TAG = "ShopDetailActivity";
	
	public static Shop shop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.shop_detail_activity);
		Log.d(TAG, shop.getName());
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText(shop.getName());
		TextView address = (TextView) this.findViewById(R.id.address);
		address.setText(shop.getAddress().toString());
		TextView phone = (TextView) this.findViewById(R.id.phone);
		phone.setText(shop.getPhone());
		TextView description = (TextView) this.findViewById(R.id.description);
		description.setText(shop.getDescription());
		
		// 电话
		LinearLayout phoneLayout = (LinearLayout) this.findViewById(R.id.phoneLayout);
		phoneLayout.setOnClickListener(this);
		
		// 点外卖
		Button orderButton = (Button) this.findViewById(R.id.order);
		orderButton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch(v.getId()) {
		case R.id.phoneLayout: // 电话
			intent = new Intent();
			intent.setAction("android.intent.action.CALL");
			intent.setData(Uri.parse("tel:" + shop.getPhone()));
			this.startActivity(intent);
			break;
		case R.id.order: // 点外卖
			intent = new Intent(this, DishActivity.class);
			DishActivity.shop = shop;
			this.startActivity(intent);
			break;
		}
	}
}
