package com.elisoft.servicebus.modules.esb.config;

import org.springframework.integration.ws.SimpleWebServiceOutboundGateway;

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
