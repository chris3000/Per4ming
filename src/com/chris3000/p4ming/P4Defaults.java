package com.chris3000.p4ming;

public class P4Defaults {
	//editor
	public static int[] editorLoc = {0,0};
	public static int[] editorSize = {650,480};
	
	//setup
	public static int[] size = {800,600};
	public static int frameRate=30;
	public static int[] bgColor = {0,0,0};
	public static boolean openGL = true;
	public static boolean fullScreen = false;
	static public boolean primaryMonitor = true;
	static public boolean softFullScreen = false;
	
	//audio
	public static boolean audioEnabled=true;
	public static String lineInName="aud";
	public static String channelType="Mono";
	public static int bufferSize = 512;
	public static float sampleRate = 44100;
	public static int bitDepth=16;
}
