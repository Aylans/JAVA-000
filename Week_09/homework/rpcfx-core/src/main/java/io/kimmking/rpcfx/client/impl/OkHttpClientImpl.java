package io.kimmking.rpcfx.client.impl;

import io.kimmking.rpcfx.client.IHttpClient;
import okhttp3.*;

import java.io.IOException;

public class OkHttpClientImpl implements IHttpClient {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private static final OkHttpClient client = new OkHttpClient();

    @Override
    public String name() {
        return "OkHttpClient";
    }

    @Override
    public String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    @Override
    public String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
