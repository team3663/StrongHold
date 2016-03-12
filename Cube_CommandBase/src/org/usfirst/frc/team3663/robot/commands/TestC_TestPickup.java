package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TestC_TestPickup extends Command {

	double endTime;
	int state = 0;
	double speed = 0;
	double delta = .01;
	double delay = 2;
	int encoder = 0;
	double topSpeed = .4;
	
	public TestC_TestPickup() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.ss_PickupArm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 0;
    	speed = 0;
    	Robot.gui.sendString("Test/testState","pickup starting");
    	Robot.ss_Test.pickupIntakeStatus = Robot.ss_Test.untested;
    	Robot.ss_Test.pickupArmStatus = Robot.ss_Test.untested;
    	Robot.ss_Test.update();
    	
//    	if (!Robot.ss_PickupArm.isDown()){
//    		Robot.ss_Test.pickupIntakeStatus = Robot.ss_Test.untested+" pickup must be down to start test";
//        	Robot.ss_Test.pickupArmStatus = Robot.ss_Test.untested+" check limit switch if pickup is down";
//        	Robot.ss_Test.update();
//        	state = -1; // this will stall the test until the pickup arm is down and the sensor works
//    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	switch (state){
    	case 0:
			Robot.ss_Test.pickupIntakeStatus = Robot.ss_Test.testing;
    		Robot.ss_PickupArm.setPickupSpeed(speed);
    		speed += delta;
    		if (speed > topSpeed)
    		{
    			speed = topSpeed;
    			state++;
    		}
    		break;
    	case 1:
    		Robot.ss_PickupArm.setPickupSpeed(speed);
    		speed -= delta;
    		if (speed < -topSpeed)
    		{
    			speed = -topSpeed;
    			state++;
    		}
    		break;
    	case 2:
    		Robot.ss_PickupArm.setPickupSpeed(speed);
    		speed += delta;
    		if (speed > 0)
    		{
    			speed = 0;
    			state++;
    			Robot.ss_PickupArm.setPickupSpeed(speed);
				Robot.ss_Test.pickupIntakeStatus = "verify intake motor spun in both directions";
				Robot.ss_Test.update();
    		}
    		break;
    	case 3:
			Robot.ss_Test.pickupArmStatus = Robot.ss_Test.testing;
    		if (!Robot.ss_PickupArm.isDown()){
    			Robot.ss_Test.pickupArmStatus = "Failed - limit switch is not working";
    		}
			Robot.ss_PickupArm.firePickupSolenoid(false);
			state++;
			endTime = Timer.getFPGATimestamp()+delay;
    		break;
    	case 4:
    		if (Timer.getFPGATimestamp() > endTime)
    			state++;
    		break;
    	case 5:
    		if (Robot.ss_PickupArm.isDown()){
    			Robot.ss_Test.pickupArmStatus = "Failed - limit switch is not working";
				Robot.ss_Test.update();
    		}
    		Robot.ss_PickupArm.firePickupSolenoid(true);
    		state++;
    		endTime = Timer.getFPGATimestamp()+delay;
    		break;

    	case 6:
    		if (Timer.getFPGATimestamp() > endTime){
    			state++;
        		if (Robot.ss_Test.pickupArmStatus.equals(Robot.ss_Test.testing)){
        			Robot.ss_Test.pickupArmStatus = "verify pickup arm went up and down";
        		}
    		}
    		break;
    	}

    	Robot.gui.sendString("Test/testState","pickup "+state);
		Robot.ss_Test.update();
	}

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return state >= 7;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.ss_PickupArm.setPickupSpeed(0);
    	//Robot.ss_PickupArm.firePickupSolenoid(true);

    	Robot.gui.sendString("Test/testState","pickup done");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
