package org.usfirst.frc.team3663.robot;

import org.usfirst.frc.team3663.robot.commands.C_PickupRunMotor;
import org.usfirst.frc.team3663.robot.commands.C_PickupToggle;
import org.usfirst.frc.team3663.robot.commands.TC_TurnByGyro;
import org.usfirst.frc.team3663.robot.commands.TestC_Cycle;
import org.usfirst.frc.team3663.robot.commands.TestC_ToggleTestMode;

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
	public Joystick testJoystick = new Joystick(1);

	//Buttons
	private JoystickButton togglePickupSolenoid = new JoystickButton(driveJoystick, 1);
	private JoystickButton runPickupMotorIn 	= new JoystickButton(driveJoystick, 2); 
	private JoystickButton runPickupMotorOut 	= new JoystickButton(driveJoystick, 3);
	
	//Test Buttons
	private JoystickButton turn90Degrees 		= new JoystickButton(driveJoystick, 4);
	private JoystickButton toggleTestMode		= new JoystickButton(testJoystick, 7);
	private JoystickButton cycleUp				= new JoystickButton(testJoystick, 2);
	private JoystickButton cycleDown			= new JoystickButton(testJoystick, 1);
	
	public OI(){
		//Real Buttons
		togglePickupSolenoid.whenPressed(new C_PickupToggle());
		runPickupMotorIn.whileHeld(new C_PickupRunMotor(1));
		runPickupMotorOut.whileHeld(new C_PickupRunMotor(-1));
		
		//Test Buttons
		turn90Degrees.whenPressed(new TC_TurnByGyro());
		toggleTestMode.whenPressed(new TestC_ToggleTestMode());
		cycleUp.whenPressed(new TestC_Cycle(true));
		cycleDown.whenPressed(new TestC_Cycle(false));
		
	}
//	public void canTest(boolean inTestMode){
//		if(inTestMode) testJoystick = new Joystick(1);
//		else testJoystick = null;
//	}
}

