package com.elisoft.servicebus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.json.ObjectToJsonTransformer;
import org.springframework.integration.transformer.Transformer;
import org.springframework.integration.ws.SimpleWebServiceOutboundGateway;
import org.springframework.integration.ws.WebServiceHeaders;

/**
 * @author JiJie.LianG
 */
@SpringBootApplication
@EnableCaching
@EnableAutoConfiguration //spring-boot 自动根据依赖配置
//@EnableIntegration  //启用 spring-integration
@IntegrationComponentScan //spring-integration DSL 特性需启动
@ImportResource({"classpath:/META-INF/spring/integration/esb-conf-2.xml","classpath:/META-INF/spring/integration/ws-conf.xml","classpath:/META-INF/spring/integration/esb-conf.xml"}) //加载 spring-integration 业务集成配置
public class MessageServiceBus {
    public static void main(String[] args){
        SpringApplication.run(MessageServiceBus.class, args);
        System.out.println("启动成功。。。。");
    }

}
