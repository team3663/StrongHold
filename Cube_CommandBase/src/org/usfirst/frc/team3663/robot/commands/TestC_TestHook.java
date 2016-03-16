package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TestC_TestHook extends Command {

	double endTime;
	double speed = 0.6;
	int state;
	double delay = 0.3;
	
    public TestC_TestHook() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.ss_Hook);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 0;
    	Robot.gui.sendString("Test/testState","hook starting");
    	Robot.ss_Test.driveTrainStatus = Robot.ss_Test.untested;
    	Robot.ss_Test.update();
    	endTime = Timer.getFPGATimestamp()+delay;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch (state)
    	{//piston up, piston down, drive upwards, drive downwards
    	case 0:
    		Robot.ss_Hook.fireHookPiston(true);
    		Robot.ss_Test.hookStatus = "verify went up";
    		if (Timer.getFPGATimestamp() > endTime)
    		{
    			state++;
    			endTime = Timer.getFPGATimestamp()+delay;
    		}
    		break;
    	case 1:
    		Robot.ss_Hook.fireHookPiston(false);
    		Robot.ss_Test.hookStatus = "verify went down";
    		delay = 0.5;
    		if (Timer.getFPGATimestamp() > endTime)
    		{
    			state++;
    			endTime = Timer.getFPGATimestamp()+delay;
    		}
    		break;
    	case 2:
    		Robot.ss_Hook.moveHook(speed,true);
    		Robot.ss_Test.hookStatus = "verify extending";
    		if (Timer.getFPGATimestamp() > endTime)
    		{
    			state++;
    			endTime = Timer.getFPGATimestamp()+delay;
    		}
    		break;
    	case 3:
    		Robot.ss_Hook.moveHook(-speed,true);
    		Robot.ss_Test.hookStatus = "verify contracting";
    		if (Timer.getFPGATimestamp() > endTime)
    		{
    			state++;
    		}
    		break;
    	}
    	Robot.gui.sendString("Test/testState","hook " + state);
    	Robot.ss_Test.update();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return state > 3;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.ss_Hook.moveHook(0,true);
    	Robot.gui.sendString("Test/testState", "hook done");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
