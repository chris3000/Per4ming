package com.chris3000.p4ming.viewer

import com.chris3000.p4ming.editor.P4Message;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;

class P4Core {
	static P4Applet p = null;
	//using binding for added properties.  Kind of a hack?  rather use metaclass.
	Binding binding = new Binding([:]);
	GroovyShell shell = new GroovyShell(binding);
	GroovyClassLoader gcl = new GroovyClassLoader();
	def queue = [] as LinkedList;
	private def internal_classes=Collections.synchronizedMap([:]);
	
	synchronized void addMethod (P4Message message) {
		println("adding message ${message.toString()}")
		queue.add message
		//println("queue size:"+queue.size)
	}
	
	def propertyMissing(String internal_name, internal_value)  {
		//internal_properties[internal_name] = internal_value
		binding.setVariable (internal_name, internal_value);
	}

	def propertyMissing(String internal_name) {
		//internal_properties[internal_name]
		def prop = null;
		try {
			prop=binding.getVariable (internal_name);
		} catch (e){
			
		}
		if (prop){
			return prop;
		}
		//try to find it in P4Applet
		return p."${internal_name}";
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
	
/*	def makeClass = {className, classText->
		Class clz = gcl.parseClass(classText);
		String simpleName=clz.getSimpleName();
		internal_classes.put( clz.getSimpleName(), clz)
		this.metaClass."new${simpleName}"= {Object[] args, String key="${simpleName}"->
			Class clz2 =internal_classes.get(key);
			clz2.newInstance(args)
		}
		println("new class ="+clz.getSimpleName());
	}*/
	
	def runOnce = {value ->
		//GroovyShell shell = new GroovyShell();
		Closure received = shell.evaluate("{->${value}}");
		received.delegate=this;
		received.call()
	}
}
