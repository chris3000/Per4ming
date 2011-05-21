package com.chris3000.p4ming.viewer.text;

import java.awt.Point;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PFont;

public class P4Text {
	public static PApplet p = null;
	private final static char cr = '\n';
	private final static String crStr = "\n";
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
	float scaleVel = 0.12f;
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
	/** Holds cursor location data */
	P4Cursor cursor = new P4Cursor();
	/** should be true if an external editor is sending caret data */
	public boolean caretOverride = true;
	/** sets alpha of darkened bg */
	private float bgAlpha = 128;
	private boolean cursorEnabled = true;
	private boolean showFramerate = true;
	
	
	/**Are there any error messages?*/
	private boolean showErrorMessage = false;
	private String errorMessage = "";
	
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
			
			currentScale = currentScale - (dist*scaleVel*dir);
			//are we close enough?
			if ((dist*2*scaleVel)< 0.0001){
				currentScale = targetScale;
				p.println("settled");
			}
		}
		if (cursorEnabled){
			cursor.calcBlink();
		}
	}
	
	public void render(){
		p.frustum(p.width*-1, p.width, p.height*-1, p.height, 1038, 0);
		p.fill(0,bgAlpha);
		p.rect(0,0,p.width, p.height);
		p.fill(255);
		p.textFont(font);
		p.pushMatrix();
			float h = (((float)p.width / 2) - (lines.size()*(maxLineSpace*currentScale)));
			p.translate(leftPadding,h,0);
			p.scale(currentScale);
			p.text(getText(), 0, 0, 0);
			//draw cursor
			if (cursorEnabled){
				p.fill(255,128);
				p.noStroke();
				if (cursor.isVisible()){
				p.rect(cursor.x*38, cursor.y*maxLineSpace+15, 40, -70);
				}
				if(cursor.isSelected()){
					Point[] range = cursor.getSelectRange();
					Point begin = range[0];
					Point end = range[1];
					if (begin.y == end.y){//just one line
						p.rect(begin.x*38, begin.y*maxLineSpace+15, (end.x-begin.x)*38, -70);
					} else { //multiple lines
						for (int i = begin.y; i <= end.y; i++) {
							P4TextLine line = lines.get(i);
							int length = line.length+1;
							if (i==begin.y){
								p.rect(begin.x*38, begin.y*maxLineSpace+15, (length-begin.x)*38, -70);
							} else if (i == end.y){
								p.rect(0, end.y*maxLineSpace+15, end.x*38, -70);
							} else {//whole line
								p.rect(0, i*maxLineSpace+15, length*38, -70);
							}
						}
					}
					//p.println("selected!");
				}
			}
		p.popMatrix();
		if (showErrorMessage){
			p.fill(255,128,128);
			p.textFont(font, 14);
			p.text(errorMessage,5,p.height-80);
			showErrorMessage = false;//turn off.  must get reset every frame
		}
		if (showFramerate){
			p.fill(255);
			p.textFont(font, 14);
			p.text(""+(int)(p.frameRate),5,p.height-30);
		}
	}
	
	public void setSelection(Point dot, Point mark){
		cursor.selectOn(dot, mark);
	}
	
	public void selectionOff(){
		cursor.selectOff();
	}
	
	public void setErrorMessage(String msg){
		if (msg.length() > 80){
			msg = msg.substring(0, 80)+cr+msg.substring(81, msg.length());
		}
		errorMessage = msg;
		showErrorMessage = true;
	}
	
	
	public String getText(){
		StringBuffer sb= new StringBuffer();
		for (int i = 0; i < lines.size(); i++) {
			sb.append(lines.get(i).toString()).append(cr);
		}
		return sb.toString();
	}
	
	/** Replace all of the current text with new text */
	public void setText(String text){
		ArrayList<P4TextLine> newLines = new ArrayList<P4TextLine>();
		if (text != null && text.length() > 0){
			String[] _lines = text.split(crStr);
			for (int i = 0; i < _lines.length; i++) {
				newLines.add(new P4TextLine(_lines[i]));
			}
		} else { //blow away all of the old text
			newLines.add(new P4TextLine());	
		}
		lines = newLines;
		calcTargetScale();
		cursor.blinkOn();
		
	}
	
	/** add new character, delete character, make new line, or move cursor */
	public void add(char key){
		if(key == p.CODED) { 
			// pts
			if (p.keyCode == p.UP) { 
				cursorUp();
			} 
			else if (p.keyCode == p.DOWN) { 
				cursorDown();
			} 
			// extrusion length
			if (p.keyCode == p.LEFT) { 
				cursorLeft();
			} 
			else if (p.keyCode == p.RIGHT) { 
				cursorRight();
			} 
		} else {
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
		}

		cursor.blinkOn();
	}
	
	public void setBgAlpha(float alpha){
		if (alpha <= 1){
			alpha = alpha*255;
		}
		bgAlpha = alpha;
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
	
	public void caretEvent(Point dotLoc){
		cursor.setLocation(dotLoc.x, dotLoc.y);
	}
	
	public void cursorUp(){
		if (!caretOverride){
			if (cursor.y > 0){
				P4TextLine line = lines.get(cursor.y-1);
				cursor.up(line.length);
			} else {
				cursor.up(0);
			}
		}
	}

	public void cursorDown(){
		if (!caretOverride){
			if (cursor.y < lines.size()-1){
				P4TextLine line = lines.get(cursor.y+1);
				cursor.down(line.length);
			}
		}
	}

	public void cursorRight(){
		if (!caretOverride){
			P4TextLine line = lines.get(cursor.y);
			if (cursor.y < lines.size()-1){
				cursor.right(line.length);
			} else if (cursor.x < line.length){
				cursor.right(line.length);
			}
		}
	}

	public void cursorLeft(){
		if (!caretOverride){
			if (cursor.y > 0){
				P4TextLine line = lines.get(cursor.y-1);
				cursor.left(line.length);
			} else {
				cursor.left(0);
			}
		}
	}
}
