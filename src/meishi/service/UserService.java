package meishi.service;

import java.io.IOException;
import java.sql.SQLException;

import meishi.MainApplication;
import meishi.db.DaoSupport;
import meishi.db.PreferenceService;
import meishi.domain.User;
import meishi.network.NetworkService;
import meishi.network.ResponseException;
import meishi.network.ResponseMessage;
import android.util.Log;

public class UserService extends DaoSupport<User, Integer> {
	private static final String url = NetworkService.hostUrl + "/user/login";
	
	private PreferenceService preferenceService;

	public UserService() throws SQLException {
		super(User.class);
		this.preferenceService = MainApplication.getInstance().getPreferenceService();
	}
	
	public void login(String name, String password, AsyncTaskCallBack<Void> callBack) {
		String[] params = new String[] {name, password};
		new UserAsyncTask(callBack).execute(params);
	}

	private class UserAsyncTask extends BaseAsyncTask<String, Void, Void> {
		public UserAsyncTask(AsyncTaskCallBack<Void> callBack) {
			this.callBack = callBack;
		}

		@Override
		protected Void doInBackground(String... params) {
			try {
				User user = NetworkService.login(url, params[0], params[1]);
				preferenceService.setCookie(user.getCookie());
				preferenceService.setUser(user);
				createOrUpdate(user);
			} catch (IOException e) {
				Log.e(TAG, "doInBackground", e);
				responseMessage = new ResponseMessage("Network error!");
				responseMessage.setErrorCode(ResponseMessage.NETWORK_ERROR);
			} catch (ResponseException e) {
				Log.e(TAG, "doInBackground", e);
				responseMessage = e.getResponseMessage();
			} catch (SQLException e) {
				Log.e(TAG, "doInBackground", e);
			}
			return null;
		}
	}
}
