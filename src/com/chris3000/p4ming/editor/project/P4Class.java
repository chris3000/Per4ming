package com.chris3000.p4ming.editor.project;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;

public class P4Class extends JTextArea{
	
	private int classLine = 0;
	public boolean isMain = false;
	public boolean checkClassName(boolean force){
		if (isMain){//main must always be called "main"
			return false;
		}
		boolean nameChanged = false;
		Caret caret = this.getCaret();
		try {
			int dotLine = this.getLineOfOffset(caret.getDot());
			int markLine = this.getLineOfOffset(caret.getMark());
			if (force || dotLine <= classLine || markLine <= classLine){
				String code = this.getText();
				// find the current class line
				int classLoc = code.indexOf("class ")+6;
				if (classLoc >= 0){
					classLine = this.getLineOfOffset(classLoc);
				} else {
					//classLine=0;
					return false;  //couldn't find "class".  We're done.
				}
				//check if commented out
				boolean commented = code.substring(this.getLineStartOffset(classLine), classLoc).contains("//");
				if (!commented && (dotLine==classLine || markLine == classLine)){
					int end = code.indexOf(" ", classLoc);
					if (end < 0){
						end = code.indexOf("{", classLoc);
					}
					if (end > 0){
						nameChanged=true;
						this.setName(code.substring(classLoc, end).trim());
					}
				}
			}
		} catch (BadLocationException e) {
			System.out.println("Bad Location Exception checking P4Class name!!!");
			nameChanged = false;
			//e.printStackTrace();
		}
		
		return nameChanged;
	}
	
	public String toString(){
		return this.getName();
	}
}
