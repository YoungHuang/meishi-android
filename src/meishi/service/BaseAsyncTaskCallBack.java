package meishi.service;

import meishi.network.ResponseMessage;
import meishi.ui.UserActivity;
import android.content.Context;
import android.content.Intent;

public abstract class BaseAsyncTaskCallBack<T> implements AsyncTaskCallBack<T> {
	protected Context context;
	
	public BaseAsyncTaskCallBack(Context context) {
		this.context = context;
	}
	
	@Override
	public void onError(ResponseMessage responseMessage) {
		if (responseMessage.getErrorCode() == ResponseMessage.NOT_LOGIN_ERROR) {
			Intent intent = new Intent(context, UserActivity.class);
			context.startActivity(intent);
		} else {
			onOtherError(responseMessage);
		}
	}
	
	abstract protected void onOtherError(ResponseMessage responseMessage);
}
