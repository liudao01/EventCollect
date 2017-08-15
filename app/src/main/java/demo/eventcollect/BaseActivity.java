package demo.eventcollect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;

import demo.eventcollect.collect.AppCollectUtil;

/**
 * @author liuml
 * @explain
 * @time 2017/6/29 18:16
 */

public class BaseActivity extends Activity  implements IActivity {
    public String titleTextV = "";//标题
    AppCollectUtil appCollectUtil;
    protected Intent startIntent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startIntent = new Intent();
        //工具初始化
        appCollectUtil = AppCollectUtil.getInstance(getApplicationContext());
        appCollectUtil.init(getApplication());
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (null != titleTextV) {
            appCollectUtil.activityDataCollect(this.toString(), titleTextV, null, true, this, 1);
        } else {//如果没有标题
            appCollectUtil.activityDataCollect(this.toString(), null, null, true, this, 1);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (null != titleTextV) {
            appCollectUtil.activityDataCollect(this.toString(), titleTextV, null, false, this, 2);
        } else {//如果没有标题
            appCollectUtil.activityDataCollect(this.toString(), null, null, false, this, 2);
        }
    }

    /**
     * @explain 重写dispatchTouchEvent
     * @author liuml.
     * @time 2017/6/22 15:24
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        /**
         * collect view interaction data, i.e. button clicked.
         */
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss:SSS");
//        String date = sdf.format(new java.util.Date());
//        LogUtil.d("开始时间 : " + date);
        appCollectUtil.getInstance(getApplicationContext()).recognizeViewEvent(ev, this.getWindow().getDecorView(), this);
//        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss:SSS");
//        String date2 = sdf2.format(new java.util.Date());
//        LogUtil.d("结束时间 : " + date2);//自己项目测试大约1 - 8毫秒 可以接受
        return super.dispatchTouchEvent(ev);
    }


    /**
     * 封装的 打开新的activity
     *
     * @param cls     类名class
     * @param isClose 是否关闭当前页面
     */
    @Override
    public void startActivity(Class<?> cls, boolean isClose) {
        startIntent.setClass(this, cls);
        startActivity(startIntent);
        if (isClose) {
            this.finish();
        }
    }

    /**
     * 打开新的activity
     *
     * @param cls
     */
    @Override
    public void startActivity(Class<?> cls) {
        startIntent.setClass(this, cls);
        startActivity(startIntent);
    }

    /**
     * 打开新的activity 通过bundle传递数据
     *
     * @param cls
     * @param bundle
     * @param isClose 是否关闭当前页面
     */
    @Override
    public void startActivity(Class<?> cls, Bundle bundle, boolean isClose) {
        startIntent.setClass(this, cls);
        startIntent.putExtras(bundle);
        startActivity(startIntent);
        if (isClose) {
            this.finish();
        }
    }

    /**
     * 打开新的activity 回传值
     *
     * @param request
     */
    @Override
    public void startActivityForResult(int request) {
    }

    /**
     * 打开新的activity  回传值
     *
     * @param request
     * @param cls
     * @param isClose 是否关闭当前页面
     */
    @Override
    public void startActivityForResult(int request, Class<?> cls, boolean isClose) {
        startIntent.setClass(this, cls);
        super.startActivityForResult(startIntent, request);
        if (isClose) {
            this.finish();
        }
    }

    /**
     * 打开新的activity  回传值  通过request
     *
     * @param request
     * @param cls
     */
    @Override
    public void startActivityForResult(int request, Class<?> cls) {
        startIntent.setClass(this, cls);
        super.startActivityForResult(startIntent, request);
    }

    /**
     * 打开新的activity  回传值 通过bundle
     *
     * @param request
     * @param cls
     * @param bundle
     */
    @Override
    public void startActivityForResult(int request, Class<?> cls, Bundle bundle) {
        startIntent.setClass(this, cls);
        startIntent.putExtras(bundle);
        super.startActivityForResult(startIntent, request);
    }

}
