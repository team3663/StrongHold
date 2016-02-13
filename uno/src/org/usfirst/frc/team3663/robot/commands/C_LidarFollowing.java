
package org.usfirst.frc.team3663.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3663.robot.Robot;

/**
 *
 */
public class C_LidarFollowing extends Command {

    public C_LidarFollowing() {
        // Use requires() here to declare subsystem dependencies
        //requires(Robot.exampleSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double driveSpeed = (Robot.lidar.getDistance() - 250) / 250;
    	if(driveSpeed < 0.1) driveSpeed = 0;
    	SmartDashboard.putNumber("Driving Speed:", driveSpeed);
    	Robot.dTrain.drive(driveSpeed);
    	SmartDashboard.putString("C_LidarFollowing", "running");
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(Robot.lidar.getDistance() > -10 && Robot.lidar.getDistance() < 10){
        	SmartDashboard.putString("C_LidarFollowing", "terminated");
    		return true;
    	}
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
