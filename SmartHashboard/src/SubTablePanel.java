import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
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
	MessageBoard msgb;
	Font f;

	public SubTablePanel(String subTable, NetworkTable table, Color bckg, Archiver archy, MessageBoard msgb){
		this.table = table;
		this.subTable = subTable;
		this.bckg = bckg;
		this.archy = archy;
		this.msgb = msgb;
		setBackground(bckg);
	}
	public SubTablePanel(String subTable, NetworkTable table, Color bckg, Archiver archy, MessageBoard msgb, Font f){
		this.table = table;
		this.subTable = subTable;
		this.bckg = bckg;
		this.archy = archy;
		this.msgb = msgb;
		this.f = f;
		setBackground(Color.BLACK);
	}
	public void init(){
        getNames();
        fillJLabels();
        fillPanel();
        if(f == null) setLayout(new GridLayout(18,0,0,0));
//        else setLayout(new GridLayout(1,0,0,0));
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
        fillPanel();
	}
	public void fillJLabels(){
		for(int i=0;i<sList.length;i++){
			if(f == null){
				jList[i] = new JLabel(sList[i] + ": " + table.getValue(sList[i],3663));
			}else{
				JLabel jl = new JLabel(""+table.getValue(sList[i],3663));
				jl.setFont(f);
				jl.setForeground(Color.WHITE);
				jList[i] = jl;
			}
			System.out.println(sList[i]);
			msgb.say(sList[i]);
		}
	}
	public void fillPanel(){
		if(f == null){
			for(int i=0;i<sList.length;i++){
				add(jList[i]);
			}
		}else{
			//this is drive mode
			for(int i=0;i<sList.length;i++){
				if(sList[i].equals("Time")){
					add(jList[i],"Center");
				}
			}
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
			update(bckg);
			count++;

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
				if(f == null)jList[i].setText(sList[i] + ": " + o);
				else{
					jList[i].setFont(updateFont(jList[i]));
					jList[i].setText("" + o);
				}
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
	public Font updateFont(JLabel label){
		Font labelFont = label.getFont();
		String labelText = label.getText();

		int stringWidth = label.getFontMetrics(labelFont).stringWidth(labelText);
		int componentWidth = this.getWidth();

		// Find out how much the font can grow in width.
		double widthRatio = (double)componentWidth / (double)stringWidth;

		int newFontSize = (int)(labelFont.getSize() * widthRatio);
		int componentHeight = this.getHeight() - (int)((double)this.getHeight()/3.0);

		// Pick a new font size so it will not be larger than the height of label.
		int fontSizeToUse = Math.min(newFontSize, componentHeight);

		// Set the label's font size to the newly determined size.
		return new Font(labelFont.getName(), Font.PLAIN, fontSizeToUse);
	}
}
