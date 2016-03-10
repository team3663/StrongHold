package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.Robot;
import org.usfirst.frc.team3663.robot.commands.C_HookMove;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SS_Hook extends Subsystem {

	
	private CANTalon hookMotor = new CANTalon(Robot.robotMap.hookMotor);
	private DoubleSolenoid hookPiston = new DoubleSolenoid(Robot.robotMap.hookSolenoid[0], Robot.robotMap.hookSolenoid[1]);
    public void initDefaultCommand() {
    	setDefaultCommand(new C_HookMove());
    }
    
    public void fireHookPiston(boolean pValue){
    	if(pValue){
    		hookPiston.set(DoubleSolenoid.Value.kForward);
    	}
    	else{
    		hookPiston.set(DoubleSolenoid.Value.kReverse);
    	}
    }
    
    public void moveHook(double pSpeed){
    	if(pSpeed > .2 || pSpeed < -.2){
        	hookMotor.set(pSpeed);    		
    	}
    	else{
    		hookMotor.set(0);
    	}
    }
    
    public boolean pastLocUp(int pValue){
    	if(pValue > hookMotor.getEncPosition()){
    		hookMotor.set(0);
    		return true;
    	}
    	else{
    		hookMotor.set(.5);
    		return false;
    	}
    }

    public boolean pastLocDonw(int pValue){
    	if(pValue < hookMotor.getEncPosition()){
    		hookMotor.set(0);
    		return true;
    	}
    	else{
    		hookMotor.set(-.5);
    		return false;
    	}
    }    
    
    public void updateDashboard(){
    }
}

