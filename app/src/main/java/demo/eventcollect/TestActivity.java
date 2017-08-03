package demo.eventcollect;

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
    private ListView listview;
    ArrayAdapter arrayAdapter;
    private List<String> itemList;//数据


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        titleTextV = "TestActivity";//标题
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
    private void initView() {

        arrayAdapter = new ArrayAdapter<String>(TestActivity.this,
                android.R.layout.simple_list_item_1, itemList);//适配器
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TestActivity.this, "TestActivity item点击 = " + position, Toast.LENGTH_SHORT).show();
            }
        });
        listview.setAdapter(arrayAdapter);
    }


}
