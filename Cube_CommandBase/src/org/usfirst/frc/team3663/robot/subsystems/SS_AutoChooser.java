package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.Robot;
import org.usfirst.frc.team3663.robot.commands.CG_AutoOverBasicDefence;
import org.usfirst.frc.team3663.robot.commands.CG_AutoUnderLowBar;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
    	SmartDashboard.putNumber("Auto Pot", value);
    	if(value > 3 && value < 200){
    		auto = null;
    	}
    	else if(value > 200 && value < 650){
    		auto = new CG_AutoOverBasicDefence();
    		autoShoot = false;    		
    	}
    	else if(value > 650 && value < 1100){
    		auto = new CG_AutoUnderLowBar();
    		autoShoot = false;
    	}
    	else if(value > 1100 && value < 1540){
    	}
    	else if(value > 1540 && value < 1970){
    	}
    	else if(value > 1970 && value < 2410){
    	}
    	else if(value > 2410 && value < 2860){
    	}
    	else if(value > 2860 && value < 3700){
    	}
    	else if(value > 3270 && value < 3700){
    		auto = new CG_AutoUnderLowBar();
    		autoShoot = true;
    	}
    	else if(value > 3700 && value < 3955){
    		auto = new CG_AutoOverBasicDefence();
    		autoShoot = true;
    	}
    	auto.start();
    }   
    
    public void updateDashboard(){
    	SmartDashboard.putNumber("dial calue", dial.getAverageValue());
    	Robot.gui.sendNumber("Auto/Dial", dial.getAverageValue());
    }
}

