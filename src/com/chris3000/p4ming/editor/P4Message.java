package com.chris3000.p4ming.editor;

import java.io.Serializable;

public class P4Message implements Serializable{
	public String name;
	public static final byte METHOD = 10;
	public static final byte PARAMETER = 20;
	public static final byte CLASS = 30;
	public static final byte UNKNOWN = 99;
	public byte type = UNKNOWN;
	public Object value;
	
	public P4Message(String name, Object value, byte type){
		this.name = name;
		this.value = value;
		this.type = type;
	}
	
	public String getTypeString(){
		String typeStr = null;
		switch(type){
		case METHOD: typeStr = "METHOD";break;
		case PARAMETER: typeStr = "PARAMETER";break;
		case CLASS: typeStr = "CLASS";break;
		default: typeStr = "UNKNOWN";break;
		}
		return typeStr;
	}
	
	public String toString(){
		return name+": "+getTypeString();
	}
}
