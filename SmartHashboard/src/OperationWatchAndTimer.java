import java.text.SimpleDateFormat;
import java.util.Date;

public class OperationWatchAndTimer implements Runnable{
	SubTablePanel operation;
	Archiver archiver;
	Date timeStamp;
	String name;
	long startTime = System.currentTimeMillis();
	public OperationWatchAndTimer(SubTablePanel operation, Archiver archiver){
		this.operation = operation;
		this.archiver = archiver;
		timeStamp = new Date();
		name = "aa_time";
	}
	@Override
	public void run(){
		archiver.addNewColumn(name);
		boolean setNewTimeFlag = true;
		boolean exportFlag = false;
		while(true){
			sleep(2);
			archiver.addValue(name, Double.toString((((double)System.currentTimeMillis() - (double)startTime))/1000.0));
			if(operation.get(1).equals("true")){//On startup (enable)
				if(setNewTimeFlag){
					setNewTimeFlag = false;
					timeStamp = new Date();
				}
				exportFlag = true;
				//Continuously add new time values
			}else{ //On disable
				if(exportFlag){
					export();
					exportFlag = false;
				}
				setNewTimeFlag = true;
			}
		}
	}
	public void export(){
		SimpleDateFormat mdy = new SimpleDateFormat("MM-dd-yyyy");
		SimpleDateFormat hms = new SimpleDateFormat("hh;mm;ss_aa");
		archiver.writeFile(mdy.format(timeStamp),hms.format(timeStamp));
	}
	public void sleep(int millis){
		try{
			Thread.sleep(millis);
		}catch(InterruptedException e){}
	}
}
