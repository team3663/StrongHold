package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_ShooterRunMotors extends Command {
	
	private double speed;
    public C_ShooterRunMotors(double pSpeed) {
    	requires(Robot.ss_Shooter);
    	speed = pSpeed;
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.ss_Shooter.setShooterMotorsSpeed(speed);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.ss_Shooter.STOP();
    }

    protected void interrupted() {
    	end();
    }
}
