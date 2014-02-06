package com.reservation.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.reservation.pojo.OneLevelType;
import com.reservation.pojo.TwoLevelType;

import android.app.Application;
import android.util.Log;

public class JSONUtils extends Application{

	private static final String TAG = Application.class.getName();
	
	private static List<OneLevelType> getOneLevelTypes(String jsonString) {
		Log.i(TAG, "getOneLevelTypes");
		List<OneLevelType> list = new ArrayList<OneLevelType>();
		try {
			JSONArray jsonArray = new JSONArray(jsonString);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = (JSONObject) jsonArray.get(i);
				OneLevelType type = new OneLevelType();
				type.setOlId(object.getInt("olId"));
				type.setOlName(object.getString("olName"));
				Log.i(TAG, "type=" + type);
				list.add(type);
			}
		} catch (JSONException e) {
			Log.i(TAG, "JSONException:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			Log.i(TAG, "Exception:" + e.getMessage());
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<Map<String, Object>> getParentList(String jsonString) {
		Log.i(TAG, "getParentList");
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		List<OneLevelType> types = getOneLevelTypes(jsonString);
		if (types != null) {
			for (OneLevelType type : types) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("parent", type.getOlName());
				map.put("parentId", type.getOlId());
				list.add(map);
 			}
		}
		return list;
	}
	
	private static List<TwoLevelType> getTwoTeLevelTypes(String jsonString) {
		List<TwoLevelType> list = new ArrayList<TwoLevelType>();
		try {
			JSONArray jsonArray = new JSONArray(jsonString);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				TwoLevelType twoLevelType = new TwoLevelType();
				JSONObject oneObject = jsonObject.getJSONObject("onetype");
				OneLevelType oneLevelType = new OneLevelType();
				oneLevelType.setOlId(oneObject.getInt("olId"));
				oneLevelType.setOlName(oneObject.getString("olName"));
				twoLevelType.setOneType(oneLevelType);
				twoLevelType.setTlId(jsonObject.getInt("tlId"));
				twoLevelType.setTlName(jsonObject.getString("tlName"));
				list.add(twoLevelType);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<List<Map<String, Object>>> getChildList(String oneJSON, String twoJSON) {
		List<List<Map<String, Object>>> list = new ArrayList<List<Map<String,Object>>>();
		List<OneLevelType> oneTypes = getOneLevelTypes(oneJSON);
		List<TwoLevelType> twoTypes = getTwoTeLevelTypes(twoJSON);
		for (OneLevelType oneType : oneTypes) {
			List<Map<String, Object>> child = new ArrayList<Map<String,Object>>();
			for (TwoLevelType twoType : twoTypes) {
				if (oneType.getOlId() == twoType.getOneType().getOlId()) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("child", twoType.getTlName());
					map.put("childId", twoType.getTlId());
					child.add(map);
				}
			}
			list.add(child);
		}
		return list;
	}
	
}
