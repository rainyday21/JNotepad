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

import java.util.*;
import java.io.*;

public class Notepad implements ActionListener{
    private JFrame frame = new JFrame("Untitled - Notepad");
    private JTextArea mainText = new JTextArea();
    public Notepad() {
    frame.setLayout(new BorderLayout());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(640, 480);
    JScrollPane textScrollPane = new JScrollPane(mainText);
    JMenuBar optionBar = new JMenuBar();
    
    JMenu[] options = new JMenu[5];
    JMenuItem[] items = new JMenuItem[19];
    
    
    options[0] = new JMenu("File"); //File
    options[1] = new JMenu("Format");
    options[2] = new JMenu("Edit");
    options[3] = new JMenu("Print");
    options[4] = new JMenu("Help");
    
        
    items[0] = new JMenuItem("Open"); //Open
    options[0].add(items[0]);
    
    options[0].add(new JMenuItem("-----"));
    items[1] = new JMenuItem("Exit");
    options[0].add(items[1]); //option one
    

    
    items[2] = new JMenuItem("Font");
    options[1].add(items[2]);
    items[3] = new JMenuItem("Set Background");
    options[1].add(items[3]);
    JCheckBoxMenuItem word_wrap = new JCheckBoxMenuItem("Word Wrap");
    word_wrap.addItemListener(new ItemListener() {
        public void itemStateChanged(ItemEvent ie) {
            if(word_wrap.isSelected())
                mainText.setWrapStyleWord(true);
            else 
                mainText.setWrapStyleWord(false);
            }
    });
    options[1].add(word_wrap);
    
    
    
    
    for (JMenu o: options) {
        optionBar.add(o);
    }
    
    for (JMenuItem i: items) {
        i.addActionListener(this);
    }
    
    
    frame.add(optionBar, BorderLayout.NORTH);
    frame.add(textScrollPane, BorderLayout.CENTER);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    
        
    }
    
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Open": {
                JFileChooser j = new JFileChooser();
                FileReader fr;
                int result = j.showOpenDialog(null);
                
                    if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                    
                       File tempText = j.getSelectedFile();
                        fr = new FileReader(tempText);
                        mainText.read(fr, null);
                    }
                    catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "Error!");
                    }
                    }
                    else 
                        JOptionPane.showMessageDialog(frame, "Invalid file or no file here!");
             break;           
            }
            case "Exit": {
                int answer = JOptionPane.showConfirmDialog(frame, "Are you sure? ", "Exit now?", JOptionPane.YES_NO_OPTION);
            switch (answer) {
                case JOptionPane.YES_OPTION:
                    System.exit(0);
                    break;
                case JOptionPane.NO_OPTION:
                    break;
            }
            break;
            }
            case "Font": {
                    Font font = JFontChooser.showFontViewer(frame, frame.getFont());
                    mainText.setFont(font);
                break;    
            } 
            case "Set Background": {
                Color back = JColorChooser.showDialog(null, "Choose Color", null);
                if (back != null) {
                    mainText.setBackground(back);
                }
                break;
            } 
            case "Print": {
                JOptionPane.showMessageDialog(frame, "Printing...");
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
    

}