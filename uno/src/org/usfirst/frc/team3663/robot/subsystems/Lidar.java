
package org.usfirst.frc.team3663.robot.subsystems;

import java.nio.ByteBuffer;

import org.usfirst.frc.team3663.robot.commands.C_PrintLidar;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Lidar extends SensorBase {
	I2C i2c;
	int LIDAR_CONFIG_REGISTER = 0x00;
	int LIDAR_DISTANCE_REGISTER = 0x8f;
	byte[] distance;
	
	public Lidar(){
		i2c = new I2C(I2C.Port.kOnboard,0x62);
		i2c.write(0x45, 0x04);
		i2c.write(0x04, 0x20);
		i2c.write(0x11, 0xff);
		i2c.write(0x00, 0x04);
		//I don't remember what these are
	}
	public int getDistance(){
		byte[] distanceArray = new byte[2];
		byte[] thisThingy = new byte[1];
		thisThingy[0] = (byte)0x8f;
		i2c.writeBulk(thisThingy);
		i2c.readOnly(distanceArray, 2);
		SmartDashboard.putNumber("distanceArray[0]", distanceArray[0]);
		SmartDashboard.putNumber("distanceArray[1]", distanceArray[1]);
		int temp = distanceArray[1] & 0xFF;
		int num = distanceArray[0]<<8;
		//int distance = (distanceArray[0] << 8) + temp;
		int distance = num+temp;
		SmartDashboard.putNumber("Lidar (in):", distance/2.75);
		return distance;
	}
	public void update(){
		SmartDashboard.putNumber("Lidar:", getDistance());
	}
	
    public void initDefaultand() {
        //setDefaultCommand(new C_PrintLidar());
    }
}

