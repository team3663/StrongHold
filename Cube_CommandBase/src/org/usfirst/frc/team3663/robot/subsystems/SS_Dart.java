package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.Robot;
import org.usfirst.frc.team3663.robot.commands.C_DartMove;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;




public class SS_Dart extends Subsystem {
	
	private int maxPotentiometer = 2120;
	private int minPotentiometer = 660;
	private int minPickup = 761;
	private int maxPickup = 1789;
	
	//Motor
	private CANTalon dartMotor = new CANTalon(Robot.robotMap.dartMotor);
	
	//Sensors
	private AnalogInput dartPotentiometer = new AnalogInput(Robot.robotMap.dartPotentiometer);
	
	//Past Values
	private boolean safe = true;
	private int countDown = 3;
	private int lastRunPotentiometer = -10000;
	
    public void initDefaultCommand() {
    	setDefaultCommand(new C_DartMove());
    }
    
    public int minDistance(){
    	return minPotentiometer;
    }
    
    public double findSpeed(int pFinalDist){ 		//finds the speed needed to hit the target
    	int distValue = dartPotentiometer.getAverageValue();
    	if(distValue < pFinalDist){
    		return 1;
    	}
    	else{
    		return -1;
    	}
    }
    
    public void moveDart(double pSpeed){			//set the speed of the dart motor 
    	int distValue = dartPotentiometer.getAverageValue();
    	if((distValue < maxPotentiometer && pSpeed < 0)||(distValue > minPotentiometer && pSpeed > 0)){
        	dartMotor.set(pSpeed);    		
    	}
    	else{
    		dartMotor.set(0);
    	}
    }
    
    public boolean hitLocation(double pSpeed, int pFinalLocation){
    	int distValue = dartPotentiometer.getAverageValue();
    	if(pSpeed < 0 && distValue < pFinalLocation){
    		 return true;
    	}
    	if(pSpeed > 0 && distValue > pFinalLocation){
    		return true;
    	}
    	return false;
    }
    
    public boolean inPickupZone(){					//checks to see if the pickup arm is in the way
    	int distValue = dartPotentiometer.getAverageValue();
    	return (distValue < maxPickup + 50 && distValue > minPickup - 50); 	
    }
    
    public boolean outOfSafeZone(){					//checks to see if the dart is in a safe location
    	int distValue = dartPotentiometer.getAverageValue();
    	return !(distValue < maxPotentiometer && distValue > minPotentiometer);
    }
    
    public void SaftyCheck(){						//checks to insure that there is no problem with the dart
    	if(countDown < 0){
    		int currentPotentiometer = dartPotentiometer.getAverageValue();
    		int diff = currentPotentiometer - lastRunPotentiometer;
    		if(lastRunPotentiometer != -10000 && Math.abs(diff) > 3 && Math.abs(dartMotor.getSpeed()) > .05 && diff * dartMotor.getSpeed() < 0){
    			safe = false;
    		}
    	}
    	countDown --;
    }
    
    public void STOP(){
    	dartMotor.set(0);
    }
    
    public void updateDashboard(){					//updates to the dash board
    	SmartDashboard.putNumber("Dart Potentiometer : ", dartPotentiometer.getAverageValue());
    	SmartDashboard.putNumber("Dart Motor : ", dartMotor.get());
    }
}

