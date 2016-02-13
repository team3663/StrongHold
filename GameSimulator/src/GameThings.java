
public class GameThings {

	public String[] names = {"Portcullis","Cheval de Frise","Moat","Ramparts","Drawbridge","Sally Port","Rock Wall","Rough Terrain",
			"Low Bar","Shot Block","Pickup","High Shot","Low Shot","Top Speed","Climb","Autonomous Reach","Autonomous Cross",
			"Autonomous Score Low","Autonomous Score High","Challenge","Autonomous Portcullis","Autonomous Cheval de Frise",
			"Autonomous Moat","Autonomous Ramparts","Autonomous Drawbridge","Autonomous Sally Port","Autonomous Rock Wall",
			"Autonomous Rough Terrain","Autonomous Low Bar"};
	
	public Positions pos = new Positions();
	public DefenseNames defNames = new DefenseNames();
	
	public int SHOTBLOCK=9;
	public int PICKUP=10;
	public int HIGHSHOT=11;
	public int LOWSHOT=12;
	public int TOPSPEED=13;
	public int CLIMB=14;
	public int AUTOREACH=15;
	public int AUTOCROSS=16;
	public int AUTOLOWSHOT=17;
	public int AUTOHIGHSHOT=18;
	public int CHALLENGE=19;
	public int APORTCULLIS=20;
	public int ACHEVALDEFRISE=21;
	public int AMOAT=22;
	public int ARAMPARTS=23;
	public int ADRAWBRIDGE=24;
	public int ASALLYPORT=25;
	public int AROCKWALL=26;
	public int AROUGHTERRAIN=27;
	public int ALOWBAR=28;
	//public int ROLE=29;
	
	public int COUNT = 29;
	
	public int DELAY = 100;

	public enum Role {
		SHOOTER, DEFENSEBREAKER, DEFENDER
	}

	public Role[] roleArr = {Role.SHOOTER, Role.DEFENSEBREAKER, Role.DEFENDER};

//	public int posAdjust(boolean redAlliance, int pos){
//		if (redAlliance)
//			return pos;
//		else
//			return 54*27-1-pos;
//	}
//	
	public String dumpNames(){
		String l = "";
		
		for (int i=0; i<names.length; i++){
			l+= names[i]+",";
			//System.out.print(names[i]+",");
		}
		for (int i=0; i<names.length; i++){
			l+= "failed "+names[i]+",";
			//System.out.print("failed "+names[i]+",");
		}
		//System.out.print(l);
		return l;
	}
}

class DefenseNames{
	String defenseNames[] = {"Portcullis","Cheval de Frise","Moat","Ramparts","Drawbridge","Sally Port","Rock Wall","Rough Terrain","Low Bar"};
	public int PORTCULLIS=0;
	public int CHEVALDEFRISE=1;
	public int MOAT=2;
	public int RAMPARTS=3;
	public int DRAWBRIDGE=4;
	public int SALLYPORT=5;
	public int ROCKWALL=6;
	public int ROUGHTERRAIN=7;
	public int LOWBAR=8;	
	
	//public DefenseNames(){
	public int totalDefenses = defenseNames.length;
}

class Positions{
	
	public int redCourt_0_7 = 7;
	public int redCourt_0_20 = 20;
	public int redCourt_8_13 = 229;
	public int redBatter_0_11 = 11;
	public int redBatter_0_15 = 15;
	public int redBatter_3_13 = 94;
	public int redSecret_13_25 = 376;
	public int neutral_26_13 = 742;
	public int topOfTower = 13;	
	public int DefenseReach_20_13 = 553;
	
	public int getPos(int position, boolean redAlliance){
		if (position == neutral_26_13)
			return neutral_26_13;
		
		if (redAlliance)
			return position;
		else
			return 27*54-1-position;
	}
	
	public String displayPos(int position){
		
		String desc;
		if (position<neutral_26_13)
			desc = "red ";
		else if (position>neutral_26_13)
			desc = "blue ";
		else
			desc ="";
		
		switch (position){
		case 742:
			desc += "center of neutral zone";
			break;
		case 376:
		case 27*54-1-376:
			desc += "secret passage";
			break;
		case 229:
		case 27*54-1-229:
			desc += "middle of courtyard";
			break;
		case 13:
		case 27*54-1-13:
			desc += "top of tower";
			break;
		case 7:
		case 27*54-1-7:
			desc += "endwall on left";
			break;
		case 553:
		case 27*54-1-553:
			desc += "touching defense in neutral zone";
			break;
		default:
			desc += position;
		}
		return desc;
	}
}
