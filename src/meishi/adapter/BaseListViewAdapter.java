package meishi.adapter;

import java.util.ArrayList;
import java.util.List;

import android.widget.BaseAdapter;

public abstract class BaseListViewAdapter<T> extends BaseAdapter {
	protected List<T> itemList;
	
	public BaseListViewAdapter() {
		itemList = new ArrayList<T>();
	}
	
	public BaseListViewAdapter(List<T> itemList) {
		itemList = this.itemList;
	}

	@Override
	public int getCount() {
		return itemList.size();
	}

	@Override
	public Object getItem(int position) {
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void addMoreItems(List<T> items) {
		itemList.addAll(items);
		this.notifyDataSetChanged();
	}
	
	public void setItems(List<T> items) {
		itemList = items;
		this.notifyDataSetChanged();
	}
}
