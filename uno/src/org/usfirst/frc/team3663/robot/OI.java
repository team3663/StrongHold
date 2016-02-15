package org.usfirst.frc.team3663.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team3663.robot.commands.C_LidarFollowing;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // button.whenPressed(new ExampleCommand());
    // button.whileHeld(new ExampleCommand());
    // button.whenReleased(new ExampleCommand());
	Joystick driveStick = new Joystick(0);
	Button followAtDistance = new JoystickButton(driveStick, 1);
	
	public OI(){
		followAtDistance.whenPressed(new C_LidarFollowing());
	}
	
    
	public Joystick getDriveStick() {
		return driveStick;
	}

}

