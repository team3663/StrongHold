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
		public static int driveLeftMotor1 =  8;
		public static int driveLeftMotor2 =  2;
		public static int driveRightMotor1 = 4;
		public static int driveRightMotor2 = 9;
	//shooter
		public static int shooterMotorTop =    11;
		public static int shooterMotorBottom = 12;
	//pickup
		public static int pickUpMotor = 6;
	//dart
		public static int dartMotor = 7;
	//Winch
		public static int winchMotor1 = 3;
		public static int winchMotor2 = 5;
	//wheely bar
		public static int wheelyBarMotor = 10;
	//Hook
		public static int hookMotor = 1;
		
	
/*********Sensors********/
	//Analog
		public static int dartPotentiometer = 0;
		public static int driveGyro = 		  1;
	//Digital IO
		public static int wheelyBarLimitSwitch = 8;
		public static int pickupLowerLimit = 	 9;
		
		public static int[] autoSwitchs = new int[]{1, 2, 3, 4};
    
/*********Relays*********/
	//Camera
		public static int cameraRelay = 0;
/*******Solenoids*******/
	    public static int[] pickUpSolenoid  = new int[]{3,2};    
	    public static int[] shooterSolenoid = new int[]{1,0};
	    
/****DriveControlles*****/
	//driveTrain
	    public static int driveAxisForward = 2;
	    public static int driveAxisReverse = 3;
	    public static int driveAxisTurn =	 0;
	//winch
	    public static int winchAxis = 5;
	//hook
	    public static int hookAxis = 1;
	    
/******ChangeValues******/
	//DriveTrain
	    public static int encoderTicksPerInch = 20;
	//Dart
	    public static int maxDistanceValue = 2000;
	    public static int minDistanceValue = 1000;
}
