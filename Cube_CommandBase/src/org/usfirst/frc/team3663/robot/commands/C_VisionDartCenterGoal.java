package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_VisionDartCenterGoal extends Command {

	int targetDist;
	double speed;
	
    public C_VisionDartCenterGoal() {
        requires(Robot.ss_Dart);
    }

    protected void initialize() {
    	if (Robot.visionTable.getBoolean("C_/centeringY: ",false))
    	{
    		if (Robot.visionTable.getBoolean("raiseDart: ",true))
    		{
    	    	targetDist = Robot.ss_Dart.getPotentiometerValue()+65;
    		}
    		else
    		{
    			targetDist = Robot.ss_Dart.getPotentiometerValue()-65;
    		}
    		speed = Robot.ss_Dart.findSpeed(targetDist);
    	}
    }
                                                                                                                             
    protected void execute() {
    	Robot.ss_Dart.incrementSpeed(speed, targetDist, Robot.ss_PickupArm.isDown());
    }

    protected boolean isFinished() {
    	return Robot.ss_Dart.hitLocation(speed, targetDist);
    }

    protected void end() {
    	Robot.ss_Dart.STOP();
    }

    protected void interrupted() {
    	end();
    }
}
