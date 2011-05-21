package com.chris3000.p4ming.viewer.text;

import java.awt.Point;

public class P4Cursor {
	public int x = 0;
	public int y = 0;
	public boolean cursorBlinking = true;
	private boolean cursorVisible = true;
	private long blinkRate = 750;
	private long lastBlink = System.currentTimeMillis();
	/**selection stuff*/
	private boolean selected = false;  //are we selected?
	private Point dot= null;
	private Point mark = null;
	
	public void setBlinkRate(long millis){
		blinkRate = millis;
	}
	
	public void selectOn(Point _dot, Point _mark){
		selected = true;
		dot = _dot;
		mark = _mark;
	}
	
	public void selectOff(){
		selected = false;
	}
	
	public boolean isSelected(){
		return selected;
	}
	
	public Point[] getSelectRange(){
		Point[] points = new Point[2];
		boolean dotMark=true;
		if (dot.y > mark.y){
			dotMark = false;
		} else if (dot.y == mark.y && dot.x > mark.x){
			dotMark=false;
		}
		if (dotMark){
			points[0]=dot;
			points[1]=mark;
		} else {
			points[0]=mark;
			points[1]=dot;
		}
		return points;
	}
	
	public void calcBlink(){
		if (cursorBlinking){
			long timeSinceLastBlink = System.currentTimeMillis() - lastBlink;
			if (timeSinceLastBlink > blinkRate){
				lastBlink = System.currentTimeMillis();
				cursorVisible = !cursorVisible;
			}
		}
	}
	
	public void setLocation(int locX, int locY){
		x = locX;
		y = locY;
	}
	
	public void blinkOn(){
		lastBlink = System.currentTimeMillis();
		cursorVisible = true;
	}
	
	public void enableBlink(){
		cursorVisible = true;
		cursorBlinking = true;
	}
	
	public void disableBlink(){
		cursorVisible = true;
		cursorBlinking = false;
	}
	
	public boolean isVisible(){
		return cursorVisible;
	}
	
	public void up(int upMax){
		y--;
		if (y < 0) {
			y=0;
		} else if (x > upMax){
			x = upMax;
		}
	}
	
	public void left(int upMax){
		x--;
		if (x < 0 && y > 0){
			x = 99999;
			up(upMax);
		} else if (x < 0 && y == 0 ){
			x = 0;
		}
	}
	
	public void right(int lineMax){
		x++;
		if (x > lineMax) {
			down(0);		
		}
	}
	
	public void down(int downMax){
		y++;
		if (x > downMax){
			x = downMax;
		}
	}
}