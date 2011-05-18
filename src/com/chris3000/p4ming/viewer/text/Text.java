package com.chris3000.p4ming.viewer.text;

import com.chris3000.p4ming.viewer.text.P4Text;

import processing.core.PApplet;
import processing.core.PFont;

public class Text extends PApplet{
	P4Text p4text = null;
	
	public void setup(){
		size(800,600, OPENGL);
		P4Text.p = this;
		p4text = new P4Text();
		p4text.init();
	}
	
	public void draw(){
		background(100,10,10);
		p4text.calc();
		p4text.render();
	}

	public void keyPressed(){
		if(key == CODED) { 
		    // pts
		    if (keyCode == UP) { 
		    	p4text.cursorUp();
		    } 
		    else if (keyCode == DOWN) { 
		    	p4text.cursorDown();
		    } 
		    // extrusion length
		    if (keyCode == LEFT) { 
		    	p4text.cursorLeft();
		    } 
		    else if (keyCode == RIGHT) { 
		    	p4text.cursorRight();
		    } 
		  } else {
			  p4text.add(key);
		  }
	}
}
