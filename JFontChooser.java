// 
// Name: Reid, Orrane
// Project: 4
// Due:       10/29/2021
// Course: CS-  2450-01-f21 
// 
// Description: 
//      Chooses fonts for the main JFrame
//
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.awt.GraphicsEnvironment;

public class JFontChooser{
    
    //Bottom text
    private static String sampleText = "the quick brown fox jumps over the lazy dog 0123456789";
    private static JLabel sample = new JLabel(sampleText);
    
    //Font list objects
    private static String[] fontList = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    //Font objects
    private static int style = 0;
    private static int index = 0;
    private static int size = 10;
    //Top Slider
    private static JSlider fontSize = new JSlider(4, 32, 10);
    private Font temp;
    
    public void setFont(Font t) {
        this.temp = t;
    }
    
    private Font getFont() {
        return temp;
    }
    
    public static Font showFontViewer(JFrame parent, Font init) {
      JFontChooser reFont = new JFontChooser();
      if (init != null)
        reFont.setFont(init);
      JDialog main = new JDialog(parent, "Get Font", true);
      
      main.setSize(750, 500);

      //Top area:
      JPanel top = new JPanel();
      top.setLayout(new BorderLayout());
      //fontSize conditions
      fontSize.setMajorTickSpacing(4);
      fontSize.setPaintTicks(true);
      fontSize.addChangeListener(ce -> {
        size = fontSize.getValue();
        sample.setFont(new Font(fontList[index], style, size));
        
      });
      top.add(new JLabel("Size:"), BorderLayout.NORTH);
      top.add(fontSize, BorderLayout.SOUTH);

      //Left area
      JPanel left = new JPanel();
      left.setLayout(new BorderLayout());    
      JList boxList = new JList(fontList);
      //boxList conditions
      boxList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      boxList.addListSelectionListener((le) -> {        
            index = boxList.getSelectedIndex();
            sample.setFont(new Font(fontList[index], style, size));                            
     });
      JScrollPane box = new JScrollPane(boxList);
      left.add(new JLabel("Fonts"), BorderLayout.NORTH);
      left.add(box, BorderLayout.CENTER);
      
      //Center area:
      JPanel center = new JPanel();
      center.setLayout(new GridLayout(4, 1));
      
      JRadioButton regular = new JRadioButton("Regular", true);
      JRadioButton italic = new JRadioButton("Italic");
      JRadioButton bold = new JRadioButton("Bold");
      ButtonGroup styleSelection = new ButtonGroup();
      center.add(new JLabel("Style"));
      regular.addActionListener(ae -> {
        style = 0;
        sample.setFont(new Font(fontList[index], style, size));
      });
      italic.addActionListener(ae -> {
        style = 1;
        sample.setFont(new Font(fontList[index], style, size));
      });
      bold.addActionListener(ae -> {
        style = 2;
        sample.setFont(new Font(fontList[index], style, size));
      });
      styleSelection.add(regular);
      styleSelection.add(italic);
      styleSelection.add(bold);
      center.add(regular);
      center.add(italic);
      center.add(bold);

      //Right area:
      JPanel right = new JPanel();
      right.setLayout(new GridLayout(5, 1));
      JCheckBox cap = new JCheckBox("All caps");
      JButton apply = new JButton("Apply");
      JButton cancel = new JButton("Cancel");
      cap.addItemListener((ie) -> {
            if (ie.getStateChange() == ItemEvent.SELECTED)
                sample.setText(sampleText.toUpperCase());
            else
            sample.setText(sampleText);
      });
      apply.addActionListener(ae -> {
        reFont.setFont( new Font(fontList[index], style, size));
        main.setVisible(false);
        
      });
      cancel.addActionListener(ae -> {
        main.setVisible(false);        
      });
      right.add(new JLabel("Effects"));
      right.add(cap);
      right.add(apply);
      right.add(cancel);

      //Bottom area:
      JPanel bottom = new JPanel();
      bottom.add(sample);
  
      //Window implementation
      main.add(top, BorderLayout.NORTH);
      main.add(left, BorderLayout.WEST);
      main.add(center, BorderLayout.CENTER);
      main.add(right, BorderLayout.EAST);
      main.add(bottom, BorderLayout.SOUTH);
      main.setLocationRelativeTo(parent);
      main.setVisible(true);
      
      return reFont.getFont();
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> {
                  Font r = JFontChooser.showFontViewer(null, null);
              });
          }

  }
  
