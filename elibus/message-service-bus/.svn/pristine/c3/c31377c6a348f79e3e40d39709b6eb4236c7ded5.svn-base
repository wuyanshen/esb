package com.elisoft.servicebus.moudles.esb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.ws.WebServiceHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
//@Scope("prototype")
@RequestMapping(value = "/esb")
public class EsbWebController {

    @Autowired
    @Qualifier("wsinputChannel")
    private MessageChannel wsinputChannel;

    @Autowired
    @Qualifier(value = "output")
    private PollableChannel output;

//    @Autowired
//    WsService wsService;
//    @Autowired
//    IntegrationFlow flow1;

    @RequestMapping(value = "/call2", method = RequestMethod.POST)
    public Object call2(@RequestHeader String soapAction, @RequestHeader String soapUrl, @RequestBody String reqXml) throws Exception {
        MessageBuilder<String> stringMessageBuilder = MessageBuilder.withPayload(reqXml);
        stringMessageBuilder.setHeader(WebServiceHeaders.SOAP_ACTION, soapAction);
        stringMessageBuilder.setHeader("soapUrl", soapUrl);


        wsinputChannel.send(stringMessageBuilder.build());
//        gatewayChannel.send(stringMessageBuilder.build());
        Message<?> receive = output.receive(500);
        String resXml = receive.getPayload().toString();
        return resXml;
    }
}
