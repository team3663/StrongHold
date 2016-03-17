package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_DriveBasedTime extends Command {
    public int delay = 0;
    public double speed = 0;

    public C_DriveBasedTime(int pDelay, double pSpeed) {
        requires(Robot.ss_DriveTrain);
        delay = pDelay;
        
    }

    // Called just before this Command runs the first time
    //Curtis
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	delay--;
    	Robot.ss_DriveTrain.arcadeRobotDrive(-.7, 0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return delay < 0;
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
