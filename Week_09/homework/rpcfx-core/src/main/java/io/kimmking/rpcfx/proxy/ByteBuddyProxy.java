package io.kimmking.rpcfx.proxy;

import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;

public class ByteBuddyProxy {

    @SneakyThrows
    public static <T> T create(final Class<T> serviceClass, final String url) {
        return (T) new ByteBuddy().subclass(Object.class)
                .implement(serviceClass)
                .intercept(InvocationHandlerAdapter.of(new io.kimmking.rpcfx.proxy.RpcfxInvocationHandler(serviceClass, url)))
                .make()
                .load(serviceClass.getClassLoader())
                .getLoaded()
                .getDeclaredConstructor()
                .newInstance();
    }
}
