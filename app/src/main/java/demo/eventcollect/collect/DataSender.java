package demo.eventcollect.collect;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.UUID;


/**
 * @author liuml.
 * @explain 数据发送前准备接口，封装环境数据
 * @time 2017/6/24 17:00
 */
public class DataSender {


    /**
     * 环境变量封装HashMap  userdata
     * <p>
     * Hash map used to encapsulate environmental data;
     */
    private HashMap<String, Object> mParams;

    /**
     * 行为Json列表
     * <p>
     * User behavior data for json array;
     */
    private JSONArray mActionArray;

    /**
     * session
     */
    private String session;

    /**
     * cookie的session id
     * <p>
     * session id of cookie;
     */
    private String mSessionId;

    /**
     * APP的user id（登录名）
     * <p>
     * user id of APP;
     */
    private String mUserId;

    private Application mContext;

    private JSONObject userData;

    /**
     * 初始化DataSender
     * <p>
     * Initiation of DataSender;
     */
    public DataSender(Application application) {
        mParams = new HashMap<String, Object>();
        mContext = application;
        userData = new JSONObject();
    }

    /**
     * 封装sissionid
     */
    public void putSessionId() {
        mParams.put("id", mSessionId + "");
    }


    /**
     * 封装当前时间
     */
    public void putTime() {
        long time = System.currentTimeMillis();
        mParams.put("time", time);
    }

    /**
     * 封装userId
     */
    public void putUserId() {
        if (null != mUserId) {
            mParams.put("ud", mUserId);
        } else {
            mParams.put("ud", "");
        }
    }

    /**
     * 设置userId
     *
     * @param userId userId需要从用户输入登录名时获取
     */
    public void setUserId(String userId) {
        mUserId = userId;
    }


    /**
     * 获取uuid
     *
     * @return uuid
     */
    private String getMyUUID() {
        final TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(mContext.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;
    }

    public void putUUID() {
        mParams.put("UUID", getMyUUID());
    }


    /**
     * 发送数据接口
     *
     * @param array json动作列表
     */
    public void sendData(JSONArray array) {
        JSONObject jsonData = new JSONObject();
        JSONObject userdata = putUserData();
        JSONObject session = putSession();
        mActionArray = array;
        try {
            jsonData.put("userData", userdata);
            jsonData.put("events", mActionArray);
            jsonData.put("session", session);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送数据
        LogUtil.d("最终发送的数据 = " + jsonData.toString());
    }

    //id 我随便写一个
    private JSONObject putSession() {
        JSONObject obj = new JSONObject();
        try {
            long time = System.currentTimeMillis();
            if (!TextUtils.isEmpty(getUserid())) {
                obj.put("id", time + "_" + getUserid());
            } else {
                obj.put("id", time + "_" + getMyUUID());
            }
            obj.put("time", time);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    private String getUserid() {
        return "1951868";
    }

    private JSONObject putUserData() {
        putUUID();
        mParams.put("comeFrom", "az_wn");//来源
        //  mParams.put("userId", sharedUtils.getUserId());//userid  我存在sp里面了
        mParams.put("network", DeviceUtils.getNetworkType(mContext));
        putUserLoaction();
        mParams.put("version", DeviceUtils.getAppVersionCode(mContext));
        mParams.put("w_and_h", DeviceUtils.getScreenWidth(mContext) + "*" + DeviceUtils.getScreenHeight(mContext));
        mParams.put("sysVersion", DeviceUtils.getOsVersion());
        mParams.put("phoneModel", DeviceUtils.getDeviceModel());
        mParams.put("app_channel", getAppMetaData(mContext, "UMENG_CHANNEL"));
        String s = mParams.toString();

        try {
            JSONObject paramObj = new JSONObject();
            if (mParams != null && !mParams.isEmpty()) {
                for (HashMap.Entry<String, Object> entry : mParams.entrySet()) {
                    paramObj.put(entry.getKey(), entry.getValue());
                }
            }
//            paramObj.put("ca", array);
            LogUtil.d("sendPost: " + paramObj.toString());
            return paramObj;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 封装用户位置信息  假数据
     */
    private void putUserLoaction() {//这块用你自己定位的数据
        HashMap<String, Object> UserLoaction = new HashMap<>();
        UserLoaction.put("lat", "37.0");
        UserLoaction.put("lng", "37.0");
        UserLoaction.put("province", "北京");
        UserLoaction.put("city", "北京");
        UserLoaction.put("district", "朝阳区");
        UserLoaction.put("streetName", "三元桥");
        JSONObject location = new JSONObject(UserLoaction);
        mParams.put("user_loaction", location);//对象
    }

    /**
     * 获取application中指定的meta-data 调用方法时key就是UMENG_CHANNEL
     *
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = "";
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return resultData;
    }

    /**
     * 数据发送返回处理
     * Returned data
     */
    private Handler mHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                parseResult("成功的数据");
            } else {
                responseFailed("msg.what is 0");
            }
            return false;
        }
    });

    private void parseResult(String data) {
        try {
            JSONObject object = new JSONObject(data);
            String resultCode = object.getString("errcode");

            /**
             * 成功返回的标志为0;
             * Here I define 0 as the success symbol;
             */
            if ("0".equals(resultCode)) {
                responseSuccess();
            } else {
                responseFailed(resultCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void responseSuccess() {
        //数据发送成功
        //Not used in silence;

    }

    private void responseFailed(String resultCode) {
        //数据发送失败
    }

}
