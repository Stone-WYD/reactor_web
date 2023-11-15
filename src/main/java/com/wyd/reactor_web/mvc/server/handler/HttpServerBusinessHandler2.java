package com.wyd.reactor_web.mvc.server.handler;

import com.wyd.reactor_web.common.AjaxResult;
import com.wyd.reactor_web.common.AjaxResultUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @program: reactor_web
 * @description: http 业务处理类
 * @author: Stone
 * @create: 2023-11-06 11:02
 **/
@ChannelHandler.Sharable
@Component
@Slf4j
public class HttpServerBusinessHandler2 extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        //通过编解码器把byteBuf解析成FullHttpRequest
        if (msg instanceof FullHttpRequest) {

            //获取httpRequest
            FullHttpRequest httpRequest = (FullHttpRequest) msg;

            try {
                //获取请求路径、请求体、请求方法
                String uri = httpRequest.uri();
                String content = httpRequest.content().toString(CharsetUtil.UTF_8);
                HttpMethod method = httpRequest.method();
                log.info("服务器接收到请求:");
                log.info("请求uri:{},请求content:{},请求method:{}", uri, content, method);

                //响应
                new Thread(()->{
                    System.out.println("一个新建的线程休眠了2s...");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("新建的线程醒啦，它开始写入结果输入内容...");
                    ctx.write(AjaxResultUtil.getTrueAjaxResult(new AjaxResult<>()));
                }).start();

            } finally {
                httpRequest.release();
            }
        }
    }
}