package org.usfirst.frc.team3663.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team3663.robot.commands.C_DogFollow;
import org.usfirst.frc.team3663.robot.commands.C_divideByZero;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	Joystick driveStick = new Joystick(0);
	Button followAtDistance = new JoystickButton(driveStick, 1);
	Button terminate = new JoystickButton(driveStick, 8);
	
	public OI(){
		followAtDistance.whenPressed(new C_DogFollow());
		terminate.whenPressed(new C_divideByZero());
	}
	
	public Joystick getDriveStick() {
		return driveStick;
	}

}

