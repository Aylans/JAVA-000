package io.kimmking.rpcfx.client;

import java.io.IOException;

public interface IHttpClient {

    String name();

    String get(String url) throws IOException;

    String post(String url, String json) throws IOException;
}
