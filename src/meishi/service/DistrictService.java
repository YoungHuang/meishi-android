package meishi.service;

import java.util.List;

import meishi.db.base.DaoSupport;
import meishi.domain.District;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DistrictService extends DaoSupport<District> {
	private static final String TAG = "AreaService";

	public static void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "createTable");
	}
	
	public List<District> findAllByCityId(Integer cityId) {
		// TODO
		return null;
	}
	
	public void loadAllByCityId(Integer cityId, AsyncTaskCallBack<List<District>> callBack) {
		List<District> districtList = findAllByCityId(cityId);
		
		if (districtList == null) {
			new DistrictAsyncTask(callBack).execute(cityId);
		} else {
			callBack.refresh(districtList);
		}
	}
	
	private class DistrictAsyncTask extends BaseAsyncTask<Integer, Void, List<District>> {
		public DistrictAsyncTask(AsyncTaskCallBack<List<District>> callBack) {
			this.callBack = callBack;
		}

		@Override
		protected List<District> doInBackground(Integer... params) {
			// Loading district list from Net
			
			// Loading area list from Net
			
			return null;
		}
	}
}
