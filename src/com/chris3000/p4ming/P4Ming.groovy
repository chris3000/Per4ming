package com.chris3000.p4ming

import com.chris3000.p4ming.editor.P4Editor
import com.chris3000.p4ming.editor.P4Message
import com.chris3000.p4ming.viewer.P4Viewer
import java.awt.Point
//blah
class P4Ming {
	P4Editor p4e = null;
	P4Viewer p4v = null;
	
	public void startViewer(int width, int height){
		if (p4v == null){
			p4v = new P4Viewer(this);
			p4v.start(width, height);
		}
	}
	
/*	public void startViewer(int[] sz, int frameRate, float[] bgColor,boolean openGL, boolean fullScreen){
		if (p4v == null){
			p4v = new P4Viewer(this);
			p4v.start(sz, frameRate, bgColor, openGL, fullScreen);
		}
	}
	public void startViewer(int[] sz, int frameRate, float[] bgColor,boolean openGL, boolean fullScreen,
		String lineInName, String chanType, int bufferSize, float sampleRate, int bitDepth){
		if (p4v == null){
			p4v = new P4Viewer(this);
			p4v.start(sz, frameRate, bgColor, openGL, fullScreen, lineInName, chanType, bufferSize, sampleRate, bitDepth);
		}
	}*/
	
	public void startViewer(P4Prefs p4p){
		if (p4v == null){
			p4v = new P4Viewer(this);
			p4v.start(p4p);
		}
	}
		
	public void keyPressed(char c){
		if (p4v != null){
			p4v.keyPressed(c);
		}
	}
	
	public void removeText(Point fromLoc, int amount){
		if (p4v != null){
			p4v.removeText(fromLoc, amount);
		}
	}
	
	public void addText(Point atLoc,String text){
		if (p4v != null){
			p4v.addText(atLoc, text);
		}
	}
	
	public void caretEvent(Point dot, Point mark){
		if (p4v != null){
			p4v.caretEvent(dot, mark);
		}
	}

	public void caretEvent(Point dot){
		if (p4v != null){
			p4v.caretEvent(dot);
		}
	}
	
	public void addMethod(P4Message message){
		if (p4v != null){
			p4v.addMethod(message);
		}
	}
	
	public void addClass(P4Message message){
		if (p4v != null){
			p4v.addMethod(message);
		}
	}
	
	public void addMethods(P4Message[] messages){
		if (p4v != null){
			for (int i = 0; i < messages.length; i++) {
				p4v.addMethod(messages[i]);
			}
		}
	}
	
	public void stopViewer(){
		if (p4v != null){
			p4v = null;
			p4e.setStop();
		}
	}

	public void editorStop(){
		if (p4v != null){
			p4v.stop();
		}
	}

	public void start(){
		p4e = new P4Editor(this);
		//p4e.setParent (this);
		p4e.setVisible(true);
	}
	
	public void getCurrentText(){
		p4e.getCurrentText();
	}
	
	public void setText(String text){
		if (p4v != null){
			p4v.setText(text);
		}
	}
	public void changeCodeID(int codeID){
		if (p4v != null){
			p4v.changeCodeID(codeID);
		}
	}
	
	public static void main(String[] args){
		//put menu bar at top of screen and not on the app.
		System.setProperty ("apple.laf.useScreenMenuBar", "true");
		//System.setProperty ("sun.java2d.d3d", "false");
		//System.setProperty ("sun.java2d.ddoffscreen", "false");
		//System.setProperty ("sun.java2d.noddraw", "false");
		//System.setProperty ("apple.awt.fakefullscreen","true");
		System.setProperty ("apple.awt.fullscreencapturealldisplays", "false");
		//System.setProperty ("apple.awt.graphics.UseQuartz", "true");
		//System.setProperty ("apple.awt.graphics.EnableDeferredUpdates", "true");
		
		
		P4Ming p4m = new P4Ming();
		p4m.start();
	}
}

