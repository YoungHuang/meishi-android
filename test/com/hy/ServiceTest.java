package com.hy;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import meishi.domain.HotArea;
import meishi.domain.Shop;
import meishi.network.NetworkService;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.gson.reflect.TypeToken;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class ServiceTest {
	@Test
	public void hotAreaServiceTest() {
		String url = NetworkService.hostUrl + "/hotArea/search";
		Type type = new TypeToken<List<HotArea>>(){}.getType();
		Map<String, String> params = new HashMap<String, String>();
		params.put("cityId", "1");
		List<HotArea> hotAreaList = null;
		try {
			hotAreaList = NetworkService.getForList(url, type, params);
			if (hotAreaList != null) {
				HotArea hotArea = hotAreaList.get(9);
				System.out.println(hotArea.getName());
				System.out.println(hotArea.getCity().getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void shopServiceTest() {
		String url = NetworkService.hostUrl + "/shop/search";
		Type type = new TypeToken<List<Shop>>(){}.getType();
		Map<String, String> params = new HashMap<String, String>();
		params.put("cityId", "1");
		List<Shop> shopList = null;
		try {
			shopList = NetworkService.getForList(url, type, params);
			if (shopList != null) {
				Shop shop = shopList.get(0);
				System.out.println(shop.getName());
				System.out.println(shop.getCity());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
