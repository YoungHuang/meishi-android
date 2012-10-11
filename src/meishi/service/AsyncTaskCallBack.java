package meishi.service;

import meishi.utils.ResponseCode;

public interface AsyncTaskCallBack<T> {
	public void onSuccess(T t);
	public void onError(ResponseCode code);
}
