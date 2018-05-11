package com.elisoft.servicebus.core.filter.ws;

import com.elisoft.servicebus.core.filter.AbstractBaseFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.core.MessageSelector;
import org.springframework.messaging.Message;

/**
 * @author JiJie.LianG
 * @date 2018-05-08 上午11:24
 */
public class WsBaseFilter extends AbstractBaseFilter implements MessageSelector{

    @Override
    public boolean accept(Message<?> message) {
        logger.debug("WebService 处理流程 过滤器=========");
        return true;
    }
}
