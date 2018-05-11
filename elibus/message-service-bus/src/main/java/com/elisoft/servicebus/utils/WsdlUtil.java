package com.elisoft.servicebus.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.wsdl.Definition;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;
import javax.wsdl.xml.WSDLWriter;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.List;



public class WsdlUtil {
	/************************************************************************************************************************
	 * 私有属性
	 */
	private static Logger log = LoggerFactory.getLogger(WsdlUtil.class);
	private static WSDLFactory wsdlFactory;

	/**
	 * wsdl文件中type包含的基本类型
	 */
	public enum SchemaDefaulyType {
		type_string("string"), type_decimal("decimal"), type_integer("integer"), type_int("int"), type_float("float"), type_long("long"), type_boolean(
				"boolean"), type_time("time"), type_date("date"), type_datetime("datetime"), type_array("array"), type_anyType("anyType");

		private String type;

		SchemaDefaulyType(final String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}
	}

	/**
	 * 得到wsdl中所有的方法
	 * 
	 * @param wsdlUrl
	 * @return
	 * @throws Exception
	 */
	public static List<String> getOperationList(String wsdlUrl) throws Exception {
		Document document = getDefinitionDocument(wsdlUrl);
		XPath xpath = getXpath(document);

		NodeList operations = DOMUtil.findNodeList(document, "wsdl:definitions/wsdl:portType/wsdl:operation");

		// 返回的结果集list
		List<String> operationList = new ArrayList<String>();
		for (int i = 0; i < operations.getLength(); i++) {
			Node operation = operations.item(i);
			String operationName = DOMUtil.getNodeName(operation);
			if (operationName != null && !"".equals(operationName)) {
				log.debug("解析" + wsdlUrl + "中的方法：" + operationName);
				operationList.add(operationName);
			}
		}
		return operationList;
	}

	/**
	 * 得到输入参数
	 * 
	 * @param inputParamList
	 * @param document
	 * @param operationName
	 * @param xpathBuilder
	 * @param parentParam
	 * @param isSelfDefinition
	 * @throws Exception
	 */
	public static void getInputParam(List<ParameterInfo> inputParamList, Document document, String operationName, StringBuilder xpathBuilder,
									 ParameterInfo parentParam, boolean isSelfDefinition) throws Exception {
		// 得到complexTypeName
		String complexTypeName = "";
		if (parentParam == null) {
			complexTypeName = operationName;
		} else {
			if (parentParam.getType().equals(SchemaDefaulyType.type_array.type)) {
				complexTypeName = parentParam.getChildType();
			} else {
				complexTypeName = parentParam.getType();
			}
		}

		// 得到所有的element结点
		List<Node> children = getSequenceElementOfComplexType(document, parentParam, complexTypeName, xpathBuilder, isSelfDefinition);
		for (int i = 0; i < children.size(); i++) {
			// 子结点
			Node child = children.get(i);
			String name = DOMUtil.getNodeName(child);

			// 参数
			ParameterInfo param = new ParameterInfo();
			param.setName(name);

			// 是否存在type属性(判断其type是引用还是自身定义)
			if (DOMUtil.assertNodeAttributeExist(child, "type")) {// type存在
				String type = DOMUtil.getNodeType(child);

				if (DOMUtil.isArray(child)) {
					param.setType(SchemaDefaulyType.type_array.type);
					param.setChildType(type);

					// 如果是简单的数组，则为数组增加一个子参数
					ParameterInfo childParam = new ParameterInfo("", type);
					param.addChild(childParam);

					// 复杂类型数组
					if (!DOMUtil.isDefaultType(child)) {
						getInputParam(inputParamList, document, operationName, xpathBuilder, childParam, false);
					}
				} else {
					param.setType("anyType".equals(type) ? "object" : type);

					// 复杂类型
					if (!DOMUtil.isDefaultType(child)) {
						StringBuilder complextXpath = new StringBuilder("wsdl:definitions/wsdl:types/xs:schema");
						getInputParam(inputParamList, document, operationName, complextXpath, param, false);
					}
				}
			} else {// 如果type属性不存在，说明该结点的类型在其子结点中定义
				String currentAppendStr = "/xs:complexType[@name='" + parentParam.getType() + "']/xs:sequence/xs:element[@name='" + name + "']";
				xpathBuilder.append(currentAppendStr);
				Node inner = DOMUtil.findNode(document, xpathBuilder.toString() + "/xs:complexType/xs:sequence/xs:element[position()=1]");

				if (DOMUtil.isArray(inner)) {
					// 得到数组的类型
					String type = getSequenceElementType(document, inner);
					param.setType(SchemaDefaulyType.type_array.type);
					param.setChildType(type);

					// 为数组增加一个子参数
					ParameterInfo childParam = new ParameterInfo("", type);
					param.addChild(childParam);

					if (!DOMUtil.isDefaultType(type)) {// 复杂类型数组
						getInputParam(inputParamList, document, operationName, xpathBuilder, childParam, true);
					}
				} else {
					param.setType(name);
					// 遍历其子结点xs:element
					getInputParam(inputParamList, document, operationName, xpathBuilder, param, true);
				}

				// 将xpath还原
				xpathBuilder.delete(xpathBuilder.length() - currentAppendStr.length(), xpathBuilder.length());
			}

			if (parentParam == null) {
				inputParamList.add(param);
			} else {
				parentParam.addChild(param);
			}
		}
	}

	/**
	 * 根据operationName从wsdl的document中查找出输入element名称， 即<wsdl:part name="parameters" element="ns:helloWorld" />中的element
	 * 
	 * @param document
	 * @param operationName
	 * @return
	 * @throws Exception
	 */
	public static String getInputElementName(Document document, String operationName) throws Exception {
		// 得到wsdl文件的xpath
		XPath xpath = getXpath(document);

		// 查找到input结点message属性的值
		Node inputMessageNode = DOMUtil.findNode(document, "wsdl:definitions/wsdl:portType/wsdl:operation[@name='" + operationName + "']/wsdl:input");
		// 完整名称为ns:helloWorldRequest，需要根据":"截取
		String simpleInputMessageName = DOMUtil.getAttributeValue(inputMessageNode, "message").split(":")[1];
		log.debug("查找到wsdl:input message为" + simpleInputMessageName);

		// 根据simpleInputMessageName名称查找message结点下的part结点的element属性值
		Node partNode = DOMUtil.findNode(document, "wsdl:definitions/wsdl:message[@name='" + simpleInputMessageName + "']/wsdl:part");
		String simpleElementName = DOMUtil.getAttributeValue(partNode, "element").split(":")[1];
		log.debug("查找到wsdl:input message为" + simpleElementName);
		return simpleElementName;
	}

	/***********************************************************************************************************************
	 * 得到WSDLFactory
	 * 
	 * @return
	 * @throws Exception
	 */
	public static WSDLFactory getWsdlFactory() throws Exception {
		if (wsdlFactory == null)
			wsdlFactory = WSDLFactory.newInstance();
		return wsdlFactory;
	}

	public static Definition getDefinition(String wsdlUrl) throws Exception {
		WSDLFactory factory = getWsdlFactory();

		WSDLReader reader = factory.newWSDLReader();
		reader.setFeature("javax.wsdl.verbose", true);
		reader.setFeature("javax.wsdl.importDocuments", true);

		Definition def = reader.readWSDL(wsdlUrl);
		return def;
	}

	/**
	 * 得到wsdl文件的根结点的document
	 * 
	 * @param wsdlUrl
	 * @return
	 * @throws Exception
	 */
	public static Document getDefinitionDocument(String wsdlUrl) throws Exception {
		Definition def = getDefinition(wsdlUrl);

		WSDLWriter writer = getWsdlFactory().newWSDLWriter();
		Document document = writer.getDocument(def);

		return document;
	}

	/**
	 * 得到document的查找工具xpath
	 * 
	 * @param document
	 * @return
	 * @throws Exception
	 */
	public static XPath getXpath(Document document) throws Exception {
		XPath xpath = XPathFactory.newInstance().newXPath();
		xpath.setNamespaceContext(new UniversalNamespaceCache(document, false));
		return xpath;
	}

	/**
	 * 查找所有的wsdl:import结点
	 * 
	 * @param document
	 * @param xpath
	 * @return
	 * @throws Exception
	 */
	public static List<Document> getImportDocumentList(Document document, XPath xpath) throws Exception {
		List<Document> importDocumentList = new ArrayList<Document>();
		// 查找所有的wsdl:import
		NodeList importNodeList = (NodeList) xpath.evaluate("wsdl:definitions/wsdl:import", document, XPathConstants.NODESET);
		if (importNodeList != null) {
			for (int i = 0; i < importNodeList.getLength(); i++) {
				Node importNode = importNodeList.item(i);
				String location = DOMUtil.getAttributeValue(importNode, "location");

				Document importDocument = getDefinitionDocument(location);
				importDocumentList.add(importDocument);
			}
		}
		return importDocumentList;
	}

	/**
	 * 根据complexTypeName在wsdl文件中查找complextType结点
	 * 
	 * @param document
	 * @param complexTypeName
	 * @return
	 * @throws Exception
	 */
	public static Node getComplexType(Document document, String complexTypeName) throws Exception {
		Node complextNode = DOMUtil.findNode(document, "wsdl:definitions/wsdl:types/xs:schema/xs:complexType[@name='" + complexTypeName + "']");
		return complextNode;
	}

	/**
	 * 根据complexTypeName查找xs:complexType结点下xs:sequence下的xs:element
	 * 
	 * @param document
	 * @param parentParam
	 * @param complexTypeName
	 * @param xpathBuilder
	 * @param isSelfDefinition
	 * @return
	 * @throws Exception
	 */
	public static List<Node> getSequenceElementOfComplexType(Document document, ParameterInfo parentParam, String complexTypeName,
			StringBuilder xpathBuilder, boolean isSelfDefinition) throws Exception {
		List<Node> list = new ArrayList<Node>();

		// 判断是否有继承
		String extensionXpath = isSelfDefinition ? xpathBuilder.toString() + "/xs:complexType/xs:complexContent/xs:extension"
				: "wsdl:definitions/wsdl:types/xs:schema/xs:complexType[@name='" + complexTypeName + "']/xs:complexContent/xs:extension";
		Node extension = DOMUtil.findNode(document, extensionXpath);
		if (extension != null) {// 存在继承
			// 查找父类
			String parentClassName = DOMUtil.getAttributeValue(extension, "base").split(":")[1];
			List<Node> parentElements = getSequenceElementOfComplexType(document, parentParam, parentClassName, xpathBuilder, false);
			if (parentElements != null && parentElements.size() > 0) {
				list.addAll(parentElements);
			}
			// 查找自己
			String selfXpath = extensionXpath + "/xs:sequence/xs:element";
			NodeList selfList = DOMUtil.findNodeList(document, selfXpath);
			if (selfList != null && selfList.getLength() > 0)
				list.addAll(DOMUtil.covertNodeListToList(selfList));
		} else {
			// 查找自己的属性
			String elementsOfSequenceXpath = isSelfDefinition ? xpathBuilder.toString() + "/xs:complexType/xs:sequence/xs:element[@name='"
					+ complexTypeName + "']/xs:complexType/xs:sequence/xs:element" : "wsdl:definitions/wsdl:types/xs:schema/xs:complexType[@name='"
					+ complexTypeName + "']/xs:sequence/xs:element";
			NodeList elementsOfSequence = DOMUtil.findNodeList(document, elementsOfSequenceXpath);
			list = DOMUtil.covertNodeListToList(elementsOfSequence);
		}
		return list;
	}

	/**
	 * 根据complexTypeName查找xs:complexType结点下xs:sequence下的xs:element
	 * 
	 * @param document
	 * @param complexTypeName
	 * @return
	 * @throws Exception
	 */
	public static List<Node> getSequenceElementOfComplexType(Document document, String complexTypeName) throws Exception {
		NodeList elementsOfSequence = DOMUtil.findNodeList(document, "wsdl:definitions/wsdl:types/xs:schema/xs:complexType[@name='" + complexTypeName
				+ "']/xs:sequence/xs:element");
		return DOMUtil.covertNodeListToList(elementsOfSequence);
	}

	/**
	 * 如果xs:element结点的type类型不是引用的，是在自己的子结点中定义的话，则查找该element的type
	 * 
	 * @param document
	 * @param node
	 * @return
	 * @throws Exception
	 */
	public static String getSequenceElementType(Document document, Node node) throws Exception {
		String type = "";
		if (node != null) {
			type = DOMUtil.getAttributeValue(node, "name");
		}
		return type;
	}
}
