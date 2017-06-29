package demo.eventcollect;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import demo.eventcollect.collect.LogUtil;

/**
 * @author liuml.
 * @explain 主页面
 * @time 2017/6/28 16:41
 */
public class MainActivity extends BaseActivity {



    private TextView textView;
    private Button button;
    private Button buttonActivity;
    private ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleTextV ="首页";//标题
        initView();

    }

    private void initView() {
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
        buttonActivity = (Button) findViewById(R.id.button_activity);
        image = (ImageView) findViewById(R.id.image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d("点击了图片");
            }
        });
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
        appCollectUtil.getInstance().recognizeViewEvent(ev, this.getWindow()
                .getDecorView(), this);
//        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss:SSS");
//        String date2 = sdf2.format(new java.util.Date());
//        LogUtil.d("结束时间 : " + date2);//自己项目测试大约1 - 8毫秒 可以接受
        return super.dispatchTouchEvent(ev);
    }

}
