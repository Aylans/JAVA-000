package io.kimmking.rpcfx.proxy;

import com.alibaba.fastjson.JSON;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.client.RpcFxClient;
import io.kimmking.rpcfx.exception.RpcfxException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public  class RpcfxInvocationHandler implements InvocationHandler {

    private final Class<?> serviceClass;
    private final String url;

    public <T> RpcfxInvocationHandler(Class<T> serviceClass, String url) {
        this.serviceClass = serviceClass;
        this.url = url;
    }

    // 可以尝试，自己去写对象序列化，二进制还是文本的，，，rpcfx是xml自定义序列化、反序列化，json: code.google.com/p/rpcfx
    // int byte char float double long bool
    // [], data class

    @Override
    public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {
        RpcfxRequest request = new RpcfxRequest();
        request.setServiceClass(this.serviceClass.getName());
        request.setMethod(method.getName());
        request.setParams(params);


        // 这里判断response.status，处理异常
        // 考虑封装一个全局的RpcfxException
        RpcfxResponse response = RpcFxClient.post(request, url);
        if (!response.isStatus()) {
            throw new RpcfxException((String) response.getResult(), response.getException());
        }

        return JSON.parse(response.getResult().toString());
    }

}