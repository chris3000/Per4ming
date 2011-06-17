package com.chris3000.p4ming.viewer;
/*ridiculous hack to make Groovy respect float values :(
Although, I gotta say, operator overloading is pretty cool.
*/
public class Operators {
	
	public static float plus(Number a, Number b){
		return a.floatValue()+b.floatValue();
	}
	
	public static float multiply(Number a, Number b){
		return a.floatValue()*b.floatValue();
	}
	
	public static float div(Number a, Number b){
		return a.floatValue()/b.floatValue();
	}
	
	public static float minus(Number a, Number b){
		return a.floatValue()-b.floatValue();
	}
	
	public static float mod(Number a, Number b){
		return a.floatValue()%b.floatValue();
	}

}
