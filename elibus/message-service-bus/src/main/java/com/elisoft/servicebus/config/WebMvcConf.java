package com.elisoft.servicebus.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConf implements WebMvcConfigurer {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //使用FastJson 替换 SpringBoot 默认JSON解析包 Jackson Edit By JiJie.LianG 2018-01-19
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        //自定义配置
        FastJsonConfig config = new FastJsonConfig();
        config.setDateFormat("yyyy-MM-dd HH:mm:ss");// 自定义时间格式
        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue); // 正常转换 null 值
        converter.setFastJsonConfig(config);
        converters.add(converter);
    }
}
