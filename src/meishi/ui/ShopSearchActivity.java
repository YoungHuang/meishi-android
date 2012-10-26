package meishi.ui;

import java.util.List;

import meishi.MainApplication;
import meishi.adapter.ShopListAdapter;
import meishi.db.PreferenceService;
import meishi.domain.City;
import meishi.domain.Shop;
import meishi.service.AsyncTaskCallBack;
import meishi.service.ShopService;
import meishi.utils.ResponseCode;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ShopSearchActivity extends Activity implements OnItemClickListener {
	private static final String TAG = "ShopSearchActivity";

	private static final int MAX_RESULT = 10;

	private ShopService shopService;
	private PreferenceService preferenceService;

	private ListView shopListView;
	private View footerView;
	private LinearLayout moreExceptionLayout;
	private ShopListAdapter shopListAdapter;
	private Integer cityId;
	private Integer districtId;
	private Integer areaId;
	private String keywords;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_search);

		int iDistrictId = getIntent().getIntExtra("districtId", -1);
		if (iDistrictId != -1) {
			districtId = iDistrictId;
		}

		int iAreaId = getIntent().getIntExtra("areaId", -1);
		if (iAreaId != -1) {
			areaId = iAreaId;
		}

		keywords = getIntent().getStringExtra("keywords");

		initVariables();

		initShopList();
	}

	private void initVariables() {
		MainApplication application = (MainApplication) getApplicationContext();
		shopService = application.getShopService();
		preferenceService = application.getPreferenceService();
	}

	private void initShopList() {
		City city = preferenceService.getCity();
		cityId = city.getId();

		shopListView = (ListView) this.findViewById(R.id.shopList);

		footerView = LayoutInflater.from(ShopSearchActivity.this).inflate(R.layout.list_item_more, null);
		shopListView.addFooterView(footerView);
		shopListAdapter = new ShopListAdapter(this);
		shopListView.setAdapter(shopListAdapter);

		moreExceptionLayout = (LinearLayout) footerView.findViewById(R.id.more_exception_layout);

		loadInitData();

		shopListView.setOnItemClickListener(this);
		shopListView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
					loadMoreData();
				}
			}
		});

		TextView textRetry = (TextView) footerView.findViewById(R.id.text_retry);
		textRetry.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadMoreData();
			}
		});
	}

	private void loadInitData() {
		final LinearLayout loadingLayout = (LinearLayout) findViewById(R.id.loadingLayout);
		final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
		final TextView loadingMessage = (TextView) findViewById(R.id.loadingMessage);
		final TextView retryButton = (TextView) findViewById(R.id.retryButton);
		
		progressBar.setVisibility(View.VISIBLE);
		
		shopService.loadShopList(cityId, districtId, areaId, keywords, 0, MAX_RESULT,
				new AsyncTaskCallBack<List<Shop>>() {
					@Override
					public void onSuccess(List<Shop> shopList) {
						loadingLayout.setVisibility(View.GONE);
						shopListView.setVisibility(View.VISIBLE);
						shopListAdapter.addMoreItems(shopList);
					}

					@Override
					public void onError(ResponseCode code) {
						progressBar.setVisibility(View.GONE);
						loadingMessage.setVisibility(View.GONE);
						retryButton.setVisibility(View.VISIBLE);
						loadInitData();
					}
				});
	}

	private void loadMoreData() {
		final LinearLayout moreLoadLayout = (LinearLayout) footerView.findViewById(R.id.more_load_layout);
		
		shopService.loadShopList(cityId, districtId, areaId, keywords, shopListAdapter.getCount(), MAX_RESULT,
				new AsyncTaskCallBack<List<Shop>>() {
					@Override
					public void onSuccess(List<Shop> shops) {
						if (shops != null) {
							shopListAdapter.addMoreItems(shops);
							moreLoadLayout.setVisibility(View.VISIBLE);
							moreExceptionLayout.setVisibility(View.GONE);
						} else {
							// No more items
							shopListView.removeFooterView(footerView);
						}
					}

					@Override
					public void onError(ResponseCode code) {
						moreLoadLayout.setVisibility(View.GONE);
						moreExceptionLayout.setVisibility(View.VISIBLE);
					}
				});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		ShopDetailActivity.shop = (Shop) shopListAdapter.getItem(position);
		Intent intent = new Intent(this, ShopDetailActivity.class);
		startActivity(intent);
	}
}
