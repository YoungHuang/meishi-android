package meishi.service;

import android.content.Context;

public class AddressService {
	private static final String TAG = "AddressService";
	
	// 获取当前地址
	public void initNowAddress(Context context) {
/*		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
		
		String provider = locationManager.getBestProvider(criteria, true);
		Location location = locationManager.getLastKnownLocation(provider);
		
		GlobalData.nowAddress.setLatitude(location.getLatitude());
		GlobalData.nowAddress.setLongitude(location.getLongitude());
		Geocoder geocoder = new Geocoder(context, "c2b0f58a6f09cafd1503c06ef08ac7aeb7ddb91adee7dc88a1db9e573d4ffd6ed83866cf0472a9f8");
		try {
			List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
			Address address = addressList.get(0);
			GlobalData.nowAddress.setCity(address.getAdminArea());
			GlobalData.nowAddress.setDistrict(address.getSubLocality());
			GlobalData.nowAddress.setRoad(address.getFeatureName());
		} catch (IOException e) {
			Log.e(TAG, "initNowAddress", e);
		}*/
	}
	
	
}
