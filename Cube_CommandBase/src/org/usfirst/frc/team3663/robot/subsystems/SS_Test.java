package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.Robot;
import org.usfirst.frc.team3663.robot.commands.TestCG_TestRequiresAll;
import org.usfirst.frc.team3663.robot.commands.TestC_CycleTest;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class SS_Test extends Subsystem {
	public int testNumber = 0;
	private boolean isTesting = false;
	public boolean stopCycleTest = false;
	public String testName = "";
	private TestCG_TestRequiresAll takeAll = null;
	
	public String untested = "untested";
	public String passed = "passed";
	public String selfVerify = "you verify";
	
	public String shooterTopStatus = untested; 
	public String shooterBottomStatus = untested; 
	public String shooterPlungerStatus = untested; 
	public String pickupIntakeStatus = untested;
	public String pickupArmStatus = untested;
	
    public void initDefaultCommand() {
    	//setDefaultCommand(new TestC_Test());
    }
    	
    public SS_Test(){
//		SmartDashboard.putNumber("Testing #:", testNumber);
//    	SmartDashboard.putBoolean("Test Mode:", isTesting);
//    	SmartDashboard.putString("Testing:", testName);
    	Robot.gui.sendNumber("Test/Test#", testNumber);
    	Robot.gui.sendBoolean("Test/TestMode", isTesting);
    	Robot.gui.sendString("Test/Testing", testName);
    	resetTestStatus();
	}
	
	public boolean currentlyTesting(){
		return isTesting;
	}
	
	public void enterTestMode(){
		if (takeAll == null){
			takeAll = new TestCG_TestRequiresAll();
		}
		if(isTesting == false){
			isTesting = true;
			takeAll.start();

			resetTestStatus();
	    	Robot.gui.sendString("Test/takeall","taken");
		}
	}
	
	private void resetTestStatus(){
		shooterTopStatus = untested;
		shooterBottomStatus = untested;
		shooterPlungerStatus = untested;
		pickupIntakeStatus = untested;
		pickupArmStatus = untested;
	}
	
	public void exitTestMode(){
		if (takeAll != null){
			takeAll.cancel();
	    	Robot.gui.sendString("Test/takeall","free");
		}
		isTesting = false;
		update();
	}
	
	public void incrementBy(int value){
		testNumber += value;
	}

	public void update(){
//    	SmartDashboard.putNumber("Testing #:", testNumber);
//    	SmartDashboard.putBoolean("Test Mode:", isTesting);
//    	SmartDashboard.putString("Testing:", testName);
    	Robot.gui.sendNumber("Test/Test#", testNumber);
    	Robot.gui.sendBoolean("Test/TestMode", isTesting);
    	Robot.gui.sendString("Test/Testing", testName);
    	
    	Robot.gui.sendString("Test/Shooter top motor+enc", shooterTopStatus);
    	Robot.gui.sendString("Test/Shooter bottom motor+enc", shooterBottomStatus);
    	Robot.gui.sendString("Test/Shooter plunger", shooterPlungerStatus);
    	Robot.gui.sendString("Test/Pickup intake", pickupIntakeStatus);
    	Robot.gui.sendString("Test/Pickup arm", pickupArmStatus);
    }
}