package meishi.db.base;

import java.util.List;


public abstract class DaoSupport<T> implements DAO<T> {
	protected SQLiteHelper sqliteHelper = SQLiteHelper.getInstance();

	@Override
	public void save(T obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(T obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T find(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> getScrollData(Integer offset, Integer maxResult) {
		// TODO Auto-generated method stub
		return null;
	}
}
