package com.elisoft.servicebus.core.transform;

import com.alibaba.fastjson.JSONObject;
import com.elisoft.servicebus.utils.Xml2JsonUtil;
import net.javacrumbs.json2xml.JsonXmlReader;
import org.dom4j.*;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import javax.xml.namespace.NamespaceContext;
import java.util.List;

import static com.elisoft.servicebus.utils.WsdlUtil.getDefinitionDocument;
import static com.elisoft.servicebus.utils.WsdlUtil.getXpath;

/**
 * 报文协议转换器
 * @author wuyanshen
 */

public class EsbTransformer {


    /**
     * json转xml
     * @param message
     * @return
     * @throws Exception
     */
    public Message<?> json2xml(Message<?> message) throws Exception{

        //如果请求报文不是json格式就不进行转换，直接返回
        if(!message.getHeaders().get("Content-Type").toString().equals("application/json")){
            return message;
        }

        //如果请求报文是json就继续转换
        String wsdlUrl = message.getHeaders().get("soapUrl").toString()+"?wsdl";
        //获取wsdl的命名空间
        org.w3c.dom.Document document = getDefinitionDocument(wsdlUrl);
        javax.xml.xpath.XPath xpath = getXpath(document);
        NamespaceContext namespaceContext =  xpath.getNamespaceContext();

        //获取webservice的命名空间
        String namespaceURI = namespaceContext.getNamespaceURI("tns");//cxf发布的webservice取tns
        if(namespaceURI==null){
            namespaceURI = namespaceContext.getNamespaceURI("ns");//axis2发布的webservice取ns
        }

        //将json转换为xml
        String result = message.getPayload().toString();
        String requestXML = Xml2JsonUtil.json2xml(result,new JsonXmlReader(namespaceURI));

        //向子节点添加命名空间 xmln=""
        Document doc = DocumentHelper.parseText(requestXML);
        Element root = doc.getRootElement();
        getNodes(root);

        //返回请求消息
        message = MessageBuilder
                .withPayload(doc.asXML())
                .copyHeadersIfAbsent(message.getHeaders())
                .build();
        return message;
    }


    /**
     * xml转json
     * @param message
     * @return
     * @throws DocumentException
     */
    public Message<?> xml2json(Message<?> message) throws DocumentException {

        String result = message.getPayload().toString();

        //将响应报文中的字符转义
        if(result.indexOf("&lt;")!=-1){
            String firtRepalce = result.replaceAll("&lt;", '<' +"");
            result = firtRepalce.replaceAll("&gt;",'>'+"");
        }

        //xml转换成json
        JSONObject jsonObject = Xml2JsonUtil.xml2Json(result);

        //返回响应消息
        message = MessageBuilder
                .withPayload(jsonObject)
                .copyHeadersIfAbsent(message.getHeaders())
                .build();
        return message;
    }


    /**
     * 给请求报文中子节点添加命名空间 xmlns=""
      * @param element
     */
    private void getNodes(Element element){
        if(element.elements().size()==0){
            if(!element.isRootElement()){//如果不是根节点才进行添加
                element.addAttribute("\nxmlns","");
            }
        }else{
            //递归遍历当前节点所有的子节点
            List<Element> listElement=element.elements();//所有一级子节点的list
            for(Element e:listElement){//遍历所有一级子节点
                e.addAttribute("\nxmlns","");
                getNodes(e);//递归
            }
        }
    }


}
