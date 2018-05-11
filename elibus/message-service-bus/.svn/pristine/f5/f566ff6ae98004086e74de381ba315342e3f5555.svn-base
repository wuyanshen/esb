package com.elisoft.servicebus.core.gateway;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;

/**
 * @author JiJie.LianG
 * 2018/5/7
 */
public interface EsbSyncGateway {
    /**
     * Web Service
     * @return
     */
    Object wsProcess(@Payload String reqXml, @Headers Map<String, Object> map);

    /**
     * HTTP
     * @return
     */
    Object httpProcess();
}
