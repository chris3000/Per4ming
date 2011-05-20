package com.chris3000.p4ming.editor;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.Point;

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
import javax.swing.text.BadLocationException;

import com.chris3000.p4ming.P4Ming;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.awt.CardLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class P4Editor extends JFrame{
	private KeyStroke ctrlEnter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, ActionEvent.CTRL_MASK);  //  @jve:decl-index=0:
	private boolean started = false;
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JScrollPane editorPane = null;
	private JTextArea editorTextArea = null;
	private JPanel controlPanel = null;
	private JButton startStopButton = null;
	private JLabel widthLabel = null;
	private JTextField widthField = null;
	private JLabel heightLabel = null;
	private JTextField heightField = null;
	private JButton submitButton = null;
	//parent class
	P4Ming p4m;  //  @jve:decl-index=0:
	private JTextField propertyField = null;
	private JButton paramsSubmit = null;
	private JMenuBar jmenuBar = null;
	private JMenu File = null;
	private JMenu Options = null;
	private JMenuItem submitMethod = null;
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
		startStopButton.setText("Start");
		started = false;
	}
	
	public void setStart(){
		startStopButton.setText("Stop");
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
		this.setSize(550, 400);
		this.setJMenuBar(getJmenuBar());
		this.setContentPane(getJContentPane());
		this.setTitle("P4Ming Editor");
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
			jContentPane.add(getEditorPane(), BorderLayout.CENTER);
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
			editorPane.setViewportView(getEditorTextArea());
		}
		return editorPane;
	}

	/**
	 * This method initializes editorTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getEditorTextArea() {
		if (editorTextArea == null) {
			editorTextArea = new JTextArea();
			editorTextArea.setMargin(new Insets(2,6,2,6));
			editorTextArea.setText("{->ellipse(200,200,random(20),random(10));}");
			editorTextArea.setFont(new Font("Monaco", Font.PLAIN, 12));
			editorTextArea.setTabSize(3);
			editorTextArea.setWrapStyleWord(true);
			editorTextArea.addCaretListener(new CaretListener() {
      public void caretUpdate(CaretEvent caretEvent) {
    	  try {
    	  int dot = caretEvent.getDot();
    	  int mark = caretEvent.getMark();
    	  Point dotLoc = new Point();
    	  dotLoc.y = editorTextArea.getLineOfOffset(dot);
    	  dotLoc.x = dot-editorTextArea.getLineStartOffset(dotLoc.y);
    	  if(mark != dot){ //selection
        	  Point markLoc = new Point();
        	  markLoc.y = editorTextArea.getLineOfOffset(dot);
        	  markLoc.x = dot-editorTextArea.getLineStartOffset(dotLoc.y);
    		  p4m.caretEvent(dotLoc,markLoc);
    	  } else { 
    		  p4m.caretEvent(dotLoc);
    	  }
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

       // System.out.println("Dot: "+ caretEvent.getDot()+" Mark: "+caretEvent.getMark());
        
      }
    });
			editorTextArea.addKeyListener(new KeyListener(){
				@Override
				public void keyPressed(KeyEvent e) {
					if(!e.isControlDown() && !e.isMetaDown() && !e.isAltDown()){
						p4m.keyPressed(e.getKeyChar());
					}
					
				}

				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		return editorTextArea;
	}

public void getCurrentText(){
	p4m.setText(editorTextArea.getText());
}
		

	/**
	 * This method initializes controlPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getControlPanel() {
		if (controlPanel == null) {
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 5;
			gridBagConstraints3.gridy = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.gridwidth = 5;
			gridBagConstraints2.gridx = 0;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints1.weightx = 1;
			heightLabel = new JLabel();
			heightLabel.setText("Height");
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints.weightx = 1;
			widthLabel = new JLabel();
			widthLabel.setText("Width");
			controlPanel = new JPanel();
			controlPanel.setLayout(new GridBagLayout());
			controlPanel.add(widthLabel, new GridBagConstraints());
			controlPanel.add(getWidthField(), gridBagConstraints);
			controlPanel.add(heightLabel, new GridBagConstraints());
			controlPanel.add(getHeightField(), gridBagConstraints1);
			controlPanel.add(getStartStopButton(), new GridBagConstraints());
			controlPanel.add(getSubmitButton(), new GridBagConstraints());
			controlPanel.add(getParamsField(), gridBagConstraints2);
			controlPanel.add(getParamsSubmit(), gridBagConstraints3);
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
			startStopButton.setText("Start");
		}
		startStopButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (!started){
        			startStopButton.setText("Stop");
        			int w = Integer.parseInt(widthField.getText());
        			int h = Integer.parseInt(heightField.getText());
        			p4m.startViewer(w, h);
        			started = true;
        		} else {
        			startStopButton.setText("Start");
        			started = false;
        			p4m.editorStop();
        		}
        		//jtfInput.setText("Button 2!");
        	}
        });
		return startStopButton;
	}

	/**
	 * This method initializes widthField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getWidthField() {
		if (widthField == null) {
			widthField = new JTextField();
			widthField.setText("800");
			widthField.setPreferredSize(new Dimension(50, 28));
		}
		return widthField;
	}

	/**
	 * This method initializes heightField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getHeightField() {
		if (heightField == null) {
			heightField = new JTextField();
			heightField.setText("600");
			heightField.setPreferredSize(new Dimension(50, 28));
		}
		return heightField;
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
		}
		submitButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		submitMethod();
        	}
        });
		return submitButton;
	}

	/**
	 * This method initializes paramsField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getParamsField() {
		if (propertyField == null) {
			propertyField = new JTextField();
			propertyField.setName("propertyField");
			propertyField.setPreferredSize(new Dimension(450, 28));
			propertyField.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent evt) {
		        		submitProperty();
					}
		});
		}
		return propertyField;
	}

	/**
	 * This method initializes paramsSubmit	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getParamsSubmit() {
		if (paramsSubmit == null) {
			paramsSubmit = new JButton();
			paramsSubmit.setText("Property");
			paramsSubmit.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		submitProperty();
	        	}
	        });
		}
		return paramsSubmit;
	}

	private void submitProperty() {
		if (started){
			String text = propertyField.getText();
			String[] nameValue = text.split("=");
			P4Message p4mess = new P4Message(nameValue[0].trim(), nameValue[1].trim(), P4Message.PROPERTY);
			p4m.addMethod(p4mess);
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
					submitMethod();
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				}
			});
		}
		return submitMethod;
	}

	private void submitMethod() {
		if (started){
			P4Message p4mess = new P4Message("draw1", editorTextArea.getText(), P4Message.METHOD);
			p4m.addMethod(p4mess);
		}
	}

}
