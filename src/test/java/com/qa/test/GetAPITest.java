package com.qa.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.model.ResponseBody;
import com.qa.model.RestResponse;
import com.qa.util.TestUtil;
import org.apache.http.Header;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.HashMap;

public class GetAPITest extends TestBase{

    TestBase testBase;
    String baseUrl, apiUrl, url;
    RestClient restClient;
    CloseableHttpResponse httpResponse;

    @BeforeMethod
    public void setup() {
        testBase = new TestBase();
        baseUrl = prop.getProperty("URL");
        apiUrl = prop.getProperty("serviceURL");
        url = baseUrl + apiUrl;
    }

    @Test
    public void getTestWithoutHeaders() throws IOException {
        restClient = new RestClient();
        httpResponse = restClient.get(url);

        int statusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println("Response Status Code -----> " + statusCode);
        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");

        String responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8"); // Uses EntityUtil to read the body
        System.out.println("Response String -----> " + responseString);
        JSONObject responseJson = new JSONObject(responseString);
        System.out.println("Response Json -----> " + responseJson);
        String totalVal = TestUtil.getValueByJsonPath(responseJson, "/total");
        System.out.println("Response Json , value of 'total'-----> " + totalVal);
        Assert.assertEquals(Integer.parseInt(totalVal), 12, "'total' value is " + totalVal + ", instead of 12");
        String firstNameVal = TestUtil.getValueByJsonPath(responseJson, "/data[0]/first_name");
        System.out.println("Response Json , value of 'First Name' from the 1'st record-----> " + firstNameVal);
        Assert.assertEquals(firstNameVal, "Michael", "'First Name' value is " + firstNameVal + ", instead of Michael");

        Header[] headersArray = httpResponse.getAllHeaders();
        HashMap<String, String> allHeaders = new HashMap();
        for(Header header : headersArray) {
            allHeaders.put(header.getName(), header.getValue());
        }
        System.out.println("Response Headers ----->" + allHeaders);
    }

    @Test
    public void getTestWithHeaders() throws IOException {
        restClient = new RestClient();
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        httpResponse = restClient.get(url, headerMap);

        int statusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println("Response Status Code -----> " + statusCode);
        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");

        ResponseHandler<String> body = new BasicResponseHandler(); // Uses BasicResponseHandler to read the body
        String responseString = body.handleResponse(httpResponse);
        System.out.println("Response String -----> " + responseString);
        JSONObject responseJson = new JSONObject(responseString);
        System.out.println("Response Json -----> " + responseJson);
        String totalVal = TestUtil.getValueByJsonPath(responseJson, "/total");
        System.out.println("Response Json , value of 'total'-----> " + totalVal);
        Assert.assertEquals(Integer.parseInt(totalVal), 12, "'total' value is " + totalVal + ", instead of 12");
        String firstNameVal = TestUtil.getValueByJsonPath(responseJson, "/data[0]/first_name");
        System.out.println("Response Json , value of 'First Name' from the 1'st record-----> " + firstNameVal);
        Assert.assertEquals(firstNameVal, "Michael", "'First Name' value is " + firstNameVal + ", instead of Michael");

        Header[] headersArray = httpResponse.getAllHeaders();
        HashMap<String, String> allHeaders = new HashMap();
        for(Header header : headersArray) {
            allHeaders.put(header.getName(), header.getValue());
        }
        System.out.println("Response Headers ----->" + allHeaders);
    }

    @Test  // Just a tweak where it uses a Rest Response Model
    public void getTestWithResponseModel() throws IOException {
        restClient = new RestClient();
        httpResponse = restClient.getWithHttpClientBuilder(url);

        RestResponse restResponse = new RestResponse(httpResponse);
        int statusCode = restResponse.getStatusCode();
        System.out.println("Response Status Code -----> " + statusCode);

        JSONObject responseJson = restResponse.getResponseBody();
        System.out.println("Response Json -----> " + responseJson);

        Header[] headersArray = httpResponse.getAllHeaders();
        HashMap<String, String> allHeaders = new HashMap();
        for(Header header : headersArray) {
            allHeaders.put(header.getName(), header.getValue());
        }
        System.out.println("Response Headers ----->" + allHeaders);
    }

    @Test  // DeSerialize Example
    public void deSerializeExample() throws IOException {
        restClient = new RestClient();
        httpResponse = restClient.getWithHttpClientBuilder(url);

        String responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8"); // Uses EntityUtil to read the body
        System.out.println("Response String -----> " + responseString);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.serializeNulls().setPrettyPrinting().create();
        ResponseBody deSerializedBody = gson.fromJson(responseString, ResponseBody.class);
        System.out.println(deSerializedBody);
        System.out.println(deSerializedBody.data.get(0).getFirst_name());

    }

    @AfterMethod
    public void tearDown() throws IOException {
        httpResponse.close();
    }



}
