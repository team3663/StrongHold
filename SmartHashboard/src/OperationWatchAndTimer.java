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
			//the time in seconds, truncated to two decimal places
			archiver.addValue(name, Double.toString((Math.round(((double)System.currentTimeMillis() - (double)startTime)/1000.0)*100.0)/100.0));
			//On startup (enable)
			if(operation.get(1).equals("true")){
				exportFlag = true;
				if(setNewTimeFlag){
					setNewTimeFlag = false;
					timeStamp = new Date();
				}
			//On disable
			}else if(!keepGoing(1500)){
				setNewTimeFlag = true;
				if(exportFlag){
					export();
					exportFlag = false;
				}
			}
		}
	}
	//this will continue adding time values to the archiver while it waits to see if the robot is re-enabled
	//after less than 1.5 seconds. This will prevent a break in csv logging between Autonomous and Teleop
	public boolean keepGoing(long wait){
		long startTime = System.currentTimeMillis();
		while(startTime + wait >= System.currentTimeMillis()){
			sleep(2);
			archiver.addValue(name, Double.toString((double)System.currentTimeMillis() - (double)startTime));
			if(operation.get(1).equals("true")) return true;
		}
		return false;
	}
	public long getTime(){
		return System.currentTimeMillis() - startTime;
	}
	public boolean isEnabled(){
		return operation.get(1).equals("true");
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
