package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**		\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/
 *		DISCLAMER : if you are passing in a negative speed the command will not run
 *		/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\
 */
public class C_WheelyBarAutoMove extends Command {

	private int target;
	private double speed;
    public C_WheelyBarAutoMove(int pTarget, double pSpeed) { 
        requires(Robot.ss_WheelyBar);
        target = pTarget;
        //in initialize speed = pSpeed;
    }

    protected void initialize() {
    	Robot.ss_WheelyBar.setEndTime(15);
    	speed = Robot.ss_WheelyBar.getSpeed(target);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return Robot.ss_WheelyBar.moveWheelyBarAuto(target, speed);
    }

    protected void end() {
    	Robot.ss_WheelyBar.STOP();
    }

    protected void interrupted() {
    	end();
    }
}
