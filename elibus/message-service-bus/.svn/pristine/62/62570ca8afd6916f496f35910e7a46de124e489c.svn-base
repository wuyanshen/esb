package com.elisoft.servicebus.moudles.esb.config;

import org.springframework.integration.ws.SimpleWebServiceOutboundGateway;
import org.springframework.messaging.Message;
import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.client.core.SourceExtractor;
import org.springframework.ws.client.core.WebServiceMessageCallback;

public class WsOutBoundGateway extends SimpleWebServiceOutboundGateway{
    public WsOutBoundGateway(String uri) {
        super(uri);
    }
    @Override
    protected Object doHandle(String uri, Message<?> requestMessage, WebServiceMessageCallback requestCallback) {
        uri = requestMessage.getHeaders().get("soapUrl").toString();
        return super.doHandle(uri, requestMessage, requestCallback);
    }
}
