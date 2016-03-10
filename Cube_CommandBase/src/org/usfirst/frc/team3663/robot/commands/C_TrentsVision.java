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
//    	//Small Move Mode
//    	if(Math.abs(degrees) < 15){
//    		if(degrees < 0){
//				drive(-0.3, -0.3);
//    		}else{
//				drive(0.3, 0.3);
//    		}
//    	}
//    	//Large Move Mode
//    	else{
//    		if(degrees < 0){
//				drive(-0.3, -0.65);
//    		}else{
//    			drive(0.3,0.65);
//    		}
//    	}
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
    private void drive(double power, double turn){
    	double startTime = Timer.getFPGATimestamp();
    	double duration = 2.0;
    	while(startTime + duration < Timer.getFPGATimestamp()){
    		Robot.ss_DriveTrain.arcadeRobotDrive(power,turn);
    	}

    }
}
