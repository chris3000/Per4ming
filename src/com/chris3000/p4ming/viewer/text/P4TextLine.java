package com.chris3000.p4ming.viewer.text;

public class P4TextLine {
	StringBuffer lineBuffer = null;
	String line = null;
	//int lineNumber = 0;
	int length = 0;
	
	public P4TextLine(){
		 init(null);
	}
	
	public P4TextLine(String str){
		 init(str);
	}
	
	private void init(String str){
		if (str != null){
			lineBuffer = new StringBuffer(str);
		} else {
			lineBuffer = new StringBuffer();
		}
		 update();
	}
	
	public String substring(int start, int end){
		return lineBuffer.substring(start, end);
	}
	
	public String remove(int start, int end){
		String sub = lineBuffer.substring(start, end);
		lineBuffer.delete(start, end);
		update();
		return sub;
	}
	
	public void add(char c){
		lineBuffer.append(c);
		update();
	}
	public void add(char c, int position){
		lineBuffer.insert(position,c);
		update();
	}
	
	public void add(String s){
		lineBuffer.append(s);
		update();
	}
	
	public void add(String s, int position){
		lineBuffer.insert(position,s);
		update();
	}
	
	public void delete(int position){
		lineBuffer.deleteCharAt(position);
		update();
	}
	
	public void delete(int start, int end){
		lineBuffer.delete(start, end);
		update();
	}
	
	private void update(){
		line = lineBuffer.toString();
		length = lineBuffer.length();
	}
	
	public String toString(){
		return line;
	}
}
