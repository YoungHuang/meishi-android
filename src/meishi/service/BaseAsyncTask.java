package meishi.service;

import android.os.AsyncTask;

public abstract class BaseAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
	protected AsyncTaskCallBack<Result> callBack;
	
	@Override
	protected void onPostExecute(Result result) {
		if (callBack != null)
			callBack.refresh(result);
	}
}
