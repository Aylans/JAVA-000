package io.kimmking.rpcfx.proxy;


import com.alibaba.fastjson.parser.ParserConfig;

import java.lang.reflect.Proxy;

public final class RpcFxDynamicProxy {

    static {
        ParserConfig.getGlobalInstance().addAccept("io.kimmking");
    }

    public static <T> T create(final Class<T> serviceClass, final String url) {
        // 0. 替换动态代理 -> ASM
        return (T) Proxy.newProxyInstance(RpcFxDynamicProxy.class.getClassLoader(), new Class[]{serviceClass}, new RpcfxInvocationHandler(serviceClass, url));
    }
}
