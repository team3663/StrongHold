
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
	boolean resetting = false;
	String[] columnHeadings;
	public Archiver(){
		rows = new CopyOnWriteArrayList<ArrayList<String>>();
	}
	public void addNewColumn(String key){
		if(acceptingValues || resetting){
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
					if(a.get(0).toLowerCase().equals(key.toLowerCase())){
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
				rows.get(i).get(0).compareTo(rows.get(i + 1).get(0));
			}
		}
	}
	public void reset(){
		acceptingValues = false;
		columnHeadings = new String[rows.size()];
		System.out.println("resetting - there are " + rows.size() + " columns");
		int count = 0;
		for(ArrayList<String> a:rows){
			columnHeadings[count] = a.get(0);
			count++;
		}
		rows = null;
		System.gc(); //Garbage collector please run?
		rows = new CopyOnWriteArrayList<ArrayList<String>>();
		for(int i=0;i<columnHeadings.length;i++){
			resetting = true;
			addNewColumn(columnHeadings[i]);
			resetting = false;
		}
		acceptingValues = true;
	}
	@SuppressWarnings("unused")
	private int shiftRows(){
		//first, find the longest row
		//iterate through each column and shift them downward until they are the same length as the longest
		//by adding empty entries "" just after line zero
		int longest = 0;
		for(ArrayList<String> al:rows){
			if(al.size() > longest){
				longest = al.size();
			}
		}
		System.out.println("The longest column is " + longest + " high");
		for(ArrayList<String> al:rows){
			int diff = longest - al.size();
			ArrayList<String> temp = new ArrayList<String>();
			for(int i=diff;i>0;i--){
				temp.add("");
			}
			if(diff > 0){
				for(String s:al){
					temp.add(s);
				}
			}
			al = temp;
		}
		return longest;
	}
	public boolean writeFile(String day, String name) throws NullPointerException{
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
		int maxLength = 0;
		try{
			for(ArrayList<String> al:rows){
				if(al.size() > maxLength){
					maxLength = al.size();
				}
			}
			System.out.println("File Length: " + maxLength);
		}catch(NullPointerException | ArrayIndexOutOfBoundsException e){
			System.out.println("Something is wrong at Archiver -> writeFile -> getting max length");
			e.printStackTrace();
			return true;
		}
//		int maxLength = shiftRows();
		String currentLine = "";
		String lastLine = "";
		String tempTime = "";
		for(int i=0;i<maxLength;i++){ //iterate vertically
			for(ArrayList<String> a:rows){ //iterate horizontally first
				if(!a.get(0).equals("aa_time")){ //if the column isn't aa_time
					try{
						currentLine = currentLine + a.get(i) + ","; //add the column[i] to currentLine
					}catch(IndexOutOfBoundsException e){
						currentLine = currentLine + "x,";
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
			}else{
//				System.out.println("deleted duplicate line at " + i);
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