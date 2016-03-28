package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_ShooterAutoStart extends Command {

	private int speed;
    public C_ShooterAutoStart(int pSpeed) {
    	requires(Robot.ss_Shooter);
    	speed = pSpeed;
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.ss_Shooter.holdSpeed(speed);
    }

    protected boolean isFinished() {
        return Robot.ss_Shooter.atSpeed(speed);
    }

    protected void end() {
    	Robot.ss_Shooter.fireShooterSolenoid(true);
    }

    protected void interrupted() {
    	//end();//if interrupted, not at speed therefore ! fire!!!
    }
}
