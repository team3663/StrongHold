import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Archiver {
	ArrayList<ArrayList<String>> rows;
	
	public Archiver(){
		rows = new ArrayList<ArrayList<String>>();
	}
	public void addNewColumn(String key){
		ArrayList<String> temp = new ArrayList<String>();
		temp.add(key);
		rows.add(temp);
	}
	public void addValue(String key, String value){
		for(ArrayList<String> a:rows){
			if(a.get(0).contains(key)){
				a.add(value);
				break;
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
	public void writeFile(String name){
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("C:\\logFiles\\" + name + ".txt","UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
		}
		
		int maxLength = rows.get(0).size();
		System.out.println(maxLength);
		for(int i=0;i<maxLength;i++){
			for(ArrayList<String> a:rows){
				writer.print(a.get(i) + ",");
			}
			writer.println();
		}
		writer.close();
	}
	
}
