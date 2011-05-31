package com.chris3000.p4ming.editor;

import javax.swing.JPanel;
import java.awt.Frame;
import java.awt.BorderLayout;
import javax.swing.JWindow;
import javax.swing.JTabbedPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Dimension;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import java.awt.ComponentOrientation;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;
import java.lang.Object;
import javax.swing.WindowConstants;
import javax.swing.JScrollBar;
import javax.swing.JComboBox;
import javax.swing.JButton;

import com.chris3000.p4ming.P4Prefs;

public class P4PrefsPane extends JFrame {
	P4Prefs p4prefs = null;
	P4PrefsPane parent= null;
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JTabbedPane prefsTabPane = null;
	private JPanel setupPanel = null;
	private JLabel sizeLabel = null;
	private JTextField sizeField = null;
	private JLabel openGLLabel = null;
	private JCheckBox openGLCheck = null;
	private JLabel frameRateLabel = null;
	private JTextField frameRateField = null;
	private JLabel bgLabel = null;
	private JTextField bgField = null;
	private JLabel fullScreenLabel = null;
	private JCheckBox fullScreenButton = null;
	private JLabel warning = null;
	private JPanel Audio = null;
	private JLabel enableAudioLabel = null;
	private JCheckBox enableAudioCheck = null;
	private JLabel lineInLabel = null;
	private JTextField lineInField = null;
	private JComboBox typeBox = null;
	private JLabel channelTypeField = null;
	private JLabel bufferSizeLabel = null;
	private JComboBox bufferSizeCombo = null;
	private JLabel sampleRateLabel = null;
	private JComboBox sampleRateCombo = null;
	private JLabel bitDepthLabel = null;
	private JComboBox bitDepthCombo = null;
	private JPanel savePanel = null;
	private JButton saveButton = null;
	private JButton cancelButton = null;
	/**
	 * @param owner
	 */
	public P4PrefsPane() {
		super();
		initialize();
	}
	public P4PrefsPane(P4Prefs p4p) {
		super();
		p4prefs = p4p;
		parent = this;
		initialize();
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(320, 300);
		this.setTitle("Preferences");
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setAlwaysOnTop(true);
		this.setUndecorated(false);
		this.setContentPane(getJContentPane());
				this.addWindowListener(new java.awt.event.WindowAdapter() { 
					public void windowClosing(WindowEvent e) {  
						//add "do you want to save" stuff later
						parent.dispose();
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
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setVgap(2);
			jContentPane = new JPanel();
			jContentPane.setLayout(borderLayout);
			jContentPane.add(getPrefsTabPane(), BorderLayout.NORTH);
			jContentPane.add(getSavePanel(), BorderLayout.EAST);
		}
		return jContentPane;
	}

	/**
	 * This method initializes prefsTabPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getPrefsTabPane() {
		if (prefsTabPane == null) {
			prefsTabPane = new JTabbedPane();
			prefsTabPane.setPreferredSize(new Dimension(320, 240));
			prefsTabPane.setTabPlacement(JTabbedPane.TOP);
			prefsTabPane.addTab("Setup", null, getSetupPanel(), null);
			prefsTabPane.addTab("Audio", null, getAudio(), null);
		}
		return prefsTabPane;
	}

	/**
	 * This method initializes setupPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getSetupPanel() {
		if (setupPanel == null) {
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridx = 0;
			gridBagConstraints12.gridwidth = 4;
			gridBagConstraints12.gridheight = 2;
			gridBagConstraints12.anchor = GridBagConstraints.SOUTH;
			gridBagConstraints12.fill = GridBagConstraints.BOTH;
			gridBagConstraints12.gridy = 4;
			warning = new JLabel();
			warning.setText("Don't change these settings for now!");
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.anchor = GridBagConstraints.WEST;
			gridBagConstraints11.gridy = 3;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = 0;
			gridBagConstraints10.fill = GridBagConstraints.BOTH;
			gridBagConstraints10.gridy = 3;
			fullScreenLabel = new JLabel();
			fullScreenLabel.setText("Full Screen");
			fullScreenLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.fill = GridBagConstraints.BOTH;
			gridBagConstraints9.gridx = 1;
			gridBagConstraints9.gridy = 2;
			gridBagConstraints9.gridwidth = 3;
			gridBagConstraints9.weightx = 1.0;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 0;
			gridBagConstraints8.fill = GridBagConstraints.BOTH;
			gridBagConstraints8.gridy = 2;
			bgLabel = new JLabel();
			bgLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			bgLabel.setText("Background");
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.fill = GridBagConstraints.BOTH;
			gridBagConstraints7.gridy = 1;
			gridBagConstraints7.weightx = 1.0;
			gridBagConstraints7.anchor = GridBagConstraints.WEST;
			gridBagConstraints7.gridwidth = 3;
			gridBagConstraints7.gridx = 1;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints6.gridy = 1;
			frameRateLabel = new JLabel();
			frameRateLabel.setText("Frame Rate");
			frameRateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 3;
			gridBagConstraints5.anchor = GridBagConstraints.WEST;
			gridBagConstraints5.gridy = 3;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 2;
			gridBagConstraints4.weightx = 1.1D;
			gridBagConstraints4.fill = GridBagConstraints.BOTH;
			gridBagConstraints4.gridy = 3;
			openGLLabel = new JLabel();
			openGLLabel.setText("OpenGL");
			openGLLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.weightx = 1.0D;
			gridBagConstraints1.anchor = GridBagConstraints.WEST;
			gridBagConstraints1.gridwidth = 3;
			gridBagConstraints1.gridx = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.ipadx = 0;
			gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints.anchor = GridBagConstraints.CENTER;
			gridBagConstraints.weightx = 0.5D;
			sizeLabel = new JLabel();
			sizeLabel.setText("Size");
			sizeLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
			sizeLabel.setVerticalAlignment(SwingConstants.TOP);
			sizeLabel.setVerticalTextPosition(SwingConstants.TOP);
			sizeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			setupPanel = new JPanel();
			setupPanel.setLayout(new GridBagLayout());
			setupPanel.setName("Setup");
			setupPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			//setupPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			setupPanel.add(sizeLabel, gridBagConstraints);
			setupPanel.add(getSizeField(), gridBagConstraints1);
			setupPanel.add(openGLLabel, gridBagConstraints4);
			setupPanel.add(getOpenGLCheck(), gridBagConstraints5);
			setupPanel.add(frameRateLabel, gridBagConstraints6);
			setupPanel.add(getFrameRateField(), gridBagConstraints7);
			setupPanel.add(bgLabel, gridBagConstraints8);
			setupPanel.add(getBgField(), gridBagConstraints9);
			setupPanel.add(fullScreenLabel, gridBagConstraints10);
			setupPanel.add(getFullScreenButton(), gridBagConstraints11);
			setupPanel.add(warning, gridBagConstraints12);
		}
		return setupPanel;
	}

	/**
	 * This method initializes sizeField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getSizeField() {
		if (sizeField == null) {
			sizeField = new JTextField();
			int[] sz = p4prefs.size;
			sizeField.setText(""+sz[0]+","+sz[1]);
			sizeField.setHorizontalAlignment(JTextField.LEFT);
			sizeField.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			sizeField.setPreferredSize(new Dimension(80, 28));
		}
		return sizeField;
	}

	/**
	 * This method initializes openGLCheck	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getOpenGLCheck() {
		if (openGLCheck == null) {
			openGLCheck = new JCheckBox();
			openGLCheck.setSelected(p4prefs.openGL);
		}
		return openGLCheck;
	}

	/**
	 * This method initializes frameRateField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getFrameRateField() {
		if (frameRateField == null) {
			frameRateField = new JTextField();
			frameRateField.setText(""+p4prefs.frameRate);
			frameRateField.setPreferredSize(new Dimension(80, 28));
		}
		return frameRateField;
	}

	/**
	 * This method initializes bgField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getBgField() {
		if (bgField == null) {
			bgField = new JTextField();
			//bgField.setPreferredSize(new Dimension(80, 28));
			float[] bg = p4prefs.bgColor;
			String str = "";
			for (int i = 0; i < bg.length; i++) {
				str+=bg[i];
				if (i!=bg.length-1){
					str+=",";
				}
			}
			bgField.setText(str);
		}
		return bgField;
	}

	/**
	 * This method initializes fullScreenButton	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getFullScreenButton() {
		if (fullScreenButton == null) {
			fullScreenButton = new JCheckBox();
			fullScreenButton.setHorizontalAlignment(SwingConstants.LEFT);
			fullScreenButton.setHorizontalTextPosition(SwingConstants.LEFT);
			fullScreenButton.setSelected(p4prefs.fullScreen);
		}
		return fullScreenButton;
	}

	/**
	 * This method initializes Audio	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getAudio() {
		if (Audio == null) {
			GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			gridBagConstraints22.fill = GridBagConstraints.BOTH;
			gridBagConstraints22.gridy = 5;
			gridBagConstraints22.weightx = 1.0;
			gridBagConstraints22.gridx = 1;
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.gridx = 0;
			gridBagConstraints21.gridy = 5;
			bitDepthLabel = new JLabel();
			bitDepthLabel.setText("Bit Depth");
			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.fill = GridBagConstraints.BOTH;
			gridBagConstraints20.gridy = 4;
			gridBagConstraints20.weightx = 1.0;
			gridBagConstraints20.gridx = 1;
			GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
			gridBagConstraints19.gridx = 0;
			gridBagConstraints19.gridy = 4;
			sampleRateLabel = new JLabel();
			sampleRateLabel.setText("Sample Rate");
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.fill = GridBagConstraints.BOTH;
			gridBagConstraints18.gridy = 3;
			gridBagConstraints18.weightx = 1.0;
			gridBagConstraints18.gridx = 1;
			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.gridx = 0;
			gridBagConstraints17.gridy = 3;
			bufferSizeLabel = new JLabel();
			bufferSizeLabel.setText("Buffer Size");
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.gridx = 0;
			gridBagConstraints16.gridy = 2;
			channelTypeField = new JLabel();
			channelTypeField.setText("Channel Type");
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.fill = GridBagConstraints.BOTH;
			gridBagConstraints15.gridy = 2;
			gridBagConstraints15.weightx = 1.5D;
			gridBagConstraints15.gridx = 1;
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.fill = GridBagConstraints.BOTH;
			gridBagConstraints14.gridy = 1;
			gridBagConstraints14.weightx = 1.0;
			gridBagConstraints14.gridx = 1;
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.gridx = 0;
			gridBagConstraints13.gridy = 1;
			lineInLabel = new JLabel();
			lineInLabel.setText("Line-In Field Name");
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.anchor = GridBagConstraints.WEST;
			gridBagConstraints3.gridy = 0;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 0;
			enableAudioLabel = new JLabel();
			enableAudioLabel.setText("Enable Minim Audio");
			Audio = new JPanel();
			Audio.setLayout(new GridBagLayout());
			Audio.add(enableAudioLabel, gridBagConstraints2);
			Audio.add(getEnableAudioCheck(), gridBagConstraints3);
			Audio.add(lineInLabel, gridBagConstraints13);
			Audio.add(getLineInField(), gridBagConstraints14);
			Audio.add(getTypeBox(), gridBagConstraints15);
			Audio.add(channelTypeField, gridBagConstraints16);
			Audio.add(bufferSizeLabel, gridBagConstraints17);
			Audio.add(getBufferSizeCombo(), gridBagConstraints18);
			Audio.add(sampleRateLabel, gridBagConstraints19);
			Audio.add(getSampleRateCombo(), gridBagConstraints20);
			Audio.add(bitDepthLabel, gridBagConstraints21);
			Audio.add(getBitDepthCombo(), gridBagConstraints22);
		}
		return Audio;
	}

	/**
	 * This method initializes enableAudioCheck	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getEnableAudioCheck() {
		if (enableAudioCheck == null) {
			enableAudioCheck = new JCheckBox();
			enableAudioCheck.setSelected(p4prefs.audioEnabled);
		}
		return enableAudioCheck;
	}

	/**
	 * This method initializes lineInField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getLineInField() {
		if (lineInField == null) {
			lineInField = new JTextField();
			lineInField.setText(p4prefs.lineInName);
		}
		return lineInField;
	}

	/**
	 * This method initializes typeBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getTypeBox() {
		if (typeBox == null) {
			typeBox = new JComboBox();
			typeBox.setMaximumRowCount(2);
			typeBox.addItem("Mono");
			typeBox.addItem("Stereo");
			typeBox.setSelectedItem(p4prefs.channelType);
		}
		return typeBox;
	}

	/**
	 * This method initializes bufferSizeCombo	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getBufferSizeCombo() {
		if (bufferSizeCombo == null) {
			bufferSizeCombo = new JComboBox();
			bufferSizeCombo.addItem(128);
			bufferSizeCombo.addItem(256);
			bufferSizeCombo.addItem(512);
			bufferSizeCombo.addItem(1024);
			bufferSizeCombo.addItem(2048);
			bufferSizeCombo.setMaximumRowCount(5);
			bufferSizeCombo.setSelectedItem(p4prefs.bufferSize);
		}
		return bufferSizeCombo;
	}

	/**
	 * This method initializes sampleRateCombo	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getSampleRateCombo() {
		if (sampleRateCombo == null) {
			sampleRateCombo = new JComboBox();
			sampleRateCombo.addItem(8000f);
			sampleRateCombo.addItem(11000f);
			sampleRateCombo.addItem(16000f);
			sampleRateCombo.addItem(22000f);
			sampleRateCombo.addItem(32000f);
			sampleRateCombo.addItem(44100f);
			sampleRateCombo.addItem(48000f);
			sampleRateCombo.setSelectedItem(p4prefs.sampleRate);
		}
		return sampleRateCombo;
	}

	/**
	 * This method initializes bitDepthCombo	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getBitDepthCombo() {
		if (bitDepthCombo == null) {
			bitDepthCombo = new JComboBox();
			bitDepthCombo.addItem(8);
			bitDepthCombo.addItem(16);
			bitDepthCombo.setSelectedItem(p4prefs.bitDepth);
		}
		return bitDepthCombo;
	}

	/**
	 * This method initializes savePanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getSavePanel() {
		if (savePanel == null) {
			GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
			gridBagConstraints24.gridx = 1;
			GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
			gridBagConstraints23.gridx = 0;
			gridBagConstraints23.gridy = 0;
			savePanel = new JPanel();
			savePanel.setLayout(new GridBagLayout());
			savePanel.add(getSaveButton(), gridBagConstraints24);
			savePanel.add(getCancelButton(), gridBagConstraints23);
		}
		return savePanel;
	}

	/**
	 * This method initializes saveButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getSaveButton() {
		if (saveButton == null) {
			saveButton = new JButton();
			saveButton.setText("Save");
			saveButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String[] szStr = sizeField.getText().split(",");
					int[] sz = new int[2];
					if (szStr.length==2){
						sz[0]= Integer.parseInt(szStr[0]);
						sz[1]= Integer.parseInt(szStr[1]);
					} else {
						sz[0]=100;
						sz[1]=100;
					}
					p4prefs.size = sz;
					p4prefs.openGL = openGLCheck.isSelected();
					p4prefs.frameRate = Integer.parseInt(frameRateField.getText());
					String[] bgStr = bgField.getText().split(",");
					float[] bg = new float[3];
					for (int i = 0; i < bgStr.length; i++) {
						bg[i]=Float.parseFloat(bgStr[i]);
					}
					p4prefs.bgColor = bg;
					p4prefs.fullScreen = fullScreenButton.isSelected();
					p4prefs.audioEnabled = enableAudioCheck.isSelected();
					p4prefs.lineInName = lineInField.getText();
					p4prefs.channelType = (String) typeBox.getSelectedItem();
					p4prefs.bufferSize = (Integer) bufferSizeCombo.getSelectedItem();
					p4prefs.sampleRate = (Float) sampleRateCombo.getSelectedItem();
					p4prefs.bitDepth = (Integer) bitDepthCombo.getSelectedItem();
					p4prefs.savePrefs();
					parent.dispose();
					
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				}
			});
		}
		return saveButton;
	}

	/**
	 * This method initializes cancelButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setText("Cancel");
			cancelButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					parent.dispose();
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				}
			});
		}
		return cancelButton;
	}

}
