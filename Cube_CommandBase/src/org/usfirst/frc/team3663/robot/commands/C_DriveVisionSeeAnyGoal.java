package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class C_DriveVisionSeeAnyGoal extends Command {

	NetworkTable table = Robot.visionTable;
	int degrees;
	boolean foundObject,leftDone;
	//====
	double startTime, endTime;
	double turnTime = 1.0;
	
    public C_DriveVisionSeeAnyGoal() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.ss_DriveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.ss_DriveTrain.resetGyro();
		leftDone = false;
		degrees = -45;
		//====
		startTime = Timer.getFPGATimestamp();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	foundObject = table.getBoolean("foundObject: ",false);
    	if (!foundObject)
    	{
    		/*leftDone = Robot.ss_DriveTrain.spinByGyro(degrees, 0.75);
    		if (leftDone)
    		{
    			degrees = 90;
    		}*/
    		//====
    		if ((Timer.getFPGATimestamp()-startTime) < (turnTime))
    		{
    			Robot.ss_DriveTrain.arcadeRobotDrive(0, 0.63);
    		}
    		else
    		{
    			Robot.ss_DriveTrain.arcadeRobotDrive(0, -0.63);
    		}
    		
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return foundObject;
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.ss_DriveTrain.STOP();
       // for (int i = 0; i < 10; i+=0.1)delay?
        {
        }
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
