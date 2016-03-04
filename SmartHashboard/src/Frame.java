import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
//import com.sun.java.swing.plaf.motif.*;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Frame implements Runnable{
	SubTablePanel[] subs;
	NetworkTable table;
	JFrame frame;
	OperationWatchAndTimer owat;
	Set<String> tableList;
	messageBoard msgBoard;
	
	public Frame(String ipAdr){
		initNetworkTable(ipAdr);
		setWindowsLookAndFeel();
		initJFrame();
		
		JButton refresh = new JButton("Refresh");
		initRefreshButton(refresh);
		refresh.setPreferredSize(new Dimension(10,30));
		
		msgBoard = new messageBoard();
		Archiver archie = new Archiver();

		do{
			tableList = table.getSubTables();
			subs = new SubTablePanel[tableList.size()];
			System.out.println("Table size: " + tableList.size());
			sleep(1300);
		}while(subs.length == 0);

		int count = 0;
		for(String k:tableList){
			subs[count] = new SubTablePanel(k,table,Color.getHSBColor(
					(float)Math.random(), 
					(float)(Math.random()/3.6), 
					(float)(0.7f + (Math.random()/3.33))),archie,msgBoard);
			count++;
			System.out.println("SubTable: " + k);
		}
//		float h = (float)Math.random();
//		float s = 0.2f;
//		float b = 1.0f;
//		for(String k:tableList){
//			subs[count] = new SubTablePanel(k,table,Color.getHSBColor(h,s,b),archie);
//			s += 0.1f;
//			b -= 0.02f;
//			count++;
//			System.out.println("SubTable: " + k);
//		}
		JPanel systems = new JPanel();
		for(int i=0;i<subs.length;i++){
			systems.add(subs[i]);
			subs[i].init();
			new Thread(subs[i]).start();
			if(tableList.toArray()[i].equals("operation")){
				owat = new OperationWatchAndTimer(subs[i],archie);
				new Thread(owat).start();
			}
		}
		systems.setLayout(new GridLayout(0,subs.length));
		systems.setPreferredSize(new Dimension(0,260));
		///////////////////////////////////////////////////////
		frame.getContentPane().add(systems, BorderLayout.NORTH);
		frame.getContentPane().add(msgBoard, BorderLayout.WEST);
		frame.getContentPane().add(refresh, BorderLayout.SOUTH);
		///////////////////////////////////////////////////////
	}
	@Override
	public void run(){
		//periodically scan the table for new systems
		//if new system is found, update subs, add it to the frame, refresh the frame
		//figure out a way to tell the Archiver to offset the log by a certain amount
		//(b/c it started later than the systems at init)
		sleep(1500);
		while(true){
			//if the tableList has changed
			if(!tableList.equals(table.getSubTables())){
				System.out.println("~~~~~New SubTable found~~~~~~");
				do{
					tableList = table.getSubTables();
					subs = new SubTablePanel[tableList.size()];
					System.out.println("Table size: " + tableList.size());
					sleep(1300);
				}while(subs.length == 0);
				
			}
			sleep(3000);
		}
	}
	public void initNetworkTable(String ip){
		NetworkTable.setClientMode();
		NetworkTable.setIPAddress(ip);
		table = NetworkTable.getTable("Gui");
		sleep(2000);
	}
	public void setWindowsLookAndFeel(){
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
	}
	public void initJFrame(){
		frame = new JFrame("Smart Hashboard");
		frame.setVisible(true);
		frame.setBounds(-7,0,1200,480); //my computer screen puts it at +2, so...
//		frame.setBounds(0,0,640,480);
		frame.setBackground(Color.white);
		frame.getContentPane().setBackground(Color.white);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	if(owat != null){
		    		owat.export();
		    	}
		        System.exit(0);
		    }
		});
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
