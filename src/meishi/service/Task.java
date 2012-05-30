package meishi.service;

import android.os.Handler;
import android.os.Message;

public abstract class Task implements Runnable {
	static Handler handler;
	
	protected RefreshCallBack callBack;
	
	/**
	 * 运行任务
	 */
	public abstract void execute();
	
	@Override
	public void run() {
		execute();
		Message message = handler.obtainMessage();
		message.obj = this;
		handler.sendMessage(message);
	}
	
	/**
	 * 刷新UI
	 */
	public abstract void refresh();
}
