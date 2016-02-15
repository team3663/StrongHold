package org.usfirst.frc.team3663.robot;

import org.usfirst.frc.team3663.robot.commands.C_PickupRunMotor;
import org.usfirst.frc.team3663.robot.commands.C_PickupToggle;
import org.usfirst.frc.team3663.robot.commands.TC_TurnByGyro;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
//Joysticks	
	public Joystick driveJoystick = new Joystick(0);

//Buttons
	private JoystickButton togglePickupSolenoid = new JoystickButton(driveJoystick, 1);
	private JoystickButton runPickupMotorIn =     new JoystickButton(driveJoystick, 2); 
	private JoystickButton runPickupMotorOut =    new JoystickButton(driveJoystick, 3);
	
//Test Joystick Buttons
	private JoystickButton turn90Degrees = new JoystickButton(driveJoystick, 4);
	
	public OI(){
	//Real Buttons
		togglePickupSolenoid.whenPressed(new C_PickupToggle());
		runPickupMotorIn.whileHeld(new C_PickupRunMotor(1));
		runPickupMotorOut.whileHeld(new C_PickupRunMotor(-1));
		
	//Test Buttons
		turn90Degrees.whenPressed(new TC_TurnByGyro());
		
	}
}

