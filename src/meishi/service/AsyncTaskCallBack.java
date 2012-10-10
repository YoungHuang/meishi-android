package meishi.service;

import meishi.utils.ResponseCode;

public interface AsyncTaskCallBack<T> {
	public void refresh(T t, ResponseCode code);
}
