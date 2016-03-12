package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class C_TrentsVision extends Command {
	NetworkTable table;
	double degrees;
	double pwr = 0.35;
    public C_TrentsVision() {
        requires(Robot.ss_DriveTrain);
        table = Robot.visionTable;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(!table.getBoolean("foundObject: ",false)){
    		end();
    	}
    	degrees = table.getNumber("cameraMoveAngle: ",360);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.ss_DriveTrain.arcadeRobotDrive(0.5, 0.5);
    	//Small Move Mode
    	if(Math.abs(degrees) < 15){
    		if(degrees < 0){
				drive(-pwr, -0.7);
    		}else{
				drive(pwr, 0.7);
    		}
    	}
    	//Large Move Mode
    	else{
    		if(degrees < 0){
				drive(-pwr, -0.8);
    		}else{
    			drive(pwr,0.8);
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
//        return !table.getBoolean("C_/centeringGoal: ",false);
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        table.putBoolean("Mode/commandRunning: ", false);
        Robot.ss_DriveTrain.STOP();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
    private void calculateDriveTime(){
    	
    }
    private void drive(double power, double turn/*, double duration*/){
    	double startTime = Timer.getFPGATimestamp();
    	while(startTime /*+ duration */> Timer.getFPGATimestamp()){
    		Robot.ss_DriveTrain.arcadeRobotDrive(power,turn);
    	}
    	Robot.ss_DriveTrain.STOP();
    	Timer.delay(1);

    }
}
