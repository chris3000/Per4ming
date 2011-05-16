package com.chris3000.p4ming.editor;

import java.awt.BorderLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.chris3000.p4ming.P4Ming;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

public class P4Editor extends JFrame {
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
	P4Ming p4m;
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

		}
		return editorTextArea;
	}

	/**
	 * This method initializes controlPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getControlPanel() {
		if (controlPanel == null) {
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
			widthField.setText("320");
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
			heightField.setText("240");
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
        		if (started){
        			P4Message p4mess = new P4Message("draw1", editorTextArea.getText(), P4Message.METHOD);
        			p4m.addMethod(p4mess);
        		}
        	}
        });
		return submitButton;
	}

}
