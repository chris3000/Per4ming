package com.chris3000.p4ming.viewer.text;

public class P4Cursor {
	public int x = 0;
	public int y = 0;
	public boolean cursorBlinking = true;
	private boolean cursorVisible = true;
	private long blinkRate = 750;
	private long lastBlink = System.currentTimeMillis();
	
	
	public void setBlinkRate(long millis){
		blinkRate = millis;
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