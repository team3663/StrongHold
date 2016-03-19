package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_DriveControllerDPad extends Command {

	CG_ConfigGoOverDefenses cg_OverD = new CG_ConfigGoOverDefenses();
	CG_ConfigLowBar cg_LowBar = new CG_ConfigLowBar();
	CG_ConfigNormalShot cg_Normal = new CG_ConfigNormalShot();
	CG_ConfigLongShot cg_Long = new CG_ConfigLongShot();
	CG_ConfigDefencePos4 cg_Pos4 = new CG_ConfigDefencePos4();
	
    public C_DriveControllerDPad() {
    }

    protected void initialize() {
    }

    protected void execute() {
    	if(Robot.oi.driveJoystick.getPOV() == 0 && !cg_Normal.isRunning()){//up
    		cg_Normal.start();
    	}
    	else if(Robot.oi.driveJoystick.getPOV() == 90 && !cg_OverD.isRunning()){//right 
    		cg_OverD.start();
    	}
    	else if(Robot.oi.driveJoystick.getPOV() == 180 && !cg_LowBar.isRunning()){//down
    		cg_LowBar.start();
    	}
    	else if(Robot.oi.driveJoystick.getPOV() == 270 && !cg_Long.isRunning()){//left
    		cg_Long.start();
    	}
    	else if(Robot.oi.driveJoystick.getRawButton(8) && !cg_Pos4.isRunning()){
    		cg_Pos4.start();
    	}
    	else if(Robot.oi.driveJoystick.getPOV() == -1){
    		//cg_Normal.cancel();
    		//cg_LowBar.cancel();
    		//cg_OverD.cancel();
    		
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
