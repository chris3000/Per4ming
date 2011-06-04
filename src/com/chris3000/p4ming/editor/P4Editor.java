package com.chris3000.p4ming.editor;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.DefaultListModel;
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

import com.chris3000.p4ming.P4Ming;
import com.chris3000.p4ming.P4Prefs;
import com.chris3000.p4ming.editor.project.P4Class;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.Font;
import java.awt.CardLayout;
import java.util.ArrayList;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.gjt.sp.jedit.Buffer;
import org.gjt.sp.jedit.View;
import processing.app.syntax.JEditTextArea;
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
	JTextField activePropField = null;
	P4Class activeTextArea = null;
	private P4Prefs p4p = new P4Prefs(); //  @jve:decl-index=0:
	private KeyStroke ctrlEnter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, ActionEvent.CTRL_MASK);  //  @jve:decl-index=0:
	private boolean started = false;
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
	private JList classList = null;
	private JSplitPane splitPane1 = null;
	private JMenuItem newClass = null;
	private JSplitPane projectPane = null;
	private JPanel propsPanel = null;
	private JButton addPropButton = null;
	private JScrollPane propsFieldContainer = null;
	private JPanel jPanel = null;
	private JTextField jTextField = null;
	private JPanel classPanel = null;
	private JButton classButton = null;
	/**
	 * This is the default constructor
	 */
	public P4Editor() {
		super();
		initialize();
	}

	public void setParent(P4Ming _p4m){
		p4m= _p4m;
	}
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
		this.setSize(654, 445);
		this.setBackground(new Color(51, 51, 51));
		this.setJMenuBar(getJmenuBar());
		this.setContentPane(getJContentPane());
		this.setTitle("P4Ming Editor");
		this.addWindowListener(new java.awt.event.WindowAdapter() { 
			public void windowClosing(WindowEvent e) {    
				//System.out.println("windowClosing()"); // TODO Auto-generated Event stub windowClosing()
				System.exit(0);
			}
		});
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

	/**
	 * This method initializes editorPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getEditorPane() {
		if (editorPane == null) {
			editorPane = new JScrollPane();
			editorPane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			editorPane.setViewportView(activeTextArea);
		}
		return editorPane;
	}

	/**
	 * This method initializes editorTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private P4Class createClass(String name) {
			P4Class editorTextArea = new P4Class();
			//editorTextArea = new JEditTextArea(new processing.app.syntax.TextAreaDefaults());
			editorTextArea.setMargin(new Insets(2,6,2,6));
			editorTextArea.setFont(new Font("Monaco", Font.PLAIN, 12));
			editorTextArea.setTabSize(3);
			editorTextArea.setWrapStyleWord(true);
			if (name != null){
				if (name.equals("main")){
					editorTextArea.setText("def draw1 = {->\n\t//code goes here\n\t//ellipse(200,200,random(20),random(10));\n}");
				} else {
					editorTextArea.setText("class "+name+" {\n\t//code goes here\n}\n");
				}
				editorTextArea.setName(name);
			}
			editorTextArea.addCaretListener(new CaretListener() {
				public void caretUpdate(CaretEvent caretEvent) {
						int dot = caretEvent.getDot();
						int mark = caretEvent.getMark();
						System.out.println(""+dot+","+mark);
						Point dotLoc = getDocXYLoc(activeTextArea,dot);
						if(mark != dot){ //selection
							Point markLoc = getDocXYLoc(activeTextArea,mark);
							p4m.caretEvent(dotLoc,markLoc);
						} else { 
							p4m.caretEvent(dotLoc);
						}
					
					// System.out.println("Dot: "+ caretEvent.getDot()+" Mark: "+caretEvent.getMark());

				}
			});
			editorTextArea.addKeyListener(new KeyListener(){
				@Override
				public void keyPressed(KeyEvent e) {
/*					char key = e.getKeyChar();
					if (key >= ' ' || key =='\n'){  //space is the first visible char
						if(!e.isControlDown() && !e.isMetaDown() && !e.isAltDown()){
							p4m.keyPressed(e.getKeyChar());
						}
					}*/
				}

				@Override
				public void keyReleased(KeyEvent e) {}
				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub

				}
			});
			editorTextArea.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusGained(java.awt.event.FocusEvent e) {
					//System.out.println("focusGained()"); // TODO Auto-generated Event stub focusGained()
					getCurrentText();
				}
			});
		editorTextArea.getDocument().addDocumentListener(new DocumentListener(){

				@Override
				public void changedUpdate(DocumentEvent e) {
					System.out.println("doc change!"+e.toString());
					System.out.println("length="+e.getLength()+", offset="+e.getOffset()+", type="+e.getType());
				}

				@Override
				public void insertUpdate(DocumentEvent e) {
					System.out.println("doc insert!"+e.toString());
					System.out.println("length="+e.getLength()+", offset="+e.getOffset()+", type="+e.getType());
					String changedText = null;
					try {
						changedText = e.getDocument().getText(e.getOffset(), e.getLength());
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Point addLoc = getDocXYLoc(activeTextArea,e.getOffset());
					p4m.addText(addLoc, changedText);
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					//System.out.println("length="+e.getLength()+", offset="+e.getOffset()+", type="+e.getType());
					Point removeLoc = getDocXYLoc(activeTextArea,e.getOffset());
					p4m.removeText(removeLoc,e.getLength());
				}	
			});
		return editorTextArea;
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
	
	public void getCurrentText(){
		p4m.setText(activeTextArea.getText());
	}


	/**
	 * This method initializes controlPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getControlPanel() {
		if (controlPanel == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.anchor = GridBagConstraints.WEST;
			gridBagConstraints1.weightx = 1.0;
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
					if (p4p.audioEnabled){
						p4m.startViewer(p4p.size, p4p.frameRate, p4p.bgColor,p4p.openGL, p4p.fullScreen,
								p4p.lineInName, p4p.channelType, p4p.bufferSize, p4p.sampleRate, p4p.bitDepth);
					} else {
						p4m.startViewer(p4p.size, p4p.frameRate, p4p.bgColor,p4p.openGL, p4p.fullScreen);
					}
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
				submitMethods(activeTextArea.getText());
			}
		});
		return submitButton;
	}

	private void submitProperty(String text) {
		if (started){
			//String text = propertyField.getText();
			String[] nameValue = text.split("=");
			if(nameValue.length == 2){
				P4Message p4mess = new P4Message(nameValue[0].trim(), nameValue[1].trim(), P4Message.PROPERTY);
				p4m.addMethod(p4mess);
			} else if (nameValue.length == 1){
				P4Message p4mess = new P4Message(null, nameValue[0].trim(), P4Message.RUN_ONCE);
				p4m.addMethod(p4mess);
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
			submitMethod.setAccelerator(ctrlEnter);
			submitMethod.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(activeTextArea.toString().equals("main")){
						submitMethods(activeTextArea.getText());
					} else {
						submitClass(activeTextArea.getText());
					}
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				}
			});
		}
		return submitMethod;
	}

	private void submitMethods(String methods) {
		if (started){
			P4Message[] p4mess = parseMethods(methods);
			p4m.addMethods(p4mess);
		}
	}
	
	private void submitClass(String classStr) {
		if (started){
			p4m.addClass(new P4Message(null, classStr,P4Message.CLASS));
		}
	}
		private P4Message[] parseMethods(String methods){
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
						p4ms.add(new P4Message(methodName, methodStr, P4Message.METHOD));
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
				tabPane.addTab("1", null, getSplitPane1(), null);
			}
			return tabPane;
		}

		/**
		 * This method initializes classList	
		 * 	
		 * @return javax.swing.JList	
		 */
		private JList getClassList() {
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
		}
		
		/**
		 * This method initializes splitPane1	
		 * 	
		 * @return javax.swing.JSplitPane	
		 */
		private JSplitPane getSplitPane1() {
			if (splitPane1 == null) {
				splitPane1 = new JSplitPane();
				splitPane1.setContinuousLayout(true);
				splitPane1.setBackground(new Color(102, 102, 102));
				splitPane1.setLeftComponent(getClassPanel());
				splitPane1.setRightComponent(getProjectPane());
			}
			return splitPane1;
		}

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
						DefaultListModel classListModel = (DefaultListModel) classList.getModel();
						classListModel.setSize(classListModel.getSize()+1);
						classListModel.addElement("New Class");
						System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
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

		/**
		 * This method initializes propsPanel	
		 * 	
		 * @return javax.swing.JPanel	
		 */
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
				propsPanel.add(getPropsFieldContainer(), gridBagConstraints5);
				propsPanel.add(getAddPropButton(), gridBagConstraints6);
			}
			return propsPanel;
		}

		/**
		 * This method initializes addPropButton	
		 * 	
		 * @return javax.swing.JButton	
		 */
		private JButton getAddPropButton() {
			if (addPropButton == null) {
				addPropButton = new JButton();
				addPropButton.setVerticalAlignment(SwingConstants.CENTER);
				addPropButton.setHorizontalTextPosition(SwingConstants.CENTER);
				addPropButton.setName("addPropButton");
				addPropButton.setPreferredSize(new Dimension(200, 29));
				addPropButton.setComponentOrientation(ComponentOrientation.UNKNOWN);
				addPropButton.setText("New Property");
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

		/**
		 * This method initializes propsFieldContainer	
		 * 	
		 * @return javax.swing.JScrollPane	
		 */
		private JScrollPane getPropsFieldContainer() {
			if (propsFieldContainer == null) {
				propsFieldContainer = new JScrollPane();
				propsFieldContainer.setViewportView(getJPanel());
			}
			return propsFieldContainer;
		}

		/**
		 * This method initializes jPanel	
		 * 	
		 * @return javax.swing.JPanel	
		 */
		private JPanel getJPanel() {
			if (jPanel == null) {
				jPanel = new JPanel();
				jPanel.setLayout(new BoxLayout(getJPanel(), BoxLayout.Y_AXIS));
				jPanel.setBackground(new Color(51, 51, 51));
				jPanel.add(getJTextField(), null);
			}
			return jPanel;
		}

		/**
		 * This method initializes jTextField	
		 * 	
		 * @return javax.swing.JTextField	
		 */
		private JTextField getJTextField() {
				JTextField jTextField = new JTextField(2);
				jTextField.setHorizontalAlignment(JTextField.LEFT);
				jTextField.setPreferredSize(new Dimension(10, 28));
				jTextField.setMaximumSize(new Dimension(900, 28));
				jTextField.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent evt) {
						submitProperty(activePropField.getText());
						System.out.println("Action performed!!");
					}
				});
				jTextField.addFocusListener(new java.awt.event.FocusAdapter() {   
					public void focusLost(java.awt.event.FocusEvent e) {
						submitProperty(activePropField.getText());
						activePropField = null;
						System.out.println("focusLost()"); // TODO Auto-generated Event stub focusLost()
					}
					public void focusGained(java.awt.event.FocusEvent e) {
						activePropField = (JTextField)e.getSource();
						System.out.println("focusGained()"); // TODO Auto-generated Event stub focusGained()
					}
				});
			return jTextField;
		}

		/**
		 * This method initializes classPanel	
		 * 	
		 * @return javax.swing.JPanel	
		 */
		private JPanel getClassPanel() {
			if (classPanel == null) {
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
				classPanel.add(getClassList(), gridBagConstraints7);
				classPanel.add(getClassButton(), gridBagConstraints8);
			}
			return classPanel;
		}

		/**
		 * This method initializes classButton	
		 * 	
		 * @return javax.swing.JButton	
		 */
		private JButton getClassButton() {
			if (classButton == null) {
				classButton = new JButton();
				classButton.setText("New Class");
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

		private void setEditorPaneView(P4Class textArea) {
			editorPane.setViewportView(textArea);
			editorPane.revalidate();
			editorPane.repaint();
		}

	}  //  @jve:decl-index=0:visual-constraint="10,10"


