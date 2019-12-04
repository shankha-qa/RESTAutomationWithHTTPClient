package com.qa.test;

import com.qa.base.TestBase;
import com.qa.client.PromptAuth;
import com.qa.client.RestClient;
import com.qa.model.RestResponse;
import com.qa.util.TestUtil;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import org.apache.commons.codec.binary.Base64;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class AuthenticationTest extends TestBase{

    TestBase testBase;
    String baseUrl, apiUrl, url, sslUrl;
    RestClient restClient;
    CloseableHttpResponse httpResponse;

    @BeforeMethod
    public void setup() {
        testBase = new TestBase();
        baseUrl = prop.getProperty("URL");
        apiUrl = prop.getProperty("serviceURL");
        url = baseUrl + apiUrl;
        sslUrl = prop.getProperty("SSLURL");
    }

    @Test
    public void getTestWithHttpClientContextAuth() throws IOException {
        restClient = new RestClient();
        HttpClientContext context = PromptAuth.authContextGeneration("admin", "password");
        httpResponse = restClient.getWithAuthentication(url, context);

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
        Assert.assertEquals(firstNameVal, "George", "'First Name' value is " + firstNameVal + ", instead of Michael");

        Header[] headersArray = httpResponse.getAllHeaders();
        HashMap<String, String> allHeaders = new HashMap();
        for(Header header : headersArray) {
            allHeaders.put(header.getName(), header.getValue());
        }
        System.out.println("Response Headers ----->" + allHeaders);
    }

    @Test
    public void getTestWithAuth() throws IOException {
        restClient = new RestClient();
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        headerMap.put("Authorization", Base64.encodeBase64String("admin:password".getBytes()));
        httpResponse = restClient.get(url, headerMap);

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
        Assert.assertEquals(firstNameVal, "George", "'First Name' value is " + firstNameVal + ", instead of Michael");

        Header[] headersArray = httpResponse.getAllHeaders();
        HashMap<String, String> allHeaders = new HashMap();
        for(Header header : headersArray) {
            allHeaders.put(header.getName(), header.getValue());
        }
        System.out.println("Response Headers ----->" + allHeaders);
    }

    @Test  // Just a tweak where it uses a Rest Response Model and calls getHTTP, which have been built with HTTPClientBuilder
    public void getTestWithResponseModel() throws NoSuchAlgorithmException, KeyStoreException,
            KeyManagementException, IOException {
        restClient = new RestClient();
        httpResponse = restClient.getWithHttpsClientBuilder(sslUrl);

        int statusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println("Response Status Code -----> " + statusCode);
    }

    @AfterMethod
    public void tearDown() throws IOException {
        httpResponse.close();
    }



}
