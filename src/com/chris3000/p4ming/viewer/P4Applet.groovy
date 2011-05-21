package com.chris3000.p4ming.viewer

import com.chris3000.p4ming.editor.P4Message
import com.chris3000.p4ming.viewer.text.P4Text;
import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.sun.tools.jdi.JDWP.StackFrame.ThisObject;

import java.awt.Point
import processing.core.PApplet
import processing.core.PImage

class P4Applet extends PApplet{
	def queue = [] as LinkedList
	//holder for added properties.  Kind of a hack?  rather use metaclass.
	private def internal_properties = Collections.synchronizedMap([:])
	P4Applet p = null;
	//visual text
	P4Text p4text = null;
	boolean showText = true;
	//error assets
	PImage errorSign;
	
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
	
	def enableText = { boolean enabled ->
		showText = enabled;	
	}
	
	synchronized void addMethod (P4Message message) {
		println("adding message ${message.toString()}")
		queue.add message
		//println("queue size:"+queue.size)
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
		image(errorSign, (width/2)-(errorSign.width/2), (height/2)-(errorSign.height/2));
		p4text.setErrorMessage(e.toString());
	}
	
	def evaluate = { String clos ->
		GroovyShell shell = new GroovyShell();
		Closure received = shell.evaluate(clos)
		received.delegate=this;
		return received;
	}

	def propEvaluate = { String clos ->
		GroovyShell shell = new GroovyShell();
		def received = shell.evaluate("{->${clos}}");
		received.delegate=this;
		return received.call()
	}
	
	def makeMethod = {String name, String content ->
		this.metaClass."${name}" = evaluate(content)
		println(content)
	}

	def makeProperty = {name, value ->
		this."${name}" = propEvaluate(value);
		println(name+"="+value);
	}

	def runOnce = {value ->
		GroovyShell shell = new GroovyShell();
		def received = shell.evaluate("{->${value}}");
		received.delegate=this;
		received.call()
	}
	
	def p4KeyPressed = { char c ->
		p4text.add(c);
	}
	
	public void caretEvent(Point dot){
	p4text.caretEvent(dot)
	p4text.selectionOff();
}
	public void caretEvent(Point dot, Point mark){
		p4text.setSelection (dot, mark);
		println(dot.toString()+"  "+mark.toString())
	}
	
	public void draw () {
		//println(queue.size())
		if (!queue.isEmpty()){
			try {
			P4Message internal_message = queue.remove()
			//println("got message ${internal_message.toString()}");
			//byte type = internal_message.type;
			//println(""+type+": "+internal_message.getTypeString());
			switch(internal_message.type){
				case P4Message.METHOD: makeMethod(internal_message.name, internal_message.value); break;
				case P4Message.PROPERTY: makeProperty(internal_message.name, internal_message.value); break;
				case P4Message.RUN_ONCE: runOnce(internal_message.value); break;
				//default: makeMethod(internal_message.name, internal_message.value); break;
				
			}
			} catch (Exception e){
				p4text.setErrorMessage(e.toString());
				e.printStackTrace();
			}
			
		}
		internal_gdraw();
		if (showText){
			p4text.calc();
			p4text.render();
		}
	}

	def propertyMissing(String internal_name, internal_value)  { 
		internal_properties[internal_name] = internal_value
	}
	
		def propertyMissing(String internal_name) { 
			internal_properties[internal_name]
		}
	
	public	void setup () {
		errorSign = loadImage("internal_assets/error_x.png");
		gsetup()
		initText();
		p=this;
		p4aInit = true;
	}
}
