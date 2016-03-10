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
    	if(Robot.ss_Test.currentlyTesting()){
    		Robot.ss_Test.enterTestMode();
    	}else{
    		Robot.ss_Test.exitTestMode();
    	}
    	Robot.ss_Test.update();
    	
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
