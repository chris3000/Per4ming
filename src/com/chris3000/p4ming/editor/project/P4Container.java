package com.chris3000.p4ming.editor.project;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import com.chris3000.p4ming.editor.P4Editor;
import com.chris3000.p4ming.editor.P4KeyStroke;




public class P4Container extends JSplitPane {
	public int id = -1;
	P4Editor parent = null;
	//JTextField activePropField = null;
	String propFieldPreviousText = null;
	public ArrayList<String> runOncePreviousText = new ArrayList<String>();  //  @jve:decl-index=0:
	//P4Class activeTextArea = null;
	P4ActiveCode activeCode=null;
	ArrayList<String> properties = new ArrayList<String>();
	ArrayList<String> runOnce = new ArrayList<String>();
	ArrayList<P4Class> classes = new ArrayList<P4Class>();
	JPanel classPanel;
	JList classList;
	JSplitPane projectPane;
	JButton classButton;
	JScrollPane editorPane;
	JPanel propsPanel;
	JScrollPane propsFieldContainer;
	JButton addPropButton;
	JPanel jPanel;
	private String activeText = null;  //  @jve:decl-index=0:
	public P4Container(P4Editor p4e, int _id){
		super();
		parent = p4e;
		id= _id;
		initialize();
		//init();
	}
	//NEVER USE THIS!!
	public P4Container(){
		super();
		initialize();
		//init();
	}
	
	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		setContinuousLayout(true);
		this.setSize(new Dimension(400, 400));
		setBackground(new Color(102, 102, 102));
        this.setLeftComponent(getClassPanel());
        this.setRightComponent(getProjectPane());
		//this.setDividerLocation(100);
	}
	
	public String getActiveText(){
		return activeText;
	}
	
	public void setActiveText(String txt){
		activeText = txt;
	}
	public void submitRunOnce(String code){
		parent.submitRunOnce(code, id);
	}
	
	public void caretEvent(Point dotLoc,Point markLoc){
		parent.caretEvent(dotLoc, markLoc);
	}
	
	public void caretEvent(Point dotLoc){
		parent.caretEvent(dotLoc);
	}
	
	public void addText(Point addLoc, String changedText){
		parent.addText(addLoc, changedText);
	}
	
	public void removeText(Point fromLoc,int amount){
		parent.removeText(fromLoc, amount);
	}
	
	public void getCurrentText(String text){
		parent.getCurrentText(text);
	}
/*	private void init(){
		setLeftComponent(getClassPanel());
		setRightComponent(getProjectPane());
	}*/
	
	private JPanel getClassPanel() {
		if (classPanel == null){
		GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
		gridBagConstraints8.gridx = 0;
		gridBagConstraints8.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints8.anchor = GridBagConstraints.SOUTH;
		gridBagConstraints8.gridy = 1;
		GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
		gridBagConstraints7.fill = GridBagConstraints.BOTH;
		gridBagConstraints7.weighty = 1.0;
		gridBagConstraints7.weightx = 1.0;
		classPanel = new JPanel();
		classPanel.setLayout(new GridBagLayout());
		classPanel.setBackground(new Color(51, 51, 51));
		classPanel.add(getClassList(), gridBagConstraints7);
		classPanel.add(getClassButton(), gridBagConstraints8);
		}
		return classPanel;
	}
	/**
	 * This method initializes projectPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getProjectPane() {
		if (projectPane == null) {
			projectPane = new JSplitPane();
			projectPane.setBackground(new Color(102, 102, 102));
			projectPane.setContinuousLayout(true);
			projectPane.setResizeWeight(1.0D);
			projectPane.setLeftComponent(getEditorPane());
			projectPane.setRightComponent(getPropsPanel());
		}
		return projectPane;
	}
	
	private JPanel getPropsPanel() {
		if (propsPanel == null) {
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints6.gridy = 1;
			gridBagConstraints6.ipadx = 0;
			gridBagConstraints6.fill = GridBagConstraints.BOTH;
			gridBagConstraints6.anchor = GridBagConstraints.SOUTH;
			gridBagConstraints6.gridx = 0;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.fill = GridBagConstraints.BOTH;
			gridBagConstraints5.gridy = 0;
			gridBagConstraints5.ipadx = 116;
			gridBagConstraints5.ipady = 156;
			gridBagConstraints5.weightx = 1.0;
			gridBagConstraints5.weighty = 1.0;
			gridBagConstraints5.gridx = 0;
			propsPanel = new JPanel();
			propsPanel.setLayout(new GridBagLayout());
			propsPanel.setPreferredSize(new Dimension(50, 39));
			propsPanel.setBackground(new Color(51, 51, 51));
			propsPanel.add(getPropsFieldContainer(), gridBagConstraints5);
			propsPanel.add(getAddPropButton(), gridBagConstraints6);
		}
		return propsPanel;
	}
	
	private JButton getAddPropButton() {
		if (addPropButton == null) {
			addPropButton = new JButton();
			addPropButton.setVerticalAlignment(SwingConstants.CENTER);
			addPropButton.setHorizontalTextPosition(SwingConstants.CENTER);
			addPropButton.setName("addPropButton");
			addPropButton.setBorderPainted(false);
			addPropButton.setPreferredSize(new Dimension(200, 29));
			addPropButton.setComponentOrientation(ComponentOrientation.UNKNOWN);
			addPropButton.setBackground(new Color(51, 51, 51));
			addPropButton.setIcon(new ImageIcon(getClass().getResource("/editor/new_property_button.png")));
			addPropButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					jPanel.add(getJTextField(), null);
					jPanel.validate();
					jPanel.repaint();
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				}
			});
		}
		return addPropButton;
	}
	
	private JTextField getJTextField() {
		JTextField jTextField = new JTextField(2);
		jTextField.setHorizontalAlignment(JTextField.LEFT);
		jTextField.setPreferredSize(new Dimension(10, 28));
		jTextField.setMaximumSize(new Dimension(900, 28));
		jTextField.addCaretListener(new CaretLoc(P4ActiveCode.TEXT_FIELD));
		jTextField.addFocusListener(new FocusListen(jTextField.getDocument()));
		jTextField.getDocument().addDocumentListener(new DocListener());
		jTextField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				String propFieldText=activeCode.getText();
				parent.submitProperty(propFieldText, id);
				propFieldPreviousText=propFieldText;
				//System.out.println("Action performed!!");
			}
		});
		jTextField.setCaretPosition(0);
		return jTextField;
	}
	
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BoxLayout(getJPanel(), BoxLayout.Y_AXIS));
			jPanel.setBackground(new Color(51, 51, 51));
			jPanel.add(getJTextField(), null);
		}
		return jPanel;
	}
	
	private JScrollPane getPropsFieldContainer() {
		if (propsFieldContainer == null) {
			propsFieldContainer = new JScrollPane();
			propsFieldContainer.setViewportView(getJPanel());
		}
		return propsFieldContainer;
	}
	
	private JScrollPane getEditorPane() {
		if (editorPane == null) {
			editorPane = new JScrollPane();
			editorPane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			editorPane.setViewportView((P4Class)activeCode.component);
		}
		return editorPane;
	}
	
	private JList getClassList() {
		if (classList == null) {
			DefaultListModel classListModel = new DefaultListModel();
			//classListModel.setSize(1);
			P4Class activeTextArea = createClass("main");
			activeTextArea.setCaretPosition(0);
			activeCode = new P4ActiveCode(activeTextArea, 0);
			classListModel.addElement(activeTextArea);
			classList = new JList(classListModel);
			//classList.set
			classList.setPreferredSize(new Dimension(80, 80));
			classList.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			classList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			classList.setFont(new Font("Monaco", Font.PLAIN, 12));
			classList.setSelectedIndex(0);
			classList.setModel(classListModel);
			classList.setBackground(new Color(237, 243, 254));
			classList
			.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
				public void valueChanged(javax.swing.event.ListSelectionEvent e) {
					if (classList.getSelectedIndex()>-1){
						P4Class p4c = (P4Class) classList.getSelectedValue();
						activeCode = new P4ActiveCode(p4c, classList.getSelectedIndex());
						setEditorPaneView(p4c);
						parent.getCurrentText(p4c.getText());
						System.out.println("valueChanged() "+System.currentTimeMillis()); // TODO Auto-generated Event stub valueChanged()
					}
				}
			});
		}
		return classList;
	}
	
	private void setEditorPaneView(P4Class textArea) {
		editorPane.setViewportView(textArea);
		editorPane.revalidate();
		editorPane.repaint();
	}
	
	
	private P4Class createClass(String name) {
		P4Class editorTextArea = new P4Class();
		//editorTextArea = new JEditTextArea(new processing.app.syntax.TextAreaDefaults());
		editorTextArea.setMargin(new Insets(2,6,2,6));
		editorTextArea.setFont(new Font("Monaco", Font.PLAIN, 12));
		editorTextArea.setTabSize(3);
		editorTextArea.setWrapStyleWord(true);
		if (name != null){
			if (name.equals("main")){
				editorTextArea.isMain = true;
				if (id==1){
					editorTextArea.setText("def draw"+id+" = {->\n\t//code goes here\n\tbackground(0)\n\t//ellipse(400,100,20,20)\n}");
				} else {
					editorTextArea.setText("def draw"+id+" = {->\n\t\n}");
				}
			} else {
				editorTextArea.setText("import com.chris3000.p4ming.viewer.P4Core;\n\nclass "+name+" extends P4Core {\n\t//code goes here\n}\n");
				editorTextArea.checkClassName(true);
			}
			editorTextArea.setName(name);
		}
		editorTextArea.addCaretListener(new CaretLoc(P4ActiveCode.TEXT_AREA));
		editorTextArea.addFocusListener(new FocusListen(editorTextArea.getDocument()));
		editorTextArea.getDocument().addDocumentListener(new DocListener());
		//editorTextArea.addKeyListener(new CodeKeyListener());
		editorTextArea.getInputMap().put(P4KeyStroke.enter, "enter");
		editorTextArea.getActionMap().put("enter", new EnterAction(editorTextArea));
		editorTextArea.getInputMap().put(P4KeyStroke.altEnter, "submitText");
		editorTextArea.getActionMap().put("submitText", new SubmitTextAction(editorTextArea));
		editorTextArea.getInputMap().put(P4KeyStroke.cmdI, "indent");
		editorTextArea.getActionMap().put("indent", new IndentTextAction(editorTextArea));
		editorTextArea.requestFocus();
		activeText = editorTextArea.getText();
		return editorTextArea;
	}
	
	/**
	 * This method initializes classButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getClassButton() {
		if (classButton == null) {
			classButton = new JButton();
			classButton.setBorderPainted(false);
			classButton.setIcon(new ImageIcon(getClass().getResource("/editor/new_class_button.png")));
			classButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DefaultListModel listModel = (DefaultListModel)classList.getModel();
					int index = listModel.getSize();
					P4Class activeTextArea = createClass("MyClass"+index);
					activeTextArea.setCaretPosition(0);
					activeCode = new P4ActiveCode(activeTextArea, index);
					listModel.addElement(activeTextArea);
					classList.setSelectedIndex(index);
					classList.revalidate();
					classList.repaint();
					setEditorPaneView(activeTextArea);
					System.out.println("class active code is from class index "+activeCode.componentIndex); // TODO Auto-generated Event stub focusGained()
				}
			});
		}
		return classButton;
	}
	
	public void updateActiveCode(){
		if (activeCode != null){
			if (activeCode.type == P4ActiveCode.TEXT_AREA){
				classList.setSelectedIndex(activeCode.componentIndex);
				activeCode.component.requestFocus();
				activeCode.setCaretPosition();
				caretEvent(activeCode.component, activeCode.type, activeCode.dot, activeCode.mark);
				System.out.println("resetting active code to class index "+activeCode.componentIndex);
			} else if (activeCode.type == P4ActiveCode.TEXT_FIELD){
				jPanel.getComponent(activeCode.componentIndex).requestFocus();
				activeCode.setCaretPosition();
				System.out.println("resetting active code to property index "+activeCode.componentIndex);
			}
		} else {
			System.out.println("active code is null in updateActiveCode()");
		}
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

	public Point getDocXYLoc(P4ActiveCode code, int offset){
		Point loc = new Point();
		if (code.type == P4ActiveCode.TEXT_AREA){
			JTextArea textArea = (JTextArea) code.component;
			try {
				loc.y = textArea.getLineOfOffset(offset);
				loc.x = offset-textArea.getLineStartOffset(loc.y);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (code.type== P4ActiveCode.TEXT_FIELD){
			JTextField textField = (JTextField) code.component;
			loc.y=0;
			loc.x = textField.getCaretPosition();
		}
		return loc;
	}
	
	class CaretLoc implements CaretListener {
		
		byte textType = P4ActiveCode.UNKNOWN;
		
		public CaretLoc(byte type){
			textType=type;
		}
		
		public void caretUpdate(CaretEvent caretEvent) {
			System.out.println("CARET UPDATE!!!");
			Object textObject = caretEvent.getSource();
			int dot = caretEvent.getDot();
			int mark = caretEvent.getMark();
			if (activeCode != null){
				activeCode.dot = dot;
				activeCode.mark=mark;
			}else {
				System.out.println("active code is null in caretUpdate()");
			}
			//System.out.println(""+dot+","+mark);
			caretEvent(textObject,textType, dot, mark);

			// System.out.println("Dot: "+ caretEvent.getDot()+" Mark: "+caretEvent.getMark());

		}
	}
	
	class FocusListen implements java.awt.event.FocusListener{

		Document doc = null;
		public FocusListen(Document doc){
			this.doc = doc;
		}
		public void focusGained(java.awt.event.FocusEvent e) {
			if (e.getSource() instanceof JTextField){
				JTextField activeProperty = (JTextField) e.getSource();
				int index = 0;
				Component[] comps = jPanel.getComponents();
				for (int i = 0; i < comps.length; i++) {
					Component c = comps[i];
					if (c.equals(activeProperty)){
						index = i;
						System.out.println("active property index = "+index);
						break;
					}
				}
				activeCode = new P4ActiveCode(activeProperty, index);
			activeText = activeCode.getText();
			System.out.println("active code is from property index "+activeCode.componentIndex); // TODO Auto-generated Event stub focusGained()
			} else if (e.getSource() instanceof P4Class){
				P4Class activeClass = (P4Class)e.getSource();
				int index = classList.getSelectedIndex();
				if (index < 0){
					for (int i =0; i < classList.getModel().getSize(); i++){
						Object obj = classList.getModel().getElementAt(i);
						if (obj.equals(activeClass)){
							index = i;
							System.out.println("active class index = "+index);
							break;
						}
					}
				}
				activeCode = new P4ActiveCode(activeClass, index);
				System.out.println("active code is from class index "+activeCode.componentIndex); // TODO Auto-generated Event stub focusGained()

			}
			//System.out.println("focusGained()"); // TODO Auto-generated Event stub focusGained()
			try {
				String txt = doc.getText(doc.getStartPosition().getOffset(), doc.getEndPosition().getOffset());
				parent.getCurrentText(txt);
				activeText = txt;
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		@Override
		public void focusLost(FocusEvent e) {
			if (e.getSource() instanceof JTextField){
			String propFieldText = activeCode.getText();
			if (!propFieldText.equals(propFieldPreviousText)){
				parent.submitProperty(activeCode.getText(), id);
			}
			//activePropField = null;
			propFieldPreviousText=propFieldText;
			//System.out.println("focusLost()"); // TODO Auto-generated Event stub focusLost()
			}
		}

	}
	class DocListener implements DocumentListener {
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
				Point addLoc = getDocXYLoc(activeCode,e.getOffset());
				checkClassName(e.getLength());
				parent.addText(addLoc, changedText);
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			//System.out.println("length="+e.getLength()+", offset="+e.getOffset()+", type="+e.getType());
			Point removeLoc = null;
			if (activeCode.type == P4ActiveCode.TEXT_AREA){
				removeLoc = getDocXYLoc(activeCode,e.getOffset());
			} else {
				removeLoc = new Point(e.getOffset(),0);
			}
			checkClassName(e.getLength());
			System.out.println("removing from loc "+removeLoc.toString());
			parent.removeText(removeLoc,e.getLength());
		}	
	}
	
	private void checkClassName(int textLength) {
		if (activeCode.type==P4ActiveCode.TEXT_AREA){
			boolean force = false;
			if (textLength>1){
				force = true;
			}
			P4Class p4c = (P4Class)activeCode.component;
			boolean nameChanged = p4c.checkClassName(force);
			if (nameChanged){
				System.out.println("name changed to "+p4c.getName());
				//DefaultListModel listModel = (DefaultListModel)classList.getModel();
				//listModel.indexOf(activeTextArea);
				classList.revalidate();
				classList.repaint();
			}
		}
	}
	
	private void caretEvent(Object textObject, byte textType, int dot, int mark) {
		Point dotLoc = null;
		if (textType == P4ActiveCode.TEXT_AREA){
			dotLoc = getDocXYLoc((P4Class)textObject,dot);
		} else if (textType==P4ActiveCode.TEXT_FIELD){
			dotLoc = new Point(dot,0);
		}
		if(mark != dot){ //selection
			Point markLoc = null;
			if (textType==P4ActiveCode.TEXT_AREA){
				markLoc= getDocXYLoc((P4Class)textObject,mark);
			}else if (textType==P4ActiveCode.TEXT_FIELD){
				markLoc = new Point(mark,0);
			}
			parent.caretEvent(dotLoc,markLoc);
		} else { 
			parent.caretEvent(dotLoc);
		}
	}

	class EnterAction extends AbstractAction {
		P4Class textArea;
		final static private char TAB = '\t';
		final static private char ENTER = '\n';		
		public EnterAction(P4Class _textArea){
			textArea = _textArea;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			StringBuilder sb = new StringBuilder();
			boolean injectCR=true;
			JTextComponent c = (JTextComponent) e.getSource();
			int caretPos = c.getCaretPosition();

			if (caretPos > 1){
				try {
					//how many tabs to insert?
					int curlyCount=getLeftCurlyOffset(c.getText(0, caretPos));
					for (int i = 0; i < curlyCount; i++) {
						sb.append(TAB);
					}
					Document doc =c.getDocument();
					//remove cr?
					//doc.remove(caretPos-1, 1);
					String lastChar = doc.getText(caretPos-1, 1);
					//System.out.println("ENTER- last char was "+lastChar);
					if (lastChar.equals("{")){
						//check to see if it's a class
						int line = ((JTextArea) c).getLineOfOffset(caretPos);
						int lineOffset = (caretPos)-((JTextArea) c).getLineStartOffset(line);
						String lineStr = c.getText(((JTextArea) c).getLineStartOffset(line), lineOffset);
						System.out.println("caretPos="+caretPos+" line="+line+" lineOffset="+lineOffset+" lineStr="+lineStr);
						if (lineStr.contains("class")|| lineStr.contains(".times") || lineStr.contains(".each") || lineStr.contains("if")){
							sb.insert(0," \n");
							sb.append("\n");
							for (int i = 0; i < (curlyCount-1); i++) {
								sb.append(TAB);
							}
							sb.append("}");
							doc.insertString(caretPos, sb.toString(), null); //" \n\t\n}"
							//Point addLoc = getDocXYLoc(textArea,c.getCaretPosition());
							//p4m.addText(addLoc, "\n");
							parent.getCurrentText(textArea.getText()); //hack!  god I hate this.
							injectCR=false;
						} else {
							sb.insert(0," ->\n");
							sb.append("\n");
							for (int i = 0; i < (curlyCount-1); i++) {
								sb.append(TAB);
							}
							sb.append("}");
							doc.insertString(caretPos, sb.toString(), null); //" ->\n\t\n}"
							parent.getCurrentText(textArea.getText()); //hack!  god I hate this.
								//Point addLoc = getDocXYLoc(textArea,c.getCaretPosition());
								//p4m.addText(addLoc, "\n");
							injectCR=false;
						}
						c.setCaretPosition(c.getCaretPosition()-(curlyCount-1)-2);
					}
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}
			
			if (injectCR){
				try {
					sb.insert(0,ENTER);
					c.getDocument().insertString(caretPos, sb.toString(), null);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				parent.getCurrentText(textArea.getText()); //hack!  god I hate this.
				//System.out.println("injectCR");
			}
		}
		private int getLeftCurlyOffset(String text){
			int curlyCount=0;
			char leftCurly='{';
			char rightCurly='}';
			char[] chars = text.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				if (chars[i]==leftCurly){
					curlyCount++;
				} else if (chars[i]==rightCurly){
					curlyCount--;
				}
			}
			if (curlyCount < 0){
				curlyCount=0;
			}
			return curlyCount;
		}
	}
	
	class SubmitTextAction extends AbstractAction {

		P4Class textArea;
		public SubmitTextAction(P4Class _textArea){
			textArea = _textArea;
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(textArea.toString().equals("main")){
				parent.submitMethods(textArea.getText(), id);
			} else {
				parent.submitClass(textArea.getText(), id);
			}
			System.out.println("actionPerformed on text area name "+textArea.toString()); // TODO Auto-generated Event stub actionPerformed()
		}

	}
	class IndentTextAction extends AbstractAction {

		P4Class textArea;
		final static private char leftCurly='{';
		final static private char rightCurly='}';
		final static private char TAB = '\t';
		final static private char ENTER = '\n';	
		public IndentTextAction(P4Class _textArea){
			textArea = _textArea;
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			int selectBegin = textArea.getSelectionStart();
			int selectEnd = textArea.getSelectionEnd();
			if (selectBegin == selectEnd){
				textArea.selectAll();
				selectBegin = textArea.getSelectionStart();
				selectEnd = textArea.getSelectionEnd();
			}
			String selectedText = textArea.getSelectedText();
			char[] chars = selectedText.toCharArray();
			if (chars == null){
				chars = new char[0];
			}
			int curlyCount = 0;
			int startLineBegin = 0;
			try {
				int startLine = textArea.getLineOfOffset(selectBegin);
				startLineBegin = textArea.getLineStartOffset(startLine);
				curlyCount = getLeftCurlyOffset(textArea.getText(0,selectBegin));
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			boolean atBeginning = false;
			if (startLineBegin == selectBegin){
				atBeginning=true;
			}
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < chars.length; i++) {
				curlyCount += checkCurly(chars[i]);
				if (atBeginning && !Character.isWhitespace(chars[i])){
					for (int j = 0; j < curlyCount; j++) {
						sb.append(TAB);
					}
					atBeginning = false;
				}
				if (!atBeginning){
					if (chars[i]==ENTER){
						atBeginning = true;
					}
					sb.append(chars[i]);
				}
			}
			textArea.replaceSelection(sb.toString());
			//int curlyCount = getLeftCurlyCount(textArea.getText())
			System.out.println("indent actionPerformed on text area name "+textArea.toString()); // TODO Auto-generated Event stub actionPerformed()
		}
		
		private int checkCurly(char c){
			if (c==leftCurly){
				return 1;
			} else if (c==rightCurly){
				return -1;
			} else {
				return 0;
			}
		}
		private int getLeftCurlyOffset(String text){
			int curlyCount=0;
			char[] chars = text.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				if (chars[i]==leftCurly){
					curlyCount++;
				} else if (chars[i]==rightCurly){
					curlyCount--;
				}
			}
			if (curlyCount < 0){
				curlyCount=0;
			}
			return curlyCount;
		}
	}
}
