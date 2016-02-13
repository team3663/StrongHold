package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_PickupRunMotor extends Command {

	private double speed;
    public C_PickupRunMotor(double pSpeed) {
    	requires(Robot.ss_PickupArm);
    	speed = pSpeed;
    }

    protected void initialize() {
    	
    }

    protected void execute() {
    	Robot.ss_PickupArm.setPickupSpeed(speed);
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    	
    }

    protected void interrupted() {
    	end();
    }
}
