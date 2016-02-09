
public class Defense {

	int position;
	int strength;
	int type;
	String name;
	
	public Defense(int type, int position, String name){
		this.position = position;
		strength = 2;
		this.type = type;
		this.name = name;
		System.out.println("new defense "+name+" "+type+" "+position);
	}
	
	public void reduceStrength(){
		if (strength > 0)
			strength--;
	}
}
