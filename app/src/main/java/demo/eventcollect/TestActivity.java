package demo.eventcollect;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author liuml.
 * @explain 测试跳转页面
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
        titleTextV ="TestActivity";//标题
        initView();

    }

    private void initView() {

    }




}
