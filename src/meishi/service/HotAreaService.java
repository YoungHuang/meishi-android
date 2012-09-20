package meishi.service;

import java.util.List;

import meishi.db.base.DaoSupport;
import meishi.domain.HotArea;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

public class HotAreaService extends DaoSupport<HotArea> {
	private static final String TAG = "HotAreaService";

	public static void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "createTable");
	}

	public List<HotArea> getAll(AsyncTaskCallBack<List<HotArea>> callBack) {
		List<HotArea> hotAreaList = findAll();

		if (hotAreaList == null) {
			new HotAreaTask(callBack).execute(null);
		}

		return hotAreaList;
	}

	private class HotAreaTask extends AsyncTask<Void, Void, List<HotArea>> {
		private AsyncTaskCallBack<List<HotArea>> callBack;

		public HotAreaTask(AsyncTaskCallBack<List<HotArea>> callBack) {
			this.callBack = callBack;
		}

		@Override
		protected List<HotArea> doInBackground(Void... params) {

			return null;
		}

		@Override
		protected void onPostExecute(List<HotArea> hotAreaList) {
			callBack.refresh(hotAreaList);
		}
	}
}
