package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TestC_TestDart extends Command {

	int state;
	double speed;
	int initPotValue;
	
	double endTime;
	double delay = 0.5;
	
    public TestC_TestDart() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.ss_Dart);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 0;
    	speed = 0.6;//for now
    	Robot.gui.sendString("Test/testState", "dart starting");
    	Robot.ss_Test.dartStatus = Robot.ss_Test.untested;
    	Robot.ss_Test.update();

    	initPotValue = Robot.ss_Dart.getPotentiometerValue();
    	if (initPotValue >= ((double)Robot.ss_Dart.minDistance()+(double)Robot.ss_Dart.maxDistance())/2)
    	{
    		speed = -speed;
    	}
    	endTime = Timer.getFPGATimestamp()+delay;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//move slightly to check potentiometer working, go to max, go to min, 
    	switch(state){
    	case 0:
    		Robot.ss_Test.dartStatus = Robot.ss_Test.testing;
    		int currPotValue = Robot.ss_Dart.getPotentiometerValue();
    		Robot.ss_Dart.moveDart(speed,Robot.ss_PickupArm.isDown());
    		if ((currPotValue > initPotValue+5 && speed > 0) || (currPotValue < initPotValue-5 && speed < 0))
    		{
    			state++;
    		}
    		else if (Timer.getFPGATimestamp() >= endTime)
    		{
    			Robot.ss_Test.dartStatus = "Failed - potentiometer not following";
    			state = 100;
    		}
    		break;
    	case 1:
    		Robot.ss_Dart.moveDart(1.0, Robot.ss_PickupArm.isDown());
    		if (Robot.ss_Dart.hitLocation(1.0, Robot.ss_Dart.maxDistance()))
			{
    			Robot.ss_Dart.STOP();
    			state++;
			}
    		break;
    	case 2:
    		Robot.ss_Dart.moveDart(-1.0, Robot.ss_PickupArm.isDown());
    		if (Robot.ss_Dart.hitLocation(-1.0, Robot.ss_Dart.minDistance()))
    		{
    			Robot.ss_Dart.STOP();
    			state++;
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (state >= 3);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.ss_Dart.STOP();
    	Robot.gui.sendString("Test/testState", "dart done");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
