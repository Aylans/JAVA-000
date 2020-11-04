package filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * created by Aylan
 * on 2020/11/4 10:29 下午
 */
public class HttpHeadersFilter implements HttpRequestFilter {

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        fullRequest.headers().add("hello",System.currentTimeMillis());
    }
}
