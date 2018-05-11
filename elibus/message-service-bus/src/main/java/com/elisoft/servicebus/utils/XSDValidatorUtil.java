package com.elisoft.servicebus.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.SAXValidator;
import org.dom4j.io.XMLWriter;
import org.dom4j.util.XMLErrorHandler;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

/**
 * 通过xsd校验xml工具类
 * 
 */
public class XSDValidatorUtil {

    public static void checkXML(String requestXML,String xsdName) throws Exception {
        String path = System.getProperty("user.dir");
        //xsd文件路径，自己填写路径
        String xsdPath = path + "\\axis2_api\\src\\main\\resources\\xsd\\"+xsdName;
        try {
            //错误消息处理类
            XMLErrorHandler errorHandler = new XMLErrorHandler();
            //获得解析器工厂类
            SAXParserFactory factory = SAXParserFactory.newInstance();
            //在解析XML是进行验证
            factory.setValidating(true);
            //支持命名空间
            factory.setNamespaceAware(true);
            //获得解析器
            SAXParser parser = factory.newSAXParser();
            parser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
                    "http://www.w3.org/2001/XMLSchema");
            parser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaSource", "file:" + xsdPath);
            SAXReader reader = new SAXReader();
            //读取XML文件
            Document document = DocumentHelper.parseText(requestXML);

            SAXValidator validator = new SAXValidator(parser.getXMLReader());
            // 发生错误时得到相关信息
            validator.setErrorHandler(errorHandler);
            // 进行校验
            validator.validate(document);
            XMLWriter writer = new XMLWriter(OutputFormat.createPrettyPrint());
            //通过是否有错误信息判断校验是否匹配
            if (errorHandler.getErrors().hasContent()) {
                System.out.println("XML文件通过XSD文件校验失败！");
                writer.write(errorHandler.getErrors());
            } else {
                System.out.println("XML文件通过XSD文件校验成功！");
            }
        } catch (ParserConfigurationException | SAXException | DocumentException |
                IOException e) {
            e.printStackTrace();
        }
    }
}
