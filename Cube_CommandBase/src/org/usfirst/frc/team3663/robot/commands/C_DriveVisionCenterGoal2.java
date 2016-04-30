package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_DriveVisionCenterGoal2 extends Command {

	NetworkTable table;
	double startTime;
	double moveTime;
	
	double forwardVariable = 0.2;
	double switchForward = -1.0;
	double turnSpeed = 0.76;//0.72
	double angleToTime = 70.0;
	
	//
	//MAKE INTO A SWITCH STATEMENT FOR STAGES!!!!!!!
	//
	
    public C_DriveVisionCenterGoal2() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.ss_DriveTrain);
        table = Robot.visionTable;
    }

    boolean stop;
    boolean objectFound;
    boolean initFound = true;
    int moveCount = 0;
    int state = 0;
    int i = 0;
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	i = 0;
    	if (!table.getBoolean("foundObject: ",false))
    	{
    		initFound = false;
    		System.out.println("didn't find Object! DriveVisionSeeAnyGoal didn't work!");
    	}
    	table.putBoolean("Mode/commandRunning: ", true);
    	turnSpeed = 0.77;//75;//until we know we can go faster
    	
    	moveCount = 0;
    	state = 0;
    	Robot.ss_Shooter.autoFire = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double cameraMoveAngle = table.getNumber("cameraMoveAngle: ", 45);
    	boolean turnLeft = table.getBoolean("turnLeft: ",false);
    	
		switch (state)
		{
		case 0:
			startTime = Timer.getFPGATimestamp();
			if (Math.abs(cameraMoveAngle) < 10)//will need to add big one later
    		{
    			moveTime = Math.abs(cameraMoveAngle/(angleToTime-13));
    			forwardVariable = 0.6;
    			turnSpeed = 0.77;
    			if (moveTime < 0.13)
    			{
    				moveTime = 0.13;
    			}
    		}
			else// if (Math.abs(cameraMoveAngle) >= 10)
	    	{
	    		forwardVariable = 0.2;
	    		moveTime = Math.abs(cameraMoveAngle/(angleToTime-5));
	    	}
			state++;
			break;
		case 1:
			if (Math.abs(cameraMoveAngle) >= 10)
	    	{
	    		forwardVariable = 0.2;
	    		moveTime = Math.abs(cameraMoveAngle/(angleToTime-5));
	    	}
			if (turnLeft)
			{
				Robot.ss_DriveTrain.arcadeRobotDrive(forwardVariable*switchForward, turnSpeed);
			}
			else
			{
				Robot.ss_DriveTrain.arcadeRobotDrive(forwardVariable*switchForward, -turnSpeed);
			}
			if (Timer.getFPGATimestamp()-startTime > moveTime)
			{
				startTime = Timer.getFPGATimestamp();
				state++;
				Robot.ss_DriveTrain.arcadeRobotDrive(0,0);
			}
			break;
		case 2:
			if (Timer.getFPGATimestamp()-startTime > 0.4)
			{
				state++;// = 0;
				//startTime = Timer.getFPGATimestamp();
				moveCount++;
				switchForward*=-1;
			}
			break;
		case 3:
			if (i > 9)
			{
				i = 0;
				startTime = Timer.getFPGATimestamp();
				state = 0;
			}
			stop = !table.getBoolean("C_/centeringGoal: ",false);
			i++;
		}
	}

    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
		//stop = !table.getBoolean("C_/centeringGoal: ",false);// && state!=2;
    	return (stop || /*Robot.ss_Shooter.fireAnyways ||*/ moveCount > 7 || !initFound);// || Timer.getFPGATimestamp()-startTime > moveTime);// || !table.getBoolean("C_/centeringGoal: ", false);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.ss_Shooter.autoFire = true;
        table.putBoolean("Mode/commandRunning: ", false);
        Robot.ss_DriveTrain.STOP();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	
        end();
    }
}
