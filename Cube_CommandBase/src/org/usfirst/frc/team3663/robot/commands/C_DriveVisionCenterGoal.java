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
		
	double forwardVariable = 0.2;
	double switchForward = -1.0;
	double degrees;
	double turnSpeed = 0.68;
	double angleToTime = 70.0;
	
    public C_DriveVisionCenterGoal() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.ss_DriveTrain);
        table = Robot.visionTable;
    }

    boolean stop;
    boolean objectFound;
    //double speed;
    boolean firstTime;
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	moveTime = Math.abs(table.getNumber("cameraMoveAngle: ",45)/angleToTime);
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
    	turnSpeed = 0.75;//until we know we can go faster
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
    	double cameraMoveAngle = table.getNumber("cameraMoveAngle: ", 45);
    	boolean turnLeft = table.getBoolean("turnLeft: ",false);
    	if (table.getBoolean("C_/centeringGoal: ",false))
    	{
			/*if (!table.getBoolean("foundObject: ",false))
			{
				turnLeft = !turnLeft;
			}*/
			if (turnLeft)
			{
				Robot.ss_DriveTrain.arcadeRobotDrive(forwardVariable*switchForward, turnSpeed);
			}
			else
			{
				Robot.ss_DriveTrain.arcadeRobotDrive(forwardVariable*switchForward, -turnSpeed);
			}
	    	if (Math.abs(cameraMoveAngle) >= 10)//(turnLeft != wasLastLeft && wasLastLeft != wasLastLeft2 && moveTime > 0.05)
	    	{
	    		moveTime = Math.abs(cameraMoveAngle/angleToTime);//*1.35;
	    		//moveTime-=0.1;
	    	}
			/*else//if (Math.abs(cameraMoveAngle) < 8)
    		{
    			moveTime = Math.pow(Math.abs(cameraMoveAngle/angleToTime)*6.0, .25);
    			forwardVariable = 0.4;
    			turnSpeed = 0.65;
    		}*/
	    	wasLastLeft2 = wasLastLeft;
			wasLastLeft = turnLeft;
    	}
		if (Timer.getFPGATimestamp()-startTime > moveTime)
		{
			Robot.ss_DriveTrain.arcadeRobotDrive(0,0);
	        //Robot.ss_DriveTrain.STOP();
	        Timer.delay(1.0);
	        cameraMoveAngle = table.getNumber("cameraMoveAngle: ",45);
			startTime = Timer.getFPGATimestamp();
	    /*	if (Math.abs(cameraMoveAngle) >= 8)//(turnLeft != wasLastLeft && wasLastLeft != wasLastLeft2 && moveTime > 0.05)
	    	{
	    		moveTime = Math.abs(cameraMoveAngle/angleToTime);//*1.35;
	    		//moveTime-=0.1;
	    	}
	    	else*/if (Math.abs(cameraMoveAngle) < 10)
    		{
    			moveTime = Math.pow(Math.abs(cameraMoveAngle/angleToTime)*1.2, 1.2);
    			forwardVariable = 0.7;
    			turnSpeed = 0.67;
    		}
			switchForward*=-1;
			stop = !table.getBoolean("C_/centeringGoal: ",false);
			
		//	CHANGE CODE AND PLAY WITH VARIABLES
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
