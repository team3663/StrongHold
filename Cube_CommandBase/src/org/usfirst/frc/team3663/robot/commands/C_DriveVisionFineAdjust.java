package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class C_DriveVisionFineAdjust extends Command {

	NetworkTable table;
	boolean raiseShooterArm;
	boolean tryHitEnd;
	
    public C_DriveVisionFineAdjust() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.ss_Dart);
        table = Robot.visionTable;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        table.putBoolean("Mode/commandRunning: ", true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (table.getBoolean("ShooterArm/raiseShooterArm: ",false))
    	{
        	tryHitEnd = Robot.ss_Dart.hitLocation(1.0,Robot.ss_Dart.maxDistance());
    	}
    	else
    	{
        	tryHitEnd = Robot.ss_Dart.hitLocation(-1.0,Robot.ss_Dart.minDistance());
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (tryHitEnd || !table.getBoolean("ShooterArm/moveShooterArm: ",false));
    }

    // Called once after isFinished returns true
    protected void end() {
    	table.putBoolean("finishedMovingPot: ", true);
    	Robot.ss_Dart.STOP();
        table.putBoolean("Mode/commandRunning: ", false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
