
package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SS_PowerDistributionBoard extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private PowerDistributionPanel pdp = new PowerDistributionPanel();
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void updateDashboard(){
    	SmartDashboard.putNumber("Drive Train Front Right Voltage : ",	pdp.getCurrent(0));
    	SmartDashboard.putNumber("Drive Train Rear Right Voltage : ",	pdp.getCurrent(1));
    	SmartDashboard.putNumber("Drive Train Front Left Voltage : ",	pdp.getCurrent(2));
    	SmartDashboard.putNumber("Drive Train Rear Left Voltage : ",	pdp.getCurrent(3));
    	SmartDashboard.putNumber("Winch Motor One Voltage : ",			pdp.getCurrent(4));
    	SmartDashboard.putNumber("Winch Motor Two Voltage : ",			pdp.getCurrent(5));
    	SmartDashboard.putNumber("Shooter Motor One Voltage : ",		pdp.getCurrent(6));
    	SmartDashboard.putNumber("Shooter Motor Two Voltage : ",		pdp.getCurrent(7));
    	SmartDashboard.putNumber("Dart Motor Voltage : ", 				pdp.getCurrent(8));
    	SmartDashboard.putNumber("Pickup Motor Voltage : ",				pdp.getCurrent(9));
    	SmartDashboard.putNumber("Hook Motor Voltage : ",				pdp.getCurrent(10));
    	SmartDashboard.putNumber("Wheely Bar Voltage : ",				pdp.getCurrent(11));
    	SmartDashboard.putNumber("Total Voltage : ", 					pdp.getVoltage());
    	SmartDashboard.putNumber("Total draw : ",						pdp.getTotalCurrent());
    	SmartDashboard.putNumber("PDP Tempature : ",					pdp.getTemperature());
    	
    	Robot.gui.sendNumber("voltage/Drive Train Front Right Voltage",	pdp.getCurrent(0));
    	Robot.gui.sendNumber("voltage/Drive Train Rear Right Voltage",	pdp.getCurrent(1));
    	Robot.gui.sendNumber("voltage/Drive Train Front Left Voltage",	pdp.getCurrent(2));
    	Robot.gui.sendNumber("voltage/Drive Train Rear Left Voltage",	pdp.getCurrent(3));
    	Robot.gui.sendNumber("voltage/Winch Motor One Voltage",			pdp.getCurrent(4));
    	Robot.gui.sendNumber("voltage/Winch Motor Two Voltage",			pdp.getCurrent(5));
    	Robot.gui.sendNumber("voltage/Shooter Motor One Voltage",		pdp.getCurrent(6));
    	Robot.gui.sendNumber("voltage/Shooter Motor Two Voltage",		pdp.getCurrent(7));
    	Robot.gui.sendNumber("voltage/Dart Motor Voltage", 				pdp.getCurrent(8));
    	Robot.gui.sendNumber("voltage/Pickup Motor Voltage",			pdp.getCurrent(9));
    	Robot.gui.sendNumber("voltage/Hook Motor Voltage",				pdp.getCurrent(10));
    	Robot.gui.sendNumber("voltage/Wheely Bar Voltage",				pdp.getCurrent(11));
    	Robot.gui.sendNumber("voltage/Total Voltage", 					pdp.getVoltage());
    	Robot.gui.sendNumber("voltage/Total draw",						pdp.getTotalCurrent());
    	Robot.gui.sendString("voltage/PDP Tempature",					Double.toString(Math.round((pdp.getTemperature()*1.8+32)*100.0)/100.0) + "�F");

    }
}

