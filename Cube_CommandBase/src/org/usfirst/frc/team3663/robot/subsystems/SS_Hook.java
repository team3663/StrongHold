package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.Robot;
import org.usfirst.frc.team3663.robot.commands.C_HookMove;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class SS_Hook extends Subsystem {

	
	private CANTalon hookMotor = new CANTalon(Robot.robotMap.hookMotor);
	private DoubleSolenoid hookPiston = new DoubleSolenoid(Robot.robotMap.hookSolenoid[0], Robot.robotMap.hookSolenoid[1]);
    public void initDefaultCommand() {
    	setDefaultCommand(new C_HookMove());
    	hookMotor.enableBrakeMode(true);
    }
    
    public void fireHookPiston(boolean pValue){
    	if(pValue){
    		hookPiston.set(DoubleSolenoid.Value.kForward);
    	}
    	else{
    		hookPiston.set(DoubleSolenoid.Value.kReverse);
    	}
    }
    
    public boolean hookMoveTo(int pTarget){
    	int distValue = hookMotor.getEncPosition();
    	if(distValue < pTarget-50 && distValue > pTarget+50){
        	if(distValue > pTarget){
        		hookMotor.set(.1);
        	}
        	if(distValue < pTarget){
        		hookMotor.set(-.1);
        	}
        	return false;
    	}
    	else{
    		hookMotor.set(0);
    		return true;
    	}
    }
    
    // todo make speed and encoder positive when extending hook
    // todo make sure hook can always be moved in a safe direction
    public void moveHook(double pSpeed, boolean safety){//-200000
    	if (pSpeed < .2 && pSpeed > -.2){
    		hookMotor.set(0);
    	}
    	else
    	{
    		int distValue = hookMotor.getEncPosition();
    			hookMotor.set(pSpeed);    		
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
    
    public void resetEnc(){
    }
    
    public void updateDashboard(){
    	Robot.gui.sendNumber("hook/Hook Enc", hookMotor.getEncPosition());
    	Robot.gui.sendNumber("hook/Hook Motor", hookMotor.get());
    }
}

