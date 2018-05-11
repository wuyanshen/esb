package com.elisoft.servicebus.core.filter.auth;

import com.elisoft.servicebus.core.filter.AbstractBaseFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.core.MessageSelector;
import org.springframework.messaging.Message;

/**
 * 鉴权过滤器
 * @author JiJie.LianG
 * 2018/5/7
 */
public class AuthorizedFilter extends AbstractBaseFilter implements MessageSelector{
    @Override
    public boolean accept(Message<?> message) {
        logger.debug("服务鉴权过滤器==================");
        logger.debug("REQUEST_TYPE = " + message.getHeaders().get("REQUEST_TYPE").toString());
        logger.debug("服务鉴权过滤器==================");
        return true;
    }
}
