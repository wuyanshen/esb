package com.elisoft.servicebus.core.activator;

import org.springframework.messaging.Message;

/**
 * @author JiJie.LianG
 * 2018/5/7
 */
public class FilterDiscardService extends AbstractBaseActivator {
    /**
     * 认证
     * @param msg
     * @return
     */
    public String authenticateDiscard(Message<?> msg){
        logger.debug("认证 失败=====");
        return "Filter Authenticate Discard";
    }

    /**
     * 鉴权
     * @param msg
     * @return
     */
    public String authorizedDiscard(Message<?> msg){
        logger.debug("鉴权 失败=====");
        return "Filter Authorized Discard";
    }
}
