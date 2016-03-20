
package notDefault;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Archiver {
	CopyOnWriteArrayList<ArrayList<String>> rows;
	boolean acceptingValues = true;
	String[] columnHeadings;
	public Archiver(){
		rows = new CopyOnWriteArrayList<ArrayList<String>>();
	}
	public void addNewColumn(String key){
		if(acceptingValues){
			if(!alreadyContains(key)){
				ArrayList<String> temp = new ArrayList<String>(); //new ArrayList<String>.add(key);
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
	public void alphabetizeRows(){
		boolean sorted = false;
		while(!sorted){
			for(int i=0;i<rows.size()-1;i++){
				rows.get(i).get(0).compareTo(rows.get(i).get(0));
			}
		}
	}
	public void reset(){
		acceptingValues = false;
		int count = 0;
		columnHeadings = new String[rows.size()];
		for(ArrayList<String> a:rows){
			columnHeadings[count] = a.get(0);
			count++;
		}
		rows = null;
		System.gc(); //Garbage collector please run?
		rows = new CopyOnWriteArrayList<ArrayList<String>>();
		for(int i=0;i<columnHeadings.length;i++){
			addNewColumn(columnHeadings[i]);
		}
		acceptingValues = true;
	}
	public boolean writeFile(String day, String name){
//		alphabetizeRows(); //doesn't work yet
		PrintWriter writer = null;
		new File("C:\\logFiles").mkdir();
		new File("C:\\logFiles\\"+day).mkdir();
		try {
			writer = new PrintWriter("C:\\logFiles\\" + day + "\\" + name + ".csv","UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			System.err.println("THE PRINTWRITER FAILED TO INITIALIZE");
			System.exit(0);
		}
		PrintWriter error = null;
		new File("HashboardErrorReport.txt");
		try {
			error = new PrintWriter("HashboardErrorReport.txt","UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			System.err.println("ErrorFileFailure");
		}
		int maxLength = 0;
		try{
			maxLength = rows.get(0).size();
		}catch(NullPointerException | ArrayIndexOutOfBoundsException e){
			return true;
		}
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
		return true;
	}
	
}