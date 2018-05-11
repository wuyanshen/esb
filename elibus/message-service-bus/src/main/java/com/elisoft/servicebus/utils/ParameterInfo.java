package com.elisoft.servicebus.utils;

import java.util.ArrayList;
import java.util.List;

public class ParameterInfo {
	private String name;// 参数名
	private String value;// 参数值
	private String type;// 参数类型
	private String childType;// 如果是数组，那么该字段为数组元素的类型
	private List<ParameterInfo> children = new ArrayList<ParameterInfo>();

	public ParameterInfo() {

	}

	public ParameterInfo(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<ParameterInfo> getChildren() {
		return children;
	}

	public void setChildren(List<ParameterInfo> children) {
		this.children = children;
	}

	public String getChildType() {
		return childType;
	}

	public void setChildType(String childType) {
		this.childType = childType;
	}

	/**
	 * 增加子参数
	 * 
	 * @param param
	 */
	public void addChild(ParameterInfo param) {
		children.add(param);
	}
}
