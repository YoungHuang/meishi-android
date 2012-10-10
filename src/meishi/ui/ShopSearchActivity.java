package meishi.ui;

import java.util.ArrayList;
import java.util.List;

import meishi.MainApplication;
import meishi.adapter.ShopListAdapter;
import meishi.domain.Area;
import meishi.domain.District;
import meishi.domain.Shop;
import meishi.service.AsyncTaskCallBack;
import meishi.service.DistrictService;
import meishi.service.ShopService;
import meishi.utils.ResponseCode;
import android.app.Activity;
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
	private DistrictService districtService;

	private ListView shopListView;
	private View footerView;
	private ShopListAdapter shopListAdapter;
	private District district;
	private Area area;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_search);

		int districtId = getIntent().getIntExtra("districtId", -1);
		if (districtId != -1) {
			district = districtService.find(districtId);
		}

		initVariables();

		initShopList();
	}

	private void initVariables() {
		MainApplication application = (MainApplication) getApplicationContext();
		shopService = application.getShopService();
		districtService = application.getDistrictService();
	}

	private void initShopList() {
		shopListView = (ListView) this.findViewById(R.id.shopList);

		final LinearLayout loadingLayout = (LinearLayout) findViewById(R.id.loadingLayout);
		final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

		footerView = LayoutInflater.from(ShopSearchActivity.this).inflate(R.layout.list_item_more, null);
		shopListView.addFooterView(footerView);
		shopListAdapter = new ShopListAdapter(this, new ArrayList<Shop>());
		shopListView.setAdapter(shopListAdapter);

		shopService.loadShopList(district.getCityId(), district.getId(), area.getId(), null, 0, MAX_RESULT,
				new AsyncTaskCallBack<List<Shop>>() {
					@Override
					public void refresh(List<Shop> shopList, ResponseCode code) {
						if (code != ResponseCode.SUCCESS) {
							progressBar.setVisibility(View.GONE);

							return;
						}

						loadingLayout.setVisibility(View.GONE);
						shopListView.setVisibility(View.VISIBLE);
						shopListAdapter.addMoreItems(shopList);
					}
				});

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

	private void loadMoreData() {
		final LinearLayout moreLoadLayout = (LinearLayout) footerView.findViewById(R.id.more_load_layout);
		final LinearLayout moreExceptionLayout = (LinearLayout) footerView.findViewById(R.id.more_exception_layout);
		
		shopService.loadShopList(district.getCityId(), district.getId(), area.getId(), null,
				shopListAdapter.getCount(), MAX_RESULT, new AsyncTaskCallBack<List<Shop>>() {
					@Override
					public void refresh(List<Shop> shops, ResponseCode code) {
						if (shops != null && code == ResponseCode.SUCCESS) {
							// No more items
							shopListAdapter.addMoreItems(shops);
							moreLoadLayout.setVisibility(View.VISIBLE);
							moreExceptionLayout.setVisibility(View.GONE);
						} else if (shops == null && code == ResponseCode.SUCCESS) {
							shopListView.removeFooterView(footerView);
						} else {
							moreLoadLayout.setVisibility(View.GONE);
							moreExceptionLayout.setVisibility(View.VISIBLE);
						}
					}
				});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	}
}
