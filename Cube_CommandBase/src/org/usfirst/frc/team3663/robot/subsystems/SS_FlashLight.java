package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class SS_FlashLight extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private Relay spike = new Relay(Robot.robotMap.flashLightRelay);
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void setLight(boolean pValue){			//True is on
    	if(pValue){
    		spike.set(Relay.Value.kReverse);
    	}
    	else{
    		spike.set(Relay.Value.kForward);
    	}
    }
    
    public void toggleLight(){
    	if(spike.get() == Relay.Value.kForward){
    		spike.set(Relay.Value.kReverse);
    	}
    	else{
    		spike.set(Relay.Value.kForward);
    	}
    }   
    
}

