package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_PickupToggle extends Command {

    public C_PickupToggle() {
    	
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.ss_PickupArm.togglePickupSolenoid();
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
