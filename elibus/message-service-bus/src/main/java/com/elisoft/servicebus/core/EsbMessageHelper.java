package com.elisoft.servicebus.core;

import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.ws.WebServiceHeaders;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Date;

public class EsbMessageHelper<T> {

    private Logger logger = LoggerFactory.getLogger(EsbMessageHelper.class);

    public static final String MESSAGE_CREATE_TIME = "msg_create_time";

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 初始化 MessageBuilder
     * TODO:权限标识，身份标识等信息
     * @param payload
     * @param <T>
     * @return
     */
    public static <T> MessageBuilder<T> initMessageBuilder(T payload){
        MessageBuilder<T> messageBuilder = MessageBuilder.withPayload(payload);
        Date currDate = new Date();
        String currDateTimeStr = DateUtils.formatDate(currDate, DATE_FORMAT);
        messageBuilder.setHeader(MESSAGE_CREATE_TIME,currDateTimeStr);
        //TODO: header中存放权限标识，身份标识等信息
        return messageBuilder;
    }

    public static <T> MessageBuilder<T> initWsMessageBuilder(T payload,String soapUrl){
        return initWsMessageBuilder(payload,null,soapUrl);
    }

    /**
     * 初始化 WebService MessageBuilder
     * @param payload
     * @param <T>
     * @return
     */
    public static <T> MessageBuilder<T> initWsMessageBuilder(T payload,String soapAction,String soapUrl){
        MessageBuilder<T> messageBuilder = initMessageBuilder(payload);
        messageBuilder.setHeader(EsbMessageHeader.ESB_PROTOCOL_TYPE,EsbMessageProtocolType.WEB_SERVICE);
        if(StringUtils.hasText(soapAction)){
            messageBuilder.setHeader(WebServiceHeaders.SOAP_ACTION, soapAction);
        }else{
            messageBuilder.setHeader(WebServiceHeaders.SOAP_ACTION, "");
        }
        if(StringUtils.hasText(soapUrl)){
            messageBuilder.setHeader(EsbWsHeader.SOAP_URL, soapUrl);
        }else{
            messageBuilder.setHeader(EsbWsHeader.SOAP_URL, "");
        }
        return messageBuilder;
    }

    public static boolean validateMsgHeader(MessageBuilder msgBuilder){
        Assert.notNull(msgBuilder.getHeaders().get(EsbMessageHeader.ESB_PROTOCOL_TYPE),"Header ESB_PROTOCOL_TYPE 不存在");
        return true;
    }


    /**
     * 验证 WsMessageBuilder 是否合法
     * @param msgBuilder
     * @return
     */
    public static boolean validateWsMessageBuilder(MessageBuilder msgBuilder){
        Assert.notNull(msgBuilder.getHeaders().get(EsbWsHeader.SOAP_URL),"WebService Header SOAP_URL 不存在");
        return true;
    }

    public static void main(String[] args) throws Exception{
        MessageBuilder<String> wahahah = EsbMessageHelper.initMessageBuilder("wahahah");
        System.out.println(wahahah.getPayload());
        System.out.println(wahahah.getHeaders().get(MESSAGE_CREATE_TIME));

        MessageBuilder<String> ws = EsbMessageHelper.initWsMessageBuilder("wohahah","");
        System.out.println(ws.getPayload());
        System.out.println(ws.getHeaders().get(MESSAGE_CREATE_TIME));
        System.out.println(ws.getHeaders().get(WebServiceHeaders.SOAP_ACTION));
        System.out.println(ws.getHeaders().get(EsbWsHeader.SOAP_URL));

        EsbMessageHelper.validateMsgHeader(wahahah);

    }

}
