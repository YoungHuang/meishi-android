package meishi.service;

import java.sql.SQLException;

import meishi.db.DaoSupport;
import meishi.domain.City;

public class CityService extends DaoSupport<City, Integer> {
	public CityService() throws SQLException {
		super(City.class);
	}
	
	public City findByName(String name) {
		// TODO
		return null;
	}
}
