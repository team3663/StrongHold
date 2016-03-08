package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.Robot;
import org.usfirst.frc.team3663.robot.commands.C_HookMove;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SS_Hook extends Subsystem {

	
	private CANTalon hookMotor = new CANTalon(Robot.robotMap.hookMotor);
    public void initDefaultCommand() {
    	setDefaultCommand(new C_HookMove());
    }
    
    public void moveHook(double pSpeed){
    	if(pSpeed > .2 || pSpeed < -.2){
        	hookMotor.set(pSpeed);    		
    	}
    	else{
    		hookMotor.set(0);
    	}
    }
    
    public void updateDashboard(){
    }
}

