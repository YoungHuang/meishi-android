package meishi.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import meishi.domain.Dish;
import meishi.domain.Shop;
import meishi.network.NetworkService;

import com.google.gson.reflect.TypeToken;

import android.util.Log;


/**
 * 获取菜单列表任务
 * @author yonghuang
 *
 */
public class GetDishListFromNetTask extends Task {
	private static final String TAG = "GetDishListFromNetTask";
	
	private static final String url = NetworkService.dishListUrl;
	private List<Dish> dishList = new ArrayList<Dish>();
	private Map<String, String> params = new HashMap<String, String>();
	
	public GetDishListFromNetTask(long dishCategoryId, int offset, int max, RefreshCallBack callBack) {
		params.put("dishCategoryId", String.valueOf(dishCategoryId));
		params.put("offset", String.valueOf(offset));
		params.put("max", String.valueOf(max));
		this.callBack = callBack;
	}

	@Override
	public void execute() {
		try {
			Type type = new TypeToken<List<Shop>>(){}.getType();
			List<Dish> dishes = NetworkService.getForList(url, type, params);
			if (dishes != null) {
				dishList = dishes;
			}
		} catch (Exception e) {
			Log.w(TAG, "execute() exception: ", e);
		}
	}
	
	@Override
	public void refresh() {
		callBack.refresh(dishList);
	}
}
