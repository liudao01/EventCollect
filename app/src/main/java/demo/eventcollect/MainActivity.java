package demo.eventcollect;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import demo.eventcollect.collect.LogUtil;
import demo.eventcollect.collect.util.PermissionHelper;

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
    private Button buttonAttribute;

    PermissionHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleTextV = "首页";//标题
        buttonAttribute = (Button) findViewById(R.id.button_attribute);
        mHelper = new PermissionHelper(this);
        buttonAttribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                appCollectUtil.attributeCollect("book", "nihao", MainActivity.this);
            }
        });
        getPermissiolList();

        initView();

    }

    private void getPermissiolList() {
        mHelper.requestPermissions("请授予xx[手机信息]，[读写]权限！",
                new PermissionHelper.PermissionListener() {
                    @Override
                    public void doAfterGrand(String... permission) {

                    }

                    @Override
                    public void doAfterDenied(String... permission) {
//                        getPermissiolList();
                    }
                }, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
        );
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
