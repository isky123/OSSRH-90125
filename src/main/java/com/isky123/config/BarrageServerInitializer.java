package com.isky123.config;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class BarrageServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("http-decodec", new HttpRequestDecoder());
        pipeline.addLast("http-aggregator", new HttpObjectAggregator(65536));
        pipeline.addLast("http-encodec", new HttpResponseEncoder());
        pipeline.addLast("http-chunked", new ChunkedWriteHandler());
        pipeline.addLast("http-request", new HttpRequestHandler("/ws"));
        pipeline.addLast("WebSocket-protocol", new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast("WebSocket-request", new TextWebSocketFrameHandler());
    }
}
