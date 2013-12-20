package stc.foundation.util;

import android.util.Log;

/**
 * 可用于传递参数的可执行体
 * @author key
 *
 */
public abstract class TaskRunnable implements Runnable {
	
	private final static String TAG = "TaskRunnable";
	
	public String arg1 = null;
	public String arg2 = null;
	public Object obj = null;
	public String name = "TaskRunnable";

	public TaskRunnable(String name) {
		this.name = name;
	}
	
	public TaskRunnable(String arg1, String name) {
		this.name = name;
		this.arg1 = arg1;
	}
	
	public TaskRunnable(String arg1, String arg2, String name) {
		this.name = name;
		this.arg1 = arg1;
		this.arg2 = arg2;
	}
	
	public TaskRunnable(String arg1, String arg2, Object obj, String name) {
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.obj = obj;
		this.name = name;		
	}
	
	@Override
	public void run() {
		Log.i(TAG, "run task>:"+name);
		trun();
		Log.i(TAG, "run task<:"+name);
	}
	
	public abstract void trun();

}
