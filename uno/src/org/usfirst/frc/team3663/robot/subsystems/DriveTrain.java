
package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.Robot;
import org.usfirst.frc.team3663.robot.commands.C_ArcadeDrive;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem {
	CANTalon left,right;
	Encoder leftEnc,rightEnc;
	RobotDrive chassis;
	public DriveTrain(){
		left = new CANTalon(1);
		right = new CANTalon(3);
		leftEnc = new Encoder(3,4);
		rightEnc = new Encoder(1,2);
		chassis = new RobotDrive(left,right);
	}
    
    public void drive(double power, double angle){
    	chassis.arcadeDrive(power, angle);
    }
    public void arcadeDrive(Joystick stick){
    	chassis.arcadeDrive(stick, true);
    }

    public void initDefaultCommand() {
//        setDefaultCommand(new C_ArcadeDrive());
    }
    public void update(){
    	SmartDashboard.putNumber("DriveLeftMotor:", left.get());
    	SmartDashboard.putNumber("DriveRightMotor:", right.get());
		Robot.gui.sendNumber("drive/DriveLeftMotor", (Math.round(left.get()*100.0))/100.0);
		Robot.gui.sendNumber("drive/DriveRightMotor", (Math.round(right.get()*100.0))/100.0);
    }
}

