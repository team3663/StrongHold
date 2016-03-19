
package notDefault;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
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

public class DriveFrame implements Runnable{
	SubTablePanel[] subs;
	NetworkTable table;
	JPanel systems;
	JFrame frame;
	OperationWatchAndTimer owat;
	Set<String> tableList;
	Archiver archiver;
	String ip;
	int tableSize = 0;
	
	public DriveFrame(String ipAdr){
		ip = ipAdr;
	}
	public void init(){
		initJFrame();
		System.out.println("Initialized JFrame");
		initNetworkTable(ip);
		System.out.println("Initialized Network Table");
		
		archiver = new Archiver();
		
		do{
			tableList = table.getSubTables();
			subs = new SubTablePanel[tableList.size()];
			tableSize = tableList.size();
			System.out.println("Table size: " + tableSize);
			sleep(1300);
		}while(subs.length == 0);
		
		System.out.println("Connected");
		
		//CREATING EACH COLUMN//
		Font myFont = new Font("SanSerif", Font.PLAIN, 12);
		int count = 0;
		for(String k:tableList){
			subs[count] = new SubTablePanel(k,table,Color.DARK_GRAY,archiver,myFont);
			count++;
			System.out.println("SubTable: " + k);
		}
		initSystems();
		frame.getContentPane().revalidate();
		systems.revalidate();
		/////////////////////////////
		addToFrame(systems, "Center");
		/////////////////////////////
	}
	@Override
	public void run(){
		init();
	}
	public void initSystems(){
		systems = new JPanel();
		for(int i=0;i<subs.length;i++){
//			systems.add(subs[i]);
			subs[i].init();
			new Thread(subs[i]).start();
			if(tableList.toArray()[i].equals("operation")){
				owat = new OperationWatchAndTimer(subs[i],archiver);
				systems.add(subs[i]);
				new Thread(owat).start();
			}
		}
		systems.setLayout(new GridLayout(1,1)); //columns, rows
//		systems.setPreferredSize(new Dimension(0,300));
	}
	public void initNetworkTable(String ip){
		NetworkTable.setClientMode();
		NetworkTable.setIPAddress(ip);
		table = NetworkTable.getTable("Gui");
		sleep(2000);
	}

	public void initJFrame(){
		frame = new JFrame("Smart Hashboard");
		frame.setBounds(0,0,1200,520);
		frame.setBackground(Color.PINK);
		frame.getContentPane().setBackground(Color.white);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent windowEvent) {
		    	frame.setVisible(false);
		    	System.out.println("=======================");
		    	System.out.println("Closing... please wait");
		    	System.out.println("=======================");
		    	if(owat != null){
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
			System.err.println("We didn't get proper sleep");
		}
	}
}