import java.awt.event.*;
import java.io.IOException;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.BadLocationException;

import java.util.*;
import java.io.*;

public class JNotePad implements ActionListener{
    private JFrame frame = new JFrame("Untitled - Notepad");
    private JTextArea mainText = new JTextArea();
    public JNotePad() {
    frame.setLayout(new BorderLayout());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(640, 480);
    JScrollPane textScrollPane = new JScrollPane(mainText);
    JMenuBar optionBar = new JMenuBar();
    
    JMenu[] options = new JMenu[5];
    JMenuItem[] items = new JMenuItem[5];
    
    
    options[0] = new JMenu("File");
    items[0] = new JMenuItem("Open");
    options[0].add(items[0]);
    options[0].add(new JMenuItem("-----"));
    items[1] = new JMenuItem("Exit");
    options[0].add(items[1]);
    

    options[1] = new JMenu("Format");
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
    
    options[2] = new JMenu("Edit");
    options[3] = new JMenu("View");
    JMenuItem go = new JMenuItem("Go to");
    go.addActionListener(this);
    options[3].add(go);
    options[4] = new JMenu("Help");
    items[4] = new JMenuItem("About");
    options[4].add(items[4]);
        
    
    
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
            case "Go to": {
                int pos = GoToDlg.displayGUI(frame, mainText.getCaretPosition());
                if (pos < 0 && pos > mainText.getLineCount()) {
                    JOptionPane.showMessageDialog(frame, "This line does not exist!");
                }
                else {
                try {
                mainText.setCaretPosition(mainText.getLineStartOffset(pos)); 
                }
                catch (BadLocationException bl) {
                    JOptionPane.showMessageDialog(frame, "This position is invalid");
                    bl.printStackTrace();
                }
                /*for (int i = 0; i < (mainText.getLineCount() & pos); i++) {
                    
                    if (pos = i) {
                          
                    }
                } */
                }
                
            }
            case "About": {
                JOptionPane.showMessageDialog(frame, "Notepad by O. Reid");
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JNotePad();
            }
        });
    }


}