package com.chris3000.p4ming.viewer

import com.chris3000.p4ming.P4Ming
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Frame
import java.awt.Point
import java.awt.event.ActionListener
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.Toolkit;

public class P4Viewer extends WindowAdapter{
	public P4Ming p4m = null;
	public P4Applet p4a = null;
	public Frame p4aFrame = null;

	public P4Viewer(P4Ming _p4m){
		p4m = _p4m;
	}

	public P4Applet getPApplet(){
		return p4a;
	}
	public void stop () {
		p4a.stop();
		p4aFrame.dispose();
		p4m.stopViewer();
	}
	public void windowClosing(WindowEvent we){
		stop();
	}

	def addMethod = {closure ->
		if (p4a != null){
			p4a.addMethod(closure);
		}
	}

	public void keyPressed(char c){
		if (p4a != null){
			p4a.p4KeyPressed(c);
		}
	}
	
	public void removeText(Point fromLoc,int amount){
		if (p4a != null){
			p4a.removeText(fromLoc, amount);
		}
	}

	public void addText(Point atLoc,String text){
		if (p4a != null){
			p4a.addText(atLoc, text);
		}
	}
	
	public void caretEvent(Point dot, Point mark){
		if (p4a != null){
			p4a.caretEvent(dot, mark);
		}
	}
	
	public void caretEvent(Point dot){
		if (p4a != null){
			p4a.caretEvent(dot);
		}
	}
	
	public void start(int w, int h){
		float[] bg = new float[3];
		int[] sz = new int[2];
		sz[0]=w;
		sz[1]=h;
		start(sz,30,bg,true,false);
		
	}
	
	public void start(int[] sz, int frameRate, float[] bgColor,boolean openGL, boolean fullScreen){
		start(sz,frameRate,bgColor, openGL, fullScreen, null, null, 0, 0f,0);
	}

	public void start(int[] sz, int frameRate, float[] bgColor,boolean openGL, boolean fullScreen,
			String lineInName, String chanType, int bufferSize, float sampleRate, int bitDepth){
		p4aFrame = new Frame("P4M"); // create Frame
		int[] winLoc = new int[2];
		if (!fullScreen){
			winLoc = getCenterScreen(sz[0],sz[1]);
		}
		p4aFrame.setLocation (winLoc[0], winLoc[1]);
		//close
		p4aFrame.addWindowListener(this);
		// Setup Main Frame
		p4aFrame.setLayout(new BorderLayout());
		p4aFrame.setSize(sz[0], sz[1]);
		// Add processing PApplet
		p4a = new P4Applet((int)p4aFrame.getSize().width, (int)p4aFrame.getSize().height, frameRate, bgColor, openGL);
		p4aFrame.add(p4a, BorderLayout.CENTER);
		p4aFrame.setVisible(true);
		p4a.init();
		if (lineInName != null && chanType != null){
			p4a.initAudio(lineInName, chanType, bufferSize, sampleRate, bitDepth);
		}
		while (!p4a.isInit()){
			sleep(500);
		}
		p4m.getCurrentText();
		
	}
	public void setText(String text){
		if (p4a != null){
			p4a.setText(text);
		}
	}
	
	private int[] getCenterScreen(int w, int h){
		// Get the size of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		// Determine the new location of the window
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;
		return [x,y];
	}
}