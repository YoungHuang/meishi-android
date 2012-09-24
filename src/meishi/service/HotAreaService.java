package meishi.service;

import java.util.List;

import meishi.db.base.DaoSupport;
import meishi.domain.HotArea;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class HotAreaService extends DaoSupport<HotArea> {
	private static final String TAG = "HotAreaService";

	public static void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "createTable");
	}
	
	public List<HotArea> findAllByCityId(Integer cityId) {
		// TODO
		return null;
	}

	public void loadAll(Integer cityId, AsyncTaskCallBack<List<HotArea>> callBack) {
		List<HotArea> hotAreaList = findAllByCityId(cityId);

		if (hotAreaList == null) {
			new HotAreaAsyncTask(callBack).execute(cityId);
		} else {
			callBack.refresh(hotAreaList);
		}
	}

	private class HotAreaAsyncTask extends BaseAsyncTask<Integer, Void, List<HotArea>> {
		public HotAreaAsyncTask(AsyncTaskCallBack<List<HotArea>> callBack) {
			this.callBack = callBack;
		}

		@Override
		protected List<HotArea> doInBackground(Integer... params) {

			return null;
		}
	}
}
