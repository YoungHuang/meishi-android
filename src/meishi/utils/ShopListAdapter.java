package meishi.utils;

import java.util.List;

import meishi.domain.Shop;
import meishi.ui.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 商家列表Adapter
 * @author yonghuang
 *
 */
public class ShopListAdapter extends BaseAdapter {

	private List<Shop> shopList;
	private Context context;

	public ShopListAdapter(Context context, List<Shop> shopList) {
		this.context = context;
		this.shopList = shopList;
	}

	@Override
	public int getCount() {
		return shopList.size() + 1;
	}

	@Override
	public Object getItem(int position) {
		return shopList.get(position);
	}

	@Override
	public long getItemId(int position) {
		if (position == getCount() - 1) {
			return -1;
		} else {
			return shopList.get(position).getId();
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;

		if (position == getCount() - 1) {// 更多
			view = LayoutInflater.from(context).inflate(R.layout.list_moreitems, null);

			return view;
		} else {
			view = LayoutInflater.from(context).inflate(R.layout.shop_itemview, null);
			TextView shopName = (TextView) view.findViewById(R.id.shopName);
			TextView address = (TextView) view.findViewById(R.id.address);
			final Shop shop = shopList.get(position);
			shopName.setText(shop.getName());
			address.setText(shop.getAddress().getCity());

			return view;
		}
	}

	public void addMoreItems(List<Shop> moreShopList) {
		if (moreShopList.size() != 0) {
			shopList.addAll(moreShopList);
			this.notifyDataSetChanged();
		}
	}
}
