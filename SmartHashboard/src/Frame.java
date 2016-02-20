import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
		NetworkTable table;
		NetworkTable.setClientMode();
		NetworkTable.setIPAddress("10.36.63.78");
		table = NetworkTable.getTable("Gui");
		
		sleep(2000);
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
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
		frame.setBounds(-2,0,640,480); //my computer screen puts it at +2, so...
//		frame.setBounds(0,0,640,480);
		frame.setBackground(Color.white);
		frame.getContentPane().setBackground(Color.white);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton refresh = new JButton("Refresh");
		initRefreshButton(refresh);
		refresh.setPreferredSize(new Dimension(10,75));
		
		messageBoard msgBoard = new messageBoard();
		
		Set<String> tableList;

		do{
			tableList = table.getSubTables();
			subs = new SubTablePanel[tableList.size()];
			System.out.println("Table size: " + tableList.size());
			sleep(1300);
		}while(subs.length == 0);
		
		int count = 0;
		for(String k:tableList){
			subs[count] = new SubTablePanel(k,table,Color.getHSBColor(0.6f, 0.6f, 0.8f));
			count++;
			System.out.println("SubTable: " + k);
		}
		JPanel systems = new JPanel();
		for(int i=0;i<subs.length;i++){
			systems.add(subs[i]);
			subs[i].init();
			new Thread(subs[i]).start();
		}
		systems.setLayout(new GridLayout(0,subs.length));
		systems.setPreferredSize(new Dimension(0,200));
		///////////////////////////////////////////////////////
		frame.getContentPane().add(systems, BorderLayout.NORTH);
		frame.getContentPane().add(msgBoard, BorderLayout.CENTER);
		frame.getContentPane().add(refresh, BorderLayout.SOUTH);
		///////////////////////////////////////////////////////
		msgBoard.say("Hello World");
		sleep(500);
		msgBoard.say("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	}
	public void initRefreshButton(JButton b){
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				for(int i=0;i<subs.length;i++){
					subs[i].emptyTable();
				}
			}
		});
	}
	public void sleep(int milliseconds){
		try{
			Thread.sleep(milliseconds);
		}catch(InterruptedException ex){
			System.err.println("We didn't get proper sleep");
		}
	}
}
