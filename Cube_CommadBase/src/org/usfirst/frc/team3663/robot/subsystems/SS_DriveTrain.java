package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;




public class SS_DriveTrain extends Subsystem {
	
	//Motors
	private CANTalon driveMotorLeft1 = new CANTalon(Robot.robotMap.driveLeftMotor1);
	private CANTalon driveMotorLeft2 = new CANTalon(Robot.robotMap.driveLeftMotor2);
	private CANTalon driveMotorRight1 = new CANTalon(Robot.robotMap.driveRightMotor1);
	private CANTalon driveMotorRight2 = new CANTalon(Robot.robotMap.driveRightMotor1);
	
	//DriveTrain
	private RobotDrive driveTrain = new RobotDrive(driveMotorLeft1, driveMotorLeft2, driveMotorRight1, driveMotorRight2);
	
	//Sensors  (there will be encoders and other sensors)
	
    public void initDefaultCommand() {
    	
    }
    
    public void arcadeRobotDrive(Joystick pStick){		//Responsible for driving the robot
    	driveTrain.arcadeDrive(pStick.getRawAxis(Robot.robotMap.DriveAxisForward), pStick.getRawAxis(Robot.robotMap.DriveAxisTurn));
    }
    
    public void STOP(){									//Stops all of the wheels
    	driveMotorLeft1.set(0);
    	driveMotorLeft2.set(0);
    	driveMotorRight1.set(0);
    	driveMotorRight2.set(0);
    }
    
    public void updateDashboard(){						//updates the dash board
    	SmartDashboard.putNumber("Left Drive Motor 1 : ", driveMotorLeft1.getSpeed());
    	SmartDashboard.putNumber("Left Drive Motor 2 : ", driveMotorLeft2.getSpeed());
    	SmartDashboard.putNumber("Right Drive Motor 1 : ", driveMotorRight1.getSpeed());
    	SmartDashboard.putNumber("Right Drive Motor 2 : ", driveMotorRight2.getSpeed());
    }
}

