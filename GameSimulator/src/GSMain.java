
import edu.wpi.first.wpilibj.networktables.*;

public class GSMain {

	public static void main(String[] args) {

		System.out.println("Working Directory = " + System.getProperty("user.dir"));

		NetworkTable table;
		
		NetworkTable.setClientMode();
		NetworkTable.setIPAddress("10.36.63.20");//78");
		table = NetworkTable.getTable("Dog-NT");
		table.putBoolean("autoInitFindLeft: ",true);

		Simulator sim = new Simulator();
		sim.runSimulations();
	}
}
