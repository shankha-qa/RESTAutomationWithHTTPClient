package com.qa.model;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class RestResponse {

    private int statusCode;
    private JSONObject responseJson;
    private HashMap<String, String> responseHeader;

    public int getStatusCode() {
        return statusCode;
    }

    public JSONObject getResponseBody() {
        return responseJson;
    }

    public HashMap<String, String> getResponseHeader() {
        return responseHeader;
    }

    public RestResponse(CloseableHttpResponse httpResponse) throws IOException {
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        this.statusCode = statusCode;

        if (httpResponse.getEntity() != null) {
            String responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            JSONObject responseJson = new JSONObject(responseString);
            this.responseJson = responseJson;
        }

        Header[] headersArray = httpResponse.getAllHeaders();
        HashMap<String, String> allHeaders = new HashMap();
        for(Header header : headersArray) {
            allHeaders.put(header.getName(), header.getValue());
        }
        this.responseHeader = allHeaders;
    }

}
