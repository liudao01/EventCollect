package demo.eventcollect;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;

import demo.eventcollect.collect.AppCollectUtil;

/**
 * @author liuml
 * @explain
 * @time 2017/6/29 18:16
 */

public class BaseActivity extends Activity {
    public String titleTextV = "";//标题
    AppCollectUtil appCollectUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //工具初始化
        appCollectUtil = AppCollectUtil.getInstance();
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
        appCollectUtil.getInstance().recognizeViewEvent(ev, this.getWindow().getDecorView(), this);
//        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss:SSS");
//        String date2 = sdf2.format(new java.util.Date());
//        LogUtil.d("结束时间 : " + date2);//自己项目测试大约1 - 8毫秒 可以接受
        return super.dispatchTouchEvent(ev);
    }
}
