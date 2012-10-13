package meishi.db;

import java.sql.SQLException;


import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;


public abstract class DaoSupport<T, ID> extends BaseDaoImpl<T, ID> implements Dao<T, ID> {
	protected static String TAG;

	public DaoSupport(Class<T> clazz) throws SQLException {
		super(DatabaseHelper.getHelper().getConnectionSource(), clazz);
		TAG = clazz.getName();
	}
}
