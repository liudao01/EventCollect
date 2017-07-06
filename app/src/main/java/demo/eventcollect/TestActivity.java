package demo.eventcollect;

import android.os.Bundle;
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
public class TestActivity extends BaseActivity {



    private TextView textView;
    private Button button;
    private Button buttonActivity;
    private ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
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




}
