package meishi.service;

import java.sql.SQLException;
import java.util.List;

import meishi.db.DaoSupport;
import meishi.domain.Area;
import android.util.Log;

public class AreaService extends DaoSupport<Area, Integer> {
	public AreaService() throws SQLException {
		super(Area.class);
	}
	
	public Area findByName(String name) {
		// TODO
		return null;
	}
	
	public List<Area> findAllByDistrictId(Integer districtId) {
		// TODO
		return null;
	}
	
	public int getCountByDistrictId(Integer districtId) {
		// TODO
		return 0;
	}
	
	public void saveList(List<Area> areaList) {
		if (areaList != null) {
			for (Area area : areaList) {
				try {
					create(area);
				} catch (SQLException e) {
					Log.e(TAG, "saveList() exception", e);
				}
			}
		}
	}
}
