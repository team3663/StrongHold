package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_HoldSpeedToFlag extends Command {

	private int speed;
	
    public C_HoldSpeedToFlag(int pSpeed) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.ss_Shooter);
        speed = pSpeed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.ss_Shooter.resetHoldSpeedVars();
    	Robot.ss_Shooter.fireAnyways = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.ss_Shooter.holdSpeed(speed);
    	Robot.gui.sendBoolean("shooter/holdSpeedCommand",true);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.ss_Shooter.autoTimeUp();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.gui.sendBoolean("shooter/holdSpeedCommand", false);
    	Robot.ss_Shooter.fireAnyways = true;//be careful, if not reset to false & run again that it will blow through everything
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
