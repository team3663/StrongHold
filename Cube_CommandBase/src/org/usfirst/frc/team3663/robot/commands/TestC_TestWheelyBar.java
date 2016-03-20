package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TestC_TestWheelyBar extends Command {

	double endTime;
	int state = 0;
	double speed = 0;
	double delta = .1;
	double delay = .5;
	int encoderTolerance = 4;
	double topSpeed = .8;
	long startTime;
	
    public TestC_TestWheelyBar() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.ss_WheelyBar);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 0;
    	speed = 0.2;
    	startTime = System.currentTimeMillis();
    	Robot.gui.sendString("Test/testState","wheelyBar starting");
    	Robot.ss_Test.wheelyBarMotorStatus = Robot.ss_Test.untested;
    	Robot.ss_Test.update();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch (state){
    	case 0://Drive up to hitting the wheels
    		Robot.ss_Test.wheelyBarMotorStatus = Robot.ss_Test.testing;
    		Robot.ss_WheelyBar.moveWheelyBar(1.0);
//    		speed += delta;
//    		if(speed > topSpeed){
//    			speed = topSpeed;
//    		}
    		if(System.currentTimeMillis() > 1000 + startTime){
    			state++;
    		}
    		Robot.ss_WheelyBar.resetEncoder();
    		startTime = System.currentTimeMillis();
    		break;
    	case 1://Drive down
    		Robot.ss_Test.wheelyBarMotorStatus = Robot.ss_Test.testing;
    		Robot.ss_WheelyBar.moveWheelyBar(-1.0);
//    		speed -= delta;
//    		if (speed < -topSpeed)
//    		{
//    			speed = -topSpeed;
//    		}
    		if(System.currentTimeMillis() > 1000 + startTime){
    			state++;
    		}
    		break;
    	case 2://Drive up again
//    		Robot.ss_Test.wheelyBarMotorStatus = Robot.ss_Test.testing;
//    		Robot.ss_WheelyBar.moveWheelyBar(speed);
//    		speed += delta;
//    		if (speed > topSpeed)
//    		{
//    			speed = topSpeed;
//    		}
//    		if(System.currentTimeMillis() > 2000 + startTime){
    			state++;
//    		}
    		break;
    	case 3://Stop
    		Robot.ss_Test.wheelyBarMotorStatus = Robot.ss_Test.testing;
    		Robot.ss_WheelyBar.STOP();
			state++;
			Robot.ss_WheelyBar.moveWheelyBar(speed);
			if (Robot.ss_Test.wheelyBarMotorStatus.equals(Robot.ss_Test.testing)){
				Robot.ss_Test.wheelyBarMotorStatus = Robot.ss_Test.passed;
    		}
    		break;
    	}

    	Robot.gui.sendString("Test/testState","wheelyBar "+state);
		Robot.ss_Test.update();    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return state >= 3;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.ss_WheelyBar.STOP();
    	Robot.gui.sendString("Test/testState","wheelyBar done");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
