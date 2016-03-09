package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TestC_ToggleTestMode extends Command {

    private TestCG_TestRequiresAll takeAll;
    public TestC_ToggleTestMode() {
    	takeAll = new TestCG_TestRequiresAll();
    }
    protected void initialize() {
    	if(Robot.test.currentTestMode()){
    		Robot.test.enterTestMode();
    	}else{
    		Robot.test.exitTestMode();
    	}
    	Robot.test.update();
    	
    }

    protected void execute() {
    	
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
