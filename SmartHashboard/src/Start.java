import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Start {
	public static void main(String[] args){
		String ip = "10.36.63.20";
		try
			{
				Path file = Paths.get("IpAddress.txt"); // current dir with application
				InputStream in = Files.newInputStream(file);
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				ip = reader.readLine();
				System.out.println("Connecting to " + ip + "...");
			}catch(Exception e){
				System.out.println("No IP txt file found. Defaulting to 10.36.63.20");
			}
		initChoiceDialog(ip);
	}
	public static void initChoiceDialog(String ipAddress){
		JFrame frame = new JFrame("SmartH");
		JButton launch = new JButton("Launch");
		ButtonGroup options = new ButtonGroup();
		JRadioButton drive = new JRadioButton("Driver Mode");
		drive.setActionCommand("drive");
		JRadioButton sw = new JRadioButton("Software Mode");
		sw.setActionCommand("software");
		options.add(drive);
		options.add(sw);
		launch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String command = options.getSelection().getActionCommand();
				
				if(command.equals("drive")){
					frame.dispose();
					System.out.println("SUCCESS");
				}else if(command.equals("software")){
					frame.dispose();
					new Thread(new Frame(ipAddress)).start();
				}
			}
        });
		JPanel stuff = new JPanel();
		stuff.add(drive);
		stuff.add(sw);
		frame.getContentPane().add(stuff, "North");
		frame.getContentPane().add(launch, "South");
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
