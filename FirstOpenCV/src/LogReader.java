import java.io.BufferedOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class LogReader {

	ArrayList <DataLine> logLines = new ArrayList<DataLine>();
	byte[] logFile;

	public void readWordFile(){
		
		Path file = Paths.get("C:\\Users\\Public\\Documents\\FRC\\Log Files\\2015_12_15 10_20_18 Tue.dslog");

		try {
			logFile = Files.readAllBytes(file);
		} catch (IOException x) {
		    System.err.println(x);
		}
		
		int index = 20; // skip header
		int tripTime;
		int lostPkts;
		double voltage;
		double cpu;
		
		while (index+10 < logFile.length){
			tripTime = logFile[index];
			lostPkts = logFile[index+1];
			voltage = logFile[index+2]+(double)logFile[index+3]/255.0;
			cpu = logFile[index+4]/2.55;//+(double)logFile[index+5]/255.0;
			logLines.add(new DataLine(tripTime,lostPkts,voltage,cpu));
			index+=10;
		}
		
		Path p = Paths.get("C:\\Users\\kingb_000\\desktop\\logfile.csv");
		FileWriter fw = null;

		String headers = "index,trip,lost,voltage,cpu\n";
		
	    try {
	    	fw = new FileWriter("C:\\Users\\kingb_000\\desktop\\logfile.csv");
	    	fw.write(headers);
	    	
  			for (index=0; index<logLines.size(); index++){
				DataLine line = logLines.get(index);
				fw.write(index+","+line.tripTime+","+line.lostPkts+","+line.voltage+","+line.cpu+"\n");
    		}
    		
    		fw.close();
	    } catch (IOException x) {
	    	System.err.println(x);
	    }
	    System.out.println("here");
	}
	 
	class DataLine{
		int tripTime;
		int lostPkts;
		double voltage;
		double cpu;
		
		public DataLine(int tripTime, int lostPkts, double voltage, double cpu){
			this.tripTime = tripTime;
			this.lostPkts = lostPkts;
			this.voltage = voltage;
			this.cpu = cpu;
		}
	}
}
