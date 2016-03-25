
package notDefault;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * This class is a special case container-like thing for watching the
 * SubTablePanel that watches the "operation" subtable. This is special-cased
 * because the program records or doesn't based on if the robot is enabled
 * or not (and that information is in the operation subtable). This class
 * is also in charge of updating that subtable
 */

public class OperationWatchAndTimer implements Runnable{
	SubTablePanel operation;
	Archiver archiver;
	Date timeStamp;
	String name;
	long startTime = System.currentTimeMillis();
	boolean enabled = true;
	int modeIndex;
	
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
		modeIndex = 0;
		
		for(String s : operation.sList){
			if(!s.equals("Mode")){ 
				modeIndex++;
			}else{
				break;
			}
		}
		while(true){
			operation.update(operation.bckg);
			//the time in seconds, truncated to two decimal places
			archiver.addValue(name, Double.toString(((double)System.currentTimeMillis() - (double)startTime)/1000.0));
			//On startup (enable)
			if(operation.get(modeIndex).equals("true")){ //If enabled
				enabled = true;
				exportFlag = true;
				if(setNewTimeFlag){
					setNewTimeFlag = false;
					timeStamp = new Date();
				}
			//On disable
			}else if(!keepGoing(2000)){
				enabled = false;
				setNewTimeFlag = true;
				if(exportFlag){
					export(); //Timed out, write file
					exportFlag = false;
				}
			}
			sleep(2);
		}
	}
	//this will continue adding time values to the archiver while it waits to see if the robot is re-enabled
	//after less than 2 seconds. This will prevent a break in csv logging between Autonomous and Teleop
	public boolean keepGoing(long wait){
		enabled = true;
		long stTime = System.currentTimeMillis();
		while(stTime + wait >= System.currentTimeMillis()){
			sleep(2);
			operation.update(operation.bckg);
			archiver.addValue(name, Double.toString(((double)System.currentTimeMillis() - (double)startTime)/1000.0));
			if(operation.get(modeIndex).equals("true")){ //if enabled
				return true;
			}
		}
		return false;
	}
	public long getTime(){
		return System.currentTimeMillis() - startTime;
	}
	public boolean isEnabled(){
		return enabled;
	}
	public void export(){
		SimpleDateFormat mdy = new SimpleDateFormat("MM-dd-yyyy");
		SimpleDateFormat hms = new SimpleDateFormat("hh;mm;ss_aa");
		try{
			archiver.writeFile(mdy.format(timeStamp),hms.format(timeStamp));
		}catch(NullPointerException e){
			System.err.println("----------NullPointerException----------");
		}
	}
	public void sleep(int millis){
		try{
			Thread.sleep(millis);
		}catch(InterruptedException e){}
	}
}