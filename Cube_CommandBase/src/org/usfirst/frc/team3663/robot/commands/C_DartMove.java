package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_DartMove extends Command {

    public C_DartMove() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.ss_Dart);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    protected void execute() {
    	Robot.ss_Dart.moveDart(Robot.oi.driveJoystick.getRawAxis(5), Robot.ss_PickupArm.isDown());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.ss_Dart.STOP();
    }

    protected void interrupted() {
    	end();
    }
}
