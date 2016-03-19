
package notDefault;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Set;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Frame implements Runnable{
	SubTablePanel[] subs;
	NetworkTable table;
	JPanel systems;
	JFrame frame;
	OperationWatchAndTimer owat;
	Set<String> tableList;
	Archiver archiver;
	String ip;
	int tableSize = 0;
	
	public Frame(String ipAdr){
		ip = ipAdr;
	}
	public void init(){
		initJFrame();
		System.out.println("Initialized JFrame");
		initNetworkTable(ip);
		System.out.println("Initialized Network Table");
		setWindowsLookAndFeel();
//		JButton refresh = new JButton("Refresh");
//		initRefreshButton(refresh);
//		refresh.setPreferredSize(new Dimension(10,30));
		
		archiver = new Archiver();
		
		do{
			tableList = table.getSubTables();
			subs = new SubTablePanel[tableList.size()];
			System.out.println("Table size: " + tableList.size());
			tableSize = tableList.size();
			sleep(1300);
		}while(subs.length == 0);
		
		System.out.println("Connected");
		
		//CREATING EACH COLUMN//
		populateSubs();
//		Box box = Box.createHorizontalBox();
//		box.add(msgBoard);
//		box.add(Box.createHorizontalGlue());
		initSystems();
		/////////////////////////////
		addToFrame(systems, "Center");
//		addToFrame(box, "South");
//		addToFrame(refresh, "South");
		/////////////////////////////
	}
	@Override
	public void run(){
		init();
		boolean updateFlag = true;
		sleep(1500);
		while(true){
			if(owat.isEnabled() && updateFlag){
				updateFlag = false;
				//if the tableList has changed
				tableList = table.getSubTables();
				if(tableSize != tableList.size()){
					tableSize = tableList.size();
					System.out.println("~~~~~" + "table has changed" + "~~~~~~");
					subs = new SubTablePanel[tableList.size()];
					System.out.println("Table size: " + tableList.size());
					frame.remove(systems);
					populateSubs();
					initSystems();
					addToFrame(systems, "North");
					sleep(1300);
				}
				sleep(3000);
			}else if(!owat.isEnabled()){
				updateFlag = true;
			}
			sleep(300);
		}
	}
	public void populateSubs(){
		int count = 0;
		float offset = (float)Math.random();
		for(String k:tableList){
			subs[count] = new SubTablePanel(k,table,Color.getHSBColor(
					(float)(offset + Math.random()/5), 
					(float)(0.3 + Math.random()/4.6), 
					(float)(0.6f + (Math.random()/3))),archiver);
			count++;
			System.out.println("SubTable: " + k);
		}
	}
	public void initSystems(){
		systems = new JPanel();
		for(int i=0;i<subs.length;i++){
			systems.add(subs[i]);
			subs[i].init();
			new Thread(subs[i]).start();
			if(tableList.toArray()[i].equals("operation")){
				owat = new OperationWatchAndTimer(subs[i],archiver);
				new Thread(owat).start();
			}
		}
		systems.setLayout(new GridLayout(2,subs.length,4,4));
//		systems.setPreferredSize(new Dimension(0,300));
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
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}
	}
	public void initJFrame(){
		frame = new JFrame("Smart Hashboard");
		frame.setBounds(0,0,1200,520);
		frame.setBackground(Color.DARK_GRAY);
		frame.getContentPane().setBackground(Color.DARK_GRAY);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent windowEvent) {
		    	frame.setVisible(false);
		    	System.out.println("=======================");
		    	System.out.println("Closing... please wait");
		    	System.out.println("=======================");
		    	System.out.println("Hey");
		    	if(owat != null){
		    		owat.export();
		    	}
		        System.exit(0);
		    }
		});
		frame.setVisible(true);
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
	public void addToFrame(Component c, String position){
		frame.getContentPane().add(c, position);
	}
	public void sleep(int milliseconds){
		try{
			Thread.sleep(milliseconds);
		}catch(InterruptedException ex){
			System.err.println("We didn't get proper sleep");
		}
	}
}