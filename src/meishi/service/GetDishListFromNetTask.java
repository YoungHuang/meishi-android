package meishi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import meishi.domain.Dish;
import meishi.utils.NetUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;


/**
 * 获取菜单列表任务
 * @author yonghuang
 *
 */
public class GetDishListFromNetTask extends Task {
	private static final String TAG = "GetDishListFromNetTask";
	
	private static final String url = NetUtils.dishListUrl;
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
			byte[] data = NetUtils.sendGetRequest(url, params, "UTF-8");
			String json = new String(data, "UTF-8");
			JSONArray jsonarray = new JSONArray(json);
			for (int i = 0; i < jsonarray.length(); i++) {
				JSONObject jsonobject = jsonarray.getJSONObject(i);
				Dish dish = new Dish();
				dish.stringToBean(jsonobject.toString());
				dishList.add(dish);
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
