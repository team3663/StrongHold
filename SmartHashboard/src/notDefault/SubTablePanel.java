
package notDefault;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

@SuppressWarnings("serial")
public class SubTablePanel extends JPanel implements Runnable{
	NetworkTable table;
	Set<String> guiElements;
	String[] sList;
	JLabel[] jList;
	String subTable;
	Color bckg;
	Archiver archy;
	Font f;
	boolean isDrive = false;

	public SubTablePanel(String subTable, NetworkTable table, Color bckg, Archiver archy){
		this.table = table;
		this.subTable = subTable;
		this.bckg = bckg;
		this.archy = archy;
		setBackground(bckg);
	}
	public SubTablePanel(String subTable, NetworkTable table, Color bckg, Archiver archy, Font f){
		this.table = table;
		this.subTable = subTable;
		this.bckg = bckg;
		this.archy = archy;
		this.f = f;
		isDrive = true;
		setBackground(Color.BLACK);
	}
	public void init(){
        getNames();
        fillJLabels();
        fillPanel();
        if(!isDrive){
        	setLayout(new GridLayout(18,0,0,0));
        }
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
			if(!isDrive){
				jList[i] = new JLabel(sList[i] + ": " + table.getValue(sList[i],3663));
			}
			//DRIVER MODE CODE//
			else{
				JLabel jl = new JLabel(""+table.getValue(sList[i],3663)); //don't add name (e.g. "Time:"
				jl.setFont(f);
				jl.setForeground(Color.WHITE);
				jList[i] = jl;
			}
			System.out.println(sList[i]);
		}
	}
	public void fillPanel(){
		if(!isDrive){
			for(int i=0;i<sList.length;i++){
				add(jList[i]);
			}
		}
		//DRIVER MODE CODE//
		else{
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
		long start = System.currentTimeMillis();
		while(true){
			sleep(2); //necessary to not blow up your CPU
			//update(bckg);
			if(start + 2000 > System.currentTimeMillis()){
				start = System.currentTimeMillis();
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
				archy.addValue(sList[i], o.toString());
				if(!isDrive){
					jList[i].setText(sList[i] + ": " + o);
				}
				//DRIVER MODE CODE//
				else if(subTable.equals("operation")){
					jList[i].setForeground(Color.WHITE);
					if(sList[i].equals("Time")){
						o = (double)o - 1;
//						if((double)o < 15.0 && (double)o > 0){
//							for(int j=0;j<sList.length;j++){
//								if(sList[j].equals("Mode") && table.getSubTable(subTable).getValue(sList[j],3663).equals("Autonomous")){
//									jList[i].setForeground(Color.getHSBColor((float)Math.random(), 1.0f, 1.0f));
//									jList[i].setFont(updateFont(jList[i],true));
//								}
//							}
//						}else{
						//^^^^ the Drive team wasn't fond of this ^^^^
						jList[i].setFont(updateFont(jList[i],false));
//						}
						String text = o.toString();
						// " h a p p y . h i"
						// " 0 1 2 3 4 . 5 6"
						int decimalPlaces = text.length() - text.indexOf(".") - 1;
						if(decimalPlaces < 2){
							jList[i].setText("" + text.concat("0"));
						}else if(decimalPlaces > 2){
							jList[i].setText("" + text.substring(text.indexOf(".")));
						}else{
							jList[i].setText("" + o);
						}
					}else{
						jList[i].setFont(updateFont(jList[i],false));
					}
				}
			}else{
				//titles
				jList[i].setText("---------" + subTable.toUpperCase() + "---------");
			}
		}
	}
	public String get(int index) throws NullPointerException{
		return (table.getSubTable(subTable).getValue(sList[index],3663).toString());
	}
	public Font updateFont(JLabel label,boolean crazy){
		Font labelFont = label.getFont();
		String labelText = label.getText();

		int stringWidth = label.getFontMetrics(labelFont).stringWidth(labelText);
		int componentWidth = (int)((double)this.getWidth()/1.2)+1;

		// Find out how much the font can grow in width.
		double widthRatio = (double)componentWidth / (double)stringWidth;

		int newFontSize = (int)(labelFont.getSize() * widthRatio);
		int componentHeight = this.getHeight() - (int)((double)this.getHeight()/3.4);

		// Pick a new font size so it will not be larger than the height of label.
		int fontSizeToUse = Math.min(newFontSize, componentHeight);

		// Set the label's font size to the newly determined size.
		if(!crazy) return new Font(labelFont.getName(), Font.PLAIN, fontSizeToUse);
		return new Font(labelFont.getName(),Font.PLAIN,fontSizeToUse - (int)((fontSizeToUse/30) * Math.random()));
	}
}