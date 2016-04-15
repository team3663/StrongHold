package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *use this command in a relative manner. Put in negative angles to turn left (counter clockwise)
 *and positive angle to turn right (clockwise). (e.g. -90.0 is a left turn and 90.0 is a 
 *right turn. 180 and -180 are the same) The number entered is in degrees
 */
public class C_DriveToAngle extends Command {
	double startAngle;
	double angle;
	double forwardSpeed;
	double turnSpeed;

    public C_DriveToAngle(double angle,double forwardSpeed,double turnSpeed) {
    	this.angle = angle;
    	this.forwardSpeed = forwardSpeed;
    	this.turnSpeed = turnSpeed;
        requires(Robot.ss_DriveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	startAngle = Robot.ss_DriveTrain.getAngle();
  
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(angle > 0){
    		if(Math.abs((Robot.ss_DriveTrain.getAngle() - startAngle) - angle) < 15){
        		Robot.ss_DriveTrain.arcadeRobotDrive(forwardSpeed, -Math.abs(turnSpeed)/1.3);
    		}else{
        		Robot.ss_DriveTrain.arcadeRobotDrive(forwardSpeed, -Math.abs(turnSpeed));
    		}
    	}else{
    		if(Math.abs((Robot.ss_DriveTrain.getAngle() - startAngle) - angle) < 15){
        		Robot.ss_DriveTrain.arcadeRobotDrive(forwardSpeed, Math.abs(turnSpeed/1.3));
    		}else{
    			Robot.ss_DriveTrain.arcadeRobotDrive(forwardSpeed, Math.abs(turnSpeed));
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs((Robot.ss_DriveTrain.getAngle() - startAngle) - angle) < 2;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
