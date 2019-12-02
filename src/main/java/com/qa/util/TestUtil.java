package com.qa.util;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

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

    public static HttpEntity getHTTPEntity(Object content, ContentType type) {
        if (content instanceof String)
            return new StringEntity((String)content, type);
        else if (content instanceof File)
            return new FileEntity((File)content, type);
        else
            throw new RuntimeException("Entity Not Found");
    }

}
