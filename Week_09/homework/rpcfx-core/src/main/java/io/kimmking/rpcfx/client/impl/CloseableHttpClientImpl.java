package io.kimmking.rpcfx.client.impl;


import io.kimmking.rpcfx.client.IHttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class CloseableHttpClientImpl implements IHttpClient {

    @Override
    public String name() {
        return "CloseableHttpClient";
    }

    @Override
    public String get(String url) throws IOException {
        URI uri;
        try {
            uri = new URIBuilder(url).build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        HttpGet httpGet = new HttpGet(uri);
        try (CloseableHttpClient httpclient = HttpClients.createDefault();
             CloseableHttpResponse response = httpclient.execute(httpGet)) {
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        }
    }

    @Override
    public String post(String url, String json) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        StringEntity myEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
        httpPost.setEntity(myEntity);
        try (CloseableHttpClient httpclient = HttpClients.createDefault();
             CloseableHttpResponse response = httpclient.execute(httpPost)) {
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        }
    }
}
