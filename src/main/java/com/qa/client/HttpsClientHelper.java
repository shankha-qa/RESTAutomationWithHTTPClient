package com.qa.client;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class HttpsClientHelper {

    public static SSLContext getSSLContext() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

        TrustStrategy trust = new TrustStrategy() {
            @Override
            public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                return true;
            }
        };
        return SSLContextBuilder.create().loadTrustMaterial(trust).build();
    }

    public static CloseableHttpClient getHttpsClient() throws NoSuchAlgorithmException,
            KeyStoreException, KeyManagementException {
        return HttpClientBuilder.create().setSSLContext(getSSLContext()).build();
    }
}
