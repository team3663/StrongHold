package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.Robot;
import org.usfirst.frc.team3663.robot.commands.C_DartMove;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;




public class SS_Dart extends Subsystem {
	
	//these are maxes based on the shooter of the bot
	private int minPotentiometer = 410;//600
	private int maxPotentiometer = minPotentiometer + 1790;//2160;
	//soft is for where the bot fires the arm if in this zone
	private int soft1 = 660;
	private int soft2 = 1860;
	//hard is where the dart will stop and wait for the arm
	private int hard1 = 760;
	private int hard2 = 1740;
	//touch is where the two can connect but the dart needs to go a certain way
	private int touch1 = 1210;
	private int touch2 = 1510;
	//this is what determins what area of acceptance for the dart
	private int bufferZone = 10;
	
	//Motor
	private CANTalon dartMotor = new CANTalon(Robot.robotMap.dartMotor);
	
	//Sensors
	private AnalogInput dartPotentiometer = new AnalogInput(Robot.robotMap.dartPotentiometer);
	
	//Past Values
	private boolean movePickup = false;
	
	private int countDown = 3;
	private int lastRunPotentiometer = -10000;
	
    public void initDefaultCommand() {
    	dartMotor.enableBrakeMode(true);
    	setDefaultCommand(new C_DartMove());
    }
    
    public int minDistance(){
    	return minPotentiometer;
    }
    
    public int maxDistance(){
    	return maxPotentiometer;
    }
    
    public int ConvertInchesToTicks(int pInches){
    	return (int)((0.0253*pInches*pInches) - (8.5606*pInches) + (2399.1));
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
    
    public void NOTSAFEMoveDart(double pSpeed){
    	dartMotor.set(pSpeed);
    }
    
    public boolean inSoftZone(){
    	int distValue = dartPotentiometer.getAverageValue();
    	return distValue < soft2 && distValue > soft1;
    }
    
    public boolean inHardZone(){
    	int distValue = dartPotentiometer.getAverageValue();
    	return distValue < hard2 && distValue > hard1;
    }
    
    public boolean touchingButSafeToMove(double pSpeed){
    	int distValue = dartPotentiometer.getAverageValue();
    	return ((distValue < touch2 && pSpeed < 0) || (distValue > touch1 && pSpeed > 0));
    }
    
    public void setMovingArm(boolean pValue){
    	movePickup = pValue;
    	SmartDashboard.putBoolean("asd jfkla", pValue);
    }
    
    public boolean getMoveArm(){
    	return movePickup;
    }
    
    public double convertSpeed(double pSpeed){					//makes sure that the joystick is passing it in useful values
    	pSpeed = (int)(pSpeed*10);
    	pSpeed = pSpeed/10;
    	if(pSpeed - .1  == 0 || pSpeed + .1 == 0){
    		pSpeed = 0;
    	}
    	return pSpeed;
    }
    
    public void moveDart(double pSpeed, boolean pArm){			//set the speed of the dart motor 
    	int distValue = dartPotentiometer.getAverageValue();
    	pSpeed = convertSpeed(pSpeed);
    	SmartDashboard.putNumber("dart speed ", pSpeed);
    	if((distValue < maxPotentiometer && pSpeed < 0)||(distValue > minPotentiometer && pSpeed > 0)){
    		/*if(!pArm){
    			if((pSpeed < 0 && distValue > touch2) || (pSpeed < 0 && distValue < soft1)||
    					(pSpeed > 0 && distValue < touch1) || (pSpeed > 0 && distValue > soft2)){
    				dartMotor.set(pSpeed);
    			}
    			else if(distValue < hard2 && distValue > hard1){
    				SmartDashboard.putString("ERROR : ", "number 2");
    				setMovingArm(true);
    				dartMotor.set(0);
    			}
    			else if(distValue > soft1 && distValue < soft2){
    				SmartDashboard.putString("ERROR : ", "number 1");
    				dartMotor.set(pSpeed/4);
    				setMovingArm(true);
    			}
    			else if(distValue < touch2 && distValue > touch1){
    				SmartDashboard.putString("ERROR : ", "number 3");
    				setMovingArm(true);
    				dartMotor.set(0);
    			}
    			else{
    				SmartDashboard.putString("ERROR : ", "an error has occured in the dart subsystem" + pSpeed);
    			}
    		}
    		else{
				SmartDashboard.putString("ERROR : ", "out");
    			setMovingArm(false);
    			dartMotor.set(pSpeed);
    		}*/
    		dartMotor.set(pSpeed);
    	}
    	else{
    		dartMotor.set(0);
    	}
    }
    
/*    public boolean atLocation(int pLocation)
    {
    	int currValue = dartPotentiometer.getAverageValue();
    	return (currValue < pLocation+4 && currValue > pLocation-4);
    }*///may not need
    
    public boolean hitLocation(double pSpeed, int pFinalLocation){				//checks to see if the bot has hit the target location for the dart
    	int distValue = dartPotentiometer.getAverageValue();
    	if(pSpeed < 0 && distValue < pFinalLocation+bufferZone){
    		 return true;
    	}
    	if(pSpeed > 0 && distValue > pFinalLocation-bufferZone){
    		return true;
    	}
    	return false;
    }
    
    public boolean outOfSafeZone(){					//checks to see if the dart is in a safe location
    	int distValue = dartPotentiometer.getAverageValue();
    	return !(distValue < maxPotentiometer && distValue > minPotentiometer);
    }
    
    public void STOP(){								//stops the dart from moving
    	dartMotor.set(0);
    }
    
    public void updateDashboard(){					//updates to the dash board
    	SmartDashboard.putNumber("Dart Potentiometer : ", dartPotentiometer.getAverageValue());
    	SmartDashboard.putBoolean("SafeToRaisePickup : ", getMoveArm());

    	Robot.gui.sendNumber("dart/Dart Potentiometer", dartPotentiometer.getAverageValue());
    	Robot.gui.sendBoolean("dart/SafeToRaisePickup", getMoveArm());

    	Robot.visionTable.putBoolean("dartBelowThreshold: ", (dartPotentiometer.getAverageValue() < 480));
    }
}

