import java.util.ArrayList;

public class GSMain {

	public static void main(String[] args) {

		System.out.println("Working Directory = " + System.getProperty("user.dir"));

		Simulator sim = new Simulator();
		sim.runSimulations();
	}
}
