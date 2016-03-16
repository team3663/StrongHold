package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TestC_TestDriveTrain extends Command {

	int state;
	double speed;
	double topSpeed = 0.7;
	double delta = 0.01;
	
	double endTime;
	double delay = 0.5;
	
    public TestC_TestDriveTrain() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.ss_DriveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 0;
    	speed = 0;
    	Robot.gui.sendString("Test/testState","driveTrain starting");
    	Robot.ss_Test.driveTrainStatus = Robot.ss_Test.untested;
    	Robot.ss_Test.update();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//forward, backward, 0, leftTurn, rightTurn
    	switch(state){
    	case 0://forward
			Robot.ss_Test.driveTrainStatus = "verify going Forward";
    		Robot.ss_Test.driveTrainStatus = Robot.ss_Test.testing;
    		Robot.ss_DriveTrain.arcadeRobotDrive(speed, 0);
    		speed+=delta;
    		if (speed > topSpeed)
    		{
    			speed = topSpeed;
    			state++;
    		}
    		break;
    	case 1://backward
			Robot.ss_Test.driveTrainStatus = "verify going Backward";
    		Robot.ss_DriveTrain.arcadeRobotDrive(speed,0);
    		speed-=delta;
    		if (speed < -topSpeed)
    		{
    			speed = -topSpeed;
    			state++;
    			endTime = Timer.getFPGATimestamp()+delay;
    		}
    		break;
    	case 2://0
    		Robot.ss_DriveTrain.arcadeRobotDrive(speed, 0);
    		speed+=delta;
    		if (speed >= 0 && Timer.getFPGATimestamp() >= endTime)
    		{
    			speed = 0;
    			Robot.ss_Test.driveTrainStatus = "verifyStopped";
    			if (Timer.getFPGATimestamp() >= endTime)
    			{
    				state++;
    			}
    		}
    		break;
    	case 3://leftTurn
    		Robot.ss_Test.driveTrainStatus = "verify turning Left";
    		Robot.ss_DriveTrain.arcadeRobotDrive(0,speed);
    		speed+=delta;
    		if (speed < topSpeed)
    		{
    			speed = topSpeed;
    			state++;
    		}
    		break;
    	case 4://rightTurn
    		Robot.ss_Test.driveTrainStatus = "verify turning Right";
    		Robot.ss_DriveTrain.arcadeRobotDrive(0,speed);
    		speed-=delta;
    		if (speed < -topSpeed)
    		{
    			speed = -topSpeed;
    			state++;
    			endTime = Timer.getFPGATimestamp()+delay;
    		}
    		break;
    	case 5://0
    		Robot.ss_DriveTrain.arcadeRobotDrive(0,speed);
    		speed+=delta;
    		if (speed >= 0)
    		{
    			speed = 0;
    			Robot.ss_Test.driveTrainStatus = "verify stopped";
    			if (Timer.getFPGATimestamp() >= endTime)
    			{
    				state++;
    			}
    		}
    	}
    	Robot.gui.sendString("Test/testState","driveTrain "+state);
    	Robot.ss_Test.update();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return state >= 6;//variable that defines 6
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.ss_DriveTrain.arcadeRobotDrive(0, 0);
    	
    	Robot.gui.sendString("Test/testState","driveTrain done");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
