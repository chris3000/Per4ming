package com.chris3000.p4ming.viewer.text;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class P4TextLine {
	
	//use locks to sync the string buffer
	private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private final Lock read  = readWriteLock.readLock();
	private final Lock write = readWriteLock.writeLock();
	
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
		write.lock();
		try {
			if (str != null){
				lineBuffer = new StringBuffer(str);
			} else {
				lineBuffer = new StringBuffer();
			}
			update();
		} finally {
			write.unlock();
		}
	}
	
	public String substring(int start, int end){
		read.lock();
		try {
			return lineBuffer.substring(start, end);
		} finally {
			read.unlock();
		}
	}
	
	public String remove(int start, int end){
		write.lock();
		try {
			String sub = lineBuffer.substring(start, end);
			lineBuffer.delete(start, end);
			update();
			return sub;
		} finally {
			write.unlock();
		}
	}
	
	public void add(char c){
		write.lock();
		try {
			lineBuffer.append(c);
			update();
		} finally {
			write.unlock();
		}
	}

	public void add(char c, int position){
		write.lock();
		try {
			lineBuffer.insert(position,c);
			update();
			//System.out.println("inserted '"+c+"' at position "+position+" in buffer of length"+lineBuffer.length());

		} catch(Exception e){
			e.printStackTrace();
			System.out.println("PROBLEM!! couldn't insert '"+c+"' at position "+position+" in buffer of length"+lineBuffer.length());
		} finally {
			write.unlock();
		}
	}
	
	public void add(String s){
		write.lock();
		try {
			lineBuffer.append(s);
			update();
		} finally {
			write.unlock();
		}
	}
	
	public void add(String s, int position){
		write.lock();
		try {
			lineBuffer.insert(position,s);
			update();
		} finally {
			write.unlock();
		}
	}
	
	public void delete(int position){
		write.lock();
		try {
			lineBuffer.deleteCharAt(position);
			update();
		} finally {
			write.unlock();
		}
	}

	public void delete(int start, int end){
		write.lock();
		try {
			lineBuffer.delete(start, end);
			update();
		} finally {
			write.unlock();
		}
	}
	
	private void update(){
			line = lineBuffer.toString();
			length = lineBuffer.length();
	}
	
	public String toString(){
		return line;
	}
}
