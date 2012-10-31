package meishi.service;

import meishi.network.ResponseMessage;
import android.os.AsyncTask;

public abstract class BaseAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
	protected AsyncTaskCallBack<Result> callBack;
	protected ResponseMessage responseMessage;
	
	@Override
	protected void onPostExecute(Result result) {
		if (callBack != null) {
			if (responseMessage == null) {
				callBack.onSuccess(result);
			} else {
				callBack.onError(responseMessage);
			}
		}
	}
}
