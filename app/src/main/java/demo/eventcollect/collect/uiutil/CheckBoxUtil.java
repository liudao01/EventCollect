package demo.eventcollect.collect.uiutil;

import android.util.Log;

import org.json.JSONObject;

/**
 * checkbox动作收集
 * Encapsulate the CheckBox clicked data;
 */
public class CheckBoxUtil {
	private static final String TAG = "CheckBoxUtil";
	public static JSONObject checkBoxClickInfoGeneration(String target, String title, String tag, String activityName){
		long time = System.currentTimeMillis();
		JSONObject object = null;
		try{
			object = new JSONObject();
			object.put("evenTime", time);
			if(null != title){
				object.put("name", title);
			}

			if(null != tag){
				object.put("tg",tag);
			}

			if(null != activityName){
				activityName = activityName.substring(activityName.lastIndexOf(".")+1, activityName.indexOf("@"));
				object.put("page", activityName);
			}


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
			object.put("type", "checkbox");
//			object.put("ac", "BC");
		}catch(Exception e){
			Log.e(TAG, "CheckBoxUtil:unknown error");
		}

		return object;
	}

}
