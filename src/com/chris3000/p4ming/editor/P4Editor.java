package com.chris3000.p4ming.editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Insets;
import java.awt.Point;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.KeyListener;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import com.chris3000.p4ming.P4Ming;
import com.chris3000.p4ming.P4Prefs;
import com.chris3000.p4ming.editor.project.P4Class;
import com.chris3000.p4ming.editor.project.P4Container;
import com.chris3000.p4ming.util.ReadWriteFile;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.Font;
import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.gjt.sp.jedit.Buffer;
import org.gjt.sp.jedit.View;
import javax.swing.JList;
import javax.swing.JTabbedPane;
import java.awt.Color;
import java.awt.ComponentOrientation;
import javax.swing.ListSelectionModel;
import javax.swing.JSplitPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.GridLayout;

public class P4Editor extends JFrame{
	private final static int TAB_MAX = 9;
	/*	JTextField activePropField = null;
	String propFieldPreviousText = null;*/
	/*	ArrayList<String> runOncePreviousText = new ArrayList<String>();  //  @jve:decl-index=0:
	P4Class activeTextArea = null;*/
	private P4Prefs p4p = new P4Prefs(); //  @jve:decl-index=0:
	private P4Editor p4e = null;  //this instance
	private KeyStroke cmdN = KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.META_MASK);
	private KeyStroke cmdS = KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.META_MASK);  //  @jve:decl-index=0:
	private KeyStroke cmdShiftS = KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.META_MASK+ActionEvent.SHIFT_MASK);
	private KeyStroke cmdO = KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.META_MASK);
	private KeyStroke cmdQ = KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.META_MASK);

	/*	private KeyStroke cmdI = KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.META_MASK);  //  @jve:decl-index=0:
	private KeyStroke altEnter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, ActionEvent.ALT_MASK);  //  @jve:decl-index=0:
	private KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);  //  @jve:decl-index=0:
	 */	private boolean started = false;
	 private static final long serialVersionUID = 1L;
	 private JPanel jContentPane = null;
	 private JScrollPane editorPane = null;
	 //private P4Class editorTextArea = null;
	 private JPanel controlPanel = null;
	 private JButton startStopButton = null;
	 private JButton submitButton = null;
	 //parent class
	 P4Ming p4m;  //  @jve:decl-index=0:
	 private JMenuBar jmenuBar = null;
	 private JMenu File = null;
	 private JMenu Options = null;
	 private JMenuItem submitMethod = null;
	 private JMenuItem preferences = null;
	 protected P4PrefsPane p4Prefs;
	 private JTabbedPane tabPane = null;
	 //private JList classList = null;
	 //private JSplitPane splitPane1 = null;
	 private JMenuItem newClass = null;
	 private JSplitPane projectPane = null;
	 private JPanel propsPanel = null;
	 private JButton addPropButton = null;
	 private JScrollPane propsFieldContainer = null;
	 private JPanel jPanel = null;
	 private JTextField jTextField = null;
	 //private JPanel classPanel = null;
	 //private JButton classButton = null;
	 private JButton runOnceWindow = null;
	 private JButton addContainerButton = null;
	private JMenuItem saveMenuItem = null;
	private JMenuItem openMenuItem = null;
	private JMenuItem newMenuItem = null;
	private JMenuItem saveAsMenuItem = null;
	private JMenuItem quitMenuItem = null;
	private final static String NEW_PROJECT = "<new project>";  //  @jve:decl-index=0:
	private String projectName = NEW_PROJECT;  //  @jve:decl-index=0:
	private File projectDir = null;
	/**
	  * This is the default constructor
	  */
	 public P4Editor(P4Ming _p4m) {
		 super();
		 p4m = _p4m;
		 initialize();
	 }

/*	 public void setParent(){
		 p4m= _p4m;
	 }*/
	 public void setStop(){
		 startStopButton.setIcon(new ImageIcon(getClass().getResource("/editor/start_button.png")));
		 started = false;
	 }

	 public void setStart(){
		 startStopButton.setIcon(new ImageIcon(getClass().getResource("/editor/stop_button.png")));
		 started = true;
	 }

	 /*	public P4Editor(P4Ming _p4m) {
		super();
		p4m = _p4m;
		initialize();
	}*/

	 /**
	  * This method initializes this
	  * 
	  * @return void
	  */
	 private void initialize() {
		 p4e = this;
		 //this.setSize(p4p.editorSize[0], p4p.editorSize[1]);
		 this.setSize(650,470);
		 this.setLocation(p4p.editorLoc[0], p4p.editorLoc[1]);
		 this.setBackground(new Color(51, 51, 51));
		 this.setJMenuBar(getJmenuBar());
		 this.setContentPane(getJContentPane());
		 setProjectName(NEW_PROJECT);
		 this.addWindowListener(new java.awt.event.WindowAdapter() { 
			 public void windowClosing(WindowEvent e) {    
				 //System.out.println("windowClosing()"); // 
				 System.exit(0);
			 }
		 });
		 this.addComponentListener(new java.awt.event.ComponentAdapter() {
			 public void componentMoved(java.awt.event.ComponentEvent e) {
				 //System.out.println("componentMoved()"); // 
				 JFrame self = getSelf();
				 Point screenLoc = self.getLocationOnScreen();
				 p4p.editorLoc[0] = screenLoc.x;
				 p4p.editorLoc[1] =screenLoc.y;
				 p4p.editorSize[0] = self.getSize().width;
				 p4p.editorSize[1] =self.getSize().height;
				 p4p.savePrefs();
			 }
		 });
	 }
	 private JFrame getSelf(){
		 return this;
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
			 jContentPane.add(getControlPanel(), BorderLayout.NORTH);
			 jContentPane.add(getTabPane(), BorderLayout.CENTER);
		 }
		 return jContentPane;
	 }

	 private void createKeyBinding(int num){
		 jContentPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(P4KeyStroke.getCmdNumberKey(num), "alt"+num);
		 jContentPane.getActionMap().put("alt"+num, new CodeChangeAction(num));
	 }

	 /**
	  * This method initializes editorPane	
	  * 	
	  * @return javax.swing.JScrollPane	
	  */
	 /*	private JScrollPane getEditorPane() {
		if (editorPane == null) {
			editorPane = new JScrollPane();
			editorPane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			editorPane.setViewportView(activeTextArea);
		}
		return editorPane;
	}*/

	 /**
	  * This method initializes editorTextArea	
	  * 	
	  * @return javax.swing.JTextArea	
	  */
	 /*	private P4Class createClass(String name) {
		P4Class editorTextArea = new P4Class();
		//editorTextArea = new JEditTextArea(new processing.app.syntax.TextAreaDefaults());
		editorTextArea.setMargin(new Insets(2,6,2,6));
		editorTextArea.setFont(new Font("Monaco", Font.PLAIN, 12));
		editorTextArea.setTabSize(3);
		editorTextArea.setWrapStyleWord(true);
		if (name != null){
			if (name.equals("main")){
				editorTextArea.isMain = true;
				editorTextArea.setText("def draw1 = {->\n\t//code goes here\n\tbackground(0)\n\t//ellipse(400,100,20,20)\n}");
			} else {
				editorTextArea.setText("import com.chris3000.p4ming.viewer.P4Core;\n\nclass "+name+" extends P4Core {\n\t//code goes here\n}\n");
				editorTextArea.checkClassName(true);
			}
			editorTextArea.setName(name);
		}
		editorTextArea.addCaretListener(new CaretLoc(CaretLoc.AREA));
		editorTextArea.addFocusListener(new FocusListen(editorTextArea.getDocument()));
		editorTextArea.getDocument().addDocumentListener(new DocListener());
		//editorTextArea.addKeyListener(new CodeKeyListener());
		editorTextArea.getInputMap().put(enter, "enter");
		editorTextArea.getActionMap().put("enter", new EnterAction(editorTextArea));
		editorTextArea.getInputMap().put(altEnter, "submitText");
		editorTextArea.getActionMap().put("submitText", new SubmitTextAction(editorTextArea));
		editorTextArea.getInputMap().put(cmdI, "indent");
		editorTextArea.getActionMap().put("indent", new IndentTextAction(editorTextArea));
		return editorTextArea;
	}*/

	 public void caretEvent(Point dot){
		 p4m.caretEvent(dot);
	 }

	 public void caretEvent(Point dot, Point mark){
		 p4m.caretEvent(dot, mark);
	 }

	 public void addText(Point addLoc, String changedText){
		 p4m.addText(addLoc, changedText);
	 }

	 public void removeText(Point fromLoc, int amount){
		 p4m.removeText(fromLoc, amount);
	 }

	 public Point getDocXYLoc(JTextArea textArea, int offset){
		 Point loc = new Point();
		 try {
			 loc.y = textArea.getLineOfOffset(offset);
			 loc.x = offset-textArea.getLineStartOffset(loc.y);
		 } catch (BadLocationException e) {
			 e.printStackTrace();
		 }
		 return loc;
	 }

	 	public void getCurrentText(){
		p4m.setText(getActiveContainer().getActiveText());
	}

	 public void getCurrentText(String text){
		 p4m.setText(text);
	 }


	 /**
	  * This method initializes controlPanel	
	  * 	
	  * @return javax.swing.JPanel	
	  */
	 private JPanel getControlPanel() {
		 if (controlPanel == null) {
			 GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			 gridBagConstraints12.gridx = 2;
			 gridBagConstraints12.gridy = 0;
			 GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			 gridBagConstraints11.gridx = 3;
			 gridBagConstraints11.weightx = 1.0D;
			 gridBagConstraints11.anchor = GridBagConstraints.EAST;
			 gridBagConstraints11.gridy = 0;
			 GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			 gridBagConstraints1.anchor = GridBagConstraints.WEST;
			 gridBagConstraints1.weightx = 0.0D;
			 gridBagConstraints1.gridheight = 2;
			 gridBagConstraints1.ipady = 6;
			 gridBagConstraints1.ipadx = 6;
			 gridBagConstraints1.fill = GridBagConstraints.NONE;
			 GridBagConstraints gridBagConstraints = new GridBagConstraints();
			 gridBagConstraints.anchor = GridBagConstraints.EAST;
			 gridBagConstraints.ipady = 6;
			 gridBagConstraints.weightx = 0.0;
			 controlPanel = new JPanel();
			 controlPanel.setLayout(new GridBagLayout());
			 controlPanel.setBackground(new Color(51, 51, 51));
			 controlPanel.add(getStartStopButton(), gridBagConstraints);
			 controlPanel.add(getSubmitButton(), gridBagConstraints1);
			 controlPanel.add(getRunOnceWindow(), gridBagConstraints11);
			 controlPanel.add(getAddContainerButton(), gridBagConstraints12);
		 }
		 return controlPanel;
	 }

	 /**
	  * This method initializes startStopButton	
	  * 	
	  * @return javax.swing.JButton	
	  */
	 private JButton getStartStopButton() {
		 if (startStopButton == null) {
			 startStopButton = new JButton();
			 startStopButton.setText("");
			 startStopButton.setIcon(new ImageIcon(getClass().getResource("/editor/start_button.png")));
			 startStopButton.setPreferredSize(new Dimension(60, 20));
			 startStopButton.setBorderPainted(false);
		 }
		 startStopButton.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 if (!started){
					 startStopButton.setIcon(new ImageIcon(getClass().getResource("/editor/stop_button.png")));
					 //p4m.startViewer(w, h);
					 p4m.startViewer(p4p);
					 started = true;
				 } else {
					 startStopButton.setIcon(new ImageIcon(getClass().getResource("/editor/start_button.png")));
					 started = false;
					 p4m.editorStop();
				 }
				 //jtfInput.setText("Button 2!");
			 }
		 });
		 return startStopButton;
	 }

	 /**
	  * This method initializes submitButton	
	  * 	
	  * @return javax.swing.JButton	
	  */
	 private JButton getSubmitButton() {
		 if (submitButton == null) {
			 submitButton = new JButton();
			 //submitButton.setText("Submit");
			 submitButton.setBorderPainted(false);
			 submitButton.setPreferredSize(new Dimension(80, 20));
			 submitButton.setIcon(new ImageIcon(getClass().getResource("/editor/submit_button.png")));
		 }
		 submitButton.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 //MAKE THIS WORK WITH MULTIPLE CONTAINERS
				 //submitMethods(activeTextArea.getText());
			 }
		 });
		 return submitButton;
	 }

	 public P4Container getActiveContainer(){
		 P4Container p4c = (P4Container)tabPane.getSelectedComponent();
		 return p4c;
	 }

	 public void submitRunOnce(String text, int codeID) {
		 if (started){
			 P4Message p4mess = new P4Message(null, text, P4Message.RUN_ONCE, codeID);
			 p4m.addMethod(p4mess);
		 }
	 }

	 public void submitProperty(String text, int codeID) {
		 if (started){
			 //String text = propertyField.getText();
			 String[] nameValue = text.split("=");
			 if(nameValue.length == 2){
				 P4Message p4mess = null;
				 if (nameValue[0].contains(".")){ //it's setting an instance property.
					 submitRunOnce(text, codeID);
				 } else {
					 p4mess = new P4Message(nameValue[0].trim(), nameValue[1].trim(), P4Message.PROPERTY, codeID);
				 }
				 p4m.addMethod(p4mess);
			 } else if (nameValue.length == 1){
				 submitRunOnce(nameValue[0].trim(), codeID);
			 }
		 }
	 }

	 /**
	  * This method initializes jmenuBar	
	  * 	
	  * @return javax.swing.JMenuBar	
	  */
	 private JMenuBar getJmenuBar() {
		 if (jmenuBar == null) {
			 jmenuBar = new JMenuBar();
			 jmenuBar.add(getFile());
			 jmenuBar.add(getOptions());
		 }
		 return jmenuBar;
	 }

	 /**
	  * This method initializes File	
	  * 	
	  * @return javax.swing.JMenu	
	  */
	 private JMenu getFile() {
		 if (File == null) {
			 File = new JMenu();
			 File.setName("File");
			 File.setText("File");
			 File.add(getNewMenuItem());
			 File.addSeparator();
			 File.add(getOpenMenuItem());
			 File.addSeparator();
			 File.add(getSaveMenuItem());
			 File.add(getSaveAsMenuItem());
			 File.add(getQuitMenuItem());
			 File.addSeparator();
		 }
		 return File;
	 }

	 /**
	  * This method initializes Options	
	  * 	
	  * @return javax.swing.JMenu	
	  */
	 private JMenu getOptions() {
		 if (Options == null) {
			 Options = new JMenu();
			 Options.setText("Options");
			 Options.add(getSubmitMethod());
			 Options.add(getPreferences());
			 Options.add(getNewClass());
		 }
		 return Options;
	 }

	 /**
	  * This method initializes submitMethod	
	  * 	
	  * @return javax.swing.JMenuItem	
	  */
	 private JMenuItem getSubmitMethod() {
		 if (submitMethod == null) {
			 submitMethod = new JMenuItem();
			 submitMethod.setText("Submit Method");
			 //submitMethod.setAccelerator(ctrlEnter);
			 submitMethod.addActionListener(new java.awt.event.ActionListener() {
				 public void actionPerformed(java.awt.event.ActionEvent e) {
					 //FIX THIS LATER
					 /*					if(activeTextArea.toString().equals("main")){
						submitMethods(activeTextArea.getText());
					} else {
						submitClass(activeTextArea.getText());
					}*/
					 System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				 }
			 });
		 }
		 return submitMethod;
	 }

	 public void submitMethods(String methods, int codeID) {
		 if (started){
			 P4Message[] p4mess = parseMethods(methods, codeID);
			 p4m.addMethods(p4mess);
		 }
	 }

	 public void submitClass(String classStr, int codeID) {
		 if (started){
			 p4m.addClass(new P4Message(null, classStr,P4Message.CLASS, codeID));
		 }
	 }

	 private P4Message[] parseMethods(String methods, int codeID){
		 ArrayList<P4Message> p4ms = new ArrayList<P4Message>();
		 P4Message[] p4mArray = null;
		 char[] chars = methods.toCharArray();
		 int begin=0;
		 int end = 0;
		 int braceCount=0;
		 boolean inBlock=false;
		 for (int i = 0; i < chars.length; i++) {
			 if(chars[i]=='{'){
				 braceCount++;
				 if(!inBlock)begin=i;
				 inBlock=true;
			 } else if (chars[i]=='}'){
				 braceCount--;
				 if (braceCount==0){//end of block
					 end=i+1;
					 String methodStr = methods.substring(begin, end);
					 int eqIndex = methods.lastIndexOf("=", begin);
					 int defIndex = methods.lastIndexOf("def", eqIndex)+3;
					 String methodName=methods.substring(defIndex, eqIndex).trim();
					 p4ms.add(new P4Message(methodName, methodStr, P4Message.METHOD, codeID));
					 inBlock=false;
					 //System.out.println("name: "+methodName+" method: "+methodStr);
				 }
			 }
		 }
		 p4mArray = new P4Message[p4ms.size()];
		 p4ms.toArray(p4mArray);
		 //			for (int i = 0; i < p4mArray.length; i++) {System.out.println(p4mArray[i].toString());}
		 return p4mArray;
	 }

	 /**
	  * This method initializes preferences	
	  * 	
	  * @return javax.swing.JMenuItem	
	  */
	 private JMenuItem getPreferences() {
		 if (preferences == null) {
			 preferences = new JMenuItem();
			 preferences.setText("Preferences");
			 preferences.addActionListener(new java.awt.event.ActionListener() {
				 public void actionPerformed(java.awt.event.ActionEvent e) {
					 if (p4Prefs == null || !p4Prefs.isDisplayable()){
						 p4Prefs = new P4PrefsPane(p4p);
						 p4Prefs.setVisible(true);
					 } else {
						 p4Prefs.setFocusable(true);
					 }
					 System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				 }
			 });
		 }
		 return preferences;
	 }

	 /**
	  * This method initializes tabPane	
	  * 	
	  * @return javax.swing.JTabbedPane	
	  */
	 private JTabbedPane getTabPane() {
		 if (tabPane == null) {
			 tabPane = new JTabbedPane();
			 tabPane.setBackground(new Color(51, 51, 51));
			 tabPane.addChangeListener(new javax.swing.event.ChangeListener() {
				 public void stateChanged(javax.swing.event.ChangeEvent e) {
					 P4Container p4c=getActiveContainer();
					 if (p4c != null){
						 p4c.updateActiveCode();
						 getCurrentText(p4c.getActiveText());
					 }
				 }
			 });
			 P4Container p4c = getNewContainer(tabPane.getTabCount()+1);
			 addContainer(p4c);
			// getCurrentText(p4c.getActiveText());
		 }
		 return tabPane;
	 }

	 private void addContainer(P4Container p4c){
		 String tabName = ""+p4c.id;
		 tabPane.addTab(tabName, p4c);
	 }

	 private void clearContainers(){
		 tabPane.removeAll();
	 }
	 
	 private P4Container getNewContainer(int id){
		 P4Container p4c = new P4Container(this, id);
		 createKeyBinding(id);
		 return p4c;
	 }

	 /**
	  * This method initializes classList	
	  * 	
	  * @return javax.swing.JList	
	  */
	 /*	private JList getClassList() {
		if (classList == null) {
			DefaultListModel classListModel = new DefaultListModel();
			//classListModel.setSize(1);
			activeTextArea = createClass("main");
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
						activeTextArea = (P4Class) classList.getSelectedValue();
						setEditorPaneView(activeTextArea);
						getCurrentText();
						System.out.println("valueChanged() "+System.currentTimeMillis()); // TODO Auto-generated Event stub valueChanged()

					}
				}
			});
		}
		return classList;
	}*/

	 /**
	  * This method initializes splitPane1	
	  * 	
	  * @return javax.swing.JSplitPane	
	  */
	 /*	private JSplitPane getNewContainer() {
		JSplitPane splitPane = new JSplitPane();
		splitPane.setContinuousLayout(true);
		splitPane.setBackground(new Color(102, 102, 102));
		splitPane.setLeftComponent(getClassPanel());
		splitPane.setRightComponent(getProjectPane());
		return splitPane;
	}*/

	 /**
	  * This method initializes newClass	
	  * 	
	  * @return javax.swing.JMenuItem	
	  */
	 private JMenuItem getNewClass() {
		 if (newClass == null) {
			 newClass = new JMenuItem();
			 newClass.setText("New Class");
			 newClass.addActionListener(new java.awt.event.ActionListener() {
				 public void actionPerformed(java.awt.event.ActionEvent e) {
					 //FIX THIS LATER BY GRABBING THE CURRENT TAB
					 //DefaultListModel classListModel = (DefaultListModel) classList.getModel();
					 //classListModel.setSize(classListModel.getSize()+1);
					 //classListModel.addElement("New Class");
					 //System.out.println("actionPerformed()");
				 }
			 });
		 }
		 return newClass;
	 }

	 /**
	  * This method initializes projectPane	
	  * 	
	  * @return javax.swing.JSplitPane	
	  */
	 /*	private JSplitPane getProjectPane() {
		if (projectPane == null) {
			projectPane = new JSplitPane();
			projectPane.setBackground(new Color(102, 102, 102));
			projectPane.setContinuousLayout(true);
			projectPane.setResizeWeight(1.0D);
			projectPane.setLeftComponent(getEditorPane());
			projectPane.setRightComponent(getPropsPanel());
		}
		return projectPane;
	}*/

	 /**
	  * This method initializes propsPanel	
	  * 	
	  * @return javax.swing.JPanel	
	  */
	 /*	private JPanel getPropsPanel() {
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
	}*/

	 /**
	  * This method initializes addPropButton	
	  * 	
	  * @return javax.swing.JButton	
	  */
	 /*	private JButton getAddPropButton() {
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
	}*/

	 /**
	  * This method initializes propsFieldContainer	
	  * 	
	  * @return javax.swing.JScrollPane	
	  */
	 /*	private JScrollPane getPropsFieldContainer() {
		if (propsFieldContainer == null) {
			propsFieldContainer = new JScrollPane();
			propsFieldContainer.setViewportView(getJPanel());
		}
		return propsFieldContainer;
	}*/

	 /**
	  * This method initializes jPanel	
	  * 	
	  * @return javax.swing.JPanel	
	  */
	 /*	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BoxLayout(getJPanel(), BoxLayout.Y_AXIS));
			jPanel.setBackground(new Color(51, 51, 51));
			jPanel.add(getJTextField(), null);
		}
		return jPanel;
	}*/

	 /**
	  * This method initializes jTextField	
	  * 	
	  * @return javax.swing.JTextField	
	  */
	 /*	private JTextField getJTextField() {
		JTextField jTextField = new JTextField(2);
		jTextField.setHorizontalAlignment(JTextField.LEFT);
		jTextField.setPreferredSize(new Dimension(10, 28));
		jTextField.setMaximumSize(new Dimension(900, 28));
		jTextField.addCaretListener(new CaretLoc(CaretLoc.FIELD));
		jTextField.addFocusListener(new FocusListen(jTextField.getDocument()));
		jTextField.getDocument().addDocumentListener(new DocListener());
		jTextField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				String propFieldText=activePropField.getText();
				submitProperty(propFieldText);
				propFieldPreviousText=propFieldText;
				//System.out.println("Action performed!!");
			}
		});
		jTextField.addFocusListener(new java.awt.event.FocusAdapter() {   
			public void focusLost(java.awt.event.FocusEvent e) {
				String propFieldText = activePropField.getText();
				if (!propFieldText.equals(propFieldPreviousText)){
					submitProperty(activePropField.getText());
				}
				activePropField = null;
				propFieldPreviousText=propFieldText;
				//System.out.println("focusLost()"); // TODO Auto-generated Event stub focusLost()
			}
			public void focusGained(java.awt.event.FocusEvent e) {
				activePropField = (JTextField)e.getSource();
				//System.out.println("focusGained()"); // TODO Auto-generated Event stub focusGained()
			}
		});
		return jTextField;
	}*/

	 /**
	  * This method initializes classPanel	
	  * 	
	  * @return javax.swing.JPanel	
	  */
	 /*	private JPanel getClassPanel() {
		GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
		gridBagConstraints8.gridx = 0;
		gridBagConstraints8.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints8.anchor = GridBagConstraints.SOUTH;
		gridBagConstraints8.gridy = 1;
		GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
		gridBagConstraints7.fill = GridBagConstraints.BOTH;
		gridBagConstraints7.weighty = 1.0;
		gridBagConstraints7.weightx = 1.0;
		JPanel classPanel = new JPanel();
		classPanel.setLayout(new GridBagLayout());
		classPanel.setBackground(new Color(51, 51, 51));
		classPanel.add(getClassList(), gridBagConstraints7);
		classPanel.add(getClassButton(), gridBagConstraints8);
		return classPanel;
	}*/

	 /**
	  * This method initializes classButton	
	  * 	
	  * @return javax.swing.JButton	
	  */
	 /*	private JButton getClassButton() {
		if (classButton == null) {
			classButton = new JButton();
			classButton.setBorderPainted(false);
			classButton.setIcon(new ImageIcon(getClass().getResource("/editor/new_class_button.png")));
			classButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DefaultListModel listModel = (DefaultListModel)classList.getModel();
					int index = listModel.getSize();
					activeTextArea = createClass("MyClass"+index);
					listModel.addElement(activeTextArea);
					classList.setSelectedIndex(index);
					classList.revalidate();
					classList.repaint();
					setEditorPaneView(activeTextArea);
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				}
			});
		}
		return classButton;
	}
	  */
	 /*	private void setEditorPaneView(P4Class textArea) {
		editorPane.setViewportView(textArea);
		editorPane.revalidate();
		editorPane.repaint();
	}*/

	 /*	private void checkClassName(int textLength) {
		boolean force = false;
		if (textLength>1){
			force = true;
		}
		boolean nameChanged = activeTextArea.checkClassName(force);
		if (nameChanged){
			System.out.println("name changed to "+activeTextArea.getName());
			//DefaultListModel listModel = (DefaultListModel)classList.getModel();
			//listModel.indexOf(activeTextArea);
			classList.revalidate();
			classList.repaint();
		}
	}*/

	 /**
	  * This method initializes runOnceWindow	
	  * 	
	  * @return javax.swing.JButton	
	  */
	 private JButton getRunOnceWindow() {
		 if (runOnceWindow == null) {
			 runOnceWindow = new JButton();
			 runOnceWindow.setIcon(new ImageIcon(getClass().getResource("/editor/run_once_button.png")));
			 runOnceWindow.setBorderPainted(false);
			 runOnceWindow.setPreferredSize(new Dimension(120, 20));
			 runOnceWindow.addActionListener(new java.awt.event.ActionListener() {
				 public void actionPerformed(java.awt.event.ActionEvent e) {
					 p4m.caretEvent(new Point(0,0));
					 P4RunOnce p4ro = new P4RunOnce(getActiveContainer());
					 Dimension d = p4ro.getSize();
					 p4ro.setLocation(p4p.editorLoc[0]+p4p.editorSize[0]/2-d.width/2, p4p.editorLoc[1]+p4p.editorSize[1]/2-d.height/2);
					 p4ro.setVisible(true);
					 //System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				 }
			 });
		 }
		 return runOnceWindow;
	 }


	 /*	class CaretLoc implements CaretListener {

		static final byte FIELD = 10;
		static final byte AREA = 20;
		byte textType = AREA;

		public CaretLoc(byte type){
			textType=type;
		}

		public void caretUpdate(CaretEvent caretEvent) {
			int dot = caretEvent.getDot();
			int mark = caretEvent.getMark();
			//System.out.println(""+dot+","+mark);
			Point dotLoc = null;
			if (textType == AREA){
				dotLoc = getDocXYLoc(activeTextArea,dot);
			} else if (textType==FIELD){
				dotLoc = new Point(dot,0);
			}
			if(mark != dot){ //selection
				Point markLoc = null;
				if (textType==AREA){
					markLoc= getDocXYLoc(activeTextArea,mark);
				}else if (textType==FIELD){
					markLoc = new Point(mark,0);
				}
				p4m.caretEvent(dotLoc,markLoc);
			} else { 
				p4m.caretEvent(dotLoc);
			}

			// System.out.println("Dot: "+ caretEvent.getDot()+" Mark: "+caretEvent.getMark());

		}
	}*/

	 /*	class DocListener implements DocumentListener {
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
				Point addLoc = getDocXYLoc(activeTextArea,e.getOffset());
				checkClassName(e.getLength());
				p4m.addText(addLoc, changedText);
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			//System.out.println("length="+e.getLength()+", offset="+e.getOffset()+", type="+e.getType());
			Point removeLoc = getDocXYLoc(activeTextArea,e.getOffset());
			checkClassName(e.getLength());
			p4m.removeText(removeLoc,e.getLength());
		}	
	}*/

	 class FocusListen implements java.awt.event.FocusListener{

		 Document doc = null;
		 public FocusListen(Document doc){
			 this.doc = doc;
		 }
		 public void focusGained(java.awt.event.FocusEvent e) {
			 //System.out.println("focusGained()"); // TODO Auto-generated Event stub focusGained()
			 try {
				 getCurrentText(doc.getText(doc.getStartPosition().getOffset(), doc.getEndPosition().getOffset()));
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
								 c.setCaretPosition(c.getCaretPosition()-3);
								 //evt.consume();
							 }
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
							 getCurrentText(textArea.getText()); //hack!  god I hate this.
							 injectCR=false;
						 } else {
							 sb.insert(0," ->\n");
							 sb.append("\n");
							 for (int i = 0; i < (curlyCount-1); i++) {
								 sb.append(TAB);
							 }
							 sb.append("}");
							 doc.insertString(caretPos, sb.toString(), null); //" ->\n\t\n}"
							 getCurrentText(textArea.getText()); //hack!  god I hate this.
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
				 getCurrentText(textArea.getText()); //hack!  god I hate this.
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
/*	 class SubmitTextAction extends AbstractAction {

		 P4Class textArea;
		 public SubmitTextAction(P4Class _textArea){
			 textArea = _textArea;
		 }
		 @Override
		 public void actionPerformed(ActionEvent arg0) {
			 if(textArea.toString().equals("main")){
				 submitMethods(textArea.getText());
			 } else {
				 submitClass(textArea.getText());
			 }
			 System.out.println("actionPerformed on text area name "+textArea.toString()); // TODO Auto-generated Event stub actionPerformed()
		 }

	 }*/

	 class CodeChangeAction extends AbstractAction {

		 int codeID = 0;

		 public CodeChangeAction(int codeID){
			 this.codeID = codeID;
			 System.out.println("code change action made- id "+this.codeID);
		 }

		 public void actionPerformed(ActionEvent event){
			 p4m.changeCodeID(codeID);
			 System.out.println("code changed to ID "+codeID);
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

	 /**
	  * This method initializes addContainerButton	
	  * 	
	  * @return javax.swing.JButton	
	  */
	 private JButton getAddContainerButton() {
		 if (addContainerButton == null) {
			 addContainerButton = new JButton();
			 addContainerButton.setText("Add Container");
			 addContainerButton.addActionListener(new java.awt.event.ActionListener() {
				 public void actionPerformed(java.awt.event.ActionEvent e) {
					 int tabCount = tabPane.getTabCount();
					 if (tabCount<TAB_MAX){
						 int codeID = tabCount+1;
						 addContainer(getNewContainer(codeID));
						 tabPane.setSelectedIndex(codeID-1);
						 System.out.println("tab added");
					 }
				 }
			 });
		 }
		 return addContainerButton;
	 }

	/**
	 * This method initializes saveMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getSaveMenuItem() {
		if (saveMenuItem == null) {
			saveMenuItem = new JMenuItem();
			saveMenuItem.setText("Save");
			saveMenuItem.setAccelerator(cmdS);
			saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("save actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
						projectDir = saveProject(projectDir, false);
						if (projectDir != null){
							setProjectName(projectDir.getName());
						}
				}
			});
		}
		return saveMenuItem;
	}

	private void setProjectName(String name){
		if (name != null){
			projectName = name;
		}
		 this.setTitle("P4Ming Editor "+projectName);
	}
	
	/**
	 * This method initializes openMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getOpenMenuItem() {
		if (openMenuItem == null) {
			openMenuItem = new JMenuItem();
			openMenuItem.setText("Open...");
			openMenuItem.setAccelerator(cmdO);
			openMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					projectDir = openProject(projectDir);
					if (projectDir != null){
						setProjectName(projectDir.getName());
					}
				}
			});
		}
		return openMenuItem;
	}

	/**
	 * This method initializes newMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getNewMenuItem() {
		if (newMenuItem == null) {
			newMenuItem = new JMenuItem();
			newMenuItem.setText("New Project");
			newMenuItem.setAccelerator(cmdN);
			newMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("new actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				}
			});
		}
		return newMenuItem;
	}

	/**
	 * This method initializes saveAsMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getSaveAsMenuItem() {
		if (saveAsMenuItem == null) {
			saveAsMenuItem = new JMenuItem();
			saveAsMenuItem.setText("Save As...");
			saveAsMenuItem.setAccelerator(cmdShiftS);
			saveAsMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("save as... actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					projectDir = saveProject(projectDir, true);//force save as = true
					if (projectDir != null){
						setProjectName(projectDir.getName());
					}
				}
			});
		}
		return saveAsMenuItem;
	}

	private File openProject(File _projectDir) {
		JFileChooser chooser = new JFileChooser();
		//chooser.setCurrentDirectory(new File(fileName));
		if (_projectDir != null){
			chooser.setCurrentDirectory(_projectDir);
		}
		boolean fileSuccess = false;
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			_projectDir = chooser.getSelectedFile();
			fileSuccess = true;
		}
		if (!_projectDir.exists()){
			fileSuccess = _projectDir.mkdir();
		}
		if (fileSuccess){
			clearContainers();
			File[] containers = _projectDir.listFiles();
			for (int i = 0; i < containers.length; i++) {
				File container = containers[i];
				if (container.isDirectory()){
					int id = Integer.parseInt(container.getName());
					P4Container p4c = new P4Container(this, id, container.listFiles());
					addContainer(p4c);
				}
			}
		}
		return _projectDir;
	}
	
	 private List<File> getFileListing(File aStartingDir) throws FileNotFoundException {
		    List<File> result = new ArrayList<File>();
		    File[] filesAndDirs = aStartingDir.listFiles();
		    List<File> filesDirs = Arrays.asList(filesAndDirs);
		    for(File file : filesDirs) {
		    	if (!file.getName().startsWith(".")){ //no hidden files
		      result.add(file); //always add, even if directory
		    	}
		      if ( file.isDirectory() ) {
		        List<File> deeperList = getFileListing(file);
		        result.addAll(deeperList);
		      }
		    }
		    return result;
		  }

		  /**
		  * Directory is valid if it exists, does not represent a file, and can be read.
		  */
		   private void validateDirectory (File aDirectory) throws FileNotFoundException {
		    if (aDirectory == null) {
		      throw new IllegalArgumentException("Directory should not be null.");
		    }
		    if (!aDirectory.exists()) {
		      throw new FileNotFoundException("Directory does not exist: " + aDirectory);
		    }
		    if (!aDirectory.isDirectory()) {
		      throw new IllegalArgumentException("Is not a directory: " + aDirectory);
		    }
		    if (!aDirectory.canRead()) {
		      throw new IllegalArgumentException("Directory cannot be read: " + aDirectory);
		    }
		  }
		  
	private File saveProject(File _projectDir, boolean forceSaveAs) {
		JFileChooser chooser = new JFileChooser();
		//chooser.setCurrentDirectory(new File(fileName));
		if (_projectDir != null){
			chooser.setCurrentDirectory(_projectDir);
		}
		if (_projectDir == null || forceSaveAs){
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = chooser.showSaveDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				_projectDir = chooser.getSelectedFile();
			}
		}
		boolean fileSuccess = true;
		if (!_projectDir.exists()){
			fileSuccess = _projectDir.mkdir();
		}
		if (fileSuccess){
			//System.out.println("tab count = "+tabPane.getTabCount());
			for (int i = 0; i < tabPane.getTabCount(); i++){
				P4Container p4c = (P4Container) tabPane.getComponentAt(i);
				//System.out.println("Saving container "+i+": "+p4c);
				p4c.save(_projectDir);
			}
		}
		return _projectDir;
	}
	/**
	 * This method initializes quitMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getQuitMenuItem() {
		if (quitMenuItem == null) {
			quitMenuItem = new JMenuItem();
			quitMenuItem.setAccelerator(cmdQ);
			quitMenuItem.setText("Quit");
			quitMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JOptionPane quitOptionPane = new JOptionPane("Would you like to save before quitting?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION);
					JDialog quitDialog = quitOptionPane.createDialog("Quit");
					quitDialog.setVisible(true);
					Object selectedValue = quitOptionPane.getValue();
					if (selectedValue.equals(JOptionPane.YES_OPTION)||selectedValue.equals(JOptionPane.NO_OPTION)){
						//save
						quitApp((Integer)selectedValue);
					} else {
						System.out.println("cancelled quit.");
					}
				}
			});
		}
		return quitMenuItem;
	}
	
	public void quitApp(Integer saveFirst){
		if(saveFirst.equals(JOptionPane.YES_OPTION)){
			//save first
		}
		System.out.println("Exiting Per4Ming.  Bye.");
		System.exit(0);
	}
}


