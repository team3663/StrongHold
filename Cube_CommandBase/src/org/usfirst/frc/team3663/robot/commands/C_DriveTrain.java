package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_DriveTrain extends Command {

    public C_DriveTrain() {
        requires(Robot.ss_DriveTrain);
    }
    
    protected void initialize() {
    	
    }
    
    protected void execute() {
    	Robot.ss_DriveTrain.arcadeRobotDrive(-Robot.oi.driveJoystick.getRawAxis(Robot.robotMap.driveAxisTurn), -Robot.oi.driveJoystick.getRawAxis(1)
    		/*(Robot.oi.driveJoystick.getRawAxis(Robot.robotMap.driveAxisReverse) - Robot.oi.driveJoystick.getRawAxis(Robot.robotMap.driveAxisForward))*/);
    }
    
    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.ss_DriveTrain.STOP();
    }
    
    protected void interrupted() {
    	end();
    }
}
   