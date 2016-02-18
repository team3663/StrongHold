package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class SS_AutoChooser extends Subsystem {
	
	private DigitalInput switch1 = new DigitalInput(Robot.robotMap.autoSwitchs[0]);
	private DigitalInput switch2 = new DigitalInput(Robot.robotMap.autoSwitchs[1]);
	private DigitalInput switch3 = new DigitalInput(Robot.robotMap.autoSwitchs[2]);
	private DigitalInput switch4 = new DigitalInput(Robot.robotMap.autoSwitchs[3]);
	
    public void initDefaultCommand() {
    	
    }
    
    public int autoType(){
    	int outputNumber = 0;
    	if(switch1.get()){
    		outputNumber = outputNumber | 1;
    	}
    	if(switch2.get()){
    		outputNumber = outputNumber | 2;
    	}
    	if(switch3.get()){
    		outputNumber = outputNumber | 4;
    	}
    	if(switch4.get()){
    		outputNumber = outputNumber | 8;
    	}
    	return outputNumber;
    }    
}

