import java.util.Random;

public class Randomizer {

	int seed = 0;
	Random rnd;
	
	public Randomizer(){
		rnd = new Random();
	}
	
	public int getNextInt(int bounds){
		
		return rnd.nextInt(bounds);
	}
}