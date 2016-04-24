
package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_HookMove extends Command {

    public C_HookMove() {
    	requires(Robot.ss_Hook);
    }

    protected void initialize() {
    }

    protected void execute() {
    	double speed = Robot.oi.buttonJoystick.getRawAxis(Robot.robotMap.hookAxis);
    	Robot.ss_Hook.moveHook(speed, true);
    	if(speed > .1 || speed < -.1){
    		Robot.ss_DriveTrain.setBrakeMode(true);
    	}
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
