package meishi.service;

import meishi.network.ResponseMessage;
import meishi.ui.AddressActivity;
import meishi.ui.UserActivity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

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
	
	protected void onOtherError(ResponseMessage responseMessage) {
		Toast.makeText(context, responseMessage.getErrorMessage(), Toast.LENGTH_LONG).show();
	}
}
