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
