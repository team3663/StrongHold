import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class SubTablePanel extends JPanel implements Runnable{
	NetworkTable table;
	Set<String> guiElements;
	String[] sList;
	JLabel[] jList;
	String subTable;
	Color bckg;
	Archiver archy;
	messageBoard msgb;
	
	public SubTablePanel(String subTable, NetworkTable table, Color bckg, Archiver archy, messageBoard msgb){
		this.table = table;
		this.subTable = subTable;
		this.bckg = bckg;
		this.archy = archy;
		this.msgb = msgb;
		setBackground(bckg);
	}
	public void init(){
        getNames();
        fillJLabels();
        fillJFrame();
        setLayout(new GridLayout(10,1,0,0));
		setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        setVisible(true);
	}
	public void organizeElements(){
		Arrays.sort(sList);
	}
	public void refresh(){
		for(int i=0;i<jList.length;i++){
			remove(jList[i]);
		}
//		remove(grid);
        getNames();
        fillJLabels();
        fillJFrame();
	}
	public void fillJLabels(){
		for(int i=0;i<sList.length;i++){
			jList[i] = new JLabel(sList[i] + ": " + table.getValue(sList[i],3663));
			System.out.println(sList[i]);
			msgb.say(sList[i]);
		}
	}
	public void fillJFrame(){
		for(int i=0;i<sList.length;i++){
			add(jList[i]);
		}
	}
	public int getNames(){
        //initialize set and arrays
		//guiElements = table.getKeys();
		guiElements = table.getSubTable(subTable).getKeys();
		System.out.println("There are " + guiElements.size() + " elements in the Set");
		msgb.say("There are " + guiElements.size() + " elements in the Set");
		sList = new String[guiElements.size()+1];
		jList = new JLabel[guiElements.size()+1];
		//populate String Array
		int count = 1;
		sList[0] = "!";
		for(String k:guiElements){
			sList[count] = k;
			count++;
		}
		organizeElements();
		sList[0] = subTable;
		for(int i=1;i<sList.length;i++){
			archy.addNewColumn(sList[i]);
		}
		return count-1;
	}
	@Override
	public void run(){
		int count = 0;
		long startTime = System.currentTimeMillis();
		while(true){
			sleep(2); //necessary to not blow up your CPU
			if(count%1000 == 0){
//				update(hue,)
			}
//			if(subTable.equals("drive")){
//				double speedLeft = table.getSubTable(subTable).getNumber(sList[2],3663);
//				double speedRight = table.getSubTable(subTable).getNumber(sList[5],3663);
//				g = (((speedLeft + speedRight)/2)+1)*127.5;
//				r = 255 - g;
//				if(g<r)b=g;
//				else b=r;
//				try{
//					update(new Color((int)r,(int)g,(int)b));
//				}catch(Exception e){
//					System.err.println("Color error in " + subTable);
//				}
//			}
//			else{
//				update(bckg);
//			}
			update(bckg);
			count++;
			//Auto CHECK FOR REMOVED ELEMENTS//
//			if(System.currentTimeMillis() > startTime + 10000){
//				startTime = System.currentTimeMillis();
//				emptyTable();
//				System.out.println("Emptied NetworkTables");
//			}
			//CHECK IF NEW ELEMENTS//
			if(count%100 == 0){
				do{
					int oldSize = guiElements.size();
					guiElements = table.getSubTable(subTable).getKeys();
					int newSize = guiElements.size();
					if(oldSize == newSize) break;
					refresh();
				}while(false);
			}
		}
	}
	public void emptyTable(){
		for(int i=0;i<sList.length;i++){
			table.getSubTable("sensor").delete(sList[i]);
		}
	}
	public void sleep(int milliseconds){
		try{
			Thread.sleep(milliseconds);
		}catch(InterruptedException ex){
			System.err.println("We didn't get proper sleep");
		}
	}
	public void update(Color c){
		setBackground(c);
		for(int i=0;i<sList.length;i++){
			if(i != 0){
				Object o = table.getSubTable(subTable).getValue(sList[i],3663);
				jList[i].setText(sList[i] + ": " + o);
				archy.addValue(sList[i], o.toString());
			}else{
				//titles
				jList[i].setText("---------" + subTable.toUpperCase() + "---------");
			}
		}
	}
	public String get(int index){
		return (table.getSubTable(subTable).getValue(sList[index],3663).toString());
	}
}
