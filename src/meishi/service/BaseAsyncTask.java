package meishi.service;

import meishi.utils.ResponseCode;
import android.os.AsyncTask;

public abstract class BaseAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
	protected AsyncTaskCallBack<Result> callBack;
	protected ResponseCode code;
	
	@Override
	protected void onPostExecute(Result result) {
		if (callBack != null)
			callBack.refresh(result, code);
	}
}
