package org.usfirst.frc.team3663.robot;

import org.usfirst.frc.team3663.robot.commands.CG_DriverPickupBall;
import org.usfirst.frc.team3663.robot.commands.CG_TeleopVisionShooting;
import org.usfirst.frc.team3663.robot.commands.CG_VisionCenterGoal;
import org.usfirst.frc.team3663.robot.commands.CG_WaitForShooterThenShoot;
import org.usfirst.frc.team3663.robot.commands.C_DartPrepareForShot;
import org.usfirst.frc.team3663.robot.commands.C_DriveControllerDPad;
import org.usfirst.frc.team3663.robot.commands.C_PickupArmSwitchSafety;
import org.usfirst.frc.team3663.robot.commands.C_PickupFirePiston;
import org.usfirst.frc.team3663.robot.commands.C_PickupRunMotor;
import org.usfirst.frc.team3663.robot.commands.C_ShooterFirePiston;
import org.usfirst.frc.team3663.robot.commands.C_ShooterHoldSpeed;
import org.usfirst.frc.team3663.robot.commands.C_ShooterRunMotors;
import org.usfirst.frc.team3663.robot.commands.C_DriveVisionCenterGoal;
import org.usfirst.frc.team3663.robot.commands.C_DriveVisionFineAdjust;
import org.usfirst.frc.team3663.robot.commands.C_ShooterShoot;
import org.usfirst.frc.team3663.robot.commands.C_WheelyBarZeroEnc;
import org.usfirst.frc.team3663.robot.commands.C_WheelyBarZeroEncoder;
import org.usfirst.frc.team3663.robot.commands.C_WinchMoveNoSafety;
import org.usfirst.frc.team3663.robot.commands.C_WinchGoToLocation;
import org.usfirst.frc.team3663.robot.commands.TC_TurnByGyro;
import org.usfirst.frc.team3663.robot.commands.TestCG_TestRequiresAll;
import org.usfirst.frc.team3663.robot.commands.TestC_Cycle;
import org.usfirst.frc.team3663.robot.commands.TestC_ToggleTestMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	NetworkTable table = Robot.visionTable;
	
	//Joysticks	
	public Joystick driveJoystick = new Joystick(0);
	public Joystick buttonJoystick = new Joystick(1);
	public Joystick testJoystick1= new Joystick(5);
	public Joystick testJoystick = new Joystick(4);
	
	public Joystick visionTestStick = new Joystick(3);

//Buttons
  //Pickup Buttons
	private JoystickButton pickUpBall 		= new JoystickButton(driveJoystick, 4);
	private JoystickButton pickupRaiseArm 	= new JoystickButton(driveJoystick, 6);
	private JoystickButton pickupLowerArm 	= new JoystickButton(driveJoystick, 5);
	private JoystickButton pickupCycleSafty = new JoystickButton(driveJoystick, 8);
	private JoystickButton pickupRunOut 	= new JoystickButton(buttonJoystick, 2);
  //Shooter Buttons
	private JoystickButton shooterMotorsFullPower 	= new JoystickButton(driveJoystick, 1);
	public int shooterFirePistonNoWait 	= 7;
	public int shooterFirerPistonWait	= 2;
  //Winch Buttons
	private JoystickButton winchToHoist = 	  new JoystickButton(buttonJoystick, 1);
	private JoystickButton winchNotSafeMove = new JoystickButton(buttonJoystick, 7);
  //Wheely Bar Buttons
	private JoystickButton wheelyBarMoveToZero = new JoystickButton(buttonJoystick, 4);
	private JoystickButton wheelyBarEncZero = new JoystickButton(buttonJoystick, 8);
	
	
  //Test Joystick Buttons
	private JoystickButton toggleTestMode	= new JoystickButton(testJoystick, 7);
	private JoystickButton cycleUp			= new JoystickButton(testJoystick, 2);
	private JoystickButton cycleDown		= new JoystickButton(testJoystick, 1);
	
  //visionTestStick Buttons
	private JoystickButton testCenterGoal = new JoystickButton(visionTestStick,1);
	private JoystickButton testFineAdjust = new JoystickButton(visionTestStick,3);
	private JoystickButton testTeleopVisionShooting = new JoystickButton(visionTestStick,2);
	private JoystickButton turn90Degrees = new JoystickButton(visionTestStick,4);
	
	public OI(){
	//Real Buttons
	  //Pickup Buttons
		pickUpBall.whileHeld(new CG_DriverPickupBall());
		pickupRaiseArm.whenPressed(new C_PickupFirePiston(false));
		pickupLowerArm.whenPressed(new C_PickupFirePiston(true));
		pickupCycleSafty.whenPressed(new C_PickupArmSwitchSafety());
		pickupRunOut.whenPressed(new C_PickupRunMotor(1));
	  //Shooter Buttons
		shooterMotorsFullPower.whileHeld(new C_ShooterHoldSpeed(-28000));
	  //Winch Buttons
		winchToHoist.whileHeld(new C_WinchGoToLocation(1111, -.5));
		winchNotSafeMove.whileHeld(new C_WinchMoveNoSafety());
	  //WinchAndHookEnable
		//winchAndHookEnable.whenPressed(new CG_WinchAndHookCombine());
	  //Wheely Bar Buttons
		wheelyBarMoveToZero.whileHeld(new C_WheelyBarZeroEncoder());
		wheelyBarEncZero.whenPressed(new C_WheelyBarZeroEnc());
		
		//Test Buttons
		toggleTestMode.whenPressed(new TestC_ToggleTestMode());
		cycleUp.whenPressed(new TestC_Cycle(true));
		cycleDown.whenPressed(new TestC_Cycle(false));
		
		//VisionTestButtons
		//testCenterGoal.whileHeld(new C_DriveVisionCenterGoal());
		testCenterGoal.whileHeld(new CG_VisionCenterGoal());
		turn90Degrees.whenPressed(new TC_TurnByGyro(table.getNumber("cameraMoveAngle: ",0)));
		testFineAdjust.whileHeld(new C_DartPrepareForShot());//C_VisionFineAdjust());
		testTeleopVisionShooting.whileHeld(new CG_TeleopVisionShooting());
	}
//	public void canTest(boolean inTestMode){
//		if(inTestMode) testJoystick = new Joystick(1);
//		else testJoystick = null;
//	}
}

