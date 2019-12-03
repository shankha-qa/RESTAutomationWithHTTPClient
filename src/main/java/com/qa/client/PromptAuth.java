package com.qa.client;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;

public class PromptAuth {

    public static HttpClientContext authContextGeneration(final String userName, final String password) {
        CredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));
        HttpClientContext context = HttpClientContext.create();
        context.setCredentialsProvider(provider);

        return context;
    }
}
