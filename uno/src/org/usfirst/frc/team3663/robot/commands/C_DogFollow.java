
package org.usfirst.frc.team3663.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3663.robot.Robot;

/**
 *
 */
public class C_DogFollow extends Command {

	int distance = 130;
    public C_DogFollow() {
        //requires(Robot.dTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double turn = calculateAngle();
    	double power = calculateDistance();
		SmartDashboard.putNumber("C_Following angle", turn);
		SmartDashboard.putNumber("C_Following forward", power);
		
		//Setting Drive Limits//
		if(power < 0.1 && power > -0.1){
			power = 0;
		}else if(power > 0.5){
			power = 0.5;
		}else if(power < -0.5){
			power = -0.5;
		}
		if(power > 0) power = (power+0.3)/2;
		else power = (power + -0.3)/2;
		
//		//Figuring out drive and/or turn//
		
    	if(turn < -0.3){
    		SmartDashboard.putString("C_Following", "turnOnly");
    		Robot.dTrain.drive(0, 0.3);
    	}else if(turn > 0.3){
    		SmartDashboard.putString("C_Following", "turnOnly");
    		Robot.dTrain.drive(0, -0.3);
    	}else{
    		SmartDashboard.putString("C_Following", "drive & turn");
    		Robot.dTrain.drive(power, turn);
//    		Robot.dTrain.drive(power, 0);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
//    	if(!Robot.table.getBoolean("foundObject: ",true)){
//        	SmartDashboard.putString("C_Following", "terminated");
//    		return true;
//    	}
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
    double calculateAngle(){
		double angle = Robot.dogNT.getNumber("gMass.Middle X: ",0) - 320;
    	angle /= 320;
    	return angle;
    }
    double calculateDistance(){
    	double driveSpeed = -(Robot.lidar.getDistance() - distance);
    	SmartDashboard.putNumber("Lidar - 100", driveSpeed);
    	driveSpeed /= distance;
    	if(driveSpeed > 0){
    		driveSpeed *= 4;
    	}
    	SmartDashboard.putNumber("DrivigSpeed", driveSpeed);
    	return driveSpeed;
    }
}
