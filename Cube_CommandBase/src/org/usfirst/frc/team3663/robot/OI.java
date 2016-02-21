package org.usfirst.frc.team3663.robot;

import org.usfirst.frc.team3663.robot.commands.CG_DriverPickupBall;
import org.usfirst.frc.team3663.robot.commands.CG_TeleopVisionShooting;
import org.usfirst.frc.team3663.robot.commands.CG_WaitForShooterThenShoot;
import org.usfirst.frc.team3663.robot.commands.C_DartPrepareForShot;
import org.usfirst.frc.team3663.robot.commands.C_DriveControllerDPad;
import org.usfirst.frc.team3663.robot.commands.C_PickupArmSwitchSafty;
import org.usfirst.frc.team3663.robot.commands.C_PickupFirePiston;
import org.usfirst.frc.team3663.robot.commands.C_ShooterFirePiston;
import org.usfirst.frc.team3663.robot.commands.C_ShooterRunMotors;
import org.usfirst.frc.team3663.robot.commands.C_VisionCenterGoal;
import org.usfirst.frc.team3663.robot.commands.C_VisionFineAdjust;
import org.usfirst.frc.team3663.robot.commands.C_ShooterShoot;
import org.usfirst.frc.team3663.robot.commands.C_WheelyBarZeroEncoder;
import org.usfirst.frc.team3663.robot.commands.C_WinchMoveNoSafty;
import org.usfirst.frc.team3663.robot.commands.C_WinchGoToLocation;
import org.usfirst.frc.team3663.robot.commands.TC_TurnByGyro;
import org.usfirst.frc.team3663.robot.commands.TestC_Cycle;
import org.usfirst.frc.team3663.robot.commands.TestC_ToggleTestMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

import org.usfirst.frc.team3663.robot.commands.C_EncoderCurveDrive;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	NetworkTable table = Robot.visionTable;
	
	//Joysticks	
	public Joystick driveJoystick = new Joystick(0);
	public Joystick buttonJoystick = new Joystick(1);
	public Joystick testJoystickCurtis = new Joystick(5);
	public Joystick testJoystick = new Joystick(4);
	
	public Joystick visionTestStick = new Joystick(3);

//Buttons
  //Pickup Buttons
	private JoystickButton pickUpBall 		= new JoystickButton(driveJoystick, 4);
	private JoystickButton pickupRaiseArm 	= new JoystickButton(driveJoystick, 6);
	private JoystickButton pickupLowerArm 	= new JoystickButton(driveJoystick, 5);
	private JoystickButton pickupCycleSafty = new JoystickButton(driveJoystick, 8);
  //Shooter Buttons
	private JoystickButton shooterMotorsFullPower 	= new JoystickButton(driveJoystick, 1);
	public int shooterFirePistonNoWait 	= 7;
	public int shooterFirerPistonWait	= 2;
  //Winch Buttons
	private JoystickButton winchToHoist = 	  new JoystickButton(buttonJoystick, 1);
	private JoystickButton winchNotSafeMove = new JoystickButton(buttonJoystick, 7);
  //Wheely Bar Buttons
	private JoystickButton wheelyBarMoveToZero = new JoystickButton(buttonJoystick, 4);
	
  //Test Joystick Buttons
	private JoystickButton turn90Degrees 	= new JoystickButton(visionTestStick, 4);
	
	private JoystickButton toggleTestMode	= new JoystickButton(testJoystick, 7);
	private JoystickButton cycleUp			= new JoystickButton(testJoystick, 2);
	private JoystickButton cycleDown		= new JoystickButton(testJoystick, 1);
	
  //visionTestStick Buttons
	private JoystickButton testCenterGoal = new JoystickButton(visionTestStick,1);
	private JoystickButton testFineAdjust = new JoystickButton(visionTestStick,3);
	private JoystickButton testTeleopVisionShooting = new JoystickButton(visionTestStick,2);
	
	public OI(){
	//Real Buttons
	  //Pickup Buttons
		pickUpBall.whileHeld(new CG_DriverPickupBall());
		pickupRaiseArm.whenPressed(new C_PickupFirePiston(false));
		pickupLowerArm.whenPressed(new C_PickupFirePiston(true));
		pickupCycleSafty.whenPressed(new C_PickupArmSwitchSafty());
	  //Shooter Buttons
		shooterMotorsFullPower.whileHeld(new C_ShooterShoot());
	  //Winch Buttons
		winchToHoist.whileHeld(new C_WinchGoToLocation(1111, -.5));
		winchNotSafeMove.whileHeld(new C_WinchMoveNoSafty());
	  //Wheely Bar Buttons
		wheelyBarMoveToZero.whileHeld(new C_WheelyBarZeroEncoder());
		
		//Test Buttons
		turn90Degrees.whenPressed(new TC_TurnByGyro(table.getNumber("cameraMoveAngle: ",0)));
		toggleTestMode.whenPressed(new TestC_ToggleTestMode());
		cycleUp.whenPressed(new TestC_Cycle(true));
		cycleDown.whenPressed(new TestC_Cycle(false));
		
		//VisionTestButtons
		C_VisionCenterGoal centerGoal = new C_VisionCenterGoal();
		testCenterGoal.whileHeld(centerGoal);
		testFineAdjust.whileHeld(new C_DartPrepareForShot());//C_VisionFineAdjust());
		testTeleopVisionShooting.whileHeld(new CG_TeleopVisionShooting());
	}
//	public void canTest(boolean inTestMode){
//		if(inTestMode) testJoystick = new Joystick(1);
//		else testJoystick = null;
//	}
}

