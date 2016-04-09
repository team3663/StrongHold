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
	double turnTime = 1.25;
	int dir = 0;
	
    public C_DriveVisionSeeAnyGoal() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.ss_DriveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.ss_Shooter.fireAnyways = false;
    	Robot.ss_DriveTrain.resetGyro();
		leftDone = false;
		degrees = -45;
		//====
		dir = 0;
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
    		switch(dir)
    		{
    		case 0:
    			Robot.ss_DriveTrain.arcadeRobotDrive(0, -0.85);
    			if ((Timer.getFPGATimestamp()-startTime) > (turnTime))
    			{
    				dir++;
    				startTime = Timer.getFPGATimestamp();
    			}
    			break;
    		case 1:
    			Robot.ss_DriveTrain.arcadeRobotDrive(0, 0.82);
    			if ((Timer.getFPGATimestamp()-startTime) > (2*turnTime))
    			{
    				dir++;
    				startTime = Timer.getFPGATimestamp();
    			}
    			break;
    		case 2:
    			if (Robot.ss_Dart.hitLocation(Robot.ss_Dart.findSpeed(Robot.robotMap.touch2+450), Robot.robotMap.touch2+350))
    			{
    				Robot.ss_Dart.STOP();
    				dir++;
    				startTime = Timer.getFPGATimestamp();
    			}
    			else
    			{
    				Robot.ss_Dart.moveDart(Robot.ss_Dart.findSpeed(Robot.robotMap.touch2+450),Robot.ss_PickupArm.isDown());
    			}
    			break;
    		case 3:
    			Robot.ss_DriveTrain.arcadeRobotDrive(0, -0.85);
    			if ((Timer.getFPGATimestamp()-startTime) > (2*turnTime))
    			{
    				dir++;
    				startTime = Timer.getFPGATimestamp();
    			}
    			break;
    		case 4:
    			dir++;
    			/*if (Robot.ss_Dart.hitLocation(1.0, Robot.robotMap.touch2+300))
    			{
    				dir++;
    				startTime = Timer.getFPGATimestamp();
    			}
    			else
    			{
    				Robot.ss_Dart.moveDart(1.0,Robot.ss_PickupArm.isDown());
    			}*/
    			break;
    		case 5:
    			dir++;
    			/*Robot.ss_DriveTrain.arcadeRobotDrive(0, 0.82);
    			if ((Timer.getFPGATimestamp()-startTime) > (2*turnTime))
    			{
    				dir++;
    				startTime = Timer.getFPGATimestamp();
    			}*/
    			break;
    		case 6: 
    			break;
    		}
    		Robot.gui.sendNumber("shooter/dir", dir);
    		
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return ((foundObject) || (Robot.ss_Shooter.fireAnyways) || (dir > 5));
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
