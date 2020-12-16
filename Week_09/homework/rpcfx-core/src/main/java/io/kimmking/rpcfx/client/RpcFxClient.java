package io.kimmking.rpcfx.client;

import com.alibaba.fastjson.JSON;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.client.impl.CloseableHttpClientImpl;
import io.kimmking.rpcfx.client.impl.OkHttpClientImpl;
import lombok.SneakyThrows;

/**
 * @author bitkylin
 */
public class RpcFxClient {

    private static final IHttpClient OK_HTTP = new OkHttpClientImpl();
    private static final IHttpClient HTTP_CLIENT = new CloseableHttpClientImpl();


    public static RpcfxResponse post(RpcfxRequest req, String url) {
        String reqJson = JSON.toJSONString(req);
        System.out.println("req json: " + reqJson);

        // 1.可以复用client
        // 2.尝试使用httpclient或者netty client
        String respJson = RpcFxClient.doPostUseHttpClient(url, reqJson);
        System.out.println("resp json: " + respJson);
        return JSON.parseObject(respJson, RpcfxResponse.class);
    }

    private static String doPostUseOkHttp(String url, String json) {
        return doPost("okhttp", url, json);
    }

    private static String doPostUseHttpClient(String url, String json) {
        return doPost("client", url, json);
    }

    @SneakyThrows
    private static String doPost(String client, String url, String json) {
        return fetch(client).post(url, json);
    }

    /**
     * 简单工厂模式
     */
    private static IHttpClient fetch(String name) {
        switch (name) {
            case "okhttp":
                return OK_HTTP;
            case "client":
                return HTTP_CLIENT;
            default:
                return OK_HTTP;
        }
    }
}
