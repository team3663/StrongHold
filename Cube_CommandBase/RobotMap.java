package org.usfirst.frc.team3663.robot;

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
	//analog
		public static int dartPotentiometer = 0;
    
/*******Solenoides*******/
	    public static int[] pickUpSolenoid  = new int[]{4,5};    
	    public static int[] shooterSolenoid = new int[]{6,7};
	    
/****DriveControlles*****/
	//driveTrain
	    public static int driveAxisForward = 0;
	    public static int driveAxisTurn    = 1;
	    
	    
	    
/******ExtraValues*******/
	//Dart
	    public static int maxDartDistance = 2000;
	    public static int minDartDistance = 1000;
}
