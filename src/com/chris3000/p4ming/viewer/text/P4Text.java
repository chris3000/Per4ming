package com.chris3000.p4ming.viewer.text;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PFont;

public class P4Text {
	public static PApplet p = null;
	private final static char cr = '\n';
	/**
	 * back of napkin calculations...
	 * at 1.0 every char = 40 pixels
	 * at 0.25 min there's about 80 chars on a line on a 800 width window
	 * at 1.0 every line = 84 pixels
	 * max chars = width / 10
	 * scale = 1- numofchars / maxchars
	 */
	/**The current size of the text*/
	float currentScale = 1;
	/** The size we should eventually be set to.*/
	float targetScale = 1;
	/** How fast to scale the text*/
	float scaleVel = 0.005f;
	/**The font.  Monaco by default*/
	PFont font;
	/** Smallest size */
	float minScl = 0.25f;
	/**Maximum size */
	float maxScl = 1;
	/**at minimum size the chars are 10 pixels, so max chars is width/10 */
	float maxChars = 0;
	int lineLength = 0;
	int maxLineLength = 0;
	
	float maxLineSpace = 82.85f; //82.85 pixels between lines

	int leftPadding = 10; //space from left screen, in pixels.
	/**Holds line data*/
	ArrayList<P4TextLine> lines = new ArrayList<P4TextLine>();
	/***
	 * Holds cursor location data
	 * */
	P4Cursor cursor = new P4Cursor();
	private boolean cursorEnabled = true;

	public void init(){
		lines.add(new P4TextLine());
		maxChars = p.width/10;
		// Uncomment the following two lines to see the available fonts 
		  //String[] fontList = PFont.list();
		  //println(fontList);
		  font = p.createFont("Monaco", 64);
	}
	
	public void calc(){
		if (targetScale != currentScale){
			float dist = Math.abs(currentScale - targetScale);
			//scale up or down?
			float dir = 1; //down
			if (targetScale > currentScale){
				dir = -1; //up
			}
			currentScale = currentScale - (scaleVel*dir*(dist/dist));
			//are we close enough?
			if (dist < scaleVel){
				currentScale = targetScale;
			}
		}
		if (cursorEnabled){
			cursor.calcBlink();
		}
	}
	
	public void render(){
		p.textFont(font);
		p.pushMatrix();
			float h = (((float)p.width / 2) - (lines.size()*(maxLineSpace*currentScale)));
			p.translate(leftPadding,h,0);
			p.scale(currentScale);
			p.text(getText(), 0, 0, 0);
			//draw cursor
			if (cursorEnabled && cursor.isVisible()){
				p.noStroke();
				p.rect(cursor.x*38+10, cursor.y*maxLineSpace+15, 20, -70);
			}
		p.popMatrix();
		p.text(""+currentScale,10,500);	
	}
	
	public String getText(){
		StringBuffer sb= new StringBuffer();
		for (int i = 0; i < lines.size(); i++) {
			sb.append(lines.get(i).toString()).append(cr);
		}
		return sb.toString();
	}
	
	public void add(char key){

		if (key == cr){
				P4TextLine line = lines.get(cursor.y);
				//get the remainder of chars, if any
				String remainder = line.remove(cursor.x, line.length);
				lines.add(cursor.y+1, new P4TextLine(remainder));
				cursor.down(0);
		} else if (key == p.BACKSPACE){
			P4TextLine line = lines.get(cursor.y);
			if (cursor.x > 0){
				line.delete(cursor.x-1);
				cursorLeft();
			} else {
				//we're removing the "enter" char
				//is line empty?
				if (line.length == 0){
					//delete the line, unless it's the first line
					if (lines.size() > 1){
					lines.remove(cursor.y);
					cursorLeft();
					}
				} else {
					//we're not on the first line...
					if (cursor.y >= 1){
					P4TextLine previousLine = lines.get(cursor.y-1);
					previousLine.add(line.toString());
					lines.remove(cursor.y);
					}
					cursorLeft();
				}
			}
		} else {
			P4TextLine line = lines.get(cursor.y);
			line.add(key, cursor.x);
			calcTargetScale();
			//move cursor
			cursor.right(line.length);
		}
		calcTargetScale();
		cursor.blinkOn();
	}
	
	private void calcTargetScale(){
		int max = 0;
		for (int i = 0; i < lines.size(); i++) {
			int linelength = lines.get(i).length;
			if (linelength > max){
				max = linelength;
			}
		}
		maxLineLength = max;
		targetScale = maxScl- ((float)maxLineLength/maxChars);
		if (targetScale < minScl) targetScale = minScl;
	}
	
	public void cursorUp(){
		if (cursor.y > 0){
			P4TextLine line = lines.get(cursor.y-1);
			cursor.up(line.length);
		} else {
			cursor.up(0);
		}
	}
	
	public void cursorDown(){
		if (cursor.y < lines.size()-1){
			P4TextLine line = lines.get(cursor.y+1);
			cursor.down(line.length);
		}
	}

	public void cursorRight(){
		P4TextLine line = lines.get(cursor.y);
		if (cursor.y < lines.size()-1){
			cursor.right(line.length);
		} else if (cursor.x < line.length){
			cursor.right(line.length);
		}
	}

	public void cursorLeft(){
		if (cursor.y > 0){
			P4TextLine line = lines.get(cursor.y-1);
			cursor.left(line.length);
		} else {
			cursor.left(0);
		}
	}
}
