package meishi.service;

import meishi.utils.ResponseCode;
import android.os.AsyncTask;

public abstract class BaseAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
	protected AsyncTaskCallBack<Result> callBack;
	protected ResponseCode code = ResponseCode.SUCCESS;
	
	@Override
	protected void onPostExecute(Result result) {
		if (callBack != null) {
			if (code == ResponseCode.SUCCESS) {
				callBack.onSuccess(result);
			} else {
				callBack.onError(code);
			}
		}
	}
}
