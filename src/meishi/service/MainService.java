package meishi.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class MainService extends Service {
	private static final String TAG = "MainService";
	
	private static ExecutorService exec;
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate()");
		
		exec = new ThreadPoolExecutor(
				Runtime.getRuntime().availableProcessors(),
				Runtime.getRuntime().availableProcessors() * 2, 60L,
				TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>());
		
		Task.handler = handler;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	// 添加任务
	public static void newTask(Task task) {
			exec.execute(task);
	}
	
	// 更新UI
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			Task task = (Task) msg.obj;
			task.refresh();
			
			super.handleMessage(msg);
		}
		
	};
}
