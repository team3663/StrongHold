package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_CameraLightSet extends Command {

	private boolean state;
    public C_CameraLightSet(boolean pState) {
    	state = pState;
    	requires(Robot.ss_Camera);
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.ss_Camera.setLight(state);
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
