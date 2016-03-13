package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_ShooterShoot extends Command {

	private int count = 20;
	private boolean end = false;
    public C_ShooterShoot() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.ss_Shooter);
    }

    protected void initialize() {
    	count = 20;
    	end = false;
    }

    protected void execute() {
    	// todo this only drives the top motor
    	Robot.ss_Shooter.setShooterMotorsSpeed(1);
    	if(Robot.oi.driveJoystick.getRawButton(Robot.oi.shooterFirerPistonWait)){
    		Robot.ss_Shooter.aboveWantedSpeed(20000);
    		end = true;
    	}
    	else if(Robot.oi.driveJoystick.getRawButton(Robot.oi.shooterFirePistonNoWait)){
    		Robot.ss_Shooter.fireShooterSolenoid(true);
    		end = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(end){
    		count --;
    		return count < 0;
    	}
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.ss_Shooter.STOP();
    	Robot.ss_Shooter.fireShooterSolenoid(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
