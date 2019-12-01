package com.qa.util;

import org.json.JSONArray;
import org.json.JSONObject;

public class TestUtil {

    public static String getValueByJsonPath(JSONObject responsejson, String jpath) {
        Object obj = responsejson;
        for (String s : jpath.split("/")) {
            if (!s.isEmpty()) {
                if (!(s.contains("[") || s.contains("]")))
                    obj = ((JSONObject) obj).get(s);
                else
                    obj = ((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0]))
                            .get(Integer.parseInt(s.split("\\[")[1].replace("]", "")));
            }
        }
        return obj.toString();
    }

}
