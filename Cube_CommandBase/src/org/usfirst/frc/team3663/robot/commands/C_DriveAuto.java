package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_DriveAuto extends Command {

	private int target;
	private double turnValue;
	private double maxSpeed;
    public C_DriveAuto(int pTarget, double pTurnValue, double pMaxSpeed) {
    	maxSpeed = pMaxSpeed;
    	turnValue = pTurnValue;
    	target = pTarget;
    	requires(Robot.ss_DriveTrain);
    }

    protected void initialize() {
    	target = Robot.ss_DriveTrain.setDistanceEncoder(target);
    }

    protected void execute() {
    	Robot.ss_DriveTrain.driveByEncoder(maxSpeed, target, turnValue);
    }

    protected boolean isFinished() {
        return Robot.ss_DriveTrain.checkDistance(target);
    }

    protected void end() {
    	Robot.ss_DriveTrain.STOP();
    }

    protected void interrupted() {
    	end();
    }
}
