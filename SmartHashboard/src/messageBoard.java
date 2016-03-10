import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

public class MessageBoard extends JPanel{
	JTextArea jta = new JTextArea(7,80);
	JScrollPane jsp;
	public MessageBoard(){
//		((DefaultCaret) jta.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		jta.setBackground(Color.LIGHT_GRAY);
		jta.setText("What is love?");
		jta.setEditable(false);
		jsp = new JScrollPane(jta,
			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(jsp);
		
	}
	public void say(String msg){
		jta.append("\n" + msg);
		
		jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMaximum());
	}
}
