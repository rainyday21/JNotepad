import javax.swing.*;
import java.awt.*;

public class GoToDlg {

	private int position;
	
	public int getPosition() {
		return position;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	
	public static int displayGUI(JFrame parent, int pos) {
		GoToDlg dlg = new GoToDlg();
		if (pos >= 0) {
			dlg.setPosition(pos);
		}
		JDialog dialog = new JDialog(parent, "Go to Line");
		dialog.setLocationRelativeTo(parent);
		dialog.setLayout(new GridLayout(3,1));
		dialog.setSize(300, 300);
		JLabel label = new JLabel("Line Number: ");
		JTextField enter = new JTextField();
		
		JButton go = new JButton("Go to");
		
		go.addActionListener(ae -> {
			try {
			dlg.setPosition(Integer.parseInt(enter.getText()));
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Please type in only numbers!");
			}
			dialog.setVisible(false);
		});
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(ae -> {
			dialog.setVisible(false);
		});
		
		dialog.add(label);
		dialog.add(enter);
		dialog.add(go);
		dialog.add(cancel);
		dialog.getRootPane().setDefaultButton(go);
		dialog.setVisible(true);
			return dlg.getPosition();
		}
	

		

public static void main(String[] args) {
	SwingUtilities.invokeLater(() -> {
				int pos = GoToDlg.displayGUI(null, 0);
			});
		}

}