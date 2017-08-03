package demo.eventcollect;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import demo.eventcollect.collect.LogUtil;
import demo.eventcollect.collect.util.PermissionHelper;

/**
 * @author liuml.
 * @explain 主页面
 * @time 2017/6/28 16:41
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {


    private TextView textView;
    private Button button;
    private Button buttonActivity;
    private Button buttonAttribute;
    private ImageView image;

    private ListView listview;

    ArrayAdapter arrayAdapter;
    PermissionHelper mHelper;
    private List<String> itemList;//数据

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
        image = (ImageView) findViewById(R.id.image);

        listview = (ListView) findViewById(R.id.listview);

        initData();
        initView();

    }

    public void initData() {
        itemList = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            itemList.add("测试数据" + i);
        }
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
                Toast.makeText(MainActivity.this, "点击了图片", Toast.LENGTH_SHORT).show();
            }
        });
        buttonActivity.setOnClickListener(this);


        arrayAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, itemList);//适配器

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "item点击" + position, Toast.LENGTH_SHORT).show();
            }
        });

        listview.setAdapter(arrayAdapter);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_activity:
                startActivity(TestActivity.class);
                break;
        }
    }
}
