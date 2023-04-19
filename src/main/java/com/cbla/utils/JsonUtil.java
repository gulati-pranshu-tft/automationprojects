package com.cbla.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {
    public static String getJsonObject(String filepath, String name, String value) {
        JSONParser parser = new JSONParser();
        try {
            //  System.out.println("rootpath="+rootPath);
            Object obj = parser.parse(new FileReader(filepath));
            JSONArray jsonArray = (JSONArray) obj;
            for (Object obj_from_array : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj_from_array;
                String json_obj_name = String.valueOf(jsonObject.get("Name"));
                if (name.equals(json_obj_name.toUpperCase())) {
                    return (String) jsonObject.get(value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<String> getCompleteJsonObject(String filepath, String Param) {
        JSONParser parser = new JSONParser();
        List<String> ParamList = new ArrayList<>();
        try {
            //  System.out.println("rootpath="+rootPath);
            Object obj = parser.parse(new FileReader(filepath));
            JSONArray jsonArray = (JSONArray) obj;
            for (Object obj_from_array : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj_from_array;
                String json_obj_name = String.valueOf(jsonObject.get(Param));
                ParamList.add(json_obj_name.toUpperCase());
            }
            return ParamList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

