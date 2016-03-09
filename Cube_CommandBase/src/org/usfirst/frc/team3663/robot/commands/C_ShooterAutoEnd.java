package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_ShooterAutoEnd extends Command {

	private int loop = 10;
    public C_ShooterAutoEnd() {
        requires(Robot.ss_Shooter);
    }

    protected void initialize() {
    	loop = 10;
    }
    
    protected void execute() {
    	loop--;
    }

    protected boolean isFinished() {
        return loop<0;
    }

    protected void end() {
    	Robot.ss_Shooter.fireShooterSolenoid(false);
    	Robot.ss_Shooter.STOP();
    }
    
    protected void interrupted() {
    	end();
    }
}
