// 
// Name: Reid, Orrane
// Project: 4
// Due:       11/24/2021
// Course: CS-  2450-01-f21 
// 
// Description: 
//      JNotepad prt 1
//
import java.awt.event.*;
import java.io.IOException;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.BadLocationException;
import javax.swing.filechooser.FileFilter;

import java.util.*;
import java.io.*;

public class Notepad implements ActionListener{
	private JFrame frame = new JFrame("Untitled - Notepad");
	private JTextArea mainText;
	private boolean saved = true;
	private File currentFile;
	
	
	public Notepad() {
	frame.setLayout(new BorderLayout());
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setSize(640, 480);
	
	mainText = new JTextArea();
	mainText.getDocument().addDocumentListener(new DocumentListener() {
		public void insertUpdate(DocumentEvent e) {
			saved = false;
			if (currentFile != null && currentFile.exists()) {
				frame.setTitle(currentFile.getName() + "* - Notepad");
			}
		}
		public void changedUpdate(DocumentEvent e) {
			saved = false;	
			if (currentFile != null && currentFile.exists()) {
				frame.setTitle(currentFile.getName() + "* - Notepad");
			}
		}
		public void removeUpdate(DocumentEvent e) {
			saved = false;
			if (currentFile != null && currentFile.exists()) {
				frame.setTitle(currentFile.getName() + "* - Notepad");
			}
		}
	});
	
	JScrollPane textScrollPane = new JScrollPane(mainText);
	JMenuBar optionBar = new JMenuBar();
	
	JMenu[] options = new JMenu[5];
	JMenuItem[] items = new JMenuItem[24];
	
	
	options[0] = new JMenu("File"); //File
	options[1] = new JMenu("Edit");
	options[2] = new JMenu("Format");
	options[3] = new JMenu("View");
	options[4] = new JMenu("Help");
	
	items[0] = new JMenuItem("New");
	items[1] = new JMenuItem("Open");
	items[2] = new JMenuItem("Save");
	items[3] = new JMenuItem("Save As");
	items[4] = new JMenuItem("Page Setup");
	items[5] = new JMenuItem("Print");
	items[6] = new JMenuItem("Exit");
	items[7] = new JMenuItem("Undo");
	items[8] = new JMenuItem("Cut");
	items[9] = new JMenuItem("Copy");
	items[10] = new JMenuItem("Paste");
	items[11] = new JMenuItem("Delete");
	items[12] = new JMenuItem("Find");
	items[13] = new JMenuItem("Find Next");
	items[14] = new JMenuItem("Replace");
	items[15] = new JMenuItem("Go to");
	items[16] = new JMenuItem("Select All");
	items[17] = new JMenuItem("Time/Date");
	items[18] = new JCheckBoxMenuItem("Word Wrap");
	items[18].addItemListener(new ItemListener() {
		public void itemStateChanged(ItemEvent ie) {
			if (ie.getStateChange() == ItemEvent.SELECTED)
				mainText.setLineWrap(true);
			else
				mainText.setLineWrap(false);
		}
	});
	items[19] = new JMenuItem("Font");
	items[20] = new JMenuItem("View Help");
	items[21] = new JMenuItem("Status Bar");
	items[22] = new JMenuItem("Extra Credit");
	items[23] = new JMenuItem("About Notepad");
	for (JMenu o: options) {
		optionBar.add(o);
	}
	
	for (int i = 0; i < items.length; i++) {
		if (i >= 0 && i < 7) {
			options[0].add(items[i]);
		}
		else if (i >= 7 && i < 18) {
			options[1].add(items[i]);
		}
		else if (i >= 18 && i < 20) {
			options[2].add(items[i]);
		}
		else if (i == 21) {
			options[3].add(items[i]);
		}
		else {
			options[4].add(items[i]);
		}
		
		switch (i) {
			case 4: case 5: case 7: case 13: case 14: case 20: case 21: case 22:
			items[i].setEnabled(false);
			break;
		}
		if (i != 18)
		items[i].addActionListener(this);
	}
	
	JPopupMenu pop = new JPopupMenu();
	JMenuItem[] popItems = new JMenuItem[3];
	for (int j = 0; j == popItems.length; j++) {
		popItems[j] = items[j+7];
		pop.add(popItems[j]);
		popItems[j].addActionListener(this);
	}
	
	
	mainText.addMouseListener(new MouseAdapter() {
		public void mousePressed(MouseEvent me) {
			if (me.isPopupTrigger())
				pop.show(me.getComponent(), me.getX(), me.getY());
		}
		public void mouseReleased(MouseEvent me) {
			if(me.isPopupTrigger())
				pop.show(me.getComponent(), me.getX(), me.getY());
		}
	});
	
	frame.add(optionBar, BorderLayout.NORTH);
	frame.add(textScrollPane, BorderLayout.CENTER);
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);
	
		
	}
	
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "New": {
				if (saved = false) {
					int answer = JOptionPane.showConfirmDialog(frame, "You have not saved. Do you want to save this file? ", "Exit", JOptionPane.YES_NO_CANCEL_OPTION);
					switch (answer) {
				case JOptionPane.YES_OPTION:
					this.actionPerformed(new ActionEvent(frame, 0, "Save"));
					saved = true;
					mainText = new JTextArea();
					break;
				case JOptionPane.NO_OPTION:
					mainText = new JTextArea();
					break;
				}
				
				}
				else {
					currentFile = null;
					int temp = mainText.getCaretPosition();
					mainText.select(0, 10);
					frame.setTitle(mainText.getSelectedText() + " - Notepad");
					mainText.select(temp, temp);
				}
				break;
			}
			
			case "Open": {
				JFileChooser j = new JFileChooser();
				CustomJavaFileFilter cust1 = new CustomJavaFileFilter();
				CustomJavaFileFilter cust2 = new CustomJavaFileFilter();
				cust1.setFileType(".txt");
				cust2.setFileType(".java");
				j.setFileFilter(cust1);
				j.setFileFilter(cust2);
				FileReader fr;
				int result = j.showOpenDialog(null);
				
					if (result == JFileChooser.APPROVE_OPTION) {
					try {
					
					    currentFile = j.getSelectedFile();
						fr = new FileReader(currentFile);
						mainText.read(fr, null);
						frame.setTitle(j.getSelectedFile().getName() + " - Notepad");
						saved = true;
					}
					catch (IOException ex) {
						JOptionPane.showMessageDialog(frame, "Invalid file or no file here!");
					}
					}
			 break;           
			}
			
			case "Save": {
				FileWriter fw;
				if (currentFile == null) {
					this.actionPerformed(new ActionEvent(frame, 0, "Save As"));
					
				}
				else {
					try {
					fw = new FileWriter(currentFile);
					mainText.write(fw);
					frame.setTitle(currentFile.getName() + " - Notepad");
					saved = true;
					}
				catch (IOException ex) {
					JOptionPane.showMessageDialog(frame, "Invalid file or no file here!");
					}
				}
		
				  
			saved = true;
			break;	
			}
			
			
			case "Save As": {
				JFileChooser j = new JFileChooser();
				FileWriter fw;
				int result = j.showSaveDialog(null);
				
					if (result == JFileChooser.APPROVE_OPTION) {
					try {
					
					    File tempText = j.getSelectedFile();
					    
						fw = new FileWriter(tempText);
						
						if (j.getSelectedFile().exists()) {
					        if(JOptionPane.showConfirmDialog(j, "Do you want to replace this file?", "Replace File?",JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION) {
					            currentFile = tempText;
					            mainText.write(fw);
					        }
					        else {
					            this.actionPerformed(new ActionEvent(frame, 0, "Save As"));
					        }
					        }
					        frame.setTitle(j.getSelectedFile().getName() + " - Notepad");
						saved = true; 
					
						
					}
					catch (IOException ex) {
						JOptionPane.showMessageDialog(frame, "Invalid file or no file here!");
					}
						
			          
				saved = true;
					}
				break;  
				}
				
			case "Exit": {
				if (saved == true) {
				int answer = JOptionPane.showConfirmDialog(frame, "Exit now? ", "Exit", JOptionPane.YES_NO_OPTION);
			switch (answer) {
				case JOptionPane.YES_OPTION:
					System.exit(0);
					break;
				case JOptionPane.NO_OPTION:
					break;
				}
			}
			
			else{ 
				int answer = JOptionPane.showConfirmDialog(frame, "You have not saved. Do you want to save and exit now? ", "Exit", JOptionPane.YES_NO_CANCEL_OPTION);
			switch (answer) {
				case JOptionPane.YES_OPTION:
					saved = true;
					System.exit(0);
					break;
				case JOptionPane.NO_OPTION:
					System.exit(0);
					break;
				}
			break;
			}
			break;
			}
			
			case "Cut": {
				mainText.cut();
				break;
			}
			
			case "Copy": {
				mainText.copy();
				break;
			}
			
			case "Paste": {
				mainText.paste();
				break;
			}
			
			case "Delete": {
			mainText.replaceRange("", mainText.getSelectionStart(), mainText.getSelectionEnd());
			break;
		}
		
			case "Find": {
				JDialog findD = new JDialog(frame, "Find:");
				findD.setSize(150, 100);
				JLabel findL = new JLabel("Find:");
				JTextField findT = new JTextField();
				JButton findB1 = new JButton("Find");
				findB1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						
					}
				});
				JButton cancel = new JButton("Cancel");
			}
			
			case "Go to": {
			int pos = GoToDlg.displayGUI(frame, mainText.getCaretPosition());
			if (pos < 0 && pos > mainText.getLineCount()) {
				JOptionPane.showMessageDialog(frame, "This line does not exist!");
			}
			else {
				JOptionPane.showMessageDialog(frame, "The line you seek is line "+ Integer.toString(pos)+ ".");
			}
			break;
		}
		
			
			case "Font": {
					Font font = JFontChooser.showFontViewer(frame, frame.getFont());
					mainText.setFont(font);
				break;    
			}
			
		
		
		case "About": {
			JOptionPane.showMessageDialog(frame, "Notepad by O. Reid");
			break;
		}
	}
		
		
		
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Notepad();
			}
		});
	}
	
	class CustomJavaFileFilter extends FileFilter {
		
		private String filetype;
		public boolean accept(File file) {
			if(file.getName().endsWith(filetype))
				return true;
			if(file.isDirectory())
			return true;
			
			return false;
		}
		
		public String getDescription() {
			String desc = "";
			if (filetype ==".java") {
					desc = "Java Source Files";
				}
			else if (filetype == ".txt") {
					desc = "Text Documents";
			}
			return desc;
		}
		
		public void setFileType(String fType) {
			filetype = fType;
		}
		}

		
	}

