package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TC_TurnByGyro extends Command {

	double degrees;
    public TC_TurnByGyro(double angle) {
        requires(Robot.ss_DriveTrain);
        degrees = angle;//assuming positive is to the right
    }

    protected void initialize() {
    	Robot.ss_DriveTrain.resetGyro();
    }

    protected void execute() {
    }
    
    protected boolean isFinished() {
        return Robot.ss_DriveTrain.spinByGyro((int)degrees,1.0);
    }

    protected void end() {
    }
    
    protected void interrupted() {
    	end();
    }
}
