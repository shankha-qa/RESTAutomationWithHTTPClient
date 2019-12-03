package com.qa.test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.model.RestResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;

public class DeleteAPITest extends TestBase{

    TestBase testBase;
    String baseUrl, apiUrl, url, index;
    RestClient restClient;
    CloseableHttpResponse httpResponse;

    @BeforeMethod
    public void setup() {
        testBase = new TestBase();
        baseUrl = prop.getProperty("URL");
        apiUrl = prop.getProperty("serviceURL");
        index = prop.getProperty("index");
        url = baseUrl + apiUrl + index;
    }

    @Test //Instead of String, it reads from a File
    public void deleteTest() throws IOException {
        restClient = new RestClient();

        httpResponse = restClient.delete(url);
        RestResponse restResponse = new RestResponse(httpResponse);

        int statusCode = restResponse.getStatusCode();
        System.out.println("Response Status Code -----> " + statusCode);
        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_204, "Status code is not 204");
    }

    @AfterMethod
    public void tearDown() throws IOException {
        httpResponse.close();
    }



}
