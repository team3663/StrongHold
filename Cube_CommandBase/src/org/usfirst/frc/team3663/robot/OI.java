package org.usfirst.frc.team3663.robot;

import org.usfirst.frc.team3663.robot.commands.CG_DriverPickupBall;
import org.usfirst.frc.team3663.robot.commands.CG_WaitForShooterThenShoot;
import org.usfirst.frc.team3663.robot.commands.C_DriveControllerDPad;
import org.usfirst.frc.team3663.robot.commands.C_PickupFirePiston;
import org.usfirst.frc.team3663.robot.commands.C_ShooterFirePiston;
import org.usfirst.frc.team3663.robot.commands.C_ShooterRunMotors;
import org.usfirst.frc.team3663.robot.commands.TC_TurnByGyro;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
//Joysticks	
	public Joystick driveJoystick = new Joystick(0);
	public Joystick buttonJoystick = new Joystick(1);
	public Joystick testJoystickCurtis = new Joystick(5);

//Buttons
  //Pickup Buttons
	private JoystickButton pickUpBall =			new JoystickButton(driveJoystick, 4);
	private JoystickButton pickupRaiseArm = 	new JoystickButton(driveJoystick, 6);
	private JoystickButton pickupLowerArm = 	new JoystickButton(driveJoystick, 5);
  //Shooter Buttons
	private JoystickButton shooterMotorsFullPower =		new JoystickButton(driveJoystick, 1);
	private JoystickButton shooterFirePistonNoWait = 	new JoystickButton(driveJoystick, 7);
	private JoystickButton shooterFirerPistonWait = 	new JoystickButton(driveJoystick, 2);
	
//Test Joystick Buttons
	private JoystickButton turn90Degrees = new JoystickButton(testJoystickCurtis, 4);
	
	public OI(){
	//Real Buttons
	  //Pickup Buttons
		pickUpBall.whileHeld(new CG_DriverPickupBall());
		pickupRaiseArm.whenPressed(new C_PickupFirePiston(false));
		pickupLowerArm.whenPressed(new C_PickupFirePiston(true));
	  //Shooter Buttons
		shooterMotorsFullPower.whileHeld(new C_ShooterRunMotors(1));
		shooterFirePistonNoWait.whenPressed(new C_ShooterFirePiston());
		shooterFirerPistonWait.whenPressed(new CG_WaitForShooterThenShoot());
		
	//Test Buttons
		turn90Degrees.whenPressed(new TC_TurnByGyro());
		
		
		
	}
}

