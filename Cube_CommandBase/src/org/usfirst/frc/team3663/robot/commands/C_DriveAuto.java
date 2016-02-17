package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_DriveAuto extends Command {

	int distValue;
    public C_DriveAuto(int pInches) {
       requires(Robot.ss_DriveTrain);
       distValue = pInches;
    }

    protected void initialize() {
    	distValue = Robot.ss_DriveTrain.setDistanceEncoder(distValue);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    	end();
    }
}
