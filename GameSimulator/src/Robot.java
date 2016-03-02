import java.util.ArrayList;

public class Robot {

	int teamNum;
	String teamName;

	GameThings GT = new GameThings();
	ArrayList<Skill> skills;
	int myMatch = 0;
	int position = 0; // on field
	ArrayList <Defense> defenses;
	int currentMatchTime = 0;
	Action action = null;
	boolean redAlliance = true;
	Randomizer rnd;
	int matchNum = 0;
	String[] skillNames;
	GameThings.Role topRole;
	BoulderMgr boulderMgr = null;
	CastleMgr cMgr = null;
	int actionCount = 0;
	ArrayList <Action> actionHistory;
	boolean defendingNow = false;
	
	ArrayList <MatchResult> matchResults = new ArrayList<MatchResult>();
	
	public Robot(String skillNamesString, String line){
		
		skillNames = skillNamesString.split(",");
		String[] info = line.split(",");
		
		teamNum = Integer.parseInt(info[0]);
		teamName = info[1];
		
		skills = new ArrayList<Skill>();
		
		if (info.length != skillNames.length){
			System.out.println(line+"---"+info.length);	
		}
		for (int i=0; i<skillNames.length-2; i++){
			if (skillNames[i+2].equalsIgnoreCase("role")){
				if (info[i+2].equalsIgnoreCase("shooter")){
					topRole = GameThings.Role.SHOOTER;
				}
				else if (info[i+2].equalsIgnoreCase("defensebreaker")){
					topRole = GameThings.Role.DEFENSEBREAKER;
				}
				else{
					topRole = GameThings.Role.DEFENDER;
				}
			}
			else {
				double code = 0;
				//System.out.println(info[i+2]);
				if (info[i+2].length()>0)
					code = Double.parseDouble(info[i+2]);
				skills.add(new Skill(code,skillNames[i+2]));
			}
		}
	}
	
	public void didSomething(){
		for (MatchResult m : matchResults){
			if (m.matchNum == matchNum){
				//System.out.println(teamName+" completed "+GT.names[action.type]);
				m.didSomething(action.getType());
				break;
			}
		}
	}
	
	public void missedSomething(){
		for (MatchResult m : matchResults){
			if (m.matchNum == matchNum){
				//System.out.println(teamName+" completed "+GT.names[action.type]);
				m.missedSomething(action.getType());
				break;
			}
		}
	}
	
	public String dumpMatchResults(){
		String line="";
		for (MatchResult m : matchResults){
			line+=(m.matchNum)+",";
			line+=m.robotMatch+",";
			line+=teamNum+",";
			line+=teamName+",";
			
			for (int j=0; j<m.achievements.length; j++){
				if (m.achievements[j].count!=-1)
					line+=m.achievements[j].count;

				line+=",";
			}
			for (int j=0; j<m.achievements.length; j++){
				if (m.achievements[j].count!=-1)
					line+=m.achievements[j].missed;

				line+=",";
			}
			line+=m.role+"\n";
		}
		return line;
	}
	
	public String dumpActions(){
		String desc = "";
		for (Action a: actionHistory){
			desc += a.dump()+System.lineSeparator();
		}
		desc = desc+System.lineSeparator();
		return desc;
	}
	
//	void activateMatchAchievements(MatchResult m){
//		for (MatchResult m : matchResults){
//			if (m.matchNum == matchNum){
//				//System.out.println(teamName+" completed "+GT.names[action.type]);
//				m.activate(action.type);
//				break;
//			}
//		}
//	}
//	
	public void startMatch(int matchNum, ArrayList<Defense>defenses, boolean redAlliance){
		this.defenses = defenses;
		this.redAlliance = redAlliance;
		this.matchNum = matchNum;
		actionHistory = new ArrayList<Action>();
		position = GT.pos.getPos(GT.pos.neutral_26_13,redAlliance);
		matchResults.add(new MatchResult(matchNum,myMatch++,topRole));
		currentMatchTime = 0;
		actionCount = 0;
		selectNextAction();
	}

	public void introduceMgrs(BoulderMgr boulderMgr, Randomizer rnd, CastleMgr castleMgr){
		this.boulderMgr = boulderMgr;
		this.rnd = rnd;
		cMgr = castleMgr;
	}

	boolean haveBoulder = false;
	
	public void receiveBoulder(){
		haveBoulder = true;
	}
	
	public void giveBoulder(){
		haveBoulder = false;		
	}
	
	int selectDefenseToCross(boolean tryToDamage){
		int fastestTime = 1000;
		int optimalDefense = 1000;

		if (tryToDamage){
			// get my best defense that is not defeated
			for (int i=0; i<5; i++){
				Defense d = defenses.get(i);
				if (d.strength>0){
					int index = d.type;
					if (skills.get(index).ave > 0 && skills.get(index).ave < fastestTime){
						fastestTime = skills.get(index).ave;
						optimalDefense = i;
					}
				}
			}
		}
		
		// could not find a good one so pick any one
		if (optimalDefense == 1000){
			for (int i=0; i<5; i++){
				Defense d = defenses.get(i);
				int index = d.type;
				if (skills.get(index).ave > 0 && skills.get(index).ave < fastestTime){
					fastestTime = skills.get(index).ave;
					optimalDefense = i;
				}
			}
			if (optimalDefense != 1000)
				System.out.println(" picked any defense "+defenses.get(optimalDefense).name);
			else
				System.out.println(" no good def to cross ");		
		}
		
		return optimalDefense;  // could be 1000 if unable to cross any defense
	}
		
	int selectDefenseToCrossAuto(){
		int fastestTime = 1000;
		int optimalDefense = 1000;

		for (int i=0; i<5; i++){
			Defense d = defenses.get(i);
			int index = d.type+GT.APORTCULLIS;
			if (skills.get(index).ave > 0 && skills.get(index).ave < fastestTime){
				fastestTime = skills.get(index).ave;
				optimalDefense = i;
			}
		}
		if (optimalDefense != 1000)
			System.out.println(" picked any defense "+defenses.get(optimalDefense).name);
		else
			System.out.println(" no good def to cross ");		
		
		return optimalDefense;  // could be 1000 if unable to cross any defense
	}
		
	void selectNextAction(){

		String note = "";
		int delayDuration;
		if (currentMatchTime < 15)
			delayDuration = 15 - currentMatchTime;
		else
			delayDuration = 135 - currentMatchTime;
		
		actionCount++;
//		if (teamNum > 4)
//		{
//			note = "disabled for testing";
//			action = new Action (position, position, GT.DELAY, delayDuration, actionCount, rnd, topRole, GT, skills, currentMatchTime, haveBoulder, teamName, matchNum, note);
//			actionHistory.add(action);
//			return;
//		}

		action = null;
		if (topRole == GameThings.Role.DEFENSEBREAKER){
			int defsWeakened = 0;
			int defsForMe = 0;
			//System.out.print("\n"+teamName+" "+redAlliance+" defenses ");

			for (int j=0; j<5; j++){
				if (defenses.get(j).strength <= 0){
					defsWeakened++;
				}
				if (defenses.get(j).strength <= 0 || skills.get(j).efficiency == 0){
					defsForMe++;
				}
				//System.out.print(defenses.get(j).strength+",");
			}
			
			System.out.println();

			if (defsWeakened >= 4 || defsForMe >= 5){
				topRole = GameThings.Role.SHOOTER;
				//System.out.println(teamName+" switching");
			}
		}

		// todo make sure castle is weakened
		// todo properly move to courtyard if needed
		if (currentMatchTime >= 115){
			if (position == GT.pos.getPos(GT.pos.redBatter_3_13,redAlliance)){
				
				if (skills.get(GT.CLIMB).efficiency>0){
					int towerTop = GT.pos.getPos(GT.pos.topOfTower,redAlliance);
					action = new Action (position, towerTop, GT.CLIMB, delayDuration, actionCount, rnd, topRole, GT, skills, currentMatchTime, haveBoulder, teamName, matchNum, note);
				}
				// todo put challenge here
				else{
					note = "(robotAbilities) unable to climb, so all done";
				}
			}
			else if (position == GT.pos.getPos(GT.pos.topOfTower,redAlliance)){
				note = "all done";
				action = new Action (position, position, GT.DELAY, delayDuration, actionCount, rnd, topRole, GT, skills, currentMatchTime, haveBoulder, teamName, matchNum, note);
			}
			else{
				int goalPos = GT.pos.getPos(GT.pos.redBatter_3_13,redAlliance);
				// todo turn this into move
				action = new Action (position, goalPos, GT.CHALLENGE, delayDuration, actionCount, rnd, topRole, GT, skills, currentMatchTime, haveBoulder, teamName, matchNum, note);
			}
		}

		else if (currentMatchTime < 15){
			// don't need boulder to reach or cross
			if (haveBoulder == false){
				note = "waiting for autonomous to end";
				action = new Action (position, position, GT.DELAY, delayDuration, actionCount, rnd, topRole, GT, skills, currentMatchTime, haveBoulder, teamName, matchNum, note);
			}
			else if (position == GT.pos.getPos(GT.pos.neutral_26_13,redAlliance)){
				int defenseIndex = selectDefenseToCrossAuto();

				if ( defenseIndex != 1000){
					action = new Action (position, GT.pos.getPos(GT.pos.redCourt_8_13,redAlliance), defenses.get(defenseIndex).type+GT.APORTCULLIS,  delayDuration, actionCount, rnd, topRole, GT, skills, currentMatchTime, haveBoulder, teamName, matchNum, note);
				}
				else{
					action = new Action (position, GT.pos.getPos(GT.pos.DefenseReach_20_13,redAlliance), GT.AUTOREACH, delayDuration, actionCount, rnd, topRole, GT, skills, currentMatchTime, haveBoulder, teamName, matchNum, note);
				}
			}
			else if (position == GT.pos.getPos(GT.pos.DefenseReach_20_13,redAlliance)){
				note = "waiting for end of autonomous";
				action = new Action (position, position, GT.DELAY, delayDuration, actionCount, rnd, topRole, GT, skills, currentMatchTime, haveBoulder, teamName, matchNum, note);
			}
			else if (position == GT.pos.getPos(GT.pos.redCourt_8_13,redAlliance)){

				int highEff = skills.get(GT.AUTOHIGHSHOT).efficiency;
				int lowEff = skills.get(GT.AUTOLOWSHOT).efficiency;
				int thisActionType = 0;
				
				if (lowEff+highEff != 0){
					if (lowEff == 0){
						thisActionType = GT.AUTOHIGHSHOT;
					}
					else if (highEff == 0){
						thisActionType = GT.AUTOLOWSHOT;
					}
					else{
						if (rnd.getNextInt(lowEff+highEff)<lowEff){
							thisActionType = GT.AUTOHIGHSHOT;
						}
						else{
							thisActionType = GT.AUTOLOWSHOT;
						}
					}
					action = new Action (position, position, thisActionType, delayDuration, actionCount, rnd, topRole, GT, skills, currentMatchTime, haveBoulder, teamName, matchNum, note);
				}
			}
		}
		else if (topRole == GameThings.Role.DEFENDER){
			int targetPos = 0;
			if (position == GT.pos.getPos(GT.pos.redCourt_8_13,redAlliance) || position == GT.pos.getPos(GT.pos.redCourt_0_7,redAlliance)){
				int defenseIndex = selectDefenseToCross(false);

				if ( defenseIndex != 1000){
					action = new Action (position, GT.pos.getPos(GT.pos.neutral_26_13,redAlliance), defenses.get(defenseIndex).type, delayDuration, actionCount, rnd, topRole, GT, skills, currentMatchTime, haveBoulder, teamName, matchNum, note);
				}
			}
			else if (position == GT.pos.getPos(GT.pos.neutral_26_13,redAlliance)){
				action = new Action (position, GT.pos.getPos(GT.pos.redSecret_13_25,!redAlliance), GT.TOPSPEED, delayDuration, actionCount, rnd, topRole, GT, skills, currentMatchTime, haveBoulder, teamName, matchNum, note);
			}
			else if (position == GT.pos.getPos(GT.pos.redSecret_13_25,!redAlliance)){
				if (rnd.getNextInt(2) == 0)
					targetPos = GT.pos.getPos(GT.pos.redCourt_8_13,!redAlliance);
				else
					targetPos = GT.pos.getPos(GT.pos.redCourt_0_7,!redAlliance);
				action = new Action (position, targetPos, GT.TOPSPEED, delayDuration, actionCount, rnd, topRole, GT, skills, currentMatchTime, haveBoulder, teamName, matchNum, note);
			}
			else if (position == GT.pos.getPos(GT.pos.redSecret_13_25,!redAlliance) || position == GT.pos.getPos(GT.pos.redCourt_0_7,redAlliance)){
				if (rnd.getNextInt(2) == 0)
					targetPos = GT.pos.getPos(GT.pos.redCourt_8_13,!redAlliance);
				else
					targetPos = GT.pos.getPos(GT.pos.redCourt_0_7,!redAlliance);
				action = new Action (position, targetPos, GT.TOPSPEED, delayDuration, actionCount, rnd, topRole, GT, skills, currentMatchTime, haveBoulder, teamName, matchNum, note);
			}
			else
				note = "(work needed) write code for Defender role";
			//todo write defense role		
		}
		else if (topRole == GameThings.Role.DEFENSEBREAKER){
			if (position == GT.pos.getPos(GT.pos.DefenseReach_20_13,redAlliance)){
				action = new Action (position, GT.pos.getPos(GT.pos.neutral_26_13,redAlliance), GT.TOPSPEED, delayDuration, actionCount, rnd, topRole, GT, skills, currentMatchTime, haveBoulder, teamName, matchNum, note);				
			}
			else if (position == GT.pos.getPos(GT.pos.neutral_26_13,redAlliance)){
				int defenseIndex = selectDefenseToCross(true);

				if ( defenseIndex != 1000){
					action = new Action (position, GT.pos.getPos(GT.pos.redCourt_8_13,redAlliance), defenses.get(defenseIndex).type, delayDuration, actionCount, rnd, topRole, GT, skills, currentMatchTime, haveBoulder, teamName, matchNum, note);
				}
			}
			else if (position == GT.pos.getPos(GT.pos.redCourt_8_13,redAlliance)){
				int defenseIndex = selectDefenseToCross(false);

				if ( defenseIndex != 1000){
					action = new Action (position, GT.pos.getPos(GT.pos.neutral_26_13,redAlliance), defenses.get(defenseIndex).type, delayDuration, actionCount, rnd, topRole, GT, skills, currentMatchTime, haveBoulder, teamName, matchNum, note);
				}
			}
			else
				note = "(work needed) write code to handle defensebreaker in unexpected position";
		}
		else{ // SHOOTER
			if (position == GT.pos.getPos(GT.pos.DefenseReach_20_13,redAlliance)){
				action = new Action (position, GT.pos.getPos(GT.pos.neutral_26_13,redAlliance), GT.TOPSPEED, delayDuration, actionCount, rnd, topRole, GT, skills, currentMatchTime, haveBoulder, teamName, matchNum, note);				
			}
			else if (haveBoulder == true){
				if (position == GT.pos.getPos(GT.pos.neutral_26_13,redAlliance)){
					int defenseIndex = selectDefenseToCross(true);
	
					if (defenseIndex == 1000)
						defenseIndex = selectDefenseToCross(false);
					
					// todo only shoot low from side
					// todo shoot low until castle weakened
					if ( defenseIndex != 1000){
						if (rnd.getNextInt(100)<35)
							action = new Action (position, GT.pos.getPos(GT.pos.redCourt_8_13,redAlliance), defenses.get(defenseIndex).type, delayDuration, actionCount, rnd, topRole, GT, skills, currentMatchTime, haveBoulder, teamName, matchNum, note);
						else
							action = new Action (position, GT.pos.getPos(GT.pos.redCourt_0_7,redAlliance), defenses.get(defenseIndex).type, delayDuration, actionCount, rnd, topRole, GT, skills, currentMatchTime, haveBoulder, teamName, matchNum, note);
					}
					else
						note = "(robotAbilities) unable to cross any of the defenses";
				}
				else if (position == GT.pos.getPos(GT.pos.redCourt_8_13,redAlliance)){
					if (skills.get(GT.HIGHSHOT).efficiency>0){
						action = new Action (position, position, GT.HIGHSHOT, delayDuration, actionCount, rnd, topRole, GT, skills, currentMatchTime, haveBoulder, teamName, matchNum, note);
					}
					else {
						action =new Action (position, GT.pos.getPos(GT.pos.redCourt_0_7,redAlliance), GT.TOPSPEED, delayDuration, actionCount, rnd, topRole, GT, skills, currentMatchTime, haveBoulder, teamName, matchNum, note);
					}
				}
				else if (position == GT.pos.getPos(GT.pos.redCourt_0_7,redAlliance)){
					int highEff = skills.get(GT.HIGHSHOT).efficiency;
					int lowEff = skills.get(GT.LOWSHOT).efficiency;
					int thisActionType = 0;
					
					if (lowEff+highEff != 0){
						if (lowEff == 0){
							thisActionType = GT.HIGHSHOT;
						}
						else if (highEff == 0){
							thisActionType = GT.LOWSHOT;
						}
						else{
							if (rnd.getNextInt(lowEff+highEff)<lowEff){
								thisActionType = GT.HIGHSHOT;
							}
							else{
								thisActionType = GT.LOWSHOT;
							}
						}
						action = new Action (position, position, thisActionType, delayDuration, actionCount, rnd, topRole, GT, skills, currentMatchTime, haveBoulder, teamName, matchNum, note);
					}
					else
						note = "(robotAbilities) unable to shoot low or high";
				}
			}
			else{ // todo add code to pickup boulder from safe passage
				int nearestBoulder = boulderMgr.findNearestBoulder(position,redAlliance);
				if(nearestBoulder == position){
					if (skills.get(GT.PICKUP).efficiency>0){
						action = new Action (position, position, GT.PICKUP, delayDuration, actionCount, rnd, topRole, GT, skills, currentMatchTime, haveBoulder, teamName, matchNum, note);
					}
					else
						note = "(robotAbilities) unable to pickup";
				}
				else if (nearestBoulder == GT.pos.neutral_26_13){
					if (position == GT.pos.getPos(GT.pos.redCourt_8_13, redAlliance) || position == GT.pos.getPos(GT.pos.redCourt_0_7, redAlliance)){
						int defenseIndex = selectDefenseToCross(false);
						
						if ( defenseIndex != 1000){
							action = new Action (position, nearestBoulder, defenses.get(defenseIndex).type, delayDuration, actionCount, rnd, topRole, GT, skills, currentMatchTime, haveBoulder, teamName, matchNum, note);
						}
						else
							note = "(robotAbilities) unable to cross any of the defenses";
					}
				}
				else if (position == GT.pos.getPos(GT.pos.redCourt_8_13, redAlliance) || position == GT.pos.getPos(GT.pos.redCourt_0_7, redAlliance)){
					action = new Action (position, nearestBoulder, GT.TOPSPEED, delayDuration, actionCount, rnd, topRole, GT, skills, currentMatchTime, haveBoulder, teamName, matchNum, note);
				}
				else
					note = "(work needed) not sure what is wrong";

			}
		}

		if (action == null){
			action = new Action (position, position, GT.DELAY, 10, actionCount, rnd, topRole, GT, skills, currentMatchTime, haveBoulder, teamName, matchNum, note);
		}
		
		actionHistory.add(action);
	}
	
	void completeAction(){
		
		// always succeeds
		if (action.getType() >= GT.DELAY){
			//System.out.print((redAlliance?"red ":"blue ")+teamName+" finished "+" Delay ");
			return;
		}
		
		//System.out.print((redAlliance?"red ":"blue ")+teamName+" finished "+GT.names[action.getType()]);

		if (!action.succeeded())
		{
			// action failed
			System.out.println(" action failed");
			if (action.getType() == GT.HIGHSHOT || action.getType() == GT.AUTOHIGHSHOT || action.getType() == GT.LOWSHOT || action.getType() == GT.AUTOLOWSHOT){
				boulderMgr.shootButMissed(this);
				haveBoulder = false;
			}
			missedSomething();
		}
		else{
			didSomething();
			position = action.getFinalPosition();
			
			// todo fix ugly use of defense indices
			if (action.getType() < GT.SHOTBLOCK){
				Defense def = null;
				for (int i=0; i<defenses.size(); i++)
				{
					def = defenses.get(i); 
					if (def.type == action.getType()){
						if (def.strength > 0 && (position == GT.pos.getPos(GT.pos.redCourt_8_13, redAlliance) || position == GT.pos.getPos(GT.pos.redCourt_0_7, redAlliance))){
							def.strength--;
							
							//System.out.println(" "+(redAlliance?"red ":"blue ")+def.name+" strength now "+def.strength);
						}
						//else
							System.out.println(" "+(redAlliance?"red ":"blue ")+def.name+" strength now "+def.strength+" pos: "+position);
					}
				}
				if (def == null)
					System.out.println("unrecognized defense");
			}
			else if (action.getType() >= GT.APORTCULLIS && action.getType() <= GT.ALOWBAR){
				Defense def = null;
				for (int i=0; i<defenses.size(); i++)
				{
					def = defenses.get(i); 
					if (def.type == action.getType()-GT.APORTCULLIS){
						if (def.strength > 0 && (position == GT.pos.getPos(GT.pos.redCourt_8_13, redAlliance) || position == GT.pos.getPos(GT.pos.redCourt_0_7, redAlliance))){
							def.strength--;
							//System.out.println(" "+(redAlliance?"red ":"blue ")+def.name+" strength now "+def.strength);
						}
						//else
							System.out.println(" "+(redAlliance?"red ":"blue ")+def.name+" strength now "+def.strength+" pos: "+position);
					}
				}
				if (def == null)
					System.out.println("unrecognized defense");
			}
			else if (action.getType() == GT.SHOTBLOCK){}
			else if (action.getType() == GT.CHALLENGE){}			
			else if (action.getType() == GT.CLIMB){}
			else if (action.getType() == GT.HIGHSHOT || action.getType() == GT.AUTOHIGHSHOT || action.getType() == GT.LOWSHOT || action.getType() == GT.AUTOLOWSHOT){
				boulderMgr.shootBoulder(this);
				haveBoulder = false;
			}
			else if (action.getType() == GT.PICKUP){
				if (boulderMgr.pickupBoulder(position, this)){
					haveBoulder = true;
				}
			}else if (action.getType() == GT.DELAY){
				// do nothing
			}
		}
	}
	
	public void clockTick(int matchTime){
		currentMatchTime=matchTime;
		if (action != null){
			if (action.decTime() <= 0){
				completeAction();
				selectNextAction();
			}
		}
	}
}

class Action{

	private int startPosition;
	private int duration;
	private int timeRemaining;
	private Skill s;
	private int type;
	private int matchNum;
	private int finalPosition;
	private int matchTime = 0;
	private boolean succeeded = false;
	private int actionCount = 0;
	private boolean haveBoulder = false;
	private GameThings.Role role;
	private GameThings GT;
	private Randomizer rnd;
	private String teamName;
	private String note;
	
	public Action(int pStartPosition, int pFinalPosition, int pActionType, int pDelayDuration, int pActionCount, Randomizer pRnd, GameThings.Role pRole, GameThings pGT, ArrayList<Skill> pSkills, int pMatchTime, boolean pHaveBoulder, String pTeamName, int pMatchNum, String pNote){
		startPosition = pStartPosition;
		type = pActionType;
		finalPosition = pFinalPosition;
		matchTime = pMatchTime;
		haveBoulder = pHaveBoulder;
		role = pRole;
		rnd = pRnd;
		teamName = pTeamName;
		matchNum = pMatchNum;
		actionCount = pActionCount;
		GT = pGT;
		note = pNote;
		
		if (type == GT.DELAY){
			duration = pDelayDuration;
			succeeded = true;
		}
		else{
			s = pSkills.get(type);
			duration = s.ave + rnd.getNextInt(s.var*2+1) - s.var;
			if (duration == 0)
				duration = 1;
			if (type == GT.TOPSPEED){
				duration = 15/duration; // 15 is an arbitrary distance in feet
			}
		}
		timeRemaining = duration;
	}
	
	public int decTime(){
		return --timeRemaining;
	}
	
	public int getType(){
		return type;
	}
	
	public int getFinalPosition(){
		return finalPosition;
	}

	// this should only be called once
	public boolean succeeded(){
		succeeded = rnd.getNextInt(100)<s.efficiency;
		
		if (!succeeded){
			finalPosition = startPosition;
		}
		
		return succeeded;
	}
	
	public String dump(){
		String s;
		if (type<GT.DELAY)
			s = "Match: "+matchNum+", "+teamName+" ("+role+") "+actionCount+": "+matchTime+" Duration: "+duration+", "+GT.names[type]+", Start: "+GT.pos.displayPos(startPosition)+", Dest: "+GT.pos.displayPos(finalPosition)+(haveBoulder?", have":", no")+" boulder, "+(succeeded?"succeeded ":"failed ")+note;
		else
			s = "Match: "+matchNum+", "+teamName+" ("+role+") "+actionCount+": "+matchTime+" Duration: "+duration+" DELAY "+note;
		
		System.out.println(s);
		return s;
	}
}

class Skill{
	int ave;
	int var;
	int efficiency;
	String name;
	
	public Skill(double skill, String name){
		ave = ((int)skill)/100;
		var = ((int)skill)%100;
		efficiency = ((int)(skill*100))%100;
		this.name = name;
	}
}