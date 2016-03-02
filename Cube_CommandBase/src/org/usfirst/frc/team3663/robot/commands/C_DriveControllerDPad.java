package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class C_DriveControllerDPad extends Command {

	CG_ConfigGoOverDefenses cg_OverD = new CG_ConfigGoOverDefenses();
	CG_ConfigLowBar cg_LowBar = new CG_ConfigLowBar();
	CG_ConfigNormalShot cg_Normal = new CG_ConfigNormalShot();
	
    public C_DriveControllerDPad() {
    }

    protected void initialize() {
    }

    protected void execute() {
    	if(Robot.oi.driveJoystick.getPOV() == 0 && !cg_Normal.isRunning()){
    		cg_Normal.start();
    	}
    	else if(Robot.oi.driveJoystick.getPOV() == 90 && !cg_OverD.isRunning()){
    		cg_OverD.start();
    	}
    	else if(Robot.oi.driveJoystick.getPOV() == 180 && !cg_LowBar.isRunning()){
    		cg_LowBar.start();
    	}
    	else if(Robot.oi.driveJoystick.getPOV() == 270){
    		
    	}
    	else if(Robot.oi.driveJoystick.getPOV() == -1){
    		cg_Normal.cancel();
    		cg_LowBar.cancel();
    		cg_OverD.cancel();
    		
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
