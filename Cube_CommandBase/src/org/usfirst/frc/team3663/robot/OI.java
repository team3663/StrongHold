package org.usfirst.frc.team3663.robot;

import org.usfirst.frc.team3663.robot.commands.CG_AutoShoot;
import org.usfirst.frc.team3663.robot.commands.CG_DriverPickupBall;
import org.usfirst.frc.team3663.robot.commands.TestCG_FullTest;
import org.usfirst.frc.team3663.robot.commands.CG_TeleopVisionShooting;
import org.usfirst.frc.team3663.robot.commands.CG_VisionCenterGoal;
import org.usfirst.frc.team3663.robot.commands.C_DartMoveNOTSAFE;
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
import org.usfirst.frc.team3663.robot.commands.C_HookSetPiston;
import org.usfirst.frc.team3663.robot.commands.C_ShooterShoot;
import org.usfirst.frc.team3663.robot.commands.C_TrentsVision;
import org.usfirst.frc.team3663.robot.commands.C_WaitSecs;
import org.usfirst.frc.team3663.robot.commands.C_WheelyBarZeroEnc;
import org.usfirst.frc.team3663.robot.commands.C_WinchMoveNoSafety;
import org.usfirst.frc.team3663.robot.commands.C_WinchGoToLocation;
import org.usfirst.frc.team3663.robot.commands.TC_TurnByGyro;
import org.usfirst.frc.team3663.robot.commands.TestCG_CycleTest;
import org.usfirst.frc.team3663.robot.commands.TestCG_TestRequiresAll;
import org.usfirst.frc.team3663.robot.commands.TestC_Cycle;
import org.usfirst.frc.team3663.robot.commands.TestC_DisableTestMode;
import org.usfirst.frc.team3663.robot.commands.TestC_StopCycleTest;
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
	//public Joystick testJoystick1= new Joystick(5);
	public Joystick testJoystick = new Joystick(4);
	
	//public Joystick visionTestStick = new Joystick(3);

//Buttons
  //Pickup Buttons
	private JoystickButton pickUpBall 		= new JoystickButton(driveJoystick, 4);
	private JoystickButton pickupRaiseArmD 	= new JoystickButton(driveJoystick, 6);
	private JoystickButton pickupLowerArmD 	= new JoystickButton(driveJoystick, 5);
	private JoystickButton pickupRaiseArmB 	= new JoystickButton(buttonJoystick, 6);
	private JoystickButton pickupLowerArmB 	= new JoystickButton(buttonJoystick, 5);
	
	private JoystickButton pickupCycleSafty = new JoystickButton(driveJoystick, 8);
	private JoystickButton pickupRunOut 	= new JoystickButton(driveJoystick, 2);
  //Shooter Buttons
	private JoystickButton shooterMotorsFullPower 	= new JoystickButton(driveJoystick, 1);
	public int shooterFirePistonNoWait 	= 7;
	public int shooterFirerPistonWait	= 2;
  //Winch Buttons
	private JoystickButton winchToHoist = 	  new JoystickButton(buttonJoystick, 1);
	private JoystickButton winchNotSafeMove = new JoystickButton(buttonJoystick, 7);
  //Hook Buttons
	private JoystickButton fireHookPiston = new JoystickButton(buttonJoystick, 3);
	private JoystickButton retractHookPiston = new JoystickButton(buttonJoystick,2);
  //NOTSAFE
	private JoystickButton moveDartNotSafe = new JoystickButton(buttonJoystick, 4); 
	
	
  //Test Joystick Buttons
	private JoystickButton runFullTest  	= new JoystickButton(testJoystick, 8);//start button on xbox ctrl
	private JoystickButton disableFullTest 	= new JoystickButton(testJoystick, 8);//start button on xbox ctrl
	private JoystickButton cycleTest    	= new JoystickButton(testJoystick, 7);
	private JoystickButton stopCycleTest   	= new JoystickButton(testJoystick, 9);
	private JoystickButton cycleUp			= new JoystickButton(testJoystick, 2);
	private JoystickButton cycleDown		= new JoystickButton(testJoystick, 1);
	
  //visionTestStick Buttons
//	private JoystickButton testCenterGoal = new JoystickButton(visionTestStick,1);
//	private JoystickButton testFineAdjust = new JoystickButton(visionTestStick,3);
//	private JoystickButton testTeleopVisionShooting = new JoystickButton(visionTestStick,2);
//	private JoystickButton turn90Degrees = new JoystickButton(visionTestStick,4);
//	private JoystickButton trentsBadCode = new JoystickButton(visionTestStick,6);

	private JoystickButton testCenterGoal = new JoystickButton(testJoystick,3);
	private JoystickButton testFineAdjust = new JoystickButton(testJoystick,4);
	private JoystickButton testTeleopVisionShooting = new JoystickButton(testJoystick,5);
	private JoystickButton turn90Degrees = new JoystickButton(testJoystick,6);

//	private JoystickButton trentsBadCode = new JoystickButton(visionTestStick,6);
	
	public OI(){
	//Real Buttons
	  //Pickup Buttons
		pickUpBall.whileHeld(new CG_DriverPickupBall());
		pickupRaiseArmD.whenPressed(new C_PickupFirePiston(false));
		pickupLowerArmD.whenPressed(new C_PickupFirePiston(true));
		pickupRaiseArmB.whenPressed(new C_PickupFirePiston(false));
		pickupLowerArmB.whenPressed(new C_PickupFirePiston(true));
		pickupCycleSafty.whenPressed(new C_PickupArmSwitchSafety());
		pickupRunOut.whileHeld(new C_PickupRunMotor(1));
	  //Shooter Buttons
		shooterMotorsFullPower.whileHeld(new C_ShooterHoldSpeed(-28000));
	  //Winch Buttons
		winchToHoist.whileHeld(new C_WinchGoToLocation(1111, -.5));
		winchNotSafeMove.whileHeld(new C_WinchMoveNoSafety());
	  //Hook
		fireHookPiston.whenPressed(new C_HookSetPiston(true));
		retractHookPiston.whenPressed(new C_HookSetPiston(false));
	  //WinchAndHookEnable
		//winchAndHookEnable.whenPressed(new CG_WinchAndHookCombine());
	  //Wheely Bar Buttons
		//...there are none
		//Test Buttons
		runFullTest.whileHeld(new TestCG_FullTest());
		disableFullTest.whenReleased(new TestC_DisableTestMode());
	  //Not Safe Buttons
		moveDartNotSafe.whenPressed(new C_DartMoveNOTSAFE());
		
		cycleTest.whenPressed(new TestCG_CycleTest());
		stopCycleTest.whenPressed(new TestC_StopCycleTest());
		cycleUp.whenPressed(new TestC_Cycle(true));
		cycleDown.whenPressed(new TestC_Cycle(false));
		
		//VisionTestButtons
		//testCenterGoal.whileHeld(new C_DriveVisionCenterGoal());
//		trentsBadCode.whenPressed(new C_TrentsVision());
		testCenterGoal.whenPressed(new CG_VisionCenterGoal());
		turn90Degrees.whenPressed(new TC_TurnByGyro(table.getNumber("cameraMoveAngle: ",0)));
		testFineAdjust.whileHeld(new C_DartPrepareForShot());//C_VisionFineAdjust());
		testTeleopVisionShooting.whileHeld(new CG_TeleopVisionShooting());
	}
//	public void canTest(boolean inTestMode){
//		if(inTestMode) testJoystick = new Joystick(1);
//		else testJoystick = null;
//	}
}

