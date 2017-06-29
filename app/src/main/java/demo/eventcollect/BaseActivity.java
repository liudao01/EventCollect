package demo.eventcollect;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

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
}
