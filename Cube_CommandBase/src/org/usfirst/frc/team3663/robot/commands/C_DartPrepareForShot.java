package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class C_DartPrepareForShot extends Command {

	NetworkTable table;
	
	private int target;
	private double speed;
	double pInches;
	
    public C_DartPrepareForShot() {
        requires(Robot.ss_Dart);	
        table = Robot.visionTable;
    }													
    													
    protected void initialize() {
    	//AY- need to test when shooter arm is lowered and sees intake arm
    	table.putBoolean("finishedMovingPot: ", false);
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
    	 speed = Robot.ss_Dart.findSpeed(target);
    	 
    	 Robot.gui.sendNumber("dart/prepare4Shot potVal", target);
    	 
    }

    protected void execute() {
    	Robot.ss_Dart.moveDart(speed, Robot.ss_PickupArm.isDown());
    }

     protected boolean isFinished() {//if too close is no longer a problem, then add
        return Robot.ss_Dart.hitLocation(speed, target) /*|| !Robot.visionTable.getBoolean("foundObject: ",false)*/|| Robot.ss_Shooter.fireAnyways;
    }

     
    protected void end() {
    	table.putBoolean("finishedMovingPot: ", true);
    	Robot.ss_Dart.STOP();
    }

     protected void interrupted() {
    	 end();
    }
}
