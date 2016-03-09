package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_WaitSecs extends Command {

	double endTime = 0;
	double delay = 0;
	int ctr=0;
    public C_WaitSecs(double pDelay) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	delay = pDelay;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	endTime = Timer.getFPGATimestamp()+delay;
    	ctr=0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.gui.sendNumber("general/wait",endTime-Timer.getFPGATimestamp());
    	Robot.gui.sendNumber("general/ctr",ctr++);

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Timer.getFPGATimestamp() > endTime;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
