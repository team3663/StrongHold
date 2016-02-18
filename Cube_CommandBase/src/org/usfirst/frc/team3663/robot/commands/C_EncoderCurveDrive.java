package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_EncoderCurveDrive extends Command {

	double angle,dInches;
	int currTicks,ticksPerSec, turningLeft = 1;
	
	NetworkTable table;
	
    public C_EncoderCurveDrive(double Angle, double DistanceInches) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.ss_DriveTrain);
        angle = Angle;
		dInches = DistanceInches;//Robot.robotMap.encoderTicksPerInch;
		table = Robot.visionTable;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        table.putBoolean("Mode/commandRunning: ", true);
        Robot.ss_DriveTrain.resetEncoders();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (angle > 0)
    	{
    		currTicks = Robot.ss_DriveTrain.getRightEnc();
    		ticksPerSec = 65*43;
    	}
    	else if (angle < 0)
    	{
    		currTicks = Robot.ss_DriveTrain.getLeftEnc();
    		ticksPerSec = 65;
    		turningLeft = -1;
    	}
    	//if ()
    	{
    		Robot.ss_DriveTrain.arcadeRobotDrive(0.5, turningLeft*(dInches+(27/2))/(dInches-(27/2)));
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Math.abs(currTicks) > (2*3.14*(dInches*ticksPerSec)*angle/360));
    }

    // Called once after isFinished returns true
    protected void end() {
        table.putBoolean("Mode/commandRunning: ", false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        table.putBoolean("Mode/commandRunning: ", false);
    }
}
