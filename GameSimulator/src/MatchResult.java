
public class MatchResult {

	GameThings GT = new GameThings();
	Achievement[] achievements = new Achievement[GT.COUNT];

	int matchNum;
	int robotMatch;
	GameThings.Role role;
	
	public MatchResult(int matchNum,int robotMatch, GameThings.Role role){
	
		this.matchNum = matchNum;
		this.robotMatch = robotMatch; 
		this.role = role;
		
		for (int i=0; i<achievements.length; i++){
			achievements[i] = new Achievement();
		}
	}
	
	void didSomething(int index){
		achievements[index].count++;
		if (achievements[index].count==0){
			achievements[index].count = 1;
			achievements[index].missed = 0;
		}
	}
	
	void missedSomething(int index){
		achievements[index].missed++;
		if (achievements[index].missed==0){
			achievements[index].count = 0;
			achievements[index].missed = 1;
		}
	}
}

class Achievement{
	int count;
	int missed;
		
	public Achievement(){
		this.count = -1; 
		this.missed = -1;
	}
}