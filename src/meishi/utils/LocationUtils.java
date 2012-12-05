package meishi.utils;

import meishi.MainApplication;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class LocationUtils {
	private static LocationUtils instance;

	private LocationClient mLocationClient;

	private LocationUtils() {
		mLocationClient = new LocationClient(MainApplication.getInstance());
		LocationClientOption option = new LocationClientOption();
		option.setCoorType("bd09ll");
		option.setProdName("meishi");
		option.setPriority(LocationClientOption.NetWorkFirst);
		mLocationClient.setLocOption(option);
	}

	public static LocationUtils getInstance() {
		if (instance != null) {
			return instance;
		}

		synchronized (LocationUtils.class) {
			if (instance != null) {
				return instance;
			}

			instance = new LocationUtils();
			return instance;
		}
	}

	public void getLocation(LocationCallBack callBack) {
		mLocationClient.registerLocationListener(new LocationListener(callBack));
		mLocationClient.requestLocation();
	}

	public interface LocationCallBack {
		void onComplete(String name);

		void onError();
	}

	private class LocationListener implements BDLocationListener {
		LocationCallBack callBack;

		public LocationListener(LocationCallBack callBack) {
			this.callBack = callBack;
		}

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location != null) {
				String cityName = location.getCity();
				if (cityName == null || cityName.isEmpty()) {
					callBack.onError();
				} else {
					callBack.onComplete(cityName);
				}
			} else {
				callBack.onError();
			}
			mLocationClient.unRegisterLocationListener(this);
		}

		@Override
		public void onReceivePoi(BDLocation location) {
			// return ;
		}
	}
}
