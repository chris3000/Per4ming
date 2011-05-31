package com.chris3000.p4ming;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class P4Prefs {
	//internal
	private String filePath = System.getProperty("user.home")+System.getProperty("file.separator")+"p4ming.conf";
	private boolean hasPrefsFile=false;
	private PropertiesConfiguration config = null;
	//setup
	public int[] size = new int[2];
	public int frameRate=-1;
	public float[] bgColor = new float[3];
	public boolean openGL = false;
	public boolean fullScreen = false;
	
	//audio
	public boolean audioEnabled=false;
	public String lineInName=null;
	public String channelType=null;
	public int bufferSize = -1;
	public float sampleRate = -1;
	public int bitDepth=-1;
	
	public P4Prefs(){
		 try {
			config = new PropertiesConfiguration(filePath);
			hasPrefsFile=true;
		} catch (ConfigurationException e) {
			System.out.println("No Prefs file found.  Using and saving defaults.");
			createNewConfFile();
			try {
				config = new PropertiesConfiguration(filePath);
			} catch (ConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (hasPrefsFile){
			loadPrefs();
		} else {
			configDefaultPrefs();
			savePrefs();
		}
		printFields();
	}
	
	public void loadPrefs(){
		Field[] f = this.getClass().getFields();
		for (int i = 0; i < f.length; i++) {
			try {
				String name=f[i].getName();
				String classType = f[i].getType().getSimpleName();
				if (classType.equals("int")){
					f[i].set(this, config.getInt(name));
				} else if (classType.equals("int[]")){
					String[] ints = config.getStringArray(name);
					int[] tempInts=new int[ints.length];
					for (int j = 0; j < ints.length; j++) {
						tempInts[j]=Integer.parseInt(ints[j]);
					}
					f[i].set(this, tempInts);
				} else if (classType.equals("float[]")){
					String[] floats = config.getStringArray(name);
					float[] tempFloats=new float[floats.length];
					for (int j = 0; j < floats.length; j++) {
						tempFloats[j]=Float.parseFloat(floats[j]);
					}
					f[i].set(this, tempFloats);
				}else if (classType.equals("boolean")){
					f[i].set(this, config.getBoolean(name));
				} else if (classType.equals("float")){
					f[i].set(this, config.getFloat(name));
				} else if (classType.equals("String")){
					f[i].set(this, config.getString(name));
				}
				//System.out.println("name: "+name+", class: "+classType);
			}
			catch (IllegalArgumentException e) {e.printStackTrace();}
			catch (SecurityException e) {e.printStackTrace();}
			catch (IllegalAccessException e) {e.printStackTrace();}
		}
	}
	
	public void savePrefs(){
		Field[] f = this.getClass().getFields();
		for (int i = 0; i < f.length; i++) {
			try {
				String name=f[i].getName();
				Object value = f[i].get(this);
				config.setProperty(name, value);
			}
			catch (IllegalArgumentException e) {e.printStackTrace();}
			catch (SecurityException e) {e.printStackTrace();}
			catch (IllegalAccessException e) {e.printStackTrace();}
		}
		try {
			config.save();
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createNewConfFile(){
		File f = new File(filePath);
		if (!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void configDefaultPrefs(){
		Field[] f = this.getClass().getFields();
		for (int i = 0; i < f.length; i++) {
			try {
			String name=f[i].getName();
			Object newValue = P4Defaults.class.getField(name).get(null);
				f[i].set(this, newValue);
			}
			catch (IllegalArgumentException e) {e.printStackTrace();}
			catch (SecurityException e) {e.printStackTrace();}
			catch (IllegalAccessException e) {e.printStackTrace();}
			catch (NoSuchFieldException e) {e.printStackTrace();}
	}

	}
	
	private void printFields(){
		Field[] f2 = this.getClass().getFields();
		for (int i = 0; i < f2.length; i++) {
			try {
				System.out.println(f2[i].getName()+": "+f2[i].get(this));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		P4Prefs p4p = new P4Prefs();
		
	}
}
