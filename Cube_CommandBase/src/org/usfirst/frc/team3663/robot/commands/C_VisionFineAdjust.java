package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class C_VisionFineAdjust extends Command {

	NetworkTable table;
	boolean raiseShooterArm;
	
    public C_VisionFineAdjust() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.ss_Dart);
        table = Robot.visionTable;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	raiseShooterArm = table.getBoolean("ShooterArm/raiseShooterArm: ",false);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (raiseShooterArm)
    	{
    		
    	}
    	else
    	{
    		
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !table.getBoolean("ShooterArm/moveShooterArm: ",false);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
