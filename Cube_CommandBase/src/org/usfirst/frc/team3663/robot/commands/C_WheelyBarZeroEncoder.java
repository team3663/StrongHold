package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_WheelyBarZeroEncoder extends Command {

    public C_WheelyBarZeroEncoder() {
    	requires(Robot.ss_WheelyBar);
    }

    protected void initialize() {
    }

    protected void execute() {
    	
    }

    protected boolean isFinished() {
        return Robot.ss_WheelyBar.moveToReset();
    }
    
    protected void end() {
    }
    
    protected void interrupted() {
    }
}
