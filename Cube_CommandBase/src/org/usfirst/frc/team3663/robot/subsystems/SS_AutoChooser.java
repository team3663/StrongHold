package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.Robot;
import org.usfirst.frc.team3663.robot.commands.CG_AutoOverBasicDefence;
import org.usfirst.frc.team3663.robot.commands.CG_AutoUnderLowBar;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class SS_AutoChooser extends Subsystem {
	
	private AnalogInput dial = new AnalogInput(Robot.robotMap.autoAnalogDial);    
    public boolean autoShoot = false;
	
    public void initDefaultCommand() {
    	
    }
    
    public void autoStart(){
    	Command auto = null;
    	int value = dial.getAverageValue();
    	if(value > 4 && value < 400){
    		auto = null;
    	}
    	if(value > 400 && value < 800){
    		auto = new CG_AutoOverBasicDefence();
    		autoShoot = false;    		
    	}
    	if(value > 800 && value < 1200){
    		auto = new CG_AutoUnderLowBar();
    		autoShoot = false;
    	}
    	if(value > 1200 && value < 1600){
    	}
    	if(value > 1600 && value < 2000){
    	}
    	if(value > 2000 && value < 2400){
    	}
    	if(value > 2400 && value < 2800){
    	}
    	if(value > 2800 && value < 3200){
    	}
    	if(value > 3200 && value < 3600){
    		auto = new CG_AutoUnderLowBar();
    		autoShoot = true;
    	}
    	if(value > 3600 && value < 4000){
    		auto = new CG_AutoOverBasicDefence();
    		autoShoot = true;
    	}
    	auto.start();
    }    
}

