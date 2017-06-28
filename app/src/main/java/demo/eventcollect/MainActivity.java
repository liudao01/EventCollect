package demo.eventcollect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import demo.eventcollect.collect.AppCollectUtil;

/**
 * @author liuml.
 * @explain 主页面
 * @time 2017/6/28 16:41
 */
public class MainActivity extends AppCompatActivity {

    String titleTextV = null;//标题
    AppCollectUtil appCollectUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
     * @explain 复写dispatchTouchEvent
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
//        LogUtil.d("时间看下 : " + date);
        appCollectUtil.getInstance().recognizeViewEvent(ev, this.getWindow()
                .getDecorView(), this);
//        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss:SSS");
//        String date2 = sdf2.format(new java.util.Date());
//        LogUtil.d("时间看下 : " + date2);
        return super.dispatchTouchEvent(ev);
    }

}
