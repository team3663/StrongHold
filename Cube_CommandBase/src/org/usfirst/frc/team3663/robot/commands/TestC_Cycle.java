package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TestC_Cycle extends Command {
	private boolean up;

    public TestC_Cycle(boolean up) {
    	this.up = up;
    }

    protected void initialize() {
    	
    }

    protected void execute() {
    	if(up){
    		Robot.test.incrementBy(1);
    	}else{
    		Robot.test.incrementBy(-1);
    	}
    	Robot.test.update();
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    	 
    }

    protected void interrupted() {
	   end();
    }
}
