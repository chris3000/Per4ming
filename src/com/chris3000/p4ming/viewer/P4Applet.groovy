package com.chris3000.p4ming.viewer

import com.chris3000.p4ming.editor.P4Message
import com.chris3000.p4ming.viewer.text.P4Text;
import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.sun.tools.jdi.JDWP.StackFrame.ThisObject;

import java.awt.Point
import processing.core.PApplet

class P4Applet extends PApplet{
	def queue = [] as LinkedList
	//visual text
	P4Text p4text = null;
	
	int p4width = 100;
	int p4height = 100;
	//editor states
	int currentEditor = 1;
	
	//are we done with initialization?
	boolean p4aInit = false;
	
	public P4Applet(int w, int h){
		p4width = w;
		p4height = h;
	}
	
	synchronized void addMethod (P4Message message) {
		println("adding message ${message.toString()}")
		queue.add message
		println("queue size:"+queue.size)
	}

	def gsetup = {
		// queue.add( "ellipse(200,200,random(20),random(10));")
		size(p4width,p4height, OPENGL);
		//background(0);
		frameRate(30);
		// makeMethod("override","ellipse(10,10,10,random(50));");
	}

	private void initText(){
		P4Text.p = this;
		p4text = new P4Text();
		p4text.init();
	}
	
	public void setText(String text){
		if (p4text != null){
			p4text.setText(text);
			//println(text);
		}
	}
	
	public boolean isInit(){
		return p4aInit;
	}
	
	def internal_gdraw = {
		switch(currentEditor){
			case 1: try{draw1();}catch (Exception e){internal_drawError(e);}; break;
		}
		/*pushMatrix();
		translate(100,200);
		box(40);
		popMatrix();
		try {
			override();
			//override2();
		} catch (Exception e){
			//println("!");
		}*/
	}
	
	def draw1 = {
		background(100);
	}
	
	def internal_drawError = { Exception e ->
		println("error!"+ e.toString())
	}
	
	def evaluate = { String clos ->
		GroovyShell shell = new GroovyShell();
		Closure received = shell.evaluate(clos)
		received.delegate=this;
		return received;
	}

	def makeMethod = {String name, String content ->
		this.metaClass."${name}" = evaluate(content)
		println(content)
		//delegate.setMetaClass(emc);
	}

	def p4KeyPressed = { char c ->
		p4text.add(c);
	}
	
	public void caretEvent(Point dot){
	p4text.caretEvent(dot)
}
	public void caretEvent(Point dot, Point mark){
		//determine cursor location
	}
	public void draw () {
		//println(queue.size())
		if (!queue.isEmpty()){
			P4Message internal_message = queue.remove()
			println("got message ${internal_message.toString()}");
			//byte type = internal_message.type;
			//println(""+type+": "+internal_message.getTypeString());
			switch(internal_message.type){
				case P4Message.METHOD: makeMethod(internal_message.name, internal_message.value); break;
				//default: makeMethod(internal_message.name, internal_message.value); break;
				
			}
			
		}
		internal_gdraw()
		p4text.calc();
		p4text.render();
	}

	public	void setup () {
		gsetup()
		initText();
		p4aInit = true;
	}
}
