package meishi.service;

import meishi.network.NetworkService;
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
		image = NetworkService.getImageFromUrl(url);
	}

	@Override
	public void refresh() {
		callBack.refresh(image);
	}
	
}
