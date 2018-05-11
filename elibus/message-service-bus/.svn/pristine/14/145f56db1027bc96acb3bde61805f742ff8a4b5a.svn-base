package com.elisoft.servicebus.core.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author JiJie.LianG
 * 2018/5/4
 */
@MessagingGateway
public interface EsbGateway {
    @Gateway(requestChannel = "gateInput",replyChannel = "wsRespOutput")
    Object toSend(@Payload String s, @Headers Map<String, Object> map);
}
