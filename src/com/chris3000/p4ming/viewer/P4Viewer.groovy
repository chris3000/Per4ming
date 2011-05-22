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
		p4aFrame = new Frame("P4M"); // create Frame
		int[] winLoc = getCenterScreen(w,h);
		p4aFrame.setLocation (winLoc[0], winLoc[1]);
		//close
		p4aFrame.addWindowListener(this);
		// Setup Main Frame
		p4aFrame.setLayout(new BorderLayout());
		p4aFrame.setSize(w, h);
		// Add processing PApplet
		p4a = new P4Applet((int)p4aFrame.getSize().width, (int)p4aFrame.getSize().height);
		p4aFrame.add(p4a, BorderLayout.CENTER);
		p4aFrame.setVisible(true);
		p4a.init();
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