import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Archiver {
	CopyOnWriteArrayList<ArrayList<String>> rows;
	boolean acceptingValues = true;
	public Archiver(){
		rows = new CopyOnWriteArrayList<ArrayList<String>>();
	}
	public void addNewColumn(String key){
		if(acceptingValues){
			if(!alreadyContains(key)){
				ArrayList<String> temp = new ArrayList<String>();
				temp.add(key);
				rows.add(temp);
			}
		}
	}
	public void addValue(String key, String value){
		if(acceptingValues){
			for(ArrayList<String> a:rows){
				try{
					if(a.get(0).contains(key)){
						a.add(value);
						break;
					}
				}catch(NullPointerException e){
					 System.err.println("Archiver: addValue: NullPointerException");
				}
			}
		}
	}
	public boolean alreadyContains(String key){
		for(ArrayList<String> a:rows){
			if(a.get(0).contains(key)){
				return true;
			}
		}
		return false;
	}
	public void alphabetize(){
		
	}
	public void reset(){
		acceptingValues = false;
		for(ArrayList<String> a:rows){
			String title = a.get(0);
			a.clear();
			System.out.println("Erasing " + title + " : size is " + a.size());
			a.add(title);
		}
		System.gc();
		acceptingValues = true;
	}
	public void writeFile(String day, String name){
		alphabetize();
		PrintWriter writer = null;
		new File("C:\\logFiles").mkdir();
		new File("C:\\logFiles\\"+day).mkdir();
		try {
			writer = new PrintWriter("C:\\logFiles\\" + day + "\\" + name + ".csv","UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			System.err.println("THE PRINTWRITER FAILED TO INITIALIZE");
			System.exit(0);
		}
		
		int maxLength = rows.get(0).size();
		//the minus 1 is to cut off one row off the bottom of every column to prevent
		//null pointer exceptions (not all columns are the same height)
		String currentLine = "";
		String lastLine = "";
		String tempTime = "";
		for(int i=0;i<maxLength;i++){
			for(ArrayList<String> a:rows){
				if(!a.get(0).equals("aa_time")){ //if the column isn't aa_time
					try{
						currentLine = currentLine + a.get(i) + ","; //add the column[i] to currentLine
					}catch(IndexOutOfBoundsException e){
						currentLine = currentLine + ",";
					}
				}else{ //if this is aa_time
					try{
						tempTime = a.get(i); //temp holds onto aa_time's value
					}catch(IndexOutOfBoundsException e){
						tempTime = "";
					}
				}
			}
			if(!currentLine.equals(lastLine)){ //if this line isn't exactly the same as the previous
				writer.println(tempTime + "," + currentLine);
			}
			lastLine = currentLine;
			currentLine = "";
		}
		System.out.println("Exported log file to C:\\logFiles\\" + day + "\\" + name + ".csv");
		reset();
		writer.close();
	}
	
}