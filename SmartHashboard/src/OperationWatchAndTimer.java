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
		while(true){
			sleep(2);
			if(operation.get(1).equals("true")){
				if(setNewTimeFlag){
					setNewTimeFlag = false;
					timeStamp = new Date();
				}
				archiver.addValue(name, Double.toString((((double)System.currentTimeMillis() - (double)startTime))/1000.0));
			}else{
				setNewTimeFlag = true;
				export();
			}
		}
	}
	public void export(){
		SimpleDateFormat mdy = new SimpleDateFormat("MM-dd-yyyy");
		SimpleDateFormat hms = new SimpleDateFormat("HH;mm;ss");
		System.out.println("Exported log file to C:\\logFiles\\" + mdy.format(timeStamp));
		archiver.writeFile(mdy.format(timeStamp),hms.format(timeStamp));
	}
	public void sleep(int millis){
		try{
			Thread.sleep(millis);
		}catch(InterruptedException e){}
	}
}
