
package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.commands.C_ArcadeDrive;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {
	CANTalon left,right;
	RobotDrive chassis;
	public DriveTrain(){
		left = new CANTalon(10);
		right = new CANTalon(0);
		chassis = new RobotDrive(left,right);
	}
    
    public void drive(double power){
    	chassis.arcadeDrive(power, 0);
    }
    public void arcadeDrive(Joystick stick){
    	chassis.arcadeDrive(stick, true);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new C_ArcadeDrive());
    }
}

