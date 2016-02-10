package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;




public class SS_Dart extends Subsystem {

	//Motor
	private CANTalon dartMotor = new CANTalon(Robot.robotMap.dartMotor);
	
	//Sensors
	private AnalogInput dartPotentiometer = new AnalogInput(Robot.robotMap.dartPotentiometer);
	
    public void initDefaultCommand() {
    	
    }
    
    public void updateDashboard(){					//updates to the dash board
    	SmartDashboard.putNumber("Dart Potentiometer : ", dartPotentiometer.getAverageValue());
    }
}

