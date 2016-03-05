package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_WheelyBarMove extends Command {

    public C_WheelyBarMove() {
        requires(Robot.ss_WheelyBar);
    }

    protected void initialize() {
    }

    protected void execute() {

    	if(Robot.oi.buttonJoystick.getRawButton(3)){
        	if(Robot.oi.buttonJoystick.getPOV() == 0){
            	Robot.ss_WheelyBar.moveWheelyBar(-1);
        	}
        	else if(Robot.oi.buttonJoystick.getPOV() == 180){
        		Robot.ss_WheelyBar.moveWheelyBar(1);
        	}
        	else{
        		Robot.ss_WheelyBar.STOP();
        	}
    	}
    	else if(Robot.oi.buttonJoystick.getPOV() == 0){
        	Robot.ss_WheelyBar.moveWheelyBarSafe(-1);
    	}
    	else if(Robot.oi.buttonJoystick.getPOV() == 180){
    		Robot.ss_WheelyBar.moveWheelyBarSafe(1);
    	}
    	else{
    		Robot.ss_WheelyBar.STOP();
    	}
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.ss_WheelyBar.STOP();
    }

    protected void interrupted() {
    	end();
    }
}
