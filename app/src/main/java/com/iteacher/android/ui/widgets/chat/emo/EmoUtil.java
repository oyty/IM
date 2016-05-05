package com.iteacher.android.ui.widgets.chat.emo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iteacher.android.util.FileUtil;
import com.iteacher.android.util.IOUtilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/** 
 * @author Stay  
 * @version create time：Oct 16, 2014 11:38:56 PM 
 */
public class EmoUtil {

	public static ArrayList<EmoEntity> getEmoEntities(EmoGroup group) {
		String path = FileUtil.getEmoPath(group.name, "emo_desc.json");
		String json;
		try {
			json = IOUtilities.readStreamToMemory(new FileInputStream(path));
			JSONObject tmp = new JSONObject(json);
			JSONArray array = tmp.getJSONArray("emo_list");
			ArrayList<EmoEntity> entities = new Gson().fromJson(array.toString(), new TypeToken<ArrayList<EmoEntity>>(){}.getType());
			return entities;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
