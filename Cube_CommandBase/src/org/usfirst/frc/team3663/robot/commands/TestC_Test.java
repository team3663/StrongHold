package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TestC_Test extends Command {

    public TestC_Test() {
    	requires(Robot.test);
    }

    protected void initialize() {
    }

    protected void execute() {
    	if(Robot.test.isTesting){
    		double jsValue = Robot.oi.testJoystick.getRawAxis(1);
	    	switch(Robot.test.testNumber){
		    	case 0:
		    		Robot.ss_DriveTrain.controlIndMotor(0, jsValue);
		    		Robot.test.testName = "DriveMotorLeft1";
		    		break;
		    	case 1:
		    		Robot.ss_DriveTrain.controlIndMotor(1, jsValue);
		    		Robot.test.testName = "DriveMotorLeft2";
		    		break;
		    	case 2:
		    		Robot.ss_DriveTrain.controlIndMotor(2, jsValue);
		    		Robot.test.testName = "DriveMotorRight1";
		    		break;
		    	case 3:
		    		Robot.ss_DriveTrain.controlIndMotor(3, jsValue);
		    		Robot.test.testName = "DriveMotorRight2";
		    		break;
		    	case 4:
		    		Robot.ss_Shooter.setShooterMotorTop(jsValue);
		    		Robot.test.testName = "ShooterMotorTop";
		    		break;
		    	case 5:
		    		Robot.ss_Shooter.setShooterMotorBottom(jsValue);
		    		Robot.test.testName = "ShooterMotorBottom";
		    		break;
		    	case 6:
		    		if(jsValue > 0.9) 		Robot.ss_Shooter.fireShooterSolenoid(true);
		    		else if(jsValue < -0.9)	Robot.ss_Shooter.fireShooterSolenoid(false);
		    		Robot.test.testName = "ShooterSolenoid";
		    		break;
		    	case 7:
		    		Robot.ss_Winch.testSetWinchMotor1(jsValue);
		    		Robot.test.testName = "WinchMotor1";
		    		break;
		    	case 8:
		    		Robot.ss_Winch.testSetWinchMotor2(jsValue);
		    		Robot.test.testName = "WinchMotor2";
		    		break;
		    	case 9:
		    		Robot.ss_Dart.moveDart(jsValue, true);
		    		Robot.test.testName = "DartMotor";
		    		break;
		    	case 10:
		    		Robot.ss_PickupArm.setPickupSpeed(jsValue);
		    		Robot.test.testName = "PickUpMotor";
		    		break;
		    	case 11:
		    		if(jsValue > 0.9) 		Robot.ss_PickupArm.firePickupSolenoid(true);
		    		else if(jsValue < -0.9)	Robot.ss_PickupArm.firePickupSolenoid(false);
		    		Robot.test.testName = "PickUpSolenoid";
		    		break;
		    	case 12:
		    		Robot.ss_WheelyBar.moveWheelyBar(jsValue);
		    		Robot.test.testName = "WheelyBarMotor";
		    		break;
		    	case 13:
		    		if(jsValue > 0.9) 		Robot.ss_Camera.setLight(true);
		    		else if(jsValue < -0.9)	Robot.ss_Camera.setLight(false);
		    		Robot.test.testName = "CameraLightSpike";
		    		break;
		    	case 14:
		    		Robot.ss_Hook.MoveHook(jsValue);
		    		Robot.test.testName = "HookMotor";
		    		break;
	    	}
    	}
    	Robot.test.update();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	 
    }

    protected void interrupted() {
	   end();
    }
}
