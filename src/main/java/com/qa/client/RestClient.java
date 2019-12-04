package com.qa.client;

import com.qa.util.TestUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
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

    //POST method - body as String
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

    //POST method - body as File
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

    //POST method - body as String/File [Decided in Runtime]]
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

    //PUT method - body as String/File [Decided in Runtime]]
    public CloseableHttpResponse put(final String url, final Object obj, final ContentType type, final HashMap<String, String> headerMap)
            throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();  //Create http connection
        HttpPut httpPutRequest = new HttpPut(url); //http put Request
        HttpEntity httpEntity = TestUtil.getHTTPEntity(obj, type);
        httpPutRequest.setEntity(httpEntity); //Add body
        if (headerMap != null) {
            for (String entry : headerMap.keySet()) { //Add headers in the request one by one
                httpPutRequest.addHeader(entry, headerMap.get(entry));
            }
        }
        CloseableHttpResponse httpResponse = httpClient.execute(httpPutRequest); //execute

        return httpResponse;
    }

    //DELETE method -
    public CloseableHttpResponse delete(final String url)
            throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();  //Create http connection
        HttpUriRequest httpDeleteRequest = RequestBuilder.delete(url).build();
        CloseableHttpResponse httpResponse = httpClient.execute(httpDeleteRequest); //execute

        return httpResponse;
    }

    //GET method - With sample Authentication
    public CloseableHttpResponse getWithAuthentication(final String url, final HttpClientContext context) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault(); //Create http connection
        HttpGet httpGetRequest = new HttpGet(url); //http get Request
        CloseableHttpResponse httpResponse = httpClient.execute(httpGetRequest, context); //execute with context having

        return httpResponse;
    }

    //GET method - With SSL protected API's Automation
    public CloseableHttpResponse getWithHttpsClientBuilder(final String url) throws NoSuchAlgorithmException, KeyStoreException,
            KeyManagementException, IOException {

        CloseableHttpClient httpClient = HttpsClientHelper.getHttpsClient(); //Create http connection
        HttpGet httpGetRequest = new HttpGet(url); //http get Request
        CloseableHttpResponse httpResponse = httpClient.execute(httpGetRequest); //execute

        return httpResponse;
    }

}
