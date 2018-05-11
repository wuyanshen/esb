package com.elisoft.servicebus.core.interceptor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author JiJie.LianG
 * 2018/5/4
 */
public class EsbLogInterceptorAdapter extends AbstractBaseInterceptorAdapter {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        logger.debug(message.getHeaders().getId().toString()+" -- ");
        return super.preSend(message,channel);
    }
}
