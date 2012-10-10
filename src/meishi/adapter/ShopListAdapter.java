package meishi.adapter;

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
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

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
		TextView averageExpense;
		TextView commentCount;
	}
}
