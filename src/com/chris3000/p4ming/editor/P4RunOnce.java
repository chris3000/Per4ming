package com.chris3000.p4ming.editor;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;


import com.chris3000.p4ming.editor.project.P4Class;
import com.chris3000.p4ming.editor.project.P4Container;

public class P4RunOnce extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;
	private JPanel jPanel = null;
	private JButton submitButton = null;
	private JButton Next = null;
	private JButton Previous = null;
	private P4Container parent = null;
	private JTextArea jTextArea = null;
	private int codeIndex = 0;
	private int codeIndexLimit=0;
	//ArrayList<String> runOncePreviousText = new ArrayList<String>();  //  @jve:decl-index=0:
	/**
	 * This is the default constructor
	 */
	public P4RunOnce(P4Container p) {
		super();
		parent = p;
		codeIndex = parent.runOncePreviousText.size();
		codeIndexLimit=codeIndex;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setContentPane(getJContentPane());
		this.setTitle("Run Once");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
			jContentPane.add(getJPanel(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTextArea());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 0;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 2;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 1;
			gridBagConstraints.gridy = 0;
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.add(getSubmitButton(), gridBagConstraints1);
			jPanel.add(getNext(), gridBagConstraints);
			jPanel.add(getPrevious(), gridBagConstraints2);
		}
		return jPanel;
	}

	/**
	 * This method initializes submitButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getSubmitButton() {
		if (submitButton == null) {
			submitButton = new JButton();
			submitButton.setText("Submit");
			submitButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String code = jTextArea.getText();
					upsert(code);
					//remove all blank code
					for(int i = parent.runOncePreviousText.size()-1; i >=0; i--){
						String lastIndex=parent.runOncePreviousText.get(i);
						if (lastIndex.isEmpty()){
							parent.runOncePreviousText.remove(i);
						}
					}
					parent.submitRunOnce(code);
					dispose();
					//System.out.println("actionPerformed()"); // 
				}
			});
		}
		return submitButton;
	}
	private void upsert(String code){
		if (codeIndex < codeIndexLimit){
			System.out.println("inserting "+code+" at index "+(codeIndex));
			parent.runOncePreviousText.set(codeIndex, code);
		} else {
			if (codeIndex==codeIndexLimit){
				System.out.println("adding "+code);
				parent.runOncePreviousText.add(code);
			}
		}
	}
	/**
	 * This method initializes Next	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getNext() {
		if (Next == null) {
			Next = new JButton();
			Next.setText("Next");
			Next.addActionListener(new NextAction());
		}
		return Next;
	}

	/**
	 * This method initializes Previous	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getPrevious() {
		if (Previous == null) {
			Previous = new JButton();
			Previous.setText("Previous");
			Previous.addActionListener(new PreviousAction());
		}
		return Previous;
	}

	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setMargin(new Insets(2,6,2,6));
			jTextArea.setFont(new Font("Monaco", Font.PLAIN, 12));
			jTextArea.setTabSize(3);
			jTextArea.setWrapStyleWord(true);
			jTextArea.setTabSize(3);
			jTextArea.addCaretListener(new CaretLoc());
			jTextArea.addFocusListener(new FocusListen(jTextArea.getDocument()));
			jTextArea.getDocument().addDocumentListener(new DocListener());
			jTextArea.getInputMap().put(P4KeyStroke.enter, "enter");
			jTextArea.getActionMap().put("enter", new EnterAction(jTextArea));
			jTextArea.getInputMap().put(P4KeyStroke.altEnter, "submitText");
			jTextArea.getActionMap().put("submitText", new SubmitTextAction(jTextArea));
			jTextArea.getInputMap().put(P4KeyStroke.altLeft, "previous");
			jTextArea.getActionMap().put("previous", new PreviousAction());
			jTextArea.getInputMap().put(P4KeyStroke.altRight, "next");
			jTextArea.getActionMap().put("next", new NextAction());
			//jTextArea.addKeyListener(new CodeKeyListener());
		}
		return jTextArea;
	}
	
	public Point getDocXYLoc(JTextArea textArea, int offset){
		Point loc = new Point();
		try {
			loc.y = textArea.getLineOfOffset(offset);
			loc.x = offset-textArea.getLineStartOffset(loc.y);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loc;
	}
	
	class CaretLoc implements CaretListener {

		public void caretUpdate(CaretEvent caretEvent) {
			int dot = caretEvent.getDot();
			int mark = caretEvent.getMark();
			System.out.println(""+dot+","+mark);
			Point dotLoc = getDocXYLoc(jTextArea,dot);
			if(mark != dot){ //selection
				Point markLoc = getDocXYLoc(jTextArea,mark);
				parent.caretEvent(dotLoc,markLoc);
			} else { 
				parent.caretEvent(dotLoc);
			}

			// System.out.println("Dot: "+ caretEvent.getDot()+" Mark: "+caretEvent.getMark());

		}
	}

	class DocListener implements DocumentListener {
		@Override
		public void changedUpdate(DocumentEvent e) {
			System.out.println("doc change!"+e.toString());
			System.out.println("length="+e.getLength()+", offset="+e.getOffset()+", type="+e.getType());
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			//System.out.println("doc insert!"+e.toString());
			//System.out.println("length="+e.getLength()+", offset="+e.getOffset()+", type="+e.getType());
			String changedText = null;
			try {
				changedText = e.getDocument().getText(e.getOffset(), e.getLength());
				Point addLoc = getDocXYLoc(jTextArea,e.getOffset());
				parent.addText(addLoc, changedText);
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			//System.out.println("length="+e.getLength()+", offset="+e.getOffset()+", type="+e.getType());
			Point removeLoc = getDocXYLoc(jTextArea,e.getOffset());
			parent.removeText(removeLoc,e.getLength());
		}	
	}

	class FocusListen implements java.awt.event.FocusListener{

		Document doc = null;
		public FocusListen(Document doc){
			this.doc = doc;
		}
		public void focusGained(java.awt.event.FocusEvent e) {
			System.out.println("focusGained()"); // TODO Auto-generated Event stub focusGained()
			try {
				parent.getCurrentText(doc.getText(doc.getStartPosition().getOffset(), doc.getEndPosition().getOffset()));
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub

		}

	}
	
	class EnterAction extends AbstractAction {
		JTextArea textArea;
		public EnterAction(JTextArea _jTextArea){
			textArea = _jTextArea;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			boolean injectCR=true;
			JTextComponent c = (JTextComponent) e.getSource();
			int caretPos = c.getCaretPosition();
			
			if (caretPos > 1){
				try {
				Document doc =c.getDocument();
				//remove cr?
				//doc.remove(caretPos-1, 1);
					String lastChar = doc.getText(caretPos-1, 1);
					System.out.println("ENTER- last char was "+lastChar);
					if (lastChar.equals("{")){
						//check to see if it's a class
						int line = ((JTextArea) c).getLineOfOffset(caretPos);
						int lineOffset = (caretPos)-((JTextArea) c).getLineStartOffset(line);
						String lineStr = c.getText(((JTextArea) c).getLineStartOffset(line), lineOffset);
						System.out.println("caretPos="+caretPos+" line="+line+" lineOffset="+lineOffset+" lineStr="+lineStr);
						if (lineStr.contains("class")|| lineStr.contains(".times") || lineStr.contains(".each")){
							doc.insertString(caretPos, " \n\t\n}", null);
							//Point addLoc = getDocXYLoc(textArea,c.getCaretPosition());
							//p4m.addText(addLoc, "\n");
							parent.getCurrentText(textArea.getText()); //hack!  god I hate this.
							injectCR=false;
						} else {
							doc.insertString(caretPos, " ->\n\t\n}", null);
							parent.getCurrentText(textArea.getText()); //hack!  god I hate this.
								//Point addLoc = getDocXYLoc(textArea,c.getCaretPosition());
								//p4m.addText(addLoc, "\n");
							injectCR=false;
						}
						c.setCaretPosition(c.getCaretPosition()-2);
					}
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}
			
			if (injectCR){
				try {
					c.getDocument().insertString(caretPos, "\n", null);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("injectCR");
			}
		}
		
	}
	
	class CodeKeyListener extends KeyAdapter {
		public void keyTyped(KeyEvent evt) {
			JTextComponent c = (JTextComponent) evt.getSource();
			char ch = evt.getKeyChar();
			try {
				if (ch == '\n' || ch == '\t') {
					int caretPos = c.getCaretPosition();
					if (caretPos > 1){
						Document doc =c.getDocument();
						if(ch=='\n'){
							String lastChar = doc.getText(caretPos-2, 1);
							System.out.println("ENTER- last char was "+lastChar);
							if (lastChar.equals("{")){
								//check to see if it's a class
								int line = ((JTextArea) c).getLineOfOffset(caretPos-1);
								int lineOffset = (caretPos-1)-((JTextArea) c).getLineStartOffset(line);
								String lineStr = c.getText(((JTextArea) c).getLineStartOffset(line), lineOffset);
								System.out.println("caretPos="+caretPos+" line="+line+" lineOffset="+lineOffset+" lineStr="+lineStr);
								if (lineStr.contains("class")|| lineStr.contains(".times") || lineStr.contains(".each")){
									doc.insertString(caretPos-1, "\n\t\n}", null);
								} else {
									doc.insertString(caretPos-1, " ->\n\t\n}", null);
								}
							}
							c.setCaretPosition(c.getCaretPosition()-3);
							evt.consume();
						}
					}
				} 


			}catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
	}
	
	class PreviousAction extends AbstractAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (codeIndex>0){
				String code = jTextArea.getText();
				upsert(code);
				codeIndex--;
				jTextArea.setText(parent.runOncePreviousText.get(codeIndex));
			}

			System.out.println("actionPerformed(previous, new index="+codeIndex+")"); // TODO Auto-generated Event stub actionPerformed()
		}
		
	}
	class NextAction extends AbstractAction implements ActionListener{
		public void actionPerformed(java.awt.event.ActionEvent e) {
			String code = jTextArea.getText();
			upsert(code);
			if (codeIndex<codeIndexLimit){
				codeIndex++;
				try{
				jTextArea.setText(parent.runOncePreviousText.get(codeIndex));
				} catch (java.lang.IndexOutOfBoundsException e1){
					codeIndex--;
					System.out.println("Out Of Bounds from Next button... caught.");
				}
			}
			System.out.println("actionPerformed(next new index="+codeIndex+")"); // TODO Auto-generated Event stub actionPerformed()
		}
	}
	class SubmitTextAction extends AbstractAction {

		JTextArea textArea;
		public SubmitTextAction(JTextArea _textArea){
			textArea = _textArea;
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String code = jTextArea.getText();
			upsert(code);
			//remove all blank code
			for(int i = parent.runOncePreviousText.size()-1; i >=0; i--){
				String lastIndex=parent.runOncePreviousText.get(i);
				if (lastIndex.isEmpty()){
					parent.runOncePreviousText.remove(i);
				}
			}
			parent.submitRunOnce(code);
			dispose();
			//System.out.println("actionPerformed()"); // 			System.out.println("actionPerformed on text area name "+textArea.toString()); // TODO Auto-generated Event stub actionPerformed()
		}

	}
}  //  @jve:decl-index=0:visual-constraint="73,33"
