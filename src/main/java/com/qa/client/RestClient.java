package com.qa.client;

import com.qa.util.TestUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class RestClient {

    //GET method - Without header
    public CloseableHttpResponse get(final String url) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault(); //Create http connection
        HttpGet httpGetRequest = new HttpGet(url); //http get Request
        CloseableHttpResponse httpResponse = httpClient.execute(httpGetRequest); //execute

        return httpResponse;
    }

    //GET method - Without header - [Just a tweak where creation of client uses HttpClientBuilder]
    public CloseableHttpResponse getWithHttpClientBuilder(final String url) throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build(); //Create http connection
        HttpGet httpGetRequest = new HttpGet(url); //http get Request
        CloseableHttpResponse httpResponse = httpClient.execute(httpGetRequest); //execute

        return httpResponse;
    }

    //GET method - Without header
    public CloseableHttpResponse get(final String url, final HashMap<String, String> headerMap) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault(); //Create http connection
        //or
        //CloseableHttpClient httpClient = HttpClientBuilder.create().build(); //Create http connection
        HttpGet httpGetRequest = new HttpGet(url); //http get Request
        for(String entry : headerMap.keySet()) { //Add headers in the request one by one
            httpGetRequest.addHeader(entry, headerMap.get(entry));
        }
        CloseableHttpResponse httpResponse = httpClient.execute(httpGetRequest); //execute

        return httpResponse;
    }

    public CloseableHttpResponse post(final String url, final String entityString, final HashMap<String, String> headerMap)
            throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault(); //Create http connection
        HttpPost httpPostRequest = new HttpPost(url); //http post Request
        httpPostRequest.setEntity(new StringEntity(entityString)); //Add body
        for(String entry : headerMap.keySet()) { //Add headers in the request one by one
            httpPostRequest.addHeader(entry, headerMap.get(entry));
        }
        CloseableHttpResponse httpResponse = httpClient.execute(httpPostRequest); //execute

        return httpResponse;

    }

    public CloseableHttpResponse post(final String url, final File entityFile, final HashMap<String, String> headerMap)
            throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault(); //Create http connection
        HttpPost httpPostRequest = new HttpPost(url); //http post Request
        httpPostRequest.setEntity(new FileEntity(entityFile)); //Add body
        for(String entry : headerMap.keySet()) { //Add headers in the request one by one
            httpPostRequest.addHeader(entry, headerMap.get(entry));
        }
        CloseableHttpResponse httpResponse = httpClient.execute(httpPostRequest); //execute

        return httpResponse;
    }

    public CloseableHttpResponse post(final String url, final Object obj, final ContentType type, final HashMap<String, String> headerMap)
            throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();  //Create http connection
        HttpPost httpPostRequest = new HttpPost(url); //http post Request
        HttpEntity httpEntity = TestUtil.getHTTPEntity(obj, type);
        httpPostRequest.setEntity(httpEntity); //Add body
        for(String entry : headerMap.keySet()) { //Add headers in the request one by one
            httpPostRequest.addHeader(entry, headerMap.get(entry));
        }
        CloseableHttpResponse httpResponse = httpClient.execute(httpPostRequest); //execute

        return httpResponse;
    }
}
