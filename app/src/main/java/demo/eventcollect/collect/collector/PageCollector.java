package demo.eventcollect.collect.collector;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import demo.eventcollect.collect.util.ACache;

/**
 * activity页面行为收集
 * activity data obtaining
 */
public class PageCollector {
    private static final String TAG = "PageCollector";
    private String mPageName;
    private String mTitle;
    private String mTag;
    private static PageCollector mInstance;
    private ACache aCache;
    /**
     * 单例
     *
     * @return 单例
     */
    public static PageCollector getInstance() {
        if (null == mInstance) {
            mInstance = new PageCollector();
        }
        return mInstance;
    }

    /**
     * activity打开
     *
     * @param pageId 标识
     * @param title  标题
     * @param tag    备注
     * @param type   activity 类型  0 默认类型 1 从后台返回前台  2 从 从前台到后台
     * @return 动作元素
     */
    public JSONObject pageOpenInfoGenerated(String pageId, String title, String tag, int type) {
        if (null == pageId && null == title && null == tag) {
            return null;
        }
        long time = System.currentTimeMillis();
        JSONObject object = null;
        try {
            object = new JSONObject();
            switch (type) {
                case 0:
                    object.put("type", "page");
                    break;
                case 1:
                    object.put("type", "app");
                    //使用新session
                    break;
                case 2:
                    object.put("type", "app");
                    //清除session
                    break;
                default:
                    object.put("type", "page");
                    break;
            }
            object.put("evenTime", time);
            object.put("even", "in");
            mPageName = pageId;
            mTitle = title;
            mTag = tag;
            if (null != pageId) {
                pageId = pageId.substring(pageId.lastIndexOf(".") + 1, pageId.indexOf("@"));
                object.put("page", pageId);
            }

            if (null != title) {
                object.put("name", title);
            } else {
                object.put("name", "");
            }

            if (null != tag) {
//				object.put("tg", tag);
            }

        } catch (Exception e) {
            Log.e(TAG, "pageOpenInfoGenerated: unknown error");
        }
        return object;
    }

    public String getPageName() {
        return mPageName;
    }

    public String getPageTitle() {
        return mTitle;
    }

    public String getPageTag() {
        return mTag;
    }

    /**
     * activity关闭
     *
     * @param pageId 标识
     * @param title  标题
     * @param tag    备注
     * @param type   activity 类型  0 默认类型 1 从后台返回前台  2 从 从前台到后台
     * @return 动作元素
     */
    public JSONObject pageCloseInfoGenerated(String pageId, String title, String tag, int type) {
        if (null == pageId && null == title && null == tag) {
            return null;
        }
        long time = System.currentTimeMillis();
        JSONObject object = null;
        try {
            object = new JSONObject();
            switch (type) {
                case 0:
                    object.put("type", "page");
                    break;
                case 1:
                    object.put("type", "app");

                    break;
                case 2:
                    object.put("type", "app");
                    break;
                default:
                    object.put("type", "page");
                    break;
            }
            object.put("evenTime", time);
            object.put("even", "leave");
            if (null != pageId) {
                pageId = pageId.substring(pageId.lastIndexOf(".") + 1, pageId.indexOf("@"));
                object.put("page", pageId);
            }

            if (null != title) {
                object.put("name", title);
            } else {
                object.put("name", "");
            }

            if (null != tag) {
                //object.put("tg", tag);
            }
        } catch (Exception e) {

        }
        return object;
    }

    /**
     * app关闭动作搜集
     *
     * @param context
     * @return 动作元素
     */
    public JSONObject appCloseEventGeneration(Context context) {
        try {
            String appName = context.getPackageName();
            JSONObject object = new JSONObject();
            object.put("time", System.currentTimeMillis());
            object.put("ta", appName);
            object.put("ac", "AC");
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private PageCollector() {

    }

}
