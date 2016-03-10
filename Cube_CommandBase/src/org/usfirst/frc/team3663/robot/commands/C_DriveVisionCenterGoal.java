package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_DriveVisionCenterGoal extends Command {

	NetworkTable table;
	double startTime;
	boolean wasLastLeft,wasLastLeft2;
	double moveTime;
		
	double degrees;
	
    public C_DriveVisionCenterGoal() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.ss_DriveTrain);
        table = Robot.visionTable;
    }

    boolean stop;
    boolean objectFound;
    double speed;
    boolean firstTime;
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	moveTime = Math.abs(table.getNumber("cameraMoveAngle: ",45)/45);
    	wasLastLeft = table.getBoolean("turnLeft: ",false);
    	wasLastLeft2 = wasLastLeft;
    	stop = !table.getBoolean("C_/centeringGoal: ",false);
    	startTime = Timer.getFPGATimestamp();
    	//====
    	
    	if (!table.getBoolean("foundObject: ",false))
    	{
    		System.out.println("didn't find Object! DriveVisionSeeAnyGoal didn't work!");
    	}
		degrees = table.getNumber("cameraMoveAngle: ",360);
    	Robot.ss_DriveTrain.resetGyro();
    	table.putBoolean("Mode/commandRunning: ", true);
    	speed = 0.75;//until we know we can go faster
    	firstTime = true;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//stop = Robot.ss_DriveTrain.spinByGyro((int)degrees);
    	
    	/*objectFound = table.getBoolean("foundObject: ",false);
    	if (objectFound)
    	{
    		if (firstTime)
    		{
    			Robot.ss_DriveTrain.resetGyro();
        		degrees = table.getNumber("cameraMoveAngle: ", 360);
        		speed = 0.7;
        		firstTime = false;
    		}
    	}*/
    	//-----------------------------------------------------------------
    	boolean turnLeft = table.getBoolean("turnLeft: ",false);
    	if (table.getBoolean("C_/centeringGoal: ",false))
    	{
			/*if (!table.getBoolean("foundObject: ",false))
			{
				turnLeft = !turnLeft;
			}*/
			if (turnLeft)
			{
				Robot.ss_DriveTrain.arcadeRobotDrive(0, 0.62);
			}
			else
			{
				Robot.ss_DriveTrain.arcadeRobotDrive(0, -0.65);
			}
	    	if (turnLeft != wasLastLeft && wasLastLeft != wasLastLeft2 && moveTime > 0.05)
	    	{
	    		moveTime = Math.abs(table.getNumber("cameraMoveAngle: ", 45)/45);
	    		//moveTime-=0.1;
	    	}
	    	wasLastLeft2 = wasLastLeft;
			wasLastLeft = turnLeft;
			if (Timer.getFPGATimestamp()-startTime > moveTime)
			{
				Robot.ss_DriveTrain.arcadeRobotDrive(0,0);
				stop = !table.getBoolean("C_/centeringGoal: ",false);
		        //Robot.ss_DriveTrain.STOP();
		        Timer.delay(1.0);
				startTime = Timer.getFPGATimestamp();
			}
    	}
 }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        //return (Robot.ss_DriveTrain.spinByGyro((int)degrees, speed));
    	return (stop);// || Timer.getFPGATimestamp()-startTime > moveTime);// || !table.getBoolean("C_/centeringGoal: ", false);
    }

    // Called once after isFinished returns true
    protected void end() {
        table.putBoolean("Mode/commandRunning: ", false);
        Robot.ss_DriveTrain.STOP();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	
        end();
    }
}
