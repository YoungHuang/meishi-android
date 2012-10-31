package meishi.service;

import meishi.network.ResponseMessage;

public interface AsyncTaskCallBack<T> {
	public void onSuccess(T t);
	public void onError(ResponseMessage responseMessage);
}
