package demo.eventcollect.collect.data;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import demo.eventcollect.collect.uiutil.ButtonUtil;
import demo.eventcollect.collect.uiutil.CheckBoxUtil;
import demo.eventcollect.collect.uiutil.ImageViewUtil;
import demo.eventcollect.collect.uiutil.TextViewUtil;


/**
 * 行为数据捕捉接口
 * Data Collection Instance;
 */
public class DataCollector {
    private static final String TAG = "DataCollector";

    public DataCollector() {
    }

    /**
     * Json列表插入
     * Json Insertion
     *
     * @param object Json元素 Json Object;
     * @param array  Json列表 Json Array;
     * @return 返回插好的Json列表 Inserted Json Array;
     */
    private JSONArray InsertJSONObject(JSONObject object, JSONArray array) {
        if (null != object) {
            return array.put(object);
        } else {
            return array;
        }
    }


    /**
     * activity打开状态收集
     * activity open data
     *
     * @param array         待插入的Json列表
     * @param activityName  activity标志
     * @param activityTitle activity标题
     * @param activityTag   activity的备注
     * @return 插好的Json列表
     */
    public JSONArray activityOpenDataCollect(JSONArray array, String activityName, String activityTitle, String activityTag, int type) {
        JSONObject object = PageCollector.getInstance().pageOpenInfoGenerated(activityName, activityTitle, activityTag,type);
        return InsertJSONObject(object, array);
    }

    /**
     * activity关闭状态收集
     * activity closed data
     *
     * @param array         待插入的Json列表
     * @param activityName
     * @param activityTitle
     * @param activityTag
     * @return 插好的Json列表
     */
    public JSONArray activityCloseDataCollect(JSONArray array, String activityName, String activityTitle, String activityTag, int type) {
        JSONObject object = PageCollector.getInstance().pageCloseInfoGenerated(activityName, activityTitle, activityTag,type);
        return InsertJSONObject(object, array);
    }


    /**
     * 用户交互数据收集 button点击行为收集
     * button clicked data
     *
     * @param target button标志 the button mark;
     * @param title button字面标题 info in the button;
     * @param tag	button携带的tag the tag brought;
     * @param activityName  button 所在activity activity which the button in;
     * @param array 待插入的json列表 Json Array;
     * @return 插好的json列表 Finished Json Array;
     */
    public JSONArray buttonPressDataCollect(String target, String title, String tag, String activityName, JSONArray array) {
        JSONObject object = ButtonUtil.buttonInfoGenerated(target, title, tag, activityName);
        return InsertJSONObject(object, array);
    }

    /**
     * ImageView点击动作收集
     * ImageView clicked data
     *
     * @param target	ImageView标志; mark;
     * @param title	ImageView标题; title;
     * @param tag		ImageView携带的tag; tag;
     * @param activityName	ImageView所在的Activity; activity
     * @param array	待插入的jsonArray; Json Array;
     * @return 插好的jsonArray; Finished Json Array;
     */
    public JSONArray imageViewPressDataCollect(String target, String title, String tag, String activityName, JSONArray array) {
        JSONObject object = ImageViewUtil.imageViewInfoGenerated(target, title, tag, activityName);
        return InsertJSONObject(object, array);
    }

    /**
     * CheckBox点击动作收集
     * @param target CheckBox标志; mark;
     * @param title CheckBox字面标题; title;
     * @param tag	CheckBox携带的tag; tag;
     * @param activityName CheckBox所在的activity; activity;
     * @param array 待插入的json列表; Json Array;
     * @return 插好的json列表; Finished Json Array;
     */
    public JSONArray checkBoxCheckDataCollect(String target, String title, String tag, String activityName, JSONArray array) {
        JSONObject object = CheckBoxUtil.checkBoxClickInfoGeneration(target, title, tag, activityName);
        return InsertJSONObject(object, array);
    }

    /**
     * TextView动作收集
     * TextView clicked data
     *
     * @param target TextView标志; mark;
     * @param title TextView字面标题; title;
     * @param tag	TextView携带的tag; tag;
     * @param activityName TextView所在的activity; activity
     * @param array 待插入的json列表; Json Array;
     * @return 插好的json列表; Finished Json Array
     */
    public JSONArray textViewInfoDataCollect(String target, String title, String tag, String activityName, JSONArray array){
        JSONObject object = TextViewUtil.textViewInfoGenerated(target, title, tag, activityName);
        return InsertJSONObject(object, array);
    }
    /**
     * 插入crash事件，搜集crash
     * crash event data
     *
     * @param object crash事件; Crash event;
     * @param array  待插入的Json列表; Json Array;
     * @return 插好的Json列表; Finished Json Array;
     */
    public JSONArray insertCrashHandler(JSONObject object, JSONArray array) {
        return InsertJSONObject(object, array);
    }

    /**
     * 退出APP事件收集 关了有可能收不到
     * App Exited data
     *
     * @param context
     * @param array   待插入的Json列表; Json Array;
     * @return 插好的Json列表; Finished Json Array;
     */
    public JSONArray appExitLogHandle(Context context, JSONArray array) {
        JSONObject object = PageCollector.getInstance().appCloseEventGeneration(context);
        return InsertJSONObject(object, array);
    }


}
