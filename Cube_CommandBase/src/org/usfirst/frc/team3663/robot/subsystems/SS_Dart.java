package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;




public class SS_Dart extends Subsystem {
	
	private int maxPotentiometer = 2000;
	private int minPotentiometer = 1000;
	
	//Motor
	private CANTalon dartMotor = new CANTalon(Robot.robotMap.dartMotor);
	
	//Sensors
	private AnalogInput dartPotentiometer = new AnalogInput(Robot.robotMap.dartPotentiometer);
	
    public void initDefaultCommand() {
    	
    }
    
    public void moveDart(double pSpeed){			//set the speed of the dart motor
    	int DistValue = dartPotentiometer.getAverageValue();
    	if((DistValue < 2000 && pSpeed < 0)||(DistValue > 1000 && pSpeed > 0)){
        	dartMotor.set(pSpeed);    		
    	}
    }
    
    public void updateDashboard(){					//updates to the dash board
    	SmartDashboard.putNumber("Dart Potentiometer : ", dartPotentiometer.getAverageValue());
    }
}

