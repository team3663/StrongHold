 package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_WinchMove extends Command {

    public C_WinchMove() {
        requires(Robot.ss_Winch);
    }

    protected void initialize() {
    }

    protected void execute() {
    	//if go backwards, flip (+) sign
//    	Robot.ss_Winch.runMotorTeleop(Robot.oi.winchJoystick.getRawAxis(Robot.robotMap.winchAxis)+ Robot.oi.buttonJoystick.getRawAxis(2));
    	Robot.ss_Winch.runMotorTeleop(Robot.oi.winchJoystick.getRawAxis(Robot.robotMap.winchAxis)+ Robot.oi.getOneDirectionButtonJoyWinchAxis());
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
