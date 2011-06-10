package com.chris3000.p4ming.viewer.text;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	//move up and down to keep cursor in frame
	float currentVert = 0;
	float currentHoriz = 0;
	//float targetVert = 1;
	//float vertVel = 0.12f;
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
	//fading info
	boolean useFade = true;
	public int fadeDelay = 3000;//millis
	public float fadeDuration = 1000;//millis
	long lastKey=System.currentTimeMillis();
	float topFade = 128;//0-255
	float currentFadePct =1;
	float maxLineSpace = 82.85f; //82.85 pixels between lines

	int leftPadding = 10; //space from left screen, in pixels.
	/**Holds line data*/

	List<P4TextLine> lines = Collections.synchronizedList(new ArrayList<P4TextLine>());
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
		calcScale();
		calcVert();
		if (useFade){
			calcFade();
		}
		if (cursorEnabled){
			cursor.calcBlink();
		}
	}

	public void fadeOn(){
		useFade =true;
		currentFadePct=1;
	}
	
	public void fadeOff(){
		useFade =false;
		currentFadePct=1;
	}
	
	private void calcFade(){
		if (currentFadePct > 0){
			float lastKeyDelay = (float)(System.currentTimeMillis()-lastKey);
			if (lastKeyDelay>fadeDelay){
				lastKeyDelay = lastKeyDelay-fadeDelay;
				currentFadePct = 1f-(lastKeyDelay/fadeDuration);
				if (currentFadePct < 0){
					currentFadePct = 0;
				}
			}
			//p.println("fadepct = "+currentFadePct);
		}
	}

	private void calcScale() {
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
				p.println("scale settled");
			}
		}
	}
	
	private void calcVert() {
/*		if (targetVert != currentVert){
			float dist = Math.abs(currentVert - targetVert);
			//scale up or down?
			float dir = 1; //down
			if (targetVert > currentVert){
				dir = -1; //up
			}

			currentVert = currentVert - (dist*vertVel*dir);
			//are we close enough?
			if ((dist*2*vertVel)< 0.0001){
				currentVert = targetVert;
				p.println("vert settled");
			}
		}			*/
	}
	
	public synchronized void render(){
		p.rectMode(p.CORNER);
		p.frustum(p.width*-1, p.width, p.height*-1, p.height, 1038, 0);
		p.fill(0,0,0,bgAlpha*currentFadePct);
		p.rect(0,0,p.width, p.height);
		p.fill(255,255,255,255f*currentFadePct);
		p.textFont(font);
		p.pushMatrix();
			//float vertOffset = (currentVert-targetVert);
			float h = (((float)p.width / 2) - (lines.size()*(maxLineSpace*currentScale)))+currentVert;
			//p.println("currentHoriz="+currentHoriz+", leftPadding="+leftPadding);
			p.translate(leftPadding+currentHoriz,h,0);
			p.scale(currentScale);
			p.text(getText(), 0, 0, 0);
			//draw cursor
			if (cursorEnabled){
				float cursorX = cursor.x*38;
				float cursorY= cursor.y*maxLineSpace+15;
				//do we need to offset the text to keep the cursor on the screen?
				float screenCX = p.screenX(cursorX, cursorY);
				float screenCY = p.screenY(cursorX, cursorY);
				offsetText(screenCX, screenCY);
				//draw it
				p.fill(255,255,255,128f*currentFadePct);
				p.noStroke();
				if (cursor.isVisible()){
				p.rect(cursorX, cursorY, 40, -70);
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
			p.fill(255,128,128,255f);
			p.textFont(font, 14);
			p.text(errorMessage,5,p.height-80);
			showErrorMessage = false;//turn off.  must get reset every frame
		}
		if (showFramerate){
			p.fill(255,255,255,255f*currentFadePct);
			p.textFont(font, 14);
			p.text(""+(int)(p.frameRate),5,p.height-30);
		}
		p.fill(255,255);
	}
	
	
	//determine whether we need to move the text up, down, right, or left to keep cursor on screen
	public void offsetText(float cx, float cy){
		if (cy< p.height*0.20f){ //too high
			currentVert += 10;
		} else if (cy > p.height*0.75f){ //too low
			currentVert-=10;
		}
		if (currentScale <=0.3f){
		if (cx< p.width*0.25f){ //too left
			currentHoriz += 30;
			if (currentHoriz > 0){
				currentHoriz = 0;
			}
		} else if (cx > p.width*0.95f){ //too right
			currentHoriz-=20;
		}
		} else if (currentHoriz < 0){
			currentHoriz+=30;
			if (currentHoriz > 0){
				currentHoriz=0;
			}
		}
	}
	
	public void setSelection(Point dot, Point mark){
		cursor.selectOn(dot, mark);
		resetFade();
	}
	
	public void selectionOff(){
		cursor.selectOff();
		resetFade();
	}
	
	public void setErrorMessage(String msg){
		if (msg.length() > 80){
			msg = msg.substring(0, 80)+cr+msg.substring(81, msg.length());
		}
		errorMessage = msg;
		showErrorMessage = true;
		resetFade();
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
		resetFade();
	}
	
	private void resetFade(){
		currentFadePct = 1;
		lastKey = System.currentTimeMillis();
	}
	
	public void removeText(Point fromLoc,int amount){
		P4TextLine line = lines.get(fromLoc.y);
		System.out.println("deleted "+fromLoc.x+", "+fromLoc.y+": "+amount);
		//is it just from this line?
		if (line.length-fromLoc.x >= amount){
			line.delete(fromLoc.x, fromLoc.x+amount);
		} else {//more than 1 line
			System.out.println("deleted "+fromLoc.x+", "+fromLoc.y+": "+amount);
			//first line
			amount = amount - (line.length-fromLoc.x)-1;//reduce amount
			if (amount>0){//we're not at the end of the first line
				line.delete(fromLoc.x, line.length);				
			} else {//were deleting the cr from the top of the line
				P4TextLine nextLine = lines.remove(fromLoc.y+1);
				line.add(nextLine.toString());
			}
			while (amount > 0){
				P4TextLine nextLine = lines.remove(fromLoc.y+1);
				System.out.println("amount="+amount+", nextLine.length="+nextLine.length);
				if (amount < nextLine.length){
					nextLine.delete(0, amount);
					line.add(nextLine.toString());
				}
				amount = amount - nextLine.length-1;
			}
		}
		calcTargetScale();
		p.println("text removed.  now text is "+getText()+" lines.size="+lines.size());
	}

	public void addText(Point atLoc,String text){
		//p.println("BEGIN!! text is "+getText()+" lines.size="+lines.size());
		String[] newLines = text.split(crStr,-1);//-1 = include trailing enters
		boolean crOnly = false;
		if (newLines.length==2){
			if (newLines[0].isEmpty() && newLines[1].isEmpty()){//cr only
			//	System.out.println("CR!");
				P4TextLine line = lines.get(cursor.y);
				//get the remainder of chars, if any
				String remainder = line.remove(cursor.x, line.length);
				lines.add(cursor.y+1, new P4TextLine(remainder));
				//cursor.down(0);
			}
			crOnly = true;
		}
		if (!crOnly){
		for (int i = 0; i < newLines.length; i++) {
			String thisLine = newLines[i];
		//	p.println("This line="+thisLine+".  lines.size="+lines.size());
			if (!thisLine.isEmpty()){
				if(atLoc.y+i==lines.size()){
				//	p.println("adding new line for line "+i+", thisLine="+thisLine);
					lines.add(new P4TextLine());
				}
				P4TextLine line = lines.get(atLoc.y+i);
			if(i==0){ //firstline
			//	p.println("adding first line which is "+thisLine+" lines.size="+lines.size());
				line.add(thisLine, atLoc.x);
			} else if (i == newLines.length-1){ //last line
				//p.println("adding last line which is "+thisLine);
				line.add(thisLine, 0);
			} else { // middle line
				//p.println("adding new line with text "+thisLine);
				lines.add(atLoc.y+i,new P4TextLine(thisLine));
			}
			} else { //just cr
				//p.println("I think this line is just a cr.  thisLine="+thisLine);
				lines.add(new P4TextLine());
			}
		}
		}
		calcTargetScale();
		cursor.blinkOn();
		resetFade();
		//p.println("END!! text is "+getText()+" lines.size="+lines.size());
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
				//System.out.println("CR!");
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
		cursor.blinkOn();
		resetFade();
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
