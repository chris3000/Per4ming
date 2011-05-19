package com.chris3000.p4ming.viewer.text.example;

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
		frameRate(60);
	}
	
	public void draw(){
		//perspective();
		background(100,10,10);
		stroke(2);
		for(int i = 0; i < 500; i++){
			fill(random(255),random(255),random(255));
			pushMatrix();
			translate(random(width*2),random(height*2), random(width)-(width/2));
			this.box(40);
			popMatrix();
		}
		p4text.calc();
		p4text.render();
	}

	public void keyPressed(){
		p4text.add(key);
	}
	
	public void mouseClicked(){
		p4text.setText(dummyText);
	}
	
	String dummyText = "	for(int i = 0; i < 3000; i++){\n		fill(random(255),random(255),random(255));\n		" +
			"pushMatrix();\n		translate(random(width*2),random(height*2), random(width)-(width/2));\n		" +
			"this.box(40);\n		popMatrix();\n	}";
}
