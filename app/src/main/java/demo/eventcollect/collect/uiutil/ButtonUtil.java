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
	 * @param target 标识
	 * @param title 标题
	 * @param tag 备注
	 * @param activityName 所在activity
	 * @return 动作元素
	 */
	public static JSONObject buttonInfoGenerated(String target, String title, String tag, String activityName){
		long time = System.currentTimeMillis();
		JSONObject object = null;
		try{
			object = new JSONObject();
			object.put("evenTime", time);
			if(null != title){
				object.put("name", title);
			}
			
			if(null != tag){
				object.put("tg",tag);//自己做的tag  没有就没有
			}
			
			if(null != activityName){
				activityName = activityName.substring(activityName.lastIndexOf(".")+1, activityName.indexOf("@"));
				object.put("page", activityName);
			}
			//这里是找的button 的id
			/*if(null != target){
				String[] splittedStr = target.split("app:id/");
				if(null != splittedStr){
					int length = 0;
					String tarStr = null;
					if(2 == splittedStr.length){
						length = splittedStr[1].length();
						tarStr = splittedStr[1].substring(0, length-1);
					}else{
						splittedStr = target.split("android:id/");
						if(2 == splittedStr.length){
							length = splittedStr[1].length();
							tarStr = splittedStr[1].substring(0, length-1);
						}else {
							tarStr = target;
						}
					}

					if(null != tarStr){
						object.put("ta", tarStr);
					}
				}
			}*/
			object.put("even", "click");
			object.put("type", "button");
//			object.put("ac", "BC");
		}catch(Exception e){
			Log.e(TAG, "ButtonUtil:unknown error");
		}

		return object;
	}

}
