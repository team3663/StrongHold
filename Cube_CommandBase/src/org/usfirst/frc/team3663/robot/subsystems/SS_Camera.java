package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SS_Camera extends Subsystem {
    
	private Relay light = new Relay(Robot.robotMap.cameraRelay);
	
	public void initDefaultCommand() {
    	
    }
	
	public void setLight(boolean pValue){
		if(pValue){
			light.set(Relay.Value.kOn);
		}			
		else{
			light.set(Relay.Value.kOff);
		}
	}
	
	public void updateDashboard(){
		SmartDashboard.putBoolean("Camera Location : ", light.get() == Relay.Value.kOn);
	}
}

