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
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
