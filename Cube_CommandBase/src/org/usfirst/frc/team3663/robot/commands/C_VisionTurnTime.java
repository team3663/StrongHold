package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_VisionTurnTime extends Command {

	double turn, time, startTime; 
	int i = 0;
	
    public C_VisionTurnTime(double pTurn, double pTime) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.ss_DriveTrain);
        turn = pTurn;
        time = pTime;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	startTime = Timer.getFPGATimestamp();
    	i = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.gui.sendNumber("drive/AY_Vision turnCount",i++);
    	Robot.ss_DriveTrain.arcadeRobotDrive(0, turn);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return ((Timer.getFPGATimestamp()-startTime > time) || Robot.visionTable.getBoolean("foundObject: ", false));
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.ss_DriveTrain.STOP();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
