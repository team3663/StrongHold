package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TC_TurnByGyro extends Command {

    public TC_TurnByGyro() {
        requires(Robot.ss_DriveTrain);
    }

    protected void initialize() {
    	Robot.ss_DriveTrain.resetGyro();
    }

    protected void execute() {
    }
    
    protected boolean isFinished() {
        return Robot.ss_DriveTrain.spinByGyro(90);
    }

    protected void end() {
    }
    
    protected void interrupted() {
    	end();
    }
}
