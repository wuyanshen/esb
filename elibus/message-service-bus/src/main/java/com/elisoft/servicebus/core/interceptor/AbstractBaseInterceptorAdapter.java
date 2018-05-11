package com.elisoft.servicebus.core.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

/**
 * @author JiJie.LianG
 * @date 2018-05-08 上午11:55
 */
public abstract class AbstractBaseInterceptorAdapter extends ChannelInterceptorAdapter{
    protected Logger logger = LoggerFactory.getLogger(getClass());
}
