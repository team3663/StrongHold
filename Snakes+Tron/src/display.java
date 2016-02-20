import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.xml.crypto.dsig.keyinfo.KeyValue;


public class display extends JFrame{

	public JPanel contentPane;
	public ButtonGroup group = new ButtonGroup();
	private Graphics g;
	
	public TheBrain theBrain;
	private Thread tBrain;

	/**
	 * Launch the application.
	 */
	public void init(PlayerConnection[] a){	
		setfree(a);
		theBrain = new TheBrain(contentPane, a);
		tBrain = new Thread(theBrain);
		tBrain.start();
		g = contentPane.getGraphics();
		
	}
	/**
	 * Create the frame.
	 */
	public display() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(1020, 100, 920, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.setBackground(Color.black);
	}
	
	public void setfree(PlayerConnection[] a){
		for(int i = 0; i < a.length; i++){
			a[i].wat.start= true;
		}
	}
}




