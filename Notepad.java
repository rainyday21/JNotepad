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
//import javax.swing.text.BadLocationException;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.BadLocationException;

//import java.util.*;
import java.io.*;

public class Notepad implements ActionListener, DocumentListener{
	private JFrame frame = new JFrame("Untitled - Notepad");
	private JTextArea mainText;
	private Font defaultFont = new Font("Courier", Font.PLAIN, 12);
	private boolean saved = true;
	private File currentFile;
	
	
	public Notepad() {
	frame.setLayout(new BorderLayout());
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setSize(800, 600);
	
	mainText = new JTextArea();
	mainText.setFont(defaultFont);
	mainText.getDocument().addDocumentListener(this);
	
	JScrollPane textScrollPane = new JScrollPane(mainText);
	JMenuBar optionBar = new JMenuBar();
	
	JMenu[] options = new JMenu[5];
	JMenuItem[] items = new JMenuItem[24];
	
	
	options[0] = new JMenu("File"); 
	options[0].setMnemonic('F');
	
	options[1] = new JMenu("Edit");
	options[1].setMnemonic('E');
	
	options[2] = new JMenu("Format");
	options[2].setMnemonic('o');
	
	options[3] = new JMenu("View");
	options[1].setMnemonic('V');
	
	options[4] = new JMenu("Help");
	options[4].setMnemonic('H'); //items and accellerators
	
	
	items[0] = new JMenuItem("New");
	items[0].setMnemonic('N');
	items[0].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
	
	items[1] = new JMenuItem("Open");
	items[1].setMnemonic('O');
	items[1].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
	
	items[2] = new JMenuItem("Save");
	items[2].setMnemonic('S');
	items[2].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
	
	items[3] = new JMenuItem("Save As");
	items[3].setMnemonic('A');
	
	items[4] = new JMenuItem("Page Setup");
	items[4].setMnemonic('u');
	
	items[5] = new JMenuItem("Print");
	items[5].setMnemonic('P');
	items[5].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
	
	items[6] = new JMenuItem("Exit");
	items[6].setMnemonic('x');
	
	items[7] = new JMenuItem("Undo");
	items[7].setMnemonic('U');
	
	items[8] = new JMenuItem("Cut");
	items[8].setMnemonic('t');
	items[8].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
	
	items[9] = new JMenuItem("Copy");
	items[9].setMnemonic('C');
	items[9].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
	
	items[10] = new JMenuItem("Paste");
	items[10].setMnemonic('P');
	items[10].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
	
	items[11] = new JMenuItem("Delete");
	items[11].setMnemonic('l');
	items[11].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
	
	items[12] = new JMenuItem("Find");
	items[12].setMnemonic('F');
	items[12].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
	
	items[13] = new JMenuItem("Find Next");
	items[13].setMnemonic('N');
	
	items[14] = new JMenuItem("Replace");
	items[14].setMnemonic('R');
	items[14].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK));
	
	items[15] = new JMenuItem("Go to");
	items[15].setMnemonic('G');
	items[15].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK));
	
	items[16] = new JMenuItem("Select All");
	items[16].setMnemonic('A');
	items[16].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
	
	items[17] = new JMenuItem("Time/Date");
	items[17].setMnemonic('D');
	items[17].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
	
	items[18] = new JCheckBoxMenuItem("Word Wrap");
	items[18].setMnemonic('W');
	
	items[18].addItemListener(new ItemListener() {
		public void itemStateChanged(ItemEvent ie) {
			if (ie.getStateChange() == ItemEvent.SELECTED)
				mainText.setLineWrap(true);
			else
				mainText.setLineWrap(false);
		}
	});
	items[19] = new JMenuItem("Font");
	items[19].setMnemonic('F');
	
	items[20] = new JMenuItem("View Help");
	items[20].setMnemonic('H');
	
	items[21] = new JMenuItem("Status Bar");
	items[21].setMnemonic('S');
	
	items[22] = new JMenuItem("Extra Credit");
	items[22].setMnemonic('x');
	
	items[23] = new JMenuItem("About Notepad");
	items[23].setMnemonic('A');
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
	JMenuItem[] popItems = new JMenuItem[4];
	popItems[0] = new JMenuItem("Cut");
	popItems[1] = new JMenuItem("Copy");
	popItems[2] = new JMenuItem("Paste");
	popItems[3] = new JMenuItem("Delete");
	for (int j = 0; j < popItems.length; j++) {
		popItems[j].addActionListener(this);
		pop.add(popItems[j]);
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
				if (saved == false && currentFile != null) {
					int answer = JOptionPane.showConfirmDialog(frame, "You have not saved. Do you want to save this file? ", "Exit", JOptionPane.YES_NO_CANCEL_OPTION);
					switch (answer) {
				case JOptionPane.YES_OPTION:
					this.actionPerformed(new ActionEvent(frame, 0, "Save"));
					saved = true;
					currentFile = null;
					this.actionPerformed(new ActionEvent(frame, 0, "Select All"));
					this.actionPerformed(new ActionEvent(frame, 0, "Delete"));
					frame.setTitle("Untitled - Notepad");
					break;
				case JOptionPane.NO_OPTION:
					currentFile = null;
					this.actionPerformed(new ActionEvent(frame, 0, "Select All"));
					this.actionPerformed(new ActionEvent(frame, 0, "Delete"));
					frame.setTitle("Untitled - Notepad");
					saved = true;
					break;
				}
				}
				else if (currentFile != null && saved == true) {
					currentFile = null;
					this.actionPerformed(new ActionEvent(frame, 0, "Select All"));
					this.actionPerformed(new ActionEvent(frame, 0, "Delete"));
					frame.setTitle("Untitled - Notepad");
					break;
				}
				else if (saved == false){
					int temp = mainText.getCaretPosition();
					mainText.select(0, 10);
					currentFile = new File(mainText.getSelectedText());
					frame.setTitle(currentFile.getName() + " - Notepad");
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
				if (saved == false) {
					int answer = JOptionPane.showConfirmDialog(frame, "You have not saved. Do you want to save this file? ", "Are you sure?", JOptionPane.YES_NO_CANCEL_OPTION);
					if (answer == JOptionPane.YES_OPTION) 
					this.actionPerformed(new ActionEvent(frame, 0, "Save"));
					else {
						int result = j.showOpenDialog(null);
						if (result == JFileChooser.APPROVE_OPTION) {
						try {
							currentFile = j.getSelectedFile();
							fr = new FileReader(currentFile);
							mainText.read(fr, null);
							fr.close();
							mainText.getDocument().addDocumentListener(this);
							frame.setTitle(j.getSelectedFile().getName() + " - Notepad");
							saved = true;
						}
						catch (IOException ex) {
							JOptionPane.showMessageDialog(frame, "Invalid file or no file here!");
						}
						}
					}
					}
				
				else {
				int result = j.showOpenDialog(null);
					if (result == JFileChooser.APPROVE_OPTION) {
					try {
					    currentFile = j.getSelectedFile();
						fr = new FileReader(currentFile);
						mainText.read(fr, null);
						fr.close();
						mainText.getDocument().addDocumentListener(this);
						frame.setTitle(j.getSelectedFile().getName() + " - Notepad");
						saved = true;
					}
					catch (IOException ex) {
						JOptionPane.showMessageDialog(frame, "Invalid file or no file here!");
					}
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
				CustomJavaFileFilter cust1 = new CustomJavaFileFilter();
				CustomJavaFileFilter cust2 = new CustomJavaFileFilter();
				cust1.setFileType(".txt");
				cust2.setFileType(".java");
				j.setFileFilter(cust1);
				j.setFileFilter(cust2);
				FileWriter fw;
				int result = j.showSaveDialog(null);
				
					if (result == JFileChooser.APPROVE_OPTION) {
					try {
					
					    File tempText = j.getSelectedFile();
					    
						fw = new FileWriter(tempText);
						
						if ((currentFile == j.getSelectedFile()) && j.getSelectedFile().exists()) {
					        if(JOptionPane.showConfirmDialog(j, "Do you want to replace this file?", "Replace File?",JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION) {
					            currentFile = tempText;
					            mainText.write(fw);
					        }
					        else {
					            this.actionPerformed(new ActionEvent(frame, 0, "Save As"));
					        }
					        }
					        else {
					            currentFile = tempText;
					            frame.setTitle(currentFile.getName() + " - Notepad");
					            mainText.write(fw);
					        }  
						saved = true; 
					}
					catch (IOException ex) {
						JOptionPane.showMessageDialog(frame, "Invalid file or no file here!");
					}
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
					this.actionPerformed(new ActionEvent(frame, 0, "Save"));
					System.exit(0);
					break;
				case JOptionPane.NO_OPTION:
					System.exit(0);
					break;
				}
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
				JPanel buttons = new JPanel(new BorderLayout());
				JLabel findL = new JLabel("Find:");
				JTextField findT = new JTextField();
				JButton findB1 = new JButton("Find");
				JButton findB2 = new JButton("Find Next");
				findB1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						int results = 0;
						String inquiry = findT.getText();
						String[] totalWords = mainText.getText().split("\\W+");
						
						for (String t: totalWords) {
							if (inquiry == t) {
								results++;
							}
						}
					}
					//public void findAndSplit() extends split()
					
					
				});
				JButton can = new JButton("Cancel");
				can.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						findD.setVisible(false);
					}
				});
			}
			
			case "Go to": {
			int pos = GoToDlg.displayGUI(frame, mainText.getCaretPosition());
			if (pos < 0 && pos > mainText.getLineCount()) {
				JOptionPane.showMessageDialog(frame, "This line does not exist!");
			}
			else {
				try {
				
				int loc = mainText.getLineStartOffset(pos);
				mainText.setCaretPosition(loc); }
				catch (BadLocationException b) {
					JOptionPane.showMessageDialog(frame, "This line does not exist!");
				}
			}
			break;
		}
			case "Select All": {
				mainText.select(0, mainText.getText().length());
				break;
			}
			
			case "Time/Date": {
				//Date curDate = new Date();
				break;
			}
			
			case "Font": {
					Font font = JFontChooser.showFontViewer(frame, defaultFont);
					mainText.setFont(font);
				break;    
			}
			
		
		
		case "About Notepad": {
			JOptionPane.showMessageDialog(frame, "Notepad by O. Reid");
			break;
		}
	}
		
		
		
	}
	
	public void insertUpdate(DocumentEvent e) {
		saved = false;
		if (currentFile != null) {
			frame.setTitle(currentFile.getName() + "* - Notepad");
		}
		else {
			frame.setTitle("Untitled* - Notepad");
		}
		
	}
	public void changedUpdate(DocumentEvent e) {
		saved = false;	
		if (currentFile != null) {
			frame.setTitle(currentFile.getName() + "* - Notepad");
		}
		else {
			frame.setTitle("Untitled* - Notepad");
		}
	}
	public void removeUpdate(DocumentEvent e) {
		saved = false;
		if (currentFile != null) {
			frame.setTitle(currentFile.getName() + "* - Notepad");
		}
		else {
			frame.setTitle("Untitled* - Notepad");
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

