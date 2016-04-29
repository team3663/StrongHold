package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.Robot;
import org.usfirst.frc.team3663.robot.commands.CG_AutoCornerShot;
import org.usfirst.frc.team3663.robot.commands.CG_AutoCornerShotDontMove;
import org.usfirst.frc.team3663.robot.commands.CG_AutoLowBarNoShoot;
import org.usfirst.frc.team3663.robot.commands.CG_AutoOverDefenceBasic;
import org.usfirst.frc.team3663.robot.commands.CG_AutoOverDefenceShot;
import org.usfirst.frc.team3663.robot.commands.CG_AutoUnderLowBar;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *  The numbers in the comments are the numbers on the auto dial which is on the left side of the bot
 */

public class SS_AutoChooser extends Subsystem {
	
	private AnalogInput dial = new AnalogInput(Robot.robotMap.autoAnalogDial);  
	
	private Command auto = null; 
	
    public void initDefaultCommand() {
    	
    }
    
    public void autoStart(){
    	auto = null;
    	int value = dial.getAverageValue();
    	SmartDashboard.putNumber("Auto Pot", value);
    	if(value > 3 && value < 200){ //0-10
    		auto = new CG_AutoOverDefenceShot(4.0);
    	}
    	else if(value > 200 && value < 650){ //11-20
    		auto = new CG_AutoUnderLowBar();
    	}
    	else if(value > 650 && value < 1100){ //21-30
    		auto = new CG_AutoCornerShot();
    	}
    	else if(value > 1100 && value < 1540){ //31-40
    		auto = new CG_AutoOverDefenceBasic(4.0);
    	}
    	else if(value > 1540 && value < 1970){ //41-50
    		auto = new CG_AutoLowBarNoShoot();
    	}
    	else if(value > 1970 && value < 2410){ //51-60
    		auto = new CG_AutoCornerShotDontMove();
    	}
    	else if(value > 2410 && value < 2860){ //61-70
    	}
    	else if(value > 2860 && value < 3700){ //71-80
    	}
    	else if(value > 3270 && value < 3700){ //81-90
    	}
    	else if(value > 3700 && value < 3955){ //91-100
    	}
    	
    	if(auto != null){
        	auto.start();    		
    	}
    }   
    
    public void autoEnd()
    {
    	if (auto != null)
    	{
    		auto.cancel();
    		auto = null;
    	}
    }
    
    public void updateDashboard(){
    	Robot.gui.sendNumber("Auto/Dial", dial.getAverageValue());
    }
}

