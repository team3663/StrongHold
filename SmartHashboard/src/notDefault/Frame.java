
package notDefault;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Frame implements Runnable{
	ArrayList<SubTablePanel> subs;
	NetworkTable table;
	JPanel systems;
	JFrame frame;
	OperationWatchAndTimer owat;
	Set<String> tableList;
	Archiver archiver;
	String ip;
	int tableSize = 0;
	float offset = (float)Math.random();
	boolean isDrive = false;

	public Frame(String ipAdr){
		ip = ipAdr;
	}
	public Frame(String ipAdr, boolean isDrive){
		ip = ipAdr;
		this.isDrive = isDrive;
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
			subs = new ArrayList<SubTablePanel>();
			System.out.println("Table size: " + tableList.size());
			tableSize = tableList.size();
			sleep(10);
		}while(tableSize == 0);
		
		System.out.println("Connected");
		
		//CREATING EACH COLUMN//
		populateSubs();
		initSystems();
		/////////////////////////////
		addToFrame(systems, "Center");
		/////////////////////////////
	}
	@Override
	public void run(){
		init();
		boolean updateFlag = true;
		long startTime = System.currentTimeMillis();
		int delay = 1500;
		while(true){
			if(/*owat.isEnabled()*/true){
				for(SubTablePanel stp:subs){
					if(!stp.subTable.equals("operation")){
						stp.update();
					}
				}
				if(System.currentTimeMillis() > startTime + delay){
					updateFlag = true;
					startTime = System.currentTimeMillis();
				}
				if(updateFlag){
//					for(SubTablePanel stp:subs){
//						stp.refresh();
//					}
					updateFlag = false;
					//if the tableList has changed
					if(tableSize != table.getSubTables().size()){
						System.out.println("~~~~~Table has changed~~~~~");
						Set<String> oldTable = tableList;
						tableList = table.getSubTables();
						//get the difference between the old table and current one
						for(String s: tableList){
							boolean addNew = true;
							for(String old: oldTable){
								if(s.equals(old)){
									addNew = false;
									break;
								}
							}
							if(addNew){
								addNewSub(s);
							}
						}
						tableSize = tableList.size();
					}
				}
			}else{
				updateFlag = true;
			}
			sleep(2);
		}
	}
	public void addNewSub(String name){
		SubTablePanel addThis = new SubTablePanel(name,table,Color.getHSBColor(
				(float)(offset + Math.random()/5), 
				(float)(0.3 + Math.random()/4.6), 
				(float)(0.6f + (Math.random()/3))),archiver);
		subs.add(addThis);
		if(!isDrive){
			systems.add(addThis);
			systems.revalidate();
		}
		addThis.init();
//		new Thread(addThis).start();
	}
	public void populateSubs(){
		if(isDrive){
			Font f = new Font("SansSerif",Font.PLAIN,12);
			for(String k:tableList){
				subs.add(new SubTablePanel(k,table,Color.DARK_GRAY,archiver,f));
				System.out.println("SubTable: " + k);
			}
		}
		else{
			for(String k:tableList){
				subs.add(new SubTablePanel(k,table,Color.getHSBColor(
						(float)(offset + Math.random()/5), 
						(float)(0.3 + Math.random()/4.6), 
						(float)(0.6f + (Math.random()/3))),archiver));
				System.out.println("SubTable: " + k);
			}
		}
	}
	public void initSystems(){
		systems = new JPanel();
		for(SubTablePanel stp: subs){
			if(!isDrive){
				systems.add(stp);
			}
			stp.init();
			if(stp.subTable.equals("operation")){
				if(isDrive){
					systems.add(stp);
					System.out.println("Added " + stp.subTable + " in drive mode");
				}
				owat = new OperationWatchAndTimer(stp,archiver);
				new Thread(owat).start();
			}
		}
		if(!isDrive){
			systems.setLayout(new GridLayout(2,subs.size(),4,4));
		}else{
			systems.setLayout(new GridLayout(1,1));
		}
//		systems.setPreferredSize(new Dimension(0,300));
	}
	public void initNetworkTable(String ip){
		NetworkTable.setClientMode();
		NetworkTable.setIPAddress(ip);
		table = NetworkTable.getTable("Gui");
		sleep(1500);
	}
	public void setWindowsLookAndFeel(){
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}
	}
	public void initJFrame(){
		frame = new JFrame("Smart Hashboard - Drive Mode: " + Boolean.toString(isDrive));
		frame.setBounds(0,0,1200,520);
		frame.setBackground(Color.DARK_GRAY);
		frame.getContentPane().setBackground(Color.DARK_GRAY);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent windowEvent) {
		    	frame.setVisible(false);
		    	System.out.println("///////////////////////");
		    	System.out.println("Closing... please wait");
		    	System.out.println("///////////////////////");
		    	if(owat != null && owat.isEnabled()){
		    		owat.export();
		    	}
		        System.exit(0);
		    }
		});
		frame.setVisible(true);
	}
	public void addToFrame(Component c, String position){
		frame.getContentPane().add(c, position);
	}
	public void sleep(int milliseconds){
		try{
			Thread.sleep(milliseconds);
		}catch(InterruptedException ex){
			System.err.println("This will never happen");
		}
	}
}