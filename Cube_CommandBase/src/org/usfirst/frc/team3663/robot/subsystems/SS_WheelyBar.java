package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.Robot;
import org.usfirst.frc.team3663.robot.commands.C_WheelyBarMove;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Setting the speed in a positive direction (+) moves the
 * wheely bar upwards, towards the wheels and center of the robot
 */
public class SS_WheelyBar extends Subsystem {
    
	private CANTalon wheelyBarMotor = new CANTalon(Robot.robotMap.wheelyBarMotor);
	private int accptance = 10;
	//test
	private boolean setToZero = false;
	private int speedDir = Robot.robotMap.wbSpeedDir;
	
    public void initDefaultCommand() {
    	setDefaultCommand(new C_WheelyBarMove());
    	wheelyBarMotor.enableBrakeMode(true);
    }
    
    public int grabEncoder(){										//gets the value of the encoder
    	return Robot.robotMap.wheelyBarEncDir*wheelyBarMotor.getEncPosition();
    }
    
    public int maxDistance(){										//returns the max value
    	return Robot.robotMap.wbMaxEnc;
    }
    
    public void resetEncoderTo(int pValue){										//resets the encoder
		wheelyBarMotor.setEncPosition(pValue);;
    }
    
    public int getVelocity(){
    	return Robot.robotMap.wheelyBarEncDir*wheelyBarMotor.getEncVelocity();
    }
    
    private int wheelyBarDelay = 0;
    public void setEndTime(int delay){
    	wheelyBarDelay = (int) (delay+Timer.getFPGATimestamp());
    }
    
    public boolean moveWheelyBarAuto(int pTarget, double pSpeed){	//moves to a set distance on the encoder
    	int distValue = grabEncoder();
    	if(wheelyBarDelay<Timer.getFPGATimestamp()){
    		return true;
    	}
    	if((distValue < pTarget && pSpeed > 0) || (distValue > pTarget && pSpeed < 0)){
        	moveWheelyBar(pSpeed/2);
    		return false;
    	}
    	STOP();
    	return true;
    }
    public double getSpeed(int pTarget)
    {
    	if (grabEncoder() > pTarget)
    	{
    		return -0.7;
    	}
    	return 0.7;
    }
    public void moveWheelyBarSafe(double pSpeed){
    	int distValue = grabEncoder();
    	if((pSpeed > 0 && distValue < Robot.robotMap.wbMaxEnc) || (pSpeed < 0 && distValue > Robot.robotMap.wbMinEnc)&&(pSpeed>.4||pSpeed<-.4)){
    		moveWheelyBar(pSpeed/1.5);
    	}
    	else{
    		STOP();
    	}
    }
    //TODO: standardize speed division
    public void moveWheelyBar(double pSpeed){						//moves the motor based on speed
    	wheelyBarMotor.set(pSpeed/1.5);
    }
    
    public void STOP(){												//stops the motor
    	moveWheelyBar(0);
    }
    
    public void updateDashboard(){
    	Robot.gui.sendNumber("wheelyBar/Wheely Encoder",wheelyBarMotor.getEncPosition());
    }
}

