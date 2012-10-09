package meishi.ui;

import java.util.List;

import meishi.MainApplication;
import meishi.db.PreferenceService;
import meishi.domain.Area;
import meishi.domain.City;
import meishi.domain.District;
import meishi.service.AreaService;
import meishi.service.AsyncTaskCallBack;
import meishi.service.DistrictService;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AreaListActivity extends Activity {
	private static final String TAG = "AreaListActivity";

	private PreferenceService preferenceService;
	private DistrictService districtService;
	private AreaService areaService;
	
	private AreaExListViewAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_area_list);
		
		initVariables();
		
		initAreaList();
	}
	
	private void initVariables() {
		MainApplication application = (MainApplication) getApplicationContext();
		preferenceService = application.getPreferenceService();
		districtService = application.getDistrictService();
		areaService = application.getAreaService();
	}
	
	private void initAreaList() {
		final ExpandableListView areaListView = (ExpandableListView) findViewById(R.id.areaList);
		final LinearLayout loadingLayout = (LinearLayout) findViewById(R.id.loadingLayout);
		City city = preferenceService.getCity();
		
		districtService.loadAllByCityId(city.getId(), new AsyncTaskCallBack<List<District>>() {
			@Override
			public void refresh(List<District> districtList) {
				if (districtList == null)
					return;
				
				loadingLayout.setVisibility(View.GONE);
				areaListView.setVisibility(View.VISIBLE);
				District district = new District();
				district.setName("全部地区");
				districtList.add(0, district);
				adapter = new AreaExListViewAdapter(districtList);
				areaListView.setAdapter(adapter);
			}
		});
	}
	
	class AreaExListViewAdapter extends BaseExpandableListAdapter {
		List<District> districtList;
		
		public AreaExListViewAdapter(List<District> districtList) {
			this.districtList = districtList;
		}

		@Override
		public int getGroupCount() {
			return districtList.size();
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return areaService.getCountByDistrictId(districtList.get(groupPosition).getId());
		}

		@Override
		public Object getGroup(int groupPosition) {
			return districtList.get(groupPosition);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			District district = districtList.get(groupPosition);
			List<Area> areaList = areaService.findAllByDistrictId(district.getId());
			return areaList.get(childPosition);
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
			GroupHolder groupHolder;
			if (convertView == null) {
				convertView = LayoutInflater.from(AreaListActivity.this).inflate(R.layout.expandable_item_group, null);
				groupHolder = new GroupHolder();
				groupHolder.name = (TextView) convertView.findViewById(R.id.name);
				groupHolder.arrow = (ImageView) convertView.findViewById(R.id.arrow);
				convertView.setTag(groupHolder);
			} else {
				groupHolder = (GroupHolder) convertView.getTag();
			}
			
			final District district = districtList.get(groupPosition);
			groupHolder.name.setText(district.getName());
			groupHolder.name.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(AreaListActivity.this, ShopSearchActivity.class);
					intent.putExtra("districtId", district.getId());
					AreaListActivity.this.startActivity(intent);
				}
			});
			
			if (isExpanded) {
				groupHolder.arrow.setImageResource(R.drawable.up_arr);
			} else {
				groupHolder.arrow.setImageResource(R.drawable.down_arr);
			}
			
			return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
				ViewGroup parent) {
			ChildHolder childHolder;
			if (convertView == null) {
				convertView = LayoutInflater.from(AreaListActivity.this).inflate(R.layout.expandable_item_child, null);
				childHolder = new ChildHolder();
				childHolder.name = (TextView) convertView.findViewById(R.id.name);
				childHolder.expChildLayout = (RelativeLayout) convertView.findViewById(R.id.exp_child_layout);
				convertView.setTag(childHolder);
			} else {
				childHolder = (ChildHolder) convertView.getTag();
			}
			Area area = (Area) getChild(groupPosition, childPosition);
			childHolder.name.setText(area.getName());
			childHolder.expChildLayout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO
				}
			});
			
			return convertView;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
		
		class GroupHolder {
			TextView name;
			ImageView arrow;
		}

		class ChildHolder {
			TextView name;
			RelativeLayout expChildLayout;
		}
	}
}
