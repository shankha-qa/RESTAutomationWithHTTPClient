package com.qa.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;
import com.qa.model.RestResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class PostAPITest extends TestBase{

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

    @Test
    public void postTestWithHeaders() throws IOException {
        restClient = new RestClient();
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");

        //Jackson API - Marshaling or Serialization
        ObjectMapper mapper = new ObjectMapper();
        Users users = new Users("Arijit", "friend");
        mapper.writeValue(new File(System.getProperty("user.dir") + "/src/main/java/com/qa/data/users_1.json"), users);
        entityString = mapper.writeValueAsString(users);
        System.out.println("Request Body String -----> " + entityString);

        httpResponse = restClient.post(url, entityString, headerMap);
        RestResponse restResponse = new RestResponse(httpResponse);

        int statusCode = restResponse.getStatusCode();
        System.out.println("Response Status Code -----> " + statusCode);
        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_201, "Status code is not 201");

        JSONObject responseJson = restResponse.getResponseBody();
        System.out.println("Response Json -----> " + responseJson);
        Assert.assertEquals(users.getName(), responseJson.get("name"));
        Assert.assertEquals(users.getJob(), responseJson.get("job"));

        HashMap<String, String> allHeaders = restResponse.getResponseHeader();
        System.out.println("Response Headers ----->" + allHeaders);
    }

    @Test //Instead of String, it reads from a File
    public void postTestWithHeadersFromFile() throws IOException {
        restClient = new RestClient();
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");


        entityFile = new File(System.getProperty("user.dir") + "/src/main/java/com/qa/data/users_2.json");
        System.out.println("Request Body File -----> " + entityFile);

        httpResponse = restClient.post(url, entityFile, headerMap);
        RestResponse restResponse = new RestResponse(httpResponse);

        int statusCode = restResponse.getStatusCode();
        System.out.println("Response Status Code -----> " + statusCode);
        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_201, "Status code is not 201");

        JSONObject responseJson = restResponse.getResponseBody();
        System.out.println("Response Json -----> " + responseJson);

        HashMap<String, String> allHeaders = restResponse.getResponseHeader();
        System.out.println("Response Headers ----->" + allHeaders);
    }

//    @Test [Not working ... needs fix]
//    public void postTestWithHeadersInXML() throws IOException {
//        restClient = new RestClient();
//        HashMap<String, String> headerMap = new HashMap<>();
//        headerMap.put("Content-Type", "application/xml");
//
//        //Jackson API - Marshaling or Serialization
//        ObjectMapper mapper = new ObjectMapper();
//        Users users = new Users("Amit", "Employee");
//        mapper.writeValue(new File(System.getProperty("user.dir") + "/src/main/java/com/qa/data/users_3.xml"), users);
//        entityString = mapper.writeValueAsString(users);
//        System.out.println("Request Body String -----> " + entityString);
//
//        httpResponse = restClient.post(url, entityString, ContentType.APPLICATION_XML, headerMap);
//        RestResponse restResponse = new RestResponse(httpResponse);
//
//        int statusCode = restResponse.getStatusCode();
//        System.out.println("Response Status Code -----> " + statusCode);
//        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_201, "Status code is not 201");
//
//        String responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
//        System.out.println("Response String -----> " + responseString);
//
//        HashMap<String, String> allHeaders = restResponse.getResponseHeader();
//        System.out.println("Response Headers ----->" + allHeaders);
//    }

    @AfterMethod
    public void tearDown() throws IOException {
        httpResponse.close();
    }



}
