package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.Robot;
import org.usfirst.frc.team3663.robot.commands.C_WheelyBarMove;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class SS_WheelyBar extends Subsystem {
    
	private CANTalon wheelyBarMotor = new CANTalon(Robot.robotMap.wheelyBarMotor);
	private Encoder wheelyBarEncoder = new Encoder(Robot.robotMap.wheelyBarEncoder[0], Robot.robotMap.wheelyBarEncoder[1]);
	private DigitalInput wheelyBarLimit = new DigitalInput(Robot.robotMap.wheelyBarLimitSwitch);
	
	private int maxEncoderTicks = 1000;
	private int accptance = 10;
	private boolean setToZero = false;
	
    public void initDefaultCommand() {
    	setDefaultCommand(new C_WheelyBarMove());
    }
    
    public int grabEncoder(){										//gets the value of the encoder
    	return wheelyBarEncoder.getRaw();
    }
    
    public int maxDistance(){										//returns the max value
    	return maxEncoderTicks;
    }
    
    public void resetEncoder(){										//resets the encoder
		setToZero = true;
    	wheelyBarEncoder.reset();
    }
    
    public boolean moveWheelyBarAuto(int pTarget, double pSpeed){	//moves to a set distance on the encoder
    	int distValue = grabEncoder();
    	int direction = 0;
    	if((distValue > pTarget - accptance && distValue < pTarget + accptance) || 
    			!(distValue < maxEncoderTicks && pSpeed > 0) || 
    			!(distValue > 0 && pSpeed < 0) ||
    			pSpeed < 0 || setToZero){
    		return true;
    	}
    	if(pTarget > distValue){
    		direction = 1;
    	}
    	else if(pTarget < distValue){
    		direction = -1;
    	}
    	else{
    		direction = 0;
    	}
    	wheelyBarMotor.set(pSpeed * direction);
    	return false;
    }
    
    public boolean moveToReset(){									//moves the bar down until the limit switch is hit
    	wheelyBarMotor.set(-.2);
    	if(wheelyBarLimit.get()){
    		STOP();
    		resetEncoder();
    		return true;
    	}
    	return false;
    }
    
    public void moveWheelyBar(double pSpeed){						//moves the motor based on speed
    	int distValue = grabEncoder();
    	if(wheelyBarLimit.get()){
    		resetEncoder();
    	}
    	if(((distValue < maxEncoderTicks && pSpeed > 0) || (distValue > 0 && pSpeed < 0)) && setToZero){
    		wheelyBarMotor.set(pSpeed);
    	}
    	else{
    		STOP();
    	}
    }
    
    public void STOP(){												//stops the motor
    	wheelyBarMotor.set(0);
    }
}

