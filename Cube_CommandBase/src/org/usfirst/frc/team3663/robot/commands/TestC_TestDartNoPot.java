package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TestC_TestDartNoPot extends Command {
	int state;
	double speed = 0;	
	double deltaSpeed = 0.01;
	double maxSpeed = 0.4;
	double startTime;
	double delay = 1.5;
	
    public TestC_TestDartNoPot() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.ss_Dart);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 0;
    	Robot.gui.sendString("Test/testState", "dart starting");
    	Robot.ss_Test.dartStatus = Robot.ss_Test.untested;
    	Robot.ss_Test.update();
    	startTime = Timer.getFPGATimestamp();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//move slightly to check potentiometer working, go to max, go to min, 
    	switch(state){
    	case 0:
    		if(startTime + delay > Timer.getFPGATimestamp() && speed < maxSpeed){
    			Robot.ss_Dart.NOTSAFEMoveDart(speed);
    			speed += deltaSpeed;
    		}else{
    			state++;
    			startTime = Timer.getFPGATimestamp();
    		}
			break;
    	case 1:
    		if(startTime + delay > Timer.getFPGATimestamp() && speed > -maxSpeed){
    			Robot.ss_Dart.NOTSAFEMoveDart(speed);
    			speed -= deltaSpeed;
    		}else{
    			state++;
    			startTime = Timer.getFPGATimestamp();
    		}
			break;
    	case 2:
    		if(startTime + delay > Timer.getFPGATimestamp() && speed < 0){
    			Robot.ss_Dart.NOTSAFEMoveDart(speed);
    			speed += deltaSpeed;
    		}else{
    			state++;
    		}
			break;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return state >= 3;
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
