package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.Robot;
import org.usfirst.frc.team3663.robot.commands.TestC_Test;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;




public class SS_Test extends Subsystem {
	public int testNumber = 0;
	public boolean isTesting = false;
	public String testName = "";
	
	public SS_Test(){
		SmartDashboard.putNumber("Testing #:", testNumber);
    	SmartDashboard.putBoolean("Test Mode:", isTesting);
    	SmartDashboard.putString("Testing:", testName);
    	Robot.gui.sendNumber("operation/Test#", testNumber);
    	Robot.gui.sendBoolean("operation/TestMode", isTesting);
    	Robot.gui.sendString("operation/Testing", testName);
	}
	public void incrementBy(int value){
		testNumber += value;
	}
    public void initDefaultCommand() {
    	setDefaultCommand(new TestC_Test());
    }
    	
    public void update(){
    	SmartDashboard.putNumber("Testing #:", testNumber);
    	SmartDashboard.putBoolean("Test Mode:", isTesting);
    	SmartDashboard.putString("Testing:", testName);
    	Robot.gui.sendNumber("operation/Test#", testNumber);
    	Robot.gui.sendBoolean("operation/TestMode", isTesting);
    	Robot.gui.sendString("operation/Testing", testName);
    }
}