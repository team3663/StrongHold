package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_WinchMoveNoSafty extends Command {

    public C_WinchMoveNoSafty() {
        requires(Robot.ss_Winch);
    }

    protected void initialize() {
    }
    
    protected void execute() {
    	Robot.ss_Winch.setWinchSpeedNOTSAFE(Robot.oi.buttonJoystick.getRawAxis(Robot.robotMap.winchAxis)/2);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.ss_Winch.STOP();
    }

    protected void interrupted() {
    	end();
    }
}
