package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_VisionCenterGoal extends Command {

	NetworkTable table;
	
    public C_VisionCenterGoal() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.ss_DriveTrain);
        table = Robot.visionTable;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	table.putBoolean("Mode/commandRunning: ", true);
  //  	if (!table.getBoolean("C_/centeringGoal: ", false))
    	{
    //		end();
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		if (table.getBoolean("turnLeft: ", false))
		{
			Robot.ss_DriveTrain.arcadeRobotDrive(0, -0.4);
		}
		else
		{
			Robot.ss_DriveTrain.arcadeRobotDrive(0, 0.4);
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !table.getBoolean("C_/centeringGoal: ", false);
    }

    // Called once after isFinished returns true
    protected void end() {
        table.putBoolean("Mode/commandRunning: ", false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        table.putBoolean("Mode/commandRunning: ", false);
    }
}
