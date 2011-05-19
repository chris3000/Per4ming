package com.chris3000.p4ming

import com.chris3000.p4ming.editor.P4Editor
import com.chris3000.p4ming.editor.P4Message
import com.chris3000.p4ming.viewer.P4Viewer
import java.awt.Point

class P4Ming {
	P4Editor p4e = null;
	P4Viewer p4v = null;
	//GroovyShell shell = new GroovyShell();
	public void startViewer(int width, int height){
		if (p4v == null){
			p4v = new P4Viewer(this);
			p4v.start(width, height);
		}
	}

	public void keyPressed(char c){
		if (p4v != null){
			p4v.keyPressed(c);
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
			//def eval = evaluate(closure);
			p4v.addMethod(message);
		}
	}
	
	public void stopViewer(){
		p4v = null;
		p4e.setStop();
	}

	public void editorStop(){
		p4v.stop();
	}

	public void start(){
		p4e = new P4Editor();
		p4e.setParent (this);
		p4e.setVisible(true);
	}
	
	public void getCurrentText(){
		p4e.getCurrentText();
	}
	
	public void setText(String text){
		p4v.setText(text);
	}
	public static void main(String[] args){
		P4Ming p4m = new P4Ming();
		p4m.start();
	}
}

