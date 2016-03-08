import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Start {
	public static void main(String[] args){
		String ip = "10.36.63.20";
		try
			{
				Path file = Paths.get("IpAddress.txt"); // current dir with application
				InputStream in = Files.newInputStream(file);
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				ip = reader.readLine();
				System.out.println("Connected at " + ip);
			}catch(Exception e){
				System.out.println("No IP txt file found. Defaulting to 10.36.63.20");
			}
		Frame hi = new Frame(ip);
//		new Thread(hi).start(); completely untested and potentially dangerous
	}
}
