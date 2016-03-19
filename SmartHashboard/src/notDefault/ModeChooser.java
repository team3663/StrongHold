<<<<<<< HEAD:SmartHashboard/src/ModeChooser.java
package src;
=======
package notDefault;
>>>>>>> 3abd6380c8069f36fa4dbb17788080778d1e786c:SmartHashboard/src/notDefault/ModeChooser.java
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

public class ModeChooser {
	Frame f;
	DriveFrame df;
	public ModeChooser(){
		String ip = "10.36.63.2";
		try
			{
				Path file = Paths.get("IpAddress.txt"); // current dir with application
				InputStream in = Files.newInputStream(file);
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				ip = reader.readLine();
				System.out.println("Connecting to " + ip + "...");
			}catch(Exception e){
				System.out.println("No IP txt file found. Defaulting to 10.36.63.2");
			}
		initChoiceDialog(ip);
	}
	
	public void initChoiceDialog(String ipAddress){
		ButtonGroup options = new ButtonGroup();
		
		JFrame firstFrame = new JFrame("SmartH");
		JButton launch = new JButton("Launch");
		
		JRadioButton drive = new JRadioButton("Driver Mode");
		JRadioButton sw = new JRadioButton("Software Mode");
		
		drive.setActionCommand("drive");
		sw.setActionCommand("software");
		
		options.add(drive);
		options.add(sw);
		
		sw.setSelected(true);
		
		f = new Frame(ipAddress);
		df = new DriveFrame(ipAddress);
				
		launch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String command = options.getSelection().getActionCommand();
				if(command.equals("drive")){
					firstFrame.dispose();
					new Thread(df).start();
					System.out.println("IN DRIVE MODE");
				}else if(command.equals("software")){
					firstFrame.dispose();
					new Thread(f).start();
					System.out.println("IN SW MODE");
				}
			}
        });

		JPanel stuff = new JPanel();
		stuff.add(drive);
		stuff.add(sw);
		firstFrame.getContentPane().add(stuff, "North");
		firstFrame.getContentPane().add(launch, "South");
		firstFrame.pack();
		firstFrame.setVisible(true);
		firstFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		firstFrame.setResizable(false);

	}
	
}