package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.Robot;
import org.usfirst.frc.team3663.robot.commands.C_WheelyBarMove;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Setting the speed in a positive direction (+) moves the
 * wheely bar upwards, towards the wheels and center of the robot
 */
public class SS_WheelyBar extends Subsystem {
    
	private CANTalon wheelyBarMotor = new CANTalon(Robot.robotMap.wheelyBarMotor);
	
	private int maxEncTicks = 2964;
	private int minEncTicks = 1211;
	private int accptance = 10;
	private boolean setToZero = false;
	
    public void initDefaultCommand() {
    	setDefaultCommand(new C_WheelyBarMove());
    	wheelyBarMotor.enableBrakeMode(true);
    }
    
    public int grabEncoder(){										//gets the value of the encoder
    	return wheelyBarMotor.getEncPosition();
    }
    
    public int maxDistance(){										//returns the max value
    	return maxEncTicks;
    }
    
    public void resetEncoder(){										//resets the encoder
		wheelyBarMotor.reset();
    }
    
    public int getVelocity(){
    	return wheelyBarMotor.getEncVelocity();
    }
    
    public boolean moveWheelyBarAuto(int pTarget, double pSpeed){	//moves to a set distance on the encoder
    	int distValue = -grabEncoder();
    	if(distValue < pTarget){
        	moveWheelyBar(pSpeed);
    		return false;
    	}
    	STOP();
    	return true;
    }
    
    public void moveWheelyBarSafe(double pSpeed){
    	int distValue = -grabEncoder();
    	if((pSpeed > 0 && distValue < maxEncTicks) || (pSpeed < 0 && distValue > minEncTicks)&&(pSpeed>.4||pSpeed<-.4)){
    		moveWheelyBar(pSpeed/1.5);
    	}
    	else{
    		STOP();
    	}
    }
    
    public void moveWheelyBar(double pSpeed){						//moves the motor based on speed
    	wheelyBarMotor.set(pSpeed/1.5);
    }
    
    public void STOP(){												//stops the motor
    	wheelyBarMotor.set(0);
    }
    
    public void updateDashboard(){
    	SmartDashboard.putNumber("WheelyBar Encoder", wheelyBarMotor.getEncPosition());
    	Robot.gui.sendNumber("wheelyBar/Wheely Encoder",wheelyBarMotor.getEncPosition());
    }
}

