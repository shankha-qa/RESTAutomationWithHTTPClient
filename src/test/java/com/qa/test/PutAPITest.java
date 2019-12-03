package com.qa.test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.model.RestResponse;
import com.qa.util.TestUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class PutAPITest extends TestBase{

    TestBase testBase;
    String baseUrl, apiUrl, url, entityString;
    File entityFile;
    RestClient restClient;
    CloseableHttpResponse httpResponse;

    @BeforeMethod
    public void setup() {
        testBase = new TestBase();
        baseUrl = prop.getProperty("URL");
        apiUrl = prop.getProperty("serviceURL");
        url = baseUrl + apiUrl;
    }

    @Test //Instead of String, it reads from a File
    public void putTestWithHeadersFromFile() throws IOException {
        restClient = new RestClient();
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");

        entityFile = new File(System.getProperty("user.dir") + "/src/main/java/com/qa/data/users_4.json");
        System.out.println("Request Body File -----> " + entityFile);

        httpResponse = restClient.put(url, entityFile, ContentType.APPLICATION_JSON, headerMap);
        RestResponse restResponse = new RestResponse(httpResponse);

        int statusCode = restResponse.getStatusCode();
        System.out.println("Response Status Code -----> " + statusCode);
        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");

        JSONObject responseJson = restResponse.getResponseBody();
        System.out.println("Response Json -----> " + responseJson);
        String updatedAt = TestUtil.getValueByJsonPath(responseJson, "/updatedAt");
        System.out.println("Response Json , value of 'updatedAt'-----> " + updatedAt);
        Assert.assertTrue(!updatedAt.isEmpty() && updatedAt != null, "Updated at - " + updatedAt);

        HashMap<String, String> allHeaders = restResponse.getResponseHeader();
        System.out.println("Response Headers ----->" + allHeaders);
    }

    @AfterMethod
    public void tearDown() throws IOException {
        httpResponse.close();
    }



}
