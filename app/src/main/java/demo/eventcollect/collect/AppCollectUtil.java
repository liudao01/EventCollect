package demo.eventcollect.collect;

import android.app.Application;
import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import demo.eventcollect.collect.collector.DataCollector;


/**
 * 对外暴露的工具类
 * app 用户行为收集
 *
 * @author liuml
 * @explain
 * @time 2017/6/22 11:48
 */

public class AppCollectUtil {
    private static final String TAG = "AppCollectUtil";

    private boolean isSuccess;//是否成功

    /**
     * AppCollectUtil单例
     */
    private static AppCollectUtil sInstance;
    /**
     * 数据存入几条后发送
     */
    private int dataSizeSend = 5;

    /**
     * 用于储存要规避的View；
     */
    private ArrayList<View> mAvoidListView;
    /**
     * 数据收集接口。
     * Object to collect data.
     */
    private DataCollector mCollector;
    /**
     * 并发条件下的锁。
     * LOCK for sync
     */
    private final String LOCK = "lock";
    /**
     * 获取该页面View的层次栈。
     * Stack used to store view
     */
    private Stack<View> mViewStack;

    /**
     * 行为JSON列表。
     * List of JSON used for user behaviors
     */
    private JSONArray mArray;

    /**
     * 前一个加载的页面，用来判断本次加载页面是否同前一次一样，避免重复收集。
     * previous loaded activity stored to avoid to collect again
     */
    private String mPrePageName;
    /**
     * 数据发送接口。
     * Object to send collected data.
     */
    private DataSender mSender;
    //用于判断是否进入后台
    private static int sessionDepth = 0;

    /**
     * 必须为单例，一个应用只存在一个实例。
     *
     * @return Queen实例
     */
    public static AppCollectUtil getInstance() {
        if (sInstance == null) {
            sInstance = new AppCollectUtil();
        }
        return sInstance;
    }

    /**
     * app工具类初始化
     * Initiation of Queen;
     */
    private AppCollectUtil() {
        mCollector = new DataCollector();
        mArray = new JSONArray();
        mViewStack = new Stack<View>();
        mAvoidListView = new ArrayList<>();//用于储存要规避的View；
//        mObserved = new Observed();
    }

    /**
     * 初始化一些数据
     *
     * @param application
     */
    public void init(Application application) {
        mSender = new DataSender(application);
    }

    /**
     * 收集Activity状态
     * Activity status obtaining
     *
     * @param activityName  activity的标志;mark;
     * @param activityTitle activity的标题; title;
     * @param activityTag   activity的备注; tag;
     * @param isOpen        activity的状态（打开和关闭）; status(open or closed);
     * @param context
     * @param from          来自哪里 1 onstart() 2 onstop()
     */
    public void activityDataCollect(String activityName, String activityTitle, String activityTag,
                                    boolean isOpen, Context context, int from) {


        int type = isBackstace(from);
        if (isOpen) {
            if (activityName.equals(mPrePageName)) { //同一个界面不可能启动两次，判断为重复接口
                return;
            }
            mPrePageName = activityName;
        } else {
            mPrePageName = "";
        }
        synchronized (LOCK) {
            if (isOpen) {
                mArray = mCollector.
                        activityOpenDataCollect(mArray, activityName, activityTitle, activityTag, type);

            } else {
                mArray = mCollector.
                        activityCloseDataCollect(mArray, activityName, activityTitle, activityTag, type);

            }
            bufferFullSend(type);
        }
    }


    private int isBackstace(int from) {
        int type = 0;
        if (1 == from) {

            if (sessionDepth == 0) {
                // 从后台返回
                LogUtil.d("从后台返回");
                type = 1;
            }
            sessionDepth++;
        }
        if (2 == from) {
            if (sessionDepth > 0)
                sessionDepth--;
            if (sessionDepth == 0) {
                // 进入后台
                LogUtil.d("进入后台");
                type = 2;
            }
        }
        return type;
    }

    /**
     * 发送数据接口
     *
     * @param array json动作列表
     */
    public void sendData(JSONArray array,int type) {

        mSender.sendData(array,type);
    }

    /**
     * 识别在View上所进行的动作
     * Get the operation on the view.
     *
     * @param ev      动作; Operation;
     * @param myView  执行动作的View; View on the screen;
     * @param context
     */
    public void recognizeViewEvent(MotionEvent ev, View myView, Context context) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                try {
                    mViewStack = new Stack<>();
                    final float pressX = ev.getRawX();
                    final float pressY = ev.getRawY();
                    findViewAtPosition(myView, (int) pressX, (int) pressY);
                    if (mViewStack.isEmpty()) {
                        return;
                    }

                    //  mInitialView = ignoreView();
                } catch (Exception e) {
                    Log.e(TAG, "recognizeViewEvent: unknown error");
                }

                break;
            }
            case MotionEvent.ACTION_UP: {
                mViewStack = new Stack<View>();
                final float x = ev.getRawX();
                final float y = ev.getRawY();
                findViewAtPosition(myView, (int) x, (int) y);
                if (mViewStack.isEmpty()) {
                    return;
                }
                View view = ignoreView();
                if (null == view) {
                    return;
                }
                try {
                    if (view instanceof CheckBox) {
                        CheckBox checkBox = (CheckBox) view;
                        buttonPressDataCollect(checkBox.toString(), checkBox.isChecked() + "", (String) (checkBox.getTag()), context);
                    } else if (view instanceof Button) {
                        Button button = (Button) view;
                        buttonPressDataCollect(button.toString(), button.getText().toString(), (String) (button.getTag()), context);
                    } else if (view instanceof ImageView) {
                        ImageView imageView = (ImageView) view;
                        imageViewPressDataCollect(imageView.toString(), null, null, context);
                    } else if (view instanceof TextView) {
                        TextView text = (TextView) view;
                        textViewInfoDataCollect(text.toString(), text.getText().toString(), (String) (text.getTag()), context);
                    } else {
                        buttonPressDataCollect(view.toString(), null, null, context);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "recognizeViewEvent: unknown error");
                }
            }
            break;
        }
    }

    /**
     * 收集TextView的内容
     * User data obtaining(TextView);
     *
     * @param id    textView的标志; mark;
     * @param title textView字面内容; title;
     * @param tag   该textView tag; tag;
     */
    public void textViewInfoDataCollect(String id, String title, String tag, Context context) {
        synchronized (LOCK) {
            mArray = mCollector.textViewInfoDataCollect(id, title, tag, context.toString(), mArray);
            bufferFullSend(0);
        }
    }

    /**
     * 收集imageView的内容
     * User data obtaining(ImageView);
     *
     * @param imageId imageView的标志; mark;
     * @param title   imageView的标题; title;
     * @param tag     imageView携带的tag; tag;
     * @param context
     */
    public void imageViewPressDataCollect(String imageId, String title, String tag, Context context) {
        synchronized (LOCK) {
            mArray = mCollector.
                    imageViewPressDataCollect(imageId, title, tag, context.toString(), mArray);
            bufferFullSend(0);
        }
    }

    /**
     * 忽略View，不搜集该View上的动作
     * Ignore the view which is inserted in the avoided view list.
     *
     * @return
     */
    private View ignoreView() {
        View view = mViewStack.pop();
        while (isAvoidView(view) && !mViewStack.isEmpty()) {
            view = mViewStack.pop();
        }
        if (isAvoidView(view)) {
            return null;
        }
        return view;
    }

    /**
     * 判断View是否在avoid View list上
     * Check whether the view is in the avoided view list;
     *
     * @param view
     * @return
     */
    private boolean isAvoidView(View view) {
        Iterator<View> i = mAvoidListView.iterator();
        while (i.hasNext()) {
            View avoidView = i.next();
            if (view == avoidView) {
                return true;
            }
        }
        return false;
    }

    /**
     * 用户交互数据收集  按钮情况
     * User data obtaining(Button);
     *
     * @param buttonId button的标志; button mark;
     * @param title    button上的字面内容; button content;
     * @param tag      button如果携带tag，收集tag; tag brought by button;
     * @param context
     */
    public void buttonPressDataCollect(String buttonId, String title, String tag, Context context) {
        synchronized (LOCK) {
            mArray = mCollector.buttonPressDataCollect(buttonId, title, tag, context.toString(), mArray);
            bufferFullSend(0);
        }
    }

    /**
     * 自定义事件
     * User data obtaining(Button);
     *
     * @param buttonId 自定义的标识
     * @param title    button上的字面内容; button content;
     * @param context
     */
    public void attributeCollect(String buttonId, String title, Context context) {
        synchronized (LOCK) {
            mArray = mCollector.attributeDataCollect(buttonId, title, context.toString(), mArray);
            bufferFullSend(0);
        }
    }

    /**
     * 通过用户动作的范围查找相应的View
     * find view that the user interacts.
     *
     * @param parent 最上层View
     * @param x      动作触摸点x坐标
     * @param y      动作触摸点y坐标
     */
    private void findViewAtPosition(View parent, int x, int y) {
        int length = 1;
        Rect rect = new Rect();
        parent.getGlobalVisibleRect(rect);
        if (parent instanceof ViewGroup) {
            length = ((ViewGroup) parent).getChildCount();
        }
        for (int i = 0; i < length; i++) {
            if (parent instanceof ViewGroup) {

                if (View.VISIBLE == parent.getVisibility()) {
                    View child = ((ViewGroup) parent).getChildAt(i);
                    findViewAtPosition(child, x, y);
                }
            } else {
                if (rect.contains(x, y)) {
                    if (View.VISIBLE == parent.getVisibility() && parent.isClickable()) {
                        mViewStack.push(parent);
                    }
                }
            }

        }

        if (parent.isClickable()
                && rect.contains(x, y)
                && View.VISIBLE == parent.getVisibility()) {
            mViewStack.push(parent);
        }
    }


    /**
     * Json列表满dataSizeSend条随即发送
     * check whether the json array is full(10 pic)
     */
    private void bufferFullSend(int type) {
        //判断是否列表满dataSizeSend条
        if (dataSizeSend <= mArray.length() || 2 == type) {
            sendData(mArray,type);
            //发送
            LogUtil.d("type = "+type+"\n发送数据 = " + mArray.toString());
            mArray = null;
            mArray = new JSONArray();
        }
    }

}
