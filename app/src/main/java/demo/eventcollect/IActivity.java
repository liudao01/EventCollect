package demo.eventcollect;

import android.os.Bundle;

/**
 * baseActivity 实现这个
 * @author liuml
 * @time 2016-6-1 下午6:21:24
 */
public interface IActivity {

	void startActivity(Class<?> cls, boolean isClose);

	void startActivity(Class<?> cls);

	void startActivity(Class<?> cls, Bundle bundle, boolean isClose);

	void startActivityForResult(int request);

	void startActivityForResult(int request, Class<?> cls, boolean isClose);

	void startActivityForResult(int request, Class<?> cls);

	void startActivityForResult(int request, Class<?> cls, Bundle bundle);
}
