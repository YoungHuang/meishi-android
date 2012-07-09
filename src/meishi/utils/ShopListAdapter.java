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
 * 
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
		if (position == getCount() - 1) {// 更多
			convertView = LayoutInflater.from(context).inflate(R.layout.list_moreitems, null);
		} else {
			Holder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.shop_itemview, null);
				holder = new Holder();
				holder.shopName = (TextView) convertView.findViewById(R.id.shopName);
				holder.address = (TextView) convertView.findViewById(R.id.address);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			Shop shop = shopList.get(position);
			holder.shopName.setText(shop.getName());
			holder.address.setText(shop.getCity());
		}
		
		return convertView;
	}

	public void addMoreItems(List<Shop> moreShopList) {
		if (moreShopList.size() != 0) {
			shopList.addAll(moreShopList);
			this.notifyDataSetChanged();
		}
	}

	private class Holder {
		TextView shopName;
		TextView address;
	}
}
