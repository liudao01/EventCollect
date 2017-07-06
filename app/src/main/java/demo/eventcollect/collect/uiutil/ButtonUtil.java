package demo.eventcollect.collect.uiutil;

import android.util.Log;

import org.json.JSONObject;

/**
 * Button点击动作封装
 * Encapsulate the button clicked data;
 */
public class ButtonUtil {
    private static final String TAG = "ButtonUtil";

    /**
     * button动作封装
     * Encapsulation;
     *
     * @param target       标识
     * @param title        标题
     * @param tag          备注
     * @param activityName 所在activity
     * @return 动作元素
     */
    public static JSONObject buttonInfoGenerated(String target, String title, String tag, String activityName) {
        long time = System.currentTimeMillis();
        JSONObject object = null;
        try {
            object = new JSONObject();
            object.put("evenTime", time);
            if (null != title) {
                object.put("name", title);
            }

            if (null != tag) {
                object.put("tg", tag);//自己做的attribute  没有就没有
            }

            if (null != activityName) {
                activityName = activityName.substring(activityName.lastIndexOf(".") + 1, activityName.indexOf("@"));
                object.put("page", activityName);
            }
            object.put("even", "click");
            object.put("type", "button");
//			object.put("ac", "BC");
        } catch (Exception e) {
            Log.e(TAG, "ButtonUtil:unknown error");
        }

        return object;
    }

    /**
     * 自定义事件 动作封装
     * Encapsulation;
     *
     * @param attributes   自定义事件
     * @param name         标题
     * @param activityName 所在activity
     * @return 动作元素
     */
    public static JSONObject attributeGenerated(String attributes, String name, String activityName) {
        long time = System.currentTimeMillis();
        JSONObject object = null;
        try {
            object = new JSONObject();
            object.put("evenTime", time);
            if (null != name) {
                object.put("name", name);
            }

            if (null != attributes) {
                JSONObject attributesObj = new JSONObject();
                attributesObj.put("item-category", attributes);
                object.put("attributes", attributesObj);//自己做的attribute  没有就没有
            }

            if (null != activityName) {
                activityName = activityName.substring(activityName.lastIndexOf(".") + 1, activityName.indexOf("@"));
                object.put("page", activityName);
            }
            object.put("even", "click");
            object.put("type", "custum");
//			object.put("ac", "BC");
        } catch (Exception e) {
            Log.e(TAG, "ButtonUtil:unknown error");
        }

        return object;
    }

}
