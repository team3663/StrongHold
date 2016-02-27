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
    
    // Called just before this Command runs the first time
    protected void initialize() {
    /*	moveTime = 0.6;
    	wasLastLeft = table.getBoolean("turnLeft: ",false);
    	wasLastLeft2 = wasLastLeft;
    	stop = !table.getBoolean("C_/centeringGoal: ",false);
    	startTime = Timer.getFPGATimestamp();
    	*/
		degrees = table.getNumber("cameraMoveAngle: ",90);
    	Robot.ss_DriveTrain.resetGyro();
    	table.putBoolean("Mode/commandRunning: ", true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//stop = Robot.ss_DriveTrain.spinByGyro((int)degrees);
    	
    	if (!objectFound)
    	{
    		Robot.ss_DriveTrain.resetGyro();
    		degrees = table.getNumber("cameraMoveAngle: ", 360);
    	}
    	
    	/*boolean turnLeft = table.getBoolean("turnLeft: ",false);
    	if (table.getBoolean("C_/centeringGoal: ",false))
    	{
			if (table.getBoolean("turnLeft: ", false))
			{
				Robot.ss_DriveTrain.autoArcadeDrive(0.675, 0);
			}
			else
			{
				Robot.ss_DriveTrain.autoArcadeDrive(-0.68, 0);
			}
    	}
    	if (turnLeft != wasLastLeft && wasLastLeft != wasLastLeft2)
    	{
    		moveTime-=0.1;
    	}
    	wasLastLeft2 = wasLastLeft;
		wasLastLeft = turnLeft;
		if (Timer.getFPGATimestamp()-startTime > moveTime)
		{
			startTime = Timer.getFPGATimestamp();
			stop = !table.getBoolean("C_/centeringGoal: ",false);
	        //Robot.ss_DriveTrain.STOP();
	        Timer.delay(1.0);
		}*/
 }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//work on this on Monday!!!
        return (Robot.ss_DriveTrain.spinByGyro((int)degrees));
    	//return (stop);// || Timer.getFPGATimestamp()-startTime > moveTime);// || !table.getBoolean("C_/centeringGoal: ", false);
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
