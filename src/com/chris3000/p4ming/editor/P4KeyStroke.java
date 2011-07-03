package com.chris3000.p4ming.editor;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

public class P4KeyStroke {
	static final public KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);  //  @jve:decl-index=0:
	static final public KeyStroke altEnter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, ActionEvent.ALT_MASK);  //  @jve:decl-index=0:
	static final public KeyStroke altLeft = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, ActionEvent.ALT_MASK);  //  @jve:decl-index=0:
	static final public KeyStroke altRight = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, ActionEvent.ALT_MASK);  //  @jve:decl-index=0:
	static final public KeyStroke cmdI = KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.META_MASK);  //  @jve:decl-index=0:
//code container selection
	static final public KeyStroke cmd1 = KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.META_MASK);
	static final public KeyStroke cmd2 = KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.META_MASK);
	static final public KeyStroke cmd3 = KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.META_MASK);
	static final public KeyStroke cmd4 = KeyStroke.getKeyStroke(KeyEvent.VK_4, ActionEvent.META_MASK);
	static final public KeyStroke cmd5 = KeyStroke.getKeyStroke(KeyEvent.VK_5, ActionEvent.META_MASK);
	static final public KeyStroke cmd6 = KeyStroke.getKeyStroke(KeyEvent.VK_6, ActionEvent.META_MASK);
	static final public KeyStroke cmd7 = KeyStroke.getKeyStroke(KeyEvent.VK_7, ActionEvent.META_MASK);
	static final public KeyStroke cmd8 = KeyStroke.getKeyStroke(KeyEvent.VK_8, ActionEvent.META_MASK);
	static final public KeyStroke cmd9 = KeyStroke.getKeyStroke(KeyEvent.VK_9, ActionEvent.META_MASK);
	
	static public KeyStroke getCmdNumberKey(int number){
		switch(number){
		case 1: return cmd1;
		case 2: return cmd2;
		case 3: return cmd3;
		case 4: return cmd4;
		case 5: return cmd5;
		case 6: return cmd6;
		case 7: return cmd7;
		case 8: return cmd8;
		case 9: return cmd9;
		default: return null;
		}
	}
}
