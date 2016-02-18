import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
//import com.sun.java.swing.plaf.motif.*;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Frame{

	public static void main(String[] args) {
		Frame hi = new Frame();
	}
	
	SubTablePanel[] subs;
	public Frame(){
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JFrame frame = new JFrame("Smart Hashboard");
		frame.setVisible(true);
		frame.setSize(640, 480);
		frame.setBackground(Color.white);
		frame.getContentPane().setBackground(Color.white);
		JButton refresh = new JButton("Refresh");
		refresh.setSize(60,30);
		initButton(refresh);
		
		NetworkTable table;
		NetworkTable.setClientMode();
		NetworkTable.setIPAddress("10.36.63.78");
		table = NetworkTable.getTable("Gui");
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {}

		Set<String> tableList = table.getSubTables();
		System.out.println("Table is Empty: " + tableList.isEmpty());
		subs = new SubTablePanel[tableList.size()];
		System.out.println(tableList.size());
		int count = 0;
		for(String k:tableList){
			subs[count] = new SubTablePanel(k,table,Color.getHSBColor(0.6f, 0.6f, 0.8f));
			count++;
			System.out.println("SubTable: " + k);
		}
		for(int i=0;i<subs.length;i++){
			frame.add(subs[i]);
		}
		frame.setLayout(new GridLayout(1,3,10,10));
		JPanel grid = new JPanel(new FlowLayout());
		frame.add(refresh);
		frame.add(grid);

		for(int i=0;i<subs.length;i++){
			subs[i].init();
			new Thread(subs[i]).start();
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void initButton(JButton b){
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				for(int i=0;i<subs.length;i++){
					subs[i].emptyTable();
				}
			}
			
		});
	}
}
