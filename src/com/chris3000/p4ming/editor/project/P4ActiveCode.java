package com.chris3000.p4ming.editor.project;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.Caret;
import javax.swing.text.JTextComponent;
public class P4ActiveCode {
	public JTextComponent component=null;
	public int dot=0;
	public int mark=0;
	public int componentIndex =0; 
	public final static byte TEXT_FIELD=10;
	public final static byte TEXT_AREA=20;
	public final static byte UNKNOWN=0;
	public byte type = UNKNOWN;
	public P4ActiveCode(JTextComponent component, int index){
		this.component = component;
		componentIndex = index;
		if (this.component instanceof javax.swing.JTextField) {
			type=TEXT_FIELD;
		} else if (this.component instanceof com.chris3000.p4ming.editor.project.P4Class) {
			type=TEXT_AREA;
		}
		dot = this.component.getCaret().getDot();
		mark=dot = this.component.getCaret().getMark();
	}
	
	public String getText(){
		return component.getText();
	}
	
	public void setCaretPosition(){
		if (dot==mark){
			component.setCaretPosition(dot);
		} else {
			component.setSelectionStart(mark);
			component.setSelectionEnd(dot);
			component.getCaret().setSelectionVisible(true);
		}
		component.validate();
		component.repaint();
	}
	
	public int caretLocation(){
		return dot;
	}
	
	public int getDot(){
		return dot;
	}
	
	public int getMark(){
		return mark;
	}
}
