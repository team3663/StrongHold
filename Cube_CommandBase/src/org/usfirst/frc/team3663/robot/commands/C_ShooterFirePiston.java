package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_ShooterFirePiston extends Command {
	
	private int run = 10;

    public C_ShooterFirePiston() {
        requires(Robot.ss_Shooter);
    }

    protected void initialize() {
        Robot.ss_Shooter.fireShooterSolenoid(true);  
    }

    protected void execute() {
    }

    protected boolean isFinished() {
    	run --;
        return run < 0;
    }

    protected void end() {
    	Robot.ss_Shooter.fireShooterSolenoid(false);
    }

    protected void interrupted() {
    	end();
    }
}
