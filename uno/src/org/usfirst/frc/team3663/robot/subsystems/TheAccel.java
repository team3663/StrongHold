
package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.Robot;
import org.usfirst.frc.team3663.robot.commands.C_PrintAccelerometer;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TheAccel extends Subsystem {
//    private ADXL345_I2C accel;
    private Accelerometer accel;
    public TheAccel(){
//    	accel = new ADXL345_I2C(I2C.Port.kOnboard, Accelerometer.Range.k4G);
    	accel = new BuiltInAccelerometer();
    }
    public double getX(){
    	return accel.getX();
    }
    public double getY(){
    	return accel.getY();
    }
    public double getZ(){
    	return accel.getZ();
    }
    public double getPitch(){
    	return (getY()+1)*180;
    }
    public double getRoll(){
    	return (getX()+1)*180;
    }
    public void update(){
    	SmartDashboard.putNumber("Accelerometer X:", getX());
    	SmartDashboard.putNumber("Accelerometer Y:", getY());
    	SmartDashboard.putNumber("Accelerometer Z:", getZ());
    	SmartDashboard.putNumber("Pitch", getPitch());
    	SmartDashboard.putNumber("Roll", getRoll());
    	Robot.gui.sendNumber("sensor/Accelerometer X", Math.round(getX()*1000.0)/1000.0);
    	Robot.gui.sendNumber("sensor/Accelerometer Y", Math.round(getY()*1000.0)/1000.0);
    	Robot.gui.sendNumber("sensor/Accelerometer Z", Math.round(getZ()*1000.0)/1000.0);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new C_PrintAccelerometer());
    }
}