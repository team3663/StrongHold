package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_DartPrepareForShot extends Command {

	private int target;
	private double speed;
    public C_DartPrepareForShot() {
        requires(Robot.ss_Dart);			// YOUR NUMBER GOES HERE
    }										//			||
    										//			||
    protected void initialize() {			//			\/		
    	 target = Robot.ss_Dart.ConvertInchesToTicks(pInches);
    	 speed = Robot.ss_Dart.findSpeed(target);
    }

    protected void execute() {
    	Robot.ss_Dart.moveDart(-speed, Robot.ss_PickupArm.isDown());
    }

     protected boolean isFinished() {
        return Robot.ss_Dart.hitLocation(speed, target);
    }

     
    protected void end() {
    	Robot.ss_Dart.STOP();
    }

     protected void interrupted() {
    	 end();
    }
}
