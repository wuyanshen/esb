package com.elisoft.servicebus.moudles.esb.config;

import org.apache.http.protocol.HTTP;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.annotation.*;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.ws.SimpleWebServiceOutboundGateway;
import org.springframework.integration.stream.CharacterStreamWritingMessageHandler;
import org.springframework.integration.ws.WebServiceHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.ws.client.core.WebServiceMessageExtractor;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.destination.DestinationProvider;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.transport.WebServiceMessageSender;
import org.springframework.xml.transform.StringResult;
import org.w3c.dom.Document;

import javax.jws.WebService;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.transform.Result;
import javax.xml.transform.dom.DOMResult;
import java.net.URI;
import java.net.URISyntaxException;

//@Configuration
public class BaseConf {

//    @Bean
//    public MessageChannel gatewayChannel() {
//        return MessageChannels.direct().get();
//    }
//
//    @Bean
//    public PollableChannel output() {
//        return new QueueChannel();
//    }

//    @Bean
//    public IntegrationFlow flow1() {
//        return IntegrationFlows.from(gatewayChannel())
////                .enrichHeaders(h -> h.header(WebServiceHeaders.SOAP_ACTION,
////                        "http://180.76.165.24:8000/elisoft/eliservice/getOpResultForPackage"))
////                .wireTap(sf -> sf
////                        .handle(f -> new SimpleWebServiceOutboundGateway(f.getHeaders().get("soapUrl").toString()))
////                )
////                .handle(gateway())
////                .handle((GenericHandler) (message) -> {
////                    System.out.println("========================================================================");
////                    System.out.println(message.getHeaders().get("soapUrl").toString());
////                    System.out.println("========================================================================");
////                    return new SimpleWebServiceOutboundGateway(message.getHeaders().get("soapUrl").toString());
////                })
////                .<String>handle((p,h) -> {
////                    SimpleWebServiceOutboundGateway wsOutBound = new SimpleWebServiceOutboundGateway(h.get("soapUrl").toString());
////                    return wsOutBound;
////                })
////                .handle(new SimpleWebServiceOutboundGateway(() -> {
////                    try {
////                        return new URI("http://180.76.165.24:8000/elisoft/eliservice");
////                    } catch (URISyntaxException e) {
////                        e.printStackTrace();
////                    }
////                    return null;
////                }))
//                .handle(new WsOutBoundGateway("http://1.1.1.1"))
//                .transform(a -> {
//                    System.out.println("========================================================================");
//                    System.out.println(a);
//                    System.out.println("========================================================================");
//                    return a.toString();
//                })
//                .channel(output())
//                .get();
////                .handle(CharacterStreamWritingMessageHandler.stdout()).get();
//    }

//    @Transformer
//    Order generateOrder(String productId) {
//        return new Order(productId);
//    }

    private SimpleWebServiceOutboundGateway gateway(String uri) {
//        @Header("soapUrl") String uri
//        System.out.println(msg.getHeaders().get("soapUrl"));
        SimpleWebServiceOutboundGateway simpleWebServiceOutboundGateway = new SimpleWebServiceOutboundGateway(uri);
//        SaajSoapMessageFactory saajSoapMessageFactory = new SaajSoapMessageFactory();
//        try {
//            saajSoapMessageFactory.setMessageFactory(MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL));
//        } catch (SOAPException e) {
//            throw new RuntimeException(e);
//        }
//        simpleWebServiceOutboundGateway.setMessageFactory(saajSoapMessageFactory);
        return  simpleWebServiceOutboundGateway;
    }
}
