
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
    	Robot.ss_Hook.moveHook(Robot.oi.buttonJoystick.getRawAxis(Robot.robotMap.hookAxis), true);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
