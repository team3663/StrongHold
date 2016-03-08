package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_ShooterHoldSpeed extends Command {

	public int speed;
	private boolean done = false;
	private int timeOut = 10;
    public C_ShooterHoldSpeed(int pSpeed) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.ss_Shooter);
        speed = pSpeed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.ss_Shooter.HoldSpeed(speed);
    	if(Robot.oi.driveJoystick.getRawButton(3)){
    		Robot.ss_Shooter.fireShooterSolenoid(true);
    		done = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(done){
    		timeOut--;
            return timeOut < 0;
    	}
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.ss_Shooter.fireShooterSolenoid(false); 
    	Robot.ss_Shooter.STOP();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
