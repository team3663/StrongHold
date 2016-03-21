package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_DriveBasedTime extends Command {
    private double targetTime = 0;
    private double startTime;
    private double speed = 0;

    public C_DriveBasedTime(double time, double pSpeed) {
        requires(Robot.ss_DriveTrain);
        targetTime = time;
        speed = pSpeed;
    }

    // Called just before this Command runs the first time
    //Curtis
    protected void initialize() {
    	startTime = Timer.getFPGATimestamp();
    	Robot.ss_DriveTrain.arcadeRobotDrive(speed, 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Timer.getFPGATimestamp() > targetTime + startTime;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.ss_DriveTrain.STOP();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
