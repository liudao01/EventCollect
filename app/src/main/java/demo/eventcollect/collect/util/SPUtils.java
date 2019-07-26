package demo.eventcollect.collect.util;

/**
 * @author liuml
 * @explain
 * @time 2017/6/30 14:31
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;

import org.json.JSONArray;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import demo.eventcollect.MyApplication;

/**
 * @author liuml.
 * @explain sp 工具类
 * @time 2017/6/30 14:41
 */
public class SPUtils {

    private static final String STRING_ARRAY = "KEY_ARRAY";
    private static final String CHANNELSET = "channelSet";
    public static String FILLNAME = "config";

    private static SharedPreferences mySharedPreferences = MyApplication.getInstance().getSharedPreferences(FILLNAME, Context.MODE_PRIVATE);
    private static SharedPreferences.Editor editor = mySharedPreferences.edit();

    /**
     * 存入某个key对应的value值
     *
     * @param key
     * @param value
     */
    public static void put(String key, Object value) {
        Context context = MyApplication.getInstance();
        SharedPreferences sp = context.getSharedPreferences(FILLNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = mySharedPreferences.edit();
        if (value instanceof String) {
            edit.putString(key, (String) value);
        } else if (value instanceof Integer) {
            edit.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            edit.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            edit.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            edit.putLong(key, (Long) value);
        } else if (value instanceof Set) {
            edit.putStringSet(key, (Set<String>) value);
        }
        SharedPreferencesCompat.EditorCompat.getInstance().apply(edit);
    }

    /**
     * 得到某个key对应的值
     *
     * @param key
     * @param defValue
     * @return
     */
    public static Object get(String key, Object defValue) {
        Context context = MyApplication.getInstance();
        SharedPreferences sp = context.getSharedPreferences(FILLNAME, Context.MODE_PRIVATE);
        if (defValue instanceof String) {
            return sp.getString(key, (String) defValue);
        } else if (defValue instanceof Integer) {
            return sp.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof Float) {
            return sp.getFloat(key, (Float) defValue);
        } else if (defValue instanceof Long) {
            return sp.getLong(key, (Long) defValue);
        } else if (defValue instanceof Set) {
            return sp.getStringSet(key, (Set<String>) defValue);
        }
        return null;
    }

    /**
     * 返回所有数据
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILLNAME, Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILLNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.remove(key);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(edit);
    }

    /**
     * 清除所有内容
     *
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILLNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.clear();
        SharedPreferencesCompat.EditorCompat.getInstance().apply(edit);
    }

    public static void saveStringArray(String[] stringArray) {
        JSONArray jsonArray = new JSONArray();
        for (String b : stringArray) {
            jsonArray.put(b);
        }
        editor.putString(STRING_ARRAY, jsonArray.toString());
        editor.commit();
    }

    public static String[] getStringArray(int arrayLength) {
        String[] resArray = new String[arrayLength];
        Arrays.fill(resArray, true);
        try {
            JSONArray jsonArray = new JSONArray(mySharedPreferences.getString(STRING_ARRAY, "[]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                resArray[i] = jsonArray.getString(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resArray;
    }

    public static void removeSet(String string) {
        HashSet set = getSet();
        HashSet channelSet;
        if (null != set) {
            channelSet = new HashSet<String>(set);
        } else {
            channelSet = new HashSet<String>();
        }
        channelSet.remove(string);
        editor.putStringSet(CHANNELSET, channelSet);
        editor.commit();

    }

    public static void addSet(String string) {


        HashSet set = getSet();
        HashSet channelSet;
        if (null != set) {
            channelSet = new HashSet<String>(set);
        } else {
            channelSet = new HashSet<String>();
        }
        channelSet.add(string);
        editor.putStringSet(CHANNELSET, channelSet);
        editor.commit();
    }

    public static HashSet getSet() {
        Set<String> stringSet = mySharedPreferences.getStringSet(CHANNELSET, null);
        return (HashSet) stringSet;
    }
}