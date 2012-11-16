package meishi.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

public class PaginatedListView extends ListView implements OnScrollListener {
	private Context context;
	
	private OnLoadMoreDataListener onLoadMoreDataListener;
	
	private View footerView;
	private LinearLayout moreExceptionLayout;
	private LinearLayout moreLoadLayout;

	public PaginatedListView(Context context) {
		super(context);
		this.context = context;
	}
	public PaginatedListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
    }

    public PaginatedListView(Context context, AttributeSet attrs, int defStyle) {
    	super(context, attrs, defStyle);
    	this.context = context;
    }
    
    public void enablePagination() {
    	footerView = LayoutInflater.from(context).inflate(R.layout.list_item_more, null);
    	this.addFooterView(footerView);
    	
    	moreExceptionLayout = (LinearLayout) footerView.findViewById(R.id.more_exception_layout);
    	moreLoadLayout = (LinearLayout) footerView.findViewById(R.id.more_load_layout);
    	this.setOnScrollListener(this);
    }

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
			if (onLoadMoreDataListener != null)
				onLoadMoreDataListener.loadMoreData();
		}
	}
	
	public void setOnLoadMoreDataListener(OnLoadMoreDataListener onLoadMoreDataListener) {
		this.onLoadMoreDataListener = onLoadMoreDataListener;
	}
	
	public void onLoadSuccess() {
		moreLoadLayout.setVisibility(View.VISIBLE);
		moreExceptionLayout.setVisibility(View.GONE);
	}
	
	public void onLoadError() {
		moreLoadLayout.setVisibility(View.GONE);
		moreExceptionLayout.setVisibility(View.VISIBLE);
	}
	
	public void onLoadComplete() {
		this.removeFooterView(footerView);
	}
	
	public interface OnLoadMoreDataListener {
		void loadMoreData();
	}
}
