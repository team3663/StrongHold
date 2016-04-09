package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class TestC_VisionDartTracking extends Command {
NetworkTable table;
	
	private int target;
	private double speed;
	double pInches;
	
	
    public TestC_VisionDartTracking() {
        requires(Robot.ss_Dart);	
        table = Robot.visionTable;
    }													
    													
    protected void initialize() {
    	//AY- need to test when shooter arm is lowered and sees intake arm
    	 
    	 
    }

    protected void execute() {
    	pInches = table.getNumber("distanceByMass: ", 0);
		 target = Robot.ss_Dart.ConvertInchesToTicks((int)pInches);
	
		 if (target > Robot.ss_Dart.maxDistance())
		 {
			 target = Robot.ss_Dart.maxDistance();
		 }
		 else if (target < Robot.ss_Dart.minDistance())
		 {
			 target = Robot.ss_Dart.minDistance();
		 }
		 speed = Robot.ss_Dart.findSpeed(target)*0.6;
		 Robot.ss_Dart.moveDart(speed, Robot.ss_PickupArm.isDown());
		/* if (Math.abs(target-Robot.ss_Dart.getPotentiometerValue()) > 100)
		 {
		    	Robot.ss_Dart.moveDart(speed, Robot.ss_PickupArm.isDown());
		 }*/
		 if (Robot.ss_Dart.hitLocation(speed, target))
		 {
			 Robot.ss_Dart.STOP();
			 Timer.delay(2.0);
		 }
    }

     protected boolean isFinished() {
        return false;
    	 //return Robot.ss_Dart.hitLocation(speed, target) || Robot.ss_Shooter.fireAnyways;
    }

     
    protected void end() {
    	Robot.ss_Dart.STOP();
    }

     protected void interrupted() {
    	 end();
    }
}
