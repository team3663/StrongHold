package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_DriveTrainArcade extends Command {

    public C_DriveTrainArcade() {
        requires(Robot.ss_DriveTrain);
    }
    
    protected void initialize() {
    	
    }
    int ctr=0;
    protected void execute() {
    	//Robot.gui.sendNumber("general/drivearcade",ctr++);

    		//this section is Angelique's change. this is correct! Check with Trent
			Robot.ss_DriveTrain.arcadeRobotDrive((Robot.oi.driveJoystick.getRawAxis(Robot.robotMap.driveAxisForward) - Robot.oi.driveJoystick.getRawAxis(Robot.robotMap.driveAxisReverse)),
					-Robot.oi.driveJoystick.getRawAxis(Robot.robotMap.driveAxisTurn));

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
   