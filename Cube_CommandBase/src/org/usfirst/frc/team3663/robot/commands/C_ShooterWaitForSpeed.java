package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_ShooterWaitForSpeed extends Command {

	private int speed;
    public C_ShooterWaitForSpeed(int pSpeed) {
    	speed = pSpeed;
        requires(Robot.ss_Shooter);
    }

    protected void initialize() {    	
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return Robot.ss_Shooter.aboveWantedSpeed(speed) || Robot.ss_Shooter.isMotorRunning();
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
