package demo.eventcollect;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * @author liuml
 * @explain
 * @time 2017/6/29 14:20
 */

public class MyApplication extends Application {
    public static Context instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = getApplicationContext();
    }

    // 实例对象
    public static WeakReference<Activity> instanceRef;

    public static synchronized Context getInstance() {
        if (instanceRef == null || instanceRef.get() == null) {
            return MyApplication.getContext();
        } else {
            return instanceRef.get();
        }
    }

    public static synchronized Context getContext() {
        return instance;
    }

}
