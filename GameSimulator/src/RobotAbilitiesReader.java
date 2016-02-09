import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class RobotAbilitiesReader {

	ArrayList <Robot> robots;
	String abilitiesNames;
	
	public RobotAbilitiesReader(){
		
	}
	
	public ArrayList <Robot> readRobotAbilitiesFile(){
		
		robots = new ArrayList<Robot>();
		
		//Path file = Paths.get("c:\\users\\kingb_000\\desktop\\RobotAbilities.csv");
		Path file = Paths.get("RobotAbilities.csv"); // current dir with application
		try (InputStream in = Files.newInputStream(file);
		    BufferedReader reader = new BufferedReader(new InputStreamReader(in))) 
		{
		    abilitiesNames = reader.readLine();
		
		    String line;
		    int counter = 0;
		    while ((line = reader.readLine()) != null){// && counter++<6) {
		    	robots.add(new Robot(abilitiesNames, line));
		    	//System.out.println(line);
		    }
		} catch (IOException x) {
			System.err.println(x);
		}
		System.out.println("Robot count = "+robots.size());
		
		return robots;
	}
	
	
}
