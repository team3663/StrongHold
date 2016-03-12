package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TestC_CycleTest extends Command {

    public TestC_CycleTest() {
    	requires(Robot.ss_Test);
    }

    protected void initialize() {
    	Robot.ss_Test.stopCycleTest = false;
    }

    protected void execute() {
		double jsValue = Robot.oi.testJoystick.getRawAxis(1);
    	switch(Robot.ss_Test.testNumber){
	    	case 0:
	    		Robot.ss_DriveTrain.controlIndMotor(0, jsValue);
	    		Robot.ss_Test.testName = "DriveMotorLeft1";
	    		break;
	    	case 1:
	    		Robot.ss_DriveTrain.controlIndMotor(1, jsValue);
	    		Robot.ss_Test.testName = "DriveMotorLeft2";
	    		break;
	    	case 2:
	    		Robot.ss_DriveTrain.controlIndMotor(2, jsValue);
	    		Robot.ss_Test.testName = "DriveMotorRight1";
	    		break;
	    	case 3:
	    		Robot.ss_DriveTrain.controlIndMotor(3, jsValue);
	    		Robot.ss_Test.testName = "DriveMotorRight2";
	    		break;
	    	case 4:
	    		Robot.ss_Shooter.setShooterTopMotorSpeed(jsValue);
	    		Robot.ss_Test.testName = "ShooterMotorTop";
	    		break;
	    	case 5:
	    		Robot.ss_Shooter.setShooterBottomMotorSpeed(jsValue);
	    		Robot.ss_Test.testName = "ShooterMotorBottom";
	    		break;
	    	case 6:
	    		if(jsValue > 0.9) 		Robot.ss_Shooter.fireShooterSolenoid(true);
	    		else if(jsValue < -0.9)	Robot.ss_Shooter.fireShooterSolenoid(false);
	    		Robot.ss_Test.testName = "ShooterSolenoid";
	    		break;
	    	case 7:
	    		Robot.ss_Winch.testSetWinchMotor1(jsValue);
	    		Robot.ss_Test.testName = "WinchMotor1";
	    		break;
	    	case 8:
	    		Robot.ss_Winch.testSetWinchMotor2(jsValue);
	    		Robot.ss_Test.testName = "WinchMotor2";
	    		break;
	    	case 9:
	    		Robot.ss_Dart.moveDart(jsValue, true);
	    		Robot.ss_Test.testName = "DartMotor";
	    		break;
	    	case 10:
	    		Robot.ss_PickupArm.setPickupSpeed(jsValue);
	    		Robot.ss_Test.testName = "PickUpMotor";
	    		break;
	    	case 11:
	    		if(jsValue > 0.9) 		Robot.ss_PickupArm.firePickupSolenoid(true);
	    		else if(jsValue < -0.9)	Robot.ss_PickupArm.firePickupSolenoid(false);
	    		Robot.ss_Test.testName = "PickUpSolenoid";
	    		break;
	    	case 12:
	    		Robot.ss_WheelyBar.moveWheelyBar(jsValue);
	    		Robot.ss_Test.testName = "WheelyBarMotor";
	    		break;
	    	case 13:
	    		if(jsValue > 0.9) 		Robot.ss_Camera.setLight(true);
	    		else if(jsValue < -0.9)	Robot.ss_Camera.setLight(false);
	    		Robot.ss_Test.testName = "CameraLightSpike";
	    		break;
	    	case 14:
	    		Robot.ss_Hook.moveHook(jsValue);
	    		Robot.ss_Test.testName = "HookMotor";
	    		break;
    	}
    	zeroMotors(Robot.ss_Test.testNumber);
	Robot.ss_Test.update();
    }

    protected boolean isFinished() {
        return Robot.ss_Test.stopCycleTest;
    }

    protected void end() {
    	 
    }

    protected void interrupted() {
	   end();
    }
    
    private void zeroMotors(int pValue){
    	if(pValue != 0){
    		Robot.ss_DriveTrain.controlIndMotor(0, 0);
    	}
    	if(pValue != 1){
    		Robot.ss_DriveTrain.controlIndMotor(1, 0);
    	}
    	if(pValue != 2){
    		Robot.ss_DriveTrain.controlIndMotor(2, 0);
    	}
    	if(pValue != 3){
    		Robot.ss_DriveTrain.controlIndMotor(3, 0);
    	}
    	if(pValue != 4){
    		Robot.ss_Shooter.setShooterTopMotorSpeed(0);
    	}
    	if(pValue != 5){
    		Robot.ss_Shooter.setShooterBottomMotorSpeed(0);
    	}
    	if(pValue != 6){
    		//shooter plunger
    	}
    	if(pValue != 7){
    		Robot.ss_Winch.testSetWinchMotor1(0);
    	}
    	if(pValue != 8){
    		Robot.ss_Winch.testSetWinchMotor2(0);
    	}
    	if(pValue != 9){
    		Robot.ss_Dart.NOTSAFEMoveDart(0);
    	}
    	if(pValue != 10){
    		Robot.ss_PickupArm.setPickupSpeed(0);
    	}
    	if(pValue != 11){
    		//pickup Piston
    	}
    	if(pValue != 12){
    		Robot.ss_WheelyBar.moveWheelyBar(0);
    	}
    	if(pValue != 13){
    		//camera light
    	}
    	if(pValue != 14){
    		Robot.ss_Hook.moveHook(0);
    	}
    }
}
