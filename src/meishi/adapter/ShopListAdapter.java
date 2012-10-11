package meishi.adapter;

import java.util.ArrayList;
import java.util.List;

import meishi.domain.Shop;
import meishi.ui.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ShopListAdapter extends BaseAdapter {
	private Context context;
	private List<Shop> shopList;

	public ShopListAdapter(Context context) {
		this(context, new ArrayList<Shop>());
	}
	
	public ShopListAdapter(Context context, List<Shop> shopList) {
		this.context = context;
		this.shopList = shopList;
	}

	@Override
	public int getCount() {
		return shopList.size();
	}

	@Override
	public Object getItem(int position) {
		return shopList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.list_item_shop, null);
			holder = new Holder();
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.coupon = (ImageView) convertView.findViewById(R.id.coupon);
			holder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
			holder.startPrice = (TextView) convertView.findViewById(R.id.startPrice);
			holder.commentCount = (TextView) convertView.findViewById(R.id.commentCount);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		Shop shop = shopList.get(position);
		holder.title.setText(shop.getName());
		holder.ratingBar.setRating(shop.getRating());
		holder.startPrice.setText(shop.getStartPrice().toString());
		holder.commentCount.setText(shop.getCommentCount());

		return convertView;
	}

	public void addMoreItems(List<Shop> shops) {
		shopList.addAll(shops);
		this.notifyDataSetChanged();
	}

	private class Holder {
		TextView title;
		ImageView coupon;
		RatingBar ratingBar;
		TextView startPrice;
		TextView commentCount;
	}
}
