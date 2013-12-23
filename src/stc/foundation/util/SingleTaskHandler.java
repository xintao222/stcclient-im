
package stc.foundation.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.util.Log;

/**
 *  以单队列的方式完成提交的任务(Runnable)
 * 
 * @author zzy
 */

public final class SingleTaskHandler {

    private final static String TAG = "SingleTaskHandler";

    // 用于标记任务，可以清除未开始的任务
    private final static String TOKEN = "token";

    // 负责处理task的handler
    private Handler handler = null;

    // 负责处理task的thread
    private HandlerThread thread = null;

    // 名称
    private String name = "";

    public SingleTaskHandler(String name) {
        if (name == null || name.length() == 0) {
            this.name = TAG;
        } else {
        	this.name = name;
        }

        // 开启任务队列处理
        thread = new HandlerThread(this.name);
        thread.start();

        handler = new Handler(thread.getLooper());
        
    }

    /**
     * 向任务队列提交任务，延时处理
     * 
     * @param r 执行对象
     * @param delayMillis 延时(毫秒)
     */
    public void postDelayed(Runnable r, long delayMillis) {
        if (delayMillis < 0) {
            delayMillis = 0;
        }

        if (handler.postAtTime(r, TOKEN, SystemClock.uptimeMillis() + delayMillis) == false) {
            Log.e(TAG, name + " postAtTime failed!");
        }
    }
    
    /**
     * 向任务队列提交任务，延时处理，同时指定token；一般是可用于后续删除
     * 
     * @param r 执行对象
     * @param delayMillis 延时(毫秒)
     * @param token		任务标记
     */
    public void postDelayedWithToken(Runnable r, long delayMillis, String token) {
        if (delayMillis < 0) {
            delayMillis = 0;
        }

        if (handler.postAtTime(r, token, SystemClock.uptimeMillis() + delayMillis) == false) {
            Log.e(TAG, name + " postAtTime failed!");
        }
    }
    
    /**
     * 清除指定排队任务,但是不会中断正在执行的任务
     * @param token 任务标记
     */
    public void clearPendingTask(String token) {
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * 向任务队列提交任务，立即处理。
     * 
     * @param r 执行对象
     */
    public void post(Runnable r) {

        if (handler.postAtTime(r, TOKEN, SystemClock.uptimeMillis()) == false) {
            Log.e(TAG, name + " postAtTime failed!");
        }
    }
    
    /**
     * 将任务放在第一个执行
     * @param r
     */
    public void postAtFront(Runnable r) {
        handler.postAtFrontOfQueue(r);
    }

    /**
     * 清除所有排队任务,但是不会中断正在执行的任务
     */
    public void clearAllPendingTask() {
        handler.removeCallbacksAndMessages(null);
    }
}
