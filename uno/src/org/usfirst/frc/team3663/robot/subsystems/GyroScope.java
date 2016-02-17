
package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.Robot;
import org.usfirst.frc.team3663.robot.commands.C_PrintGyro;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class GyroScope extends Subsystem {
	AnalogGyro gScope;
    public GyroScope(){
    	gScope = new AnalogGyro(0);
    }
    public void reset(){
    	gScope.reset();
    }
    public double angle(){
    	return gScope.getAngle();
    }

    public void initDefaultCommand() {
        setDefaultCommand(new C_PrintGyro());
    }
    public void update(){
		Robot.gui.sendNumber("sensor/Gyro", Math.round(gScope.getAngle()*100.0)/100.0);
    	SmartDashboard.putNumber("GYRO:",angle());
    }
}