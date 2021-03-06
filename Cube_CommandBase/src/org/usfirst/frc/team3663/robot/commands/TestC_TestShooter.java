package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TestC_TestShooter extends Command {

	double endTime;
	int state = 0;
	double speed = 0;
	double delta = .01;
	double delay = .5;
	int encoder = 0;
	double topSpeed = .4;
	
    public TestC_TestShooter() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.ss_Shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 0;
    	speed = 0;
    	Robot.gui.sendString("Test/testState","shooter starting");
    	Robot.ss_Test.shooterTopStatus = Robot.ss_Test.untested;
    	Robot.ss_Test.shooterBottomStatus = Robot.ss_Test.untested;
    	Robot.ss_Test.shooterPlungerStatus = Robot.ss_Test.untested;
    	Robot.ss_Test.update();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	switch (state){
    	case 0:
    		Robot.ss_Test.shooterTopStatus = Robot.ss_Test.testing;
    		Robot.ss_Shooter.setShooterTopMotorSpeed(speed);
    		speed += delta;
    		if (speed > topSpeed)
    		{
    			speed = topSpeed;
    			state++;
    			if (Robot.ss_Shooter.getShooterTopEncoderVelocity() < 1000){
    				Robot.ss_Test.shooterTopStatus = "Failed - expected encoder velocity > 1000, read "+Robot.ss_Shooter.getShooterTopEncoderVelocity();
    			}
    		}
    		break;
    	case 1:
    		Robot.ss_Shooter.setShooterTopMotorSpeed(speed);
    		speed -= delta;
    		if (speed < -topSpeed)
    		{
    			speed = -topSpeed;
    			state++;
    			if (Robot.ss_Shooter.getShooterTopEncoderVelocity() > -1000){
    				Robot.ss_Test.shooterTopStatus = "Failed - expected encoder velocity < -1000, read "+Robot.ss_Shooter.getShooterTopEncoderVelocity();
    			}
    			
    		}
    		break;
    	case 2:
    		Robot.ss_Shooter.setShooterTopMotorSpeed(speed);
    		speed += delta;
    		if (speed > 0)
    		{
    			speed = 0;
    			state++;
    			Robot.ss_Shooter.setShooterTopMotorSpeed(speed);
    			if (Robot.ss_Test.shooterTopStatus.equals(Robot.ss_Test.testing)){
    				Robot.ss_Test.shooterTopStatus = Robot.ss_Test.passed;
    			}
    		}
    		break;
    	case 3:
    		Robot.ss_Test.shooterBottomStatus = Robot.ss_Test.testing;
    		Robot.ss_Shooter.setShooterBottomMotorSpeed(speed);
    		speed += delta;
    		if (speed > topSpeed)
    		{
    			speed = topSpeed;
    			state++;
    			if (Robot.ss_Shooter.getShooterBottomEncoderVelocity() > -1000){
    				Robot.ss_Test.shooterBottomStatus = "Failed - expected encoder velocity < -1000, read "+Robot.ss_Shooter.getShooterBottomEncoderVelocity();
    			}
    		}
    		break;
    	case 4:
    		Robot.ss_Shooter.setShooterBottomMotorSpeed(speed);
    		speed -= delta;
    		if (speed < -topSpeed)
    		{
    			speed = -topSpeed;
    			state++;
    			if (Robot.ss_Shooter.getShooterBottomEncoderVelocity() < 1000){
    				Robot.ss_Test.shooterBottomStatus = "Failed - expected encoder velocity > 1000, read "+Robot.ss_Shooter.getShooterBottomEncoderVelocity();
    			}
    		}
    		break;
    	case 5:
    		Robot.ss_Shooter.setShooterBottomMotorSpeed(speed);
    		speed += delta;
    		if (speed > 0)
    		{
    			speed = 0;
    			state++;
    			Robot.ss_Shooter.setShooterBottomMotorSpeed(speed);
    			if (Robot.ss_Test.shooterBottomStatus.equals(Robot.ss_Test.testing)){
    				Robot.ss_Test.shooterBottomStatus = Robot.ss_Test.passed;
    			}
    		}
    		break;
    	case 6:
    		Robot.ss_Test.shooterPlungerStatus = Robot.ss_Test.testing;
    		Robot.ss_Shooter.fireShooterSolenoid(true);
    		state++;
    		endTime = Timer.getFPGATimestamp()+delay;
    		break;
    	case 7:
    		if (Timer.getFPGATimestamp() > endTime)
    			state++;
    		break;
    	case 8:
    		Robot.ss_Shooter.fireShooterSolenoid(false);
    		state++;
    		endTime = Timer.getFPGATimestamp()+delay;
    		break;

    	case 9:
    		if (Timer.getFPGATimestamp() > endTime){
    			state++;
    			Robot.ss_Test.shooterPlungerStatus = "verify plunger went out and in";
    		}
    		break;
    	}

    	Robot.gui.sendString("Test/testState","shooter "+state);
		Robot.ss_Test.update();    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return state >= 10;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.ss_Shooter.setShooterTopMotorSpeed(0);
    	Robot.ss_Shooter.setShooterBottomMotorSpeed(0);
    	//Robot.ss_Shooter.fireShooterSolenoid(false);

    	Robot.gui.sendString("Test/testState","shooter done");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
