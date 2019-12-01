package com.qa.client;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RestClient {

    //GET method - Without header
    public CloseableHttpResponse get(final String url) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault(); //Create http connection
        HttpGet httpGetRequest = new HttpGet(url); //http get Request
        CloseableHttpResponse httpResponse = httpClient.execute(httpGetRequest); //execute

        return httpResponse;
    }

//    GET method - Without header - with HttpClientBuilder
//    public CloseableHttpResponse get(final String url) throws IOException {
//
//        CloseableHttpClient httpClient = HttpClientBuilder.create().build(); //Create http connection
//        HttpGet httpGetRequest = new HttpGet(url); //http get Request
//        CloseableHttpResponse httpResponse = httpClient.execute(httpGetRequest); //execute
//
//        return httpResponse;
//    }

    //GET method - Without header
    public CloseableHttpResponse get(final String url, final HashMap<String, String> headerMap) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault(); //Create http connection
        //or
        //CloseableHttpClient httpClient = HttpClientBuilder.create().build(); //Create http connection
        HttpGet httpGetRequest = new HttpGet(url); //http get Request
        for(Map.Entry<String, String> entry : headerMap.entrySet()) { //Add headers in the request one by one
            httpGetRequest.addHeader(entry.getKey(), entry.getValue());
        }
        CloseableHttpResponse httpResponse = httpClient.execute(httpGetRequest); //execute

        return httpResponse;
    }

}
