package com.chris3000.p4ming.viewer

import com.chris3000.p4ming.editor.P4Message
import com.chris3000.p4ming.viewer.text.P4Text;

import ddf.minim.AudioInput;
import ddf.minim.Minim;

import java.awt.Point
import processing.core.PApplet
import processing.core.PImage

class P4Applet extends PApplet{
	float phase=0f;
	private int activeCode = 1;
	//using binding for added properties.  Kind of a hack?  rather use metaclass.
	Binding binding = new Binding([:]);
	GroovyShell shell = new GroovyShell(binding);
	GroovyClassLoader gcl = new GroovyClassLoader();
	def queue = [] as LinkedList;
	//private def internal_properties = Collections.synchronizedMap([:]);
	private def internal_classes=Collections.synchronizedMap([:]);
	private int internal_framerate = 10;
	private float[] internal_bg = new float[3];
	private boolean internal_opengl=false;
	private int matrixStackDepth; //check to make sure that matrix depth is 0 before rendering text
	P4Applet p = null;
	//audio
	Minim minim;
	String audioInputName;
	boolean audioEnabled;
	float audioLevel=0;
	//AudioInput aud;
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


	public P4Applet(int w, int h, int frameRate, float[] bgColor, boolean openGL){
		p4width = w;
		p4height = h;
		internal_framerate = frameRate;
		internal_bg = bgColor;
		internal_opengl=openGL;
	}

	def enableText = { boolean enabled ->
		showText = enabled;
	}

	public void initAudio(String lineInName, String chanType, int bufferSize, float sampleRate, int bitDepth){
		minim = new Minim(this);
		int type = Minim.STEREO;
		if (chanType.equals( "Mono")){
			type = ddf.minim.Minim.MONO;
		}
		this."${lineInName}" = minim.getLineIn(type, bufferSize, sampleRate, bitDepth);
		audioInputName=lineInName;
		audioEnabled = true;
		println("audio initialized with line out name "+lineInName);
	}

	synchronized void addMethod (P4Message message) {
		//println("adding message ${message.toString()}")
		queue.add message
		//println("queue size:"+queue.size)
	}

	def gsetup = {
		// queue.add( "ellipse(200,200,random(20),random(10));")
		if (internal_opengl){
			size(p4width,p4height, OPENGL);
		} else {
			size(p4width,p4height, P3D);
		}
		background(internal_bg[0],internal_bg[1],internal_bg[2]);
		frameRate(internal_framerate);
		//make draw for 1-9 code containers
		for (int i = 1; i < 10; i++){
			addCodeContainer(i);
		}
		// makeMethod("override","ellipse(10,10,10,random(50));");
	}

	private void initText(){
		P4Text.p = p;
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
		try{
			"draw${activeCode}"();
		}catch (Exception e){
			internal_drawError(e);
		}
	}

	/*	def draw1 = {
	 background(100);
	 }*/

	def internal_drawError = { Exception e ->
		image(errorSign, (width/2)-(errorSign.width/2), (height/2)-(errorSign.height/2));
		p4text.setErrorMessage(e.toString());
	}

	def evaluate = { String clos ->
		//GroovyShell shell = new GroovyShell();
		Closure received = shell.evaluate(clos)
		received.delegate=this;
		return received;
	}

	def propEvaluate = { String clos ->
		//GroovyShell shell = new GroovyShell();
		Closure received = shell.evaluate("{->${clos}}");
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

	def makeClass = {className, classText->
		Class clz = gcl.parseClass(classText);
		String simpleName=clz.getSimpleName();
		clz.p = p;
		internal_classes.put( clz.getSimpleName(), clz)
		this.metaClass."new${simpleName}"= {Object[] args, String key="${simpleName}"->
			Class clz2 =internal_classes.get(key);
			clz2.newInstance(args)
		}
		println("new class ="+clz.getSimpleName());
	}

	def runOnce = {value ->
		//GroovyShell shell = new GroovyShell();
		Closure received = shell.evaluate("{->${value}}");
		received.delegate=this;
		received.call()
	}

	public void p4KeyPressed (char c ){
		p4text.add(c);
	}

	public void removeText(Point fromLoc, int amount){
		p4text.removeText(fromLoc, amount);
	}

	public void addText(Point atLoc,String text){
		p4text.addText(atLoc, text);
	}

	public void caretEvent(Point dot){
		p4text.caretEvent(dot)
		p4text.selectionOff();
	}

	public void caretEvent(Point dot, Point mark){
		p4text.setSelection (dot, mark);
		//println(dot.toString()+"  "+mark.toString())
	}

	/*	public void setup(){
	 size(800,600,P3D);
	 frameRate(30);
	 noStroke();
	 p4aInit=true;
	 }
	 public void draw() {
	 background(0);
	 //code goes here
	 for (int y = 0; y < 100; y++) {
	 for (int x = 0; x < 100; x++) {
	 ellipse(x*8,y*6,random(20),random(10));
	 //pushMatrix();
	 }
	 }
	 println((int)frameRate);
	 }*/

	public void draw () {
		//println(queue.size())
		while (!queue.isEmpty()){
			try {
				P4Message internal_message = queue.remove()
				//println("got message ${internal_message.toString()}");
				//byte type = internal_message.type;
				//println(""+type+": "+internal_message.getTypeString());
				activeCode = internal_message.codeID;
				switch(internal_message.type){
					case P4Message.METHOD: makeMethod(internal_message.name, internal_message.value); break;
					case P4Message.CLASS: makeClass(internal_message.name, internal_message.value); break;
					case P4Message.PROPERTY: makeProperty(internal_message.name, internal_message.value); break;
					case P4Message.RUN_ONCE: runOnce(internal_message.value); break;
					//default: makeMethod(internal_message.name, internal_message.value); break;

				}
			} catch (Exception e){
				p4text.setErrorMessage(e.toString());
				e.printStackTrace();
			}

		}
		//audio stuff
		if (audioEnabled){
			audioLevel=this."${audioInputName}".mix.level();
		}
		internal_gdraw();
		checkMatrix();
		if (showText){
			try{
				p4text.calc();
				p4text.render();
			} catch (java.lang.RuntimeException e1){
				String int_msg = e1.getMessage();
				println(int_msg);
				if (int_msg.endsWith ("pushMatrix().")){
					println("running popMatrix() 32 times");
					32.times {
						popMatrix();
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	def propertyMissing(String internal_name, internal_value)  {
		//internal_properties[internal_name] = internal_value
		binding.setVariable (internal_name, internal_value);
	}

	def propertyMissing(String internal_name) {
		//internal_properties[internal_name]
		return binding.getVariable (internal_name);
	}

	public void stop(){
		super;
		if (audioEnabled){
			minim.stop();
		}
	}

	public void setup () {
		p=this;
		errorSign = loadImage("internal_assets/error_x.png");
		gsetup()
		initText();
		sphereDetail(10); //start with less detail
		//binding.setMetaClass(this.getMetaClass());
		p4aInit = true;
	}

	public void addCodeContainer(int id){
		makeMethod("draw"+id, "{-> \nbackground(100);\n}");
	}
	
	public void changeCodeID(int id){
		if (id > 0 && id < 10){
			activeCode = id;
		}
	}
	
	private void checkMatrix(){
		if(matrixStackDepth != 0){
			println("matrix is off by "+matrixStackDepth);
			if (matrixStackDepth > 0){//too many pushMatrix
				matrixStackDepth.abs().times {
					if (recorder != null) recorder.popMatrix();
					matrixStackDepth--;
				}
			} else {
				matrixStackDepth.abs().times { //too many popMatrix
					if (recorder != null) recorder.pushMatrix();
					matrixStackDepth++;
				}
			}
		}
	}
	//********* fix the double bug!
	/*	
	 public float random(Double num){
	 return (float)random(num);
	 }
	 public float random(){
	 return random(1f);
	 }
	 public void fill(Double r, Double g, Double b){
	 fill((float)r, (float)g, (float)b);
	 }
	 public void fill(Double r, Double g, Double b, Double a){
	 fill((float)r, (float)g, (float)b, (float)a);
	 }
	 public void stroke(Double r, Double g, Double b){
	 stroke((float)r, (float)g, (float)b);
	 }
	 public void stroke(Double r, Double g, Double b, Double a){
	 stroke((float)r, (float)g, (float)b, (float)a);
	 }
	 public void rect(Double xx, Double xy, Double w, Double h){
	 rect((float)xx, (float)xy, (float)w, (float)h);
	 }
	 public void ellipse(Double xx, Double xy, Double w, Double h){
	 ellipse((float)xx, (float)xy, (float)w, (float)h);
	 }
	 public void translate(Double x, Double y, Double z){
	 translate((float)x, (float) y, (float)z);
	 }
	 public void translate(Double x, Double y){
	 translate((float)x, (float) y);
	 }
	 public void scale(Double xy){
	 scale((float)xy);
	 }
	 public void scale(Double x, Double y, Double z){
	 scale((float)x, (float) y, (float)z);
	 }
	 public void rotate(Double z){
	 rotate((float)z);
	 }
	 public void rotate(Double x, Double y, Double z){
	 rotate((float)x, (float) y, (float)z);
	 }*/
}
