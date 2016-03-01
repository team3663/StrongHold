package org.usfirst.frc.team3663.robot;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {	
	
/********CANTalons*******/
	//drive
		public int driveLeftMotor1 =  4;
		public int driveLeftMotor2 =  9;
		public int driveRightMotor1 = 8;
		public int driveRightMotor2 = 2;
	//shooter
		public int shooterMotorTop =    11;
		public int shooterMotorBottom = 12;
	//pickup
		public int pickUpMotor = 6;
	//dart
		public int dartMotor = 7;
	//Winch
		public int winchMotor1 = 3;
		public int winchMotor2 = 5;
	//wheely bar
		public int wheelyBarMotor = 10;
	//Hook
		public int hookMotor = 1;
		
	
/*********Sensors********/
	//Analog
		public int dartPotentiometer = 0;
		public int driveGyro = 		  1;
	//Digital IO
		public int wheelyBarLimitSwitch = 8;
		public int pickupLowerLimit = 	 9;
		
		public int[] autoSwitchs = new int[]{5, 6, 7, 10};
    
/*********Relays*********/
	//Camera
		public int cameraRelay = 0;
/*******Solenoids*******/
	    public int[] pickUpSolenoid  = new int[]{7,6};//3.2  
	    public int[] shooterSolenoid = new int[]{4,5};//1.0
	    
/****DriveControlles*****/
	//driveTrain
	    public int driveAxisForward = 2;
	    public int driveAxisReverse = 3;
	    public int driveAxisTurn =	 0;
	//winch
	    public int winchAxis = 5;
	//hook
	    public int hookAxis = 1;
	    
/******ChangeValues******/
	//DriveTrain
	    public int encoderTicksPerInch = 20;
	//Dart
	    public int minDistanceValue = 900;//410;
	    public int maxDistanceValue = 2200;//minDistanceValue + 1790;
	    public int soft1;
	    public int soft2;
	    public int hard1;
	    public int hard2;
	    public int touch1;
	    public int touch2;
}
