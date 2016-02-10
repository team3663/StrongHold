
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
	}
	public int getDistance(){
//		ByteBuffer thingy = ByteBuffer.allocateDirect(1);
//		thingy.put((byte)0x01);
//		i2c.writeBulk(thingy,0x01);
//		Timer.delay(0.04); // Delay for measurement to be taken
//		ByteBuffer toRead = ByteBuffer.allocateDirect(1);
//		toRead.put((byte)0x01);
//		i2c.readOnly(toRead, 1);
//		toRead.rewind();
//		SmartDashboard.putNumber("Distance[0]: ", toRead.get());
		
//		ByteBuffer hey = ByteBuffer.allocateDirect(2);
//		hey.put((byte)0x00);
//		hey.put((byte)0x04);
//		i2c.writeBulk(hey,0x01);
//		Timer.delay(0.001);
//		ByteBuffer read = ByteBuffer.allocateDirect(2);
//		read.put((byte)0x02);
//		read.put((byte)0x02);
//		int count = 0x01;
//		i2c.readOnly(read, count);
//		return count;
		//---------------
		byte[] distanceArray = new byte[2];
//		i2c.write(0x00, 0x04);
//		Timer.delay(0.1);
///////////////////////////////////////////////////
////		ByteBuffer distanceRegister_1st = ByteBuffer.allocateDirect(0x8f);
////		if(i2c.writeBulk(distanceRegister_1st, 0x01));
///////////////////////////////////////////////////
//		if(i2c.readOnly(distanceArray, 2));
//		int distance = (int)(distanceArray[0] << 8) + (int)distanceArray[1];
//		
//		SmartDashboard.putNumber("it's... working?", distance);
//		return distance;
		//-----------------
		byte[] thisThingy = new byte[1];
		thisThingy[0] = (byte)0x8f;
		i2c.writeBulk(thisThingy);
		i2c.readOnly(distanceArray, 2);
		int distance = distanceArray[1]<<5;
//		int distance = (distanceArray[0] << 8) + distanceArray[1];
//		int distance0 = distanceArray[0] << 8;
//		int distance1 = distanceArray[1];
//		SmartDashboard.putNumber("LidarRaw0", distance0);
//		SmartDashboard.putNumber("LidarRaw1", distance1);
		SmartDashboard.putNumber("it's... working?", distance);
		return distance;
	}
	public void update(){
		SmartDashboard.putNumber("Lidar", getDistance());
	}
	
    public void initDefaultand() {
        //setDefaultCommand(new C_PrintLidar());
    }
}

