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
		public static int driveLeftMotor1  = 1;
		public static int driveLeftMotor2  = 2;
		public static int driveRightMotor1 = 3;
		public static int driveRightMotor2 = 4;
	//shooter
		public static int shooterMotorTop    = 5;
		public static int shooterMotorBottom = 6;
	//pickup
		public static int pickUpMotor = 7;
	//dart
		public static int dartMotor = 8;
	
/*********Sensors********/
	//Analog
		public static int dartPotentiometer = 0;
		public static int driveGyro  		= 1;
	//Digital IO
		public static int[] driveLeftEncoder  = new int[]{0, 1};
		public static int[] driveRightEncoder = new int[]{2, 3};
    
/*********Relays*********/
	//Camera
		public static int cameraRelay = 0;
/*******Solinoides*******/
	    public static int[] pickUpSolenoid  = new int[]{6,7};    
	    public static int[] shooterSolenoid = new int[]{5,4};
	    
/****DriveControlles*****/
	//driveTrain
	    public static int driveAxisForward = 2;
	    public static int driveAxisReverse = 3;
	    public static int driveAxisTurn    = 0;
	    
/******ChangeValues******/
	//DriveTrain
	    public static int encoderTicksPerInch = 20;
	//Dart
	    public static int maxDistanceValue = 2000;
	    public static int minDistanceValue = 1000;
}
