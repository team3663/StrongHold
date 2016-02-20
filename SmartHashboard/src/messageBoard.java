import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class messageBoard extends JPanel{
	JTextPane txt;
	public messageBoard(){
		setBackground(Color.BLACK);
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		txt = new JTextPane();
		txt.setSize(new Dimension(500,500));
		add(txt, BorderLayout.SOUTH);
		txt.setAlignmentX(LEFT_ALIGNMENT);
		txt.setAlignmentY(BOTTOM_ALIGNMENT);
	}
	public void say(String msg){
		txt.setText(msg);
	}
}
