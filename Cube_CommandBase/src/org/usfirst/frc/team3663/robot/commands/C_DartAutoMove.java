package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_DartAutoMove extends Command {

	private int targetDist;
	private double speed;
    public C_DartAutoMove(int pTarget) {
        requires(Robot.ss_Dart);
        targetDist = pTarget+20;
    }

    protected void initialize() {
    	speed = Robot.ss_Dart.findSpeed(targetDist);
    }
                                                                                                                             
    protected void execute() {
    	Robot.ss_Dart.moveDart(-speed, Robot.ss_PickupArm.isDown());
    }

    protected boolean isFinished() {
        return Robot.ss_Dart.hitLocation(speed, targetDist);
    }

    protected void end() {
    	Robot.ss_Dart.STOP();
    }

    protected void interrupted() {
    	end();
    }
}
