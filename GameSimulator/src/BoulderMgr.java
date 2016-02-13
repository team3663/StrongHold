import java.util.ArrayList;
import java.util.Random;

enum Owner {
    FIELD, ROBOT, HPRED, HPBLUE 
}

public class BoulderMgr {


	private GameThings GT = new GameThings();
	private ArrayList <Boulder> boulders;
	private Randomizer rnd;
	
	public BoulderMgr(Randomizer rnd){
		boulders = new ArrayList <Boulder>();
		this.rnd = rnd;
	}
	
	public int findNearestBoulder(int position, boolean alliance){
		int rx=position%27;
		int ry=position/27;
		int pos=-1;
		int dist = 10000;

		System.out.println("\nbReq: "+position+" "+alliance);
		for (Boulder b: boulders){
			System.out.print(b.position+" "+b.owner+", ");
		}
		System.out.println();
		
		for (Boulder b: boulders){
			if (b.owner == Owner.FIELD){
				int x = b.position%27 - rx;
				x=x*x;
				
				int y = b.position/27 -ry;
				y = y*y;
				
				if (x+y < dist && position != GT.pos.getPos(GT.pos.redSecret_13_25, !alliance)){
					pos = b.position;
					dist = x+y;
				}
			}
		}

		if (pos == -1){
			System.out.println(" no safe boulders on field, look in opponents secret passage");
			
			pos=-1;
			dist = 10000;
			
			for (Boulder b: boulders){
				if (b.owner == Owner.FIELD){
					int x = b.position%27 - rx;
					x=x*x;
					
					int y = b.position/27 -ry;
					y = y*y;
					
					if (x+y < dist){
						pos = b.position;
						dist = x+y;
					}
				}
			}
		}
		
		if (pos == -1){
			System.out.println(" problem, no boulders on field");
			return GT.pos.neutral_26_13;
		}
		else
		{
			System.out.println(" boulder found at "+pos);
			return pos;
		}
	}
	
	public boolean pickupBoulder(int position, Robot r){
		for (Boulder b: boulders){
			if (position == b.position){
				b.update(-1,Owner.ROBOT,r);
				return true;
			}
		}
		return false;
	}
	
	public boolean shootBoulder(Robot r){
		
		for (Boulder b: boulders){
			if (b.robot == r){
				if (r.redAlliance){
					b.update(-1,Owner.HPBLUE,null);
					HPAction(Owner.HPBLUE);
				}
				else{
					b.update(-1,Owner.HPRED,null);
					HPAction(Owner.HPRED);
				}
				return true;
			}
		}
		return false;
	}
	
	public void shootButMissed(Robot r){
		
		int pos;
		if (rnd.getNextInt(100)<50){
			pos = GT.pos.getPos(GT.pos.redCourt_8_13, r.redAlliance);
		}
		else{
			pos = GT.pos.getPos(GT.pos.redCourt_0_7, r.redAlliance);
		}
		for (Boulder b: boulders){
			if (b.robot == r){
				b.update(pos,Owner.FIELD,null);
				break;
			}
		}
	}
	
	private void HPAction(Owner o){
		int limit = rnd.getNextInt(7);
		
		for (Boulder b: boulders){
			if (b.owner == o){
				if (limit-- <= 0){
					if (o==Owner.HPBLUE){
						//todo fix SelectNextAction to handle ball in secret passage
						//b.update(GT.pos.getPos(GT.pos.redSecret_13_25, false), Owner.FIELD, null);
						b.update(GT.pos.getPos(GT.pos.neutral_26_13, false), Owner.FIELD, null);
					}
					else{
						//b.update(GT.pos.getPos(GT.pos.redSecret_13_25, true), Owner.FIELD, null);						
						b.update(GT.pos.getPos(GT.pos.neutral_26_13, true), Owner.FIELD, null);						
					}
				}
			}
		}
	}
	
	public void resetBoulders(ArrayList<Robot> redRobots, ArrayList<Robot> blueRobots){
		boulders = new ArrayList <Boulder>();
		
		for (int j=0; j<6; j++){
			boulders.add(new Boulder(GT.pos.getPos(GT.pos.neutral_26_13,true),Owner.FIELD,null));
		}
		 
		for (Robot r: redRobots){
			boulders.add(new Boulder(-1,Owner.ROBOT,r));
			r.receiveBoulder();
		}
		
		for (Robot r: blueRobots){
			boulders.add(new Boulder(-1,Owner.ROBOT,r));
			r.receiveBoulder();
		}
		
		for (int j=0; j<3; j++){
			boulders.add(new Boulder(-1,Owner.HPBLUE,null));
		}
		
		for (int j=0; j<3; j++){
			boulders.add(new Boulder(-1,Owner.HPRED,null));
		}		
	}
}

class Boulder {
	int position;
	Robot robot;
	Owner owner;

	public Boulder(int position, Owner owner, Robot robot){
		this.position = position;
		this.owner = owner;
		this.robot = robot;
	}
	
	public void update(int position, Owner owner, Robot robot){
		this.position = position;
		this.owner = owner;
		this.robot = robot;
	} 
	
	public Robot getRobot(){
		return robot;
	}
	
	public void setRobot(Robot robot){
		this.robot = robot;
	}

	public int getPosition(){
		return position;
	}
	
	public void setPosition(int newPosition){
		position = newPosition;
	}

	public Owner getOwner(){
		return owner;
	}
	
	public void setOwner(Owner newOwner){
		this.owner = newOwner;
	}
}

