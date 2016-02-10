package org.usfirst.frc.team3663.robot;
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
	
/*********Analogs********/
		public static int dartPotentiometer = 0;
    
/*******Solinoides*******/
	    public static int[] pickUpSolenoid  = new int[]{4,5};    
	    public static int[] shooterSolenoid = new int[]{6,7};
	    
/****DriveControlles*****/
	//driveTrain
	    public static int DriveAxisForward = 0;
	    public static int DriveAxisTurn    = 1;
}
