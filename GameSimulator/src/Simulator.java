import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Simulator {

	ArrayList <Defense> defensesRed; 
	ArrayList <Defense> defensesBlue;  
	ArrayList <Robot> robotsRed;
	ArrayList <Robot> robotsBlue;
	ArrayList <Robot> robots;
	
	Randomizer rnd = new Randomizer();
	GameThings GT = new GameThings();
	BoulderMgr boulderMgr;
	CastleMgr castleMgr;
	int[] matchTracker;
	int matchesPerRobot = 10;
	int matchCount;
	
	public Simulator(){

	}
	
	public void runSimulations(){ 
		RobotAbilitiesReader rb = new RobotAbilitiesReader();
		robots = rb.readRobotAbilitiesFile();
		
		boulderMgr = new BoulderMgr(rnd);
		castleMgr = new CastleMgr();
		
		for (Robot r: robots){
			r.introduceMgrs(boulderMgr,rnd,castleMgr);
		}

		/*
		 * create match schedule
		 * select robots and initialize
		 * prep field/select defenses
		 * place robots and prepare to start match
		 * tick/tock - advance robot
		 *     choose action
		 *     take steps
		 *     complete action
		 *     record results
		 *     repeat
		 * dump results
		 * repeat
		 * 
		 * 
		 * 
		 */

		matchCount = robots.size()/6*matchesPerRobot;
		matchTracker = new int[robots.size()];
		

		for (int i=0; i<matchTracker.length; i++){
			matchTracker[i] = matchesPerRobot;
		}
		
		Path file = Paths.get("robotActions.txt"); // current dir with application
		OutputStream out=null;
		BufferedWriter writer=null;
		try 
		{
			out = Files.newOutputStream(file);
			writer = new BufferedWriter(new OutputStreamWriter(out));
		} catch (IOException x) {
			System.err.println(x);
		}

		for (int i=0; i<matchCount; i++){
			System.out.println("Start match "+i);
			setMatchRobots(i);
			
			// select defenses
			resetDefenses();
			boulderMgr.resetBoulders(robotsRed,robotsBlue);
			castleMgr.resetCastles();
			
			// initialize each red robot in match
			int index = 0;
			for(Robot r: robotsRed){
				r.startMatch(index,defensesBlue,true);
				index++;
			}
			
			// initialize each blue robot in match
			index = 0;
			for(Robot r: robotsBlue){
				r.startMatch(index,defensesRed,false);
				index++;
			}
			for (Robot r:robotsRed){
				System.out.println("red "+r.teamName+" "+r.topRole);
			}
			for (Robot r:robotsBlue){
				System.out.println("blue "+r.teamName+" "+r.topRole);
			}	

			for (int k=0; k<135; k++){
				System.out.print(k+" ");
				for(int j=0; j<3; j++){
					robotsRed.get(j).clockTick(k);
					robotsBlue.get(j).clockTick(k);
				}				
			}
			
			try  
			{
				System.out.println();
				for (Robot r:robotsRed){
					writer.write(r.dumpActions());//"\r\n");
				}
				for (Robot r:robotsBlue){
					writer.write(r.dumpActions());//"\r\n");
				}				
			
			} catch (IOException x) {
				System.err.println(x);
			}

		}

		file = Paths.get("matchResults.csv"); // current dir with application
		try 
		{
			out = Files.newOutputStream(file);
		    writer = new BufferedWriter(new OutputStreamWriter(out));
		    
		    String header = "matchNum,robotMatch,teamNum,teamName,"+GT.dumpNames()+"Role\n";
		    writer.write(header);
		    System.out.print(header);

		    for (Robot r: robots){
		    	String line = r.dumpMatchResults(); 
				writer.write(line);
				System.out.print(line);
			}
		
		} catch (IOException x) {
			System.err.println(x);
		}
	}
	
	// schedule generator
	void setMatchRobots(int matchNum){
		robotsRed = new ArrayList<Robot>(); 
		robotsBlue = new ArrayList<Robot>(); 

		int mCount = matchesPerRobot-matchNum/(robots.size()/6);
		
		for (int i=0; i<3; i++){
			// todo generate better schedule
			int index=rnd.getNextInt(robots.size());
			while (matchTracker[index]<mCount){
				index = (index+1)%robots.size();
			}
			matchTracker[index]--;
			robotsRed.add(robots.get(index));
			
			index=rnd.getNextInt(robots.size());
			while (matchTracker[index]<mCount){
				index = (index+1)%robots.size();
			}
			matchTracker[index]--;
			robotsBlue.add(robots.get(index));
		}
	}
	
	// defense selector
	void resetDefenses(){
		//todo select defenses based on rotating schedule and opponents abilities
		
		// blue alliance
		defensesRed = resetDefensesRedBlue();
		defensesBlue = resetDefensesRedBlue();
		
	}
	
	ArrayList<Defense> resetDefensesRedBlue(){
		int num;
		ArrayList<Defense> defenses = new ArrayList <Defense>();
		
		defenses.add(0, new Defense(GT.defNames.LOWBAR,0,GT.names[GT.defNames.LOWBAR]));
		num = GT.defNames.PORTCULLIS+rnd.getNextInt(2);
		defenses.add(1, new Defense(num,1,GT.names[num]));
		num = GT.defNames.MOAT+rnd.getNextInt(2);
		defenses.add(2, new Defense(num,2,GT.names[num]));
		num = GT.defNames.DRAWBRIDGE+rnd.getNextInt(2);
		defenses.add(3, new Defense(num,3,GT.names[num]));
		num = GT.defNames.ROCKWALL+rnd.getNextInt(2);
		defenses.add(4, new Defense(num,4,GT.names[num]));
		
		return defenses;
	}
}
