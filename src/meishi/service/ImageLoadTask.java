package meishi.service;

import meishi.utils.NetUtils;
import android.graphics.drawable.Drawable;


public class ImageLoadTask extends Task {
	private String url;
	
	private Drawable image;
	
	public ImageLoadTask(String url, RefreshCallBack callBack) {
		this.callBack = callBack;
		this.url = url;
	}
	
	@Override
	public void execute() {
		image = NetUtils.getImageFromUrl(url);
	}

	@Override
	public void refresh() {
		callBack.refresh(image);
	}
	
}
