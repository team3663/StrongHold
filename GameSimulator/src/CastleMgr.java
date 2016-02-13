
public class CastleMgr {

	Castle redCastle = null; // true
	Castle blueCastle = null; // false
	
	public CastleMgr(){
	}
	
	public void resetCastles(){
		redCastle = new Castle(true);
		blueCastle = new Castle(false);
	}
	
	public boolean weakened(boolean redAlliance){
		
		if (redAlliance)
			return redCastle.weakened();
		else
			return blueCastle.weakened();
	}
	
	public void hit(boolean redAlliance){
		
		if (redAlliance)
			redCastle.hit();
		else
			blueCastle.hit();
	}
}

class Castle{
	boolean redAlliance;
	String desc; 
	int hits = 0;
	final int STRENGTH = 8;
	
	public Castle(boolean redAlliance){
		this.redAlliance = redAlliance;
		if (redAlliance)
			desc = "Red Castle";
		else
			desc = "Blue Castle";
		
		hits = 0;
	}
	
	public void hit(){
		hits++;
	}
	
	public boolean weakened(){
		return hits >= STRENGTH;
	}
}