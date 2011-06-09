package com.chris3000.p4ming.viewer

import com.chris3000.p4ming.P4Ming
import com.chris3000.p4ming.P4Prefs
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Frame
import java.awt.Rectangle
import java.awt.Window
import java.awt.GraphicsDevice
import java.awt.Point
import java.awt.event.ActionListener
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.Toolkit;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsConfiguration;
import java.awt.DisplayMode;
import javax.swing.JFrame
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

	public void start(P4Prefs p4p){
		p4aFrame = new JFrame("P4M"); // create Frame
		p4aFrame.setSize(p4p.size[0], p4p.size[1]);
		p4aFrame.setResizable(false);
		//close
		p4aFrame.addWindowListener(this);
		// Setup Main Frame
		p4aFrame.setLayout(new BorderLayout());
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		if (p4p.fullScreen){
			p4aFrame.setLocation (0, 0);
			p4aFrame.setUndecorated(true);
			GraphicsDevice gd = null;
			if (!p4p.primaryMonitor&& gs.length >1){
				gd=gs[1];
			} else {
				gd=gs[0];
			}
			//printGraphicsDevices(gs);
			if(p4p.softFullScreen){
				GraphicsConfiguration[] gc = gd.getConfigurations();
				Rectangle r = gc[0].getBounds();
				p4aFrame.setLocation ((int)r.getX(), (int)r.getY());
			} else {
				gd.setFullScreenWindow(p4aFrame);
			}
		} else {
		int[] winLoc = getCenterScreen(p4p.size[0],p4p.size[1]);
			p4aFrame.setLocation (winLoc[0], winLoc[1]);
		}
		// Add processing PApplet
		p4a = new P4Applet((int)p4aFrame.getSize().width, (int)p4aFrame.getSize().height, p4p.frameRate, p4p.bgColor, p4p.openGL);
		p4aFrame.add(p4a, BorderLayout.CENTER);
		p4aFrame.setVisible(true);
		p4a.init();
		if (p4p.audioEnabled && p4p.lineInName != null && p4p.channelType != null){
			p4a.initAudio(p4p.lineInName, p4p.channelType, p4p.bufferSize, p4p.sampleRate, p4p.bitDepth);
		}
		while (!p4a.isInit()){
			sleep(500);
		}
		p4m.getCurrentText();
		
	}

	private void printGraphicsDevices(GraphicsDevice[] gs) {
			for (int i = 0; i < gs.length; i++) {
				String id=gs[i].getIDstring();
				boolean isFullscreen=gs[i].isFullScreenSupported()
				boolean isChangeAllowed=gs[i].isDisplayChangeSupported();
				println("monitor "+i+": id="+id+", isFullScreenSupported="+isFullscreen+", isChangeAllowed="+isChangeAllowed);
			}
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