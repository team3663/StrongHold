import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class messageBoard extends JPanel{
	JLabel txt;
	public messageBoard(){
		setBackground(Color.DARK_GRAY);
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		txt = new JLabel("hello");
	}
	public void say(String msg){
		txt.setText(msg);
	}
}
