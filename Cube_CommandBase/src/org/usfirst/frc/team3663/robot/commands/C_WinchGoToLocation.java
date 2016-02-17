package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_WinchGoToLocation extends Command {

	private int target;
	private double speed;
    public C_WinchGoToLocation(int pTarget ,double pSpeed) {
    	requires(Robot.ss_Winch);
    	target = pTarget;
    	speed = pSpeed;
    }

    protected void initialize() {
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return Robot.ss_Winch.runMotorAutoToTarget(target, speed);
    }

    protected void end() {
    	Robot.ss_Winch.STOP();
    }
    
    protected void interrupted() {
    	end();
    }
}
