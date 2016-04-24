package org.usfirst.frc.team3663.robot;

import org.usfirst.frc.team3663.robot.commands.CG_AutoVisionShooting;
import org.usfirst.frc.team3663.robot.commands.CG_ConfigHookSetUp;
import org.usfirst.frc.team3663.robot.commands.CG_DriverPickupBall;
import org.usfirst.frc.team3663.robot.commands.CG_PitAutoVisionShooting;
import org.usfirst.frc.team3663.robot.commands.TestCG_FullTest;
import org.usfirst.frc.team3663.robot.commands.CG_VisionCenterGoal;
import org.usfirst.frc.team3663.robot.commands.C_DartMoveNOTSAFE;
import org.usfirst.frc.team3663.robot.commands.C_DriveToAngle;
import org.usfirst.frc.team3663.robot.commands.C_PickupFirePiston;
import org.usfirst.frc.team3663.robot.commands.C_PickupRunMotor;
import org.usfirst.frc.team3663.robot.commands.C_EndCommand;
import org.usfirst.frc.team3663.robot.commands.C_FlashLightToggle;
import org.usfirst.frc.team3663.robot.commands.C_HookSetPiston;
import org.usfirst.frc.team3663.robot.commands.C_ShooterShoot;
import org.usfirst.frc.team3663.robot.commands.C_WinchMoveNoSafety;
import org.usfirst.frc.team3663.robot.commands.C_WinchGoToLocation;
import org.usfirst.frc.team3663.robot.commands.TestCG_CycleTest;
import org.usfirst.frc.team3663.robot.commands.TestC_Cycle;
import org.usfirst.frc.team3663.robot.commands.TestC_DisableTestMode;
import org.usfirst.frc.team3663.robot.commands.TestC_StopCycleTest;
import org.usfirst.frc.team3663.robot.commands.TestC_VisionDartTracking;

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
	public Joystick winchJoystick = new Joystick(2);
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
	
	//private JoystickButton pickupCycleSafty = new JoystickButton(driveJoystick, 8);  Button 8 is used for presets
	private JoystickButton pickupRunOut 	= new JoystickButton(driveJoystick, 2);
  //Shooter Buttons
	private JoystickButton shooterMotorsFullPower 	= new JoystickButton(driveJoystick, 1);
	public int shooterFirePistonNoWait 	= 7;
	public int shooterFirerPistonWait	= 2;
  //Winch Buttons
	private JoystickButton turnOnFlashlight = 	  new JoystickButton(buttonJoystick, 1);
	private JoystickButton winchNotSafeMove = new JoystickButton(buttonJoystick, 7);
  //DartButtons
	private JoystickButton configHookSetUp = new JoystickButton(buttonJoystick,8);
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

	private JoystickButton testCenterGoal = new JoystickButton(testJoystick,3);//on xbox one is x
	//private JoystickButton testFineAdjust = new JoystickButton(testJoystick,4);
	private JoystickButton testTeleopVisionShooting = new JoystickButton(testJoystick,5);//on xbox one is bumperL
	private JoystickButton turn90Degrees = new JoystickButton(testJoystick,4);
	
//	private JoystickButton trentsBadCode = new JoystickButton(visionTestStick,6);
	
	public OI(){
	//Real Buttons
	  //Pickup Buttons
		pickUpBall.whileHeld(new CG_DriverPickupBall());
		pickupRaiseArmD.whenPressed(new C_PickupFirePiston(false));
		pickupLowerArmD.whenPressed(new C_PickupFirePiston(true));
		pickupRaiseArmB.whenPressed(new C_PickupFirePiston(false));
		pickupLowerArmB.whenPressed(new C_PickupFirePiston(true));
		//pickupCycleSafty.whenPressed(new C_PickupArmSwitchSafety());
		pickupRunOut.whileHeld(new C_PickupRunMotor(1));
	  //Shooter Buttons
		shooterMotorsFullPower.whileHeld(new C_ShooterShoot());
	  //Winch Buttons
		turnOnFlashlight.whenPressed(new C_FlashLightToggle());//new C_WinchGoToLocation(1111, -.5));
		winchNotSafeMove.whileHeld(new C_WinchMoveNoSafety());
	  //Dart Buttons
		configHookSetUp.whenPressed(new CG_ConfigHookSetUp());
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
		
		turn90Degrees.whenPressed(new C_DriveToAngle(-90.0, 0.0, 0.6));
		//VisionTestButtons
		//testCenterGoal.whileHeld(new C_DriveVisionCenterGoal());
//		trentsBadCode.whenPressed(new C_TrentsVision());
		//CG_VisionCenterGoal cCenterGoal = new CG_VisionCenterGoal();
		testCenterGoal.whileHeld(new TestC_VisionDartTracking());
		//testCenterGoal.whenReleased(new C_EndCommand(cCenterGoal));
		//NOT USE!!! //turn90Degrees.whenPressed(new TC_TurnByGyro(table.getNumber("cameraMoveAngle: ",0)));
//		C_DartPrepareForShot cPrepareDartShot = new C_DartPrepareForShot();
		//testFineAdjust.whenPressed(cPrepareDartShot);//C_VisionFineAdjust());
		//testFineAdjust.whenReleased(new C_EndCommand(cPrepareDartShot));
		
		//CG_PitAutoVisionShooting cgTeleopVision = new CG_PitAutoVisionShooting();
		//testTeleopVisionShooting.whenPressed(cgTeleopVision);
		//testTeleopVisionShooting.whenReleased(new C_EndCommand(cgTeleopVision));
		testTeleopVisionShooting.whileHeld(new CG_AutoVisionShooting());
	}
//	public void canTest(boolean inTestMode){
//		if(inTestMode) testJoystick = new Joystick(1);
//		else testJoystick = null;
//	}
	public double getOneDirectionButtonJoyWinchAxis(){
		double value = buttonJoystick.getRawAxis(5);
		if(value > 0){
			return value;
		}
		return 0;
	}
	public void updateDashboard(){
		Robot.gui.sendNumber("operation/DriveJoyAxis0", driveJoystick.getRawAxis(0));
		Robot.gui.sendNumber("operation/DriveJoyAxis1", driveJoystick.getRawAxis(1));
		Robot.gui.sendNumber("operation/DriveJoyAxis2", driveJoystick.getRawAxis(2));
		Robot.gui.sendNumber("operation/DriveJoyAxis3", driveJoystick.getRawAxis(3));
		Robot.gui.sendNumber("operation/DriveJoyAxis4", driveJoystick.getRawAxis(4));
		Robot.gui.sendNumber("operation/DriveJoyAxis5", driveJoystick.getRawAxis(5));

		Robot.gui.sendNumber("operation/ButtonJoyAxis0", buttonJoystick.getRawAxis(0));
		Robot.gui.sendNumber("operation/ButtonJoyAxis1", buttonJoystick.getRawAxis(1));
		Robot.gui.sendNumber("operation/ButtonJoyAxis2", buttonJoystick.getRawAxis(2));
		Robot.gui.sendNumber("operation/ButtonJoyAxis3", buttonJoystick.getRawAxis(3));
		Robot.gui.sendNumber("operation/ButtonJoyAxis4", buttonJoystick.getRawAxis(4));
		Robot.gui.sendNumber("operation/ButtonJoyAxis5", buttonJoystick.getRawAxis(5));

	}
}

