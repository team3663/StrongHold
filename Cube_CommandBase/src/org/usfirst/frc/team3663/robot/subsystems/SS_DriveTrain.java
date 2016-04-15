package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.Robot;
import org.usfirst.frc.team3663.robot.commands.C_DriveTrainArcade;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
/**
 * 
 * @author curti_000
 *I realy dont know how to explain this but the labeling for the driveTrain is correct
 */


public class SS_DriveTrain extends Subsystem {
	AHRS ahrs = new AHRS(SPI.Port.kMXP); 
	//Motors
	private CANTalon driveMotorLeft1 = new CANTalon(Robot.robotMap.driveLeftMotor1);
	private CANTalon driveMotorLeft2 = new CANTalon(Robot.robotMap.driveLeftMotor2);
	private CANTalon driveMotorRight1 = new CANTalon(Robot.robotMap.driveRightMotor1);
	private CANTalon driveMotorRight2 = new CANTalon(Robot.robotMap.driveRightMotor2);
	
	//DriveTrain
	private RobotDrive driveTrain = null;
	//Sensors
	private AnalogGyro driveGyro = new AnalogGyro(Robot.robotMap.driveGyro);
	private Accelerometer accel = new BuiltInAccelerometer();

	//encoders are now from CANTalons
	Encoder enc1 = new Encoder(0,1);
	Encoder enc2 = new Encoder(2,3);
	
	//Carry values
	private int currentRunNumber = 0;
	private int lastEncoderTicks = -0;
	private int currentSpeed = 0;
	private int bufferZoneEnc = 10;
	private int bufferZoneGyro = 5;

    public void initDefaultCommand() {
    	setDefaultCommand(new C_DriveTrainArcade());
    	driveGyro.reset();
    }
    
    public void arcadeRobotDrive(double pForwardSpeed, double pTurnSpeed){		//Responsible for driving the robot 
    	if(driveTrain == null){
    		
    		driveTrain = new RobotDrive(driveMotorLeft1, driveMotorLeft2, driveMotorRight1, driveMotorRight2);
    	}
    	if(Robot.robotMap.isDriveFlipped){
    		driveTrain.arcadeDrive(pTurnSpeed, pForwardSpeed*-1);
    	}
    	else{
    		driveTrain.arcadeDrive(pForwardSpeed, pTurnSpeed);
    	}
    }
    
    public void resetGyro(){							//Resets the Gyro
    	driveGyro.reset();
    }
    
    public void killDriveTrain(){
    	//driveTrain.setExpiration(10);
    	//driveTrain = null;
    }
    
    public int getLeftEnc(){							//gets the left Encoder
    	//return enc1.getRaw();
    	return driveMotorLeft1.getEncPosition();
    }
    
    public int getRightEnc(){							//gets the right encoder
    	//return enc2.getRaw();
    	return driveMotorRight1.getEncPosition();
    }
    //Spins the robot to within a tolerated amount of the target degrees. Returns true if within the tolerance
    public boolean spinByGyro(double pDegrees, double pSpeed){
    	double gyroAngle = driveGyro.getAngle();
    	//if the target degree lies outside of the current value +/- tolerance/2
    	if(pDegrees > gyroAngle+bufferZoneGyro/2 || pDegrees < gyroAngle-bufferZoneGyro/2){
    		if(pDegrees > gyroAngle) arcadeRobotDrive(0,-pSpeed);
    		if(pDegrees < gyroAngle) arcadeRobotDrive(0,pSpeed);
    	}
    	else{
    		return true;
    	}
    	return false;
    }
    
    public int setDistanceEncoder(int pInches){		//Sets the distance needed to travel
    	driveMotorLeft1.reset();
    	driveMotorRight1.reset();
    	lastEncoderTicks = -0;
    	return pInches * Robot.robotMap.encoderTicksPerInch;
    }
    
    public void driveByEncoder(double pMaxSpeed, int pTarget, double pTurnValue){			//drives the robot based on encoders
    	int distValueLeft = getLeftEnc();
    	int distValueRight = getRightEnc();
    	if(((distValueLeft - lastEncoderTicks) * currentSpeed * 20 > pTarget) && (currentSpeed > 0)){
    		currentSpeed -= .05;
    	}
    	else if(currentSpeed < pMaxSpeed && currentRunNumber > 10){
    		currentRunNumber = 0;
    		currentSpeed += .05;
    	}
    	double ratio = (distValueLeft/distValueRight) - 1;
    	driveTrain.arcadeDrive(currentSpeed, pTurnValue*ratio*currentSpeed);
    	currentRunNumber ++;
    }
    
    public boolean checkDistance(int pTarget){						//Checks if the distance was hit
    	return getLeftEnc() > pTarget-bufferZoneEnc;
    }
    
    public void resetEncoders(){									//resets the encoders
    	driveMotorLeft1.reset();
    	driveMotorRight1.reset();
    }
    
    public void STOP(){												//Stops all of the wheels
    	driveMotorLeft1.set(0);
    	driveMotorLeft2.set(0);
    	driveMotorRight1.set(0);
    	driveMotorRight2.set(0);
    }
    
    public void controlIndMotor(int motor, double value){
    	switch(motor){
			case 0:
				driveMotorLeft1.set(value);
				break;
			case 1:
				driveMotorLeft2.set(value);
				break;
			case 2:
				driveMotorRight1.set(value);
				break;
			case 3:
				driveMotorRight2.set(value);
				
				break;
    	}
    }
    
    public double getAccel(int axis){
    	if(axis == 0){
    		return accel.getX(); //On Glass, this is the z axis
    	}else if(axis == 1){
    		return accel.getY(); //On Glass, still the y
    	}else if(axis == 2){
    		return accel.getZ(); //On Glass, this is the x axis
    	}
    	return 0.0;
    }
  
    public double getAngle(){
    	return ahrs.getAngle();
    }

    public void updateDashboard(){
    	Robot.gui.sendNumber("drive/Left Drive Motor 1", Math.round(driveMotorLeft1.get()*100.0)/100.0);
    	Robot.gui.sendNumber("drive/Left Drive Motor 2", Math.round(driveMotorLeft2.get()*100.0)/100.0);
    	Robot.gui.sendNumber("drive/Right Drive Motor 1", Math.round(driveMotorRight1.get()*100.0)/100.0);
    	Robot.gui.sendNumber("drive/Right Drive Motor 2", Math.round(driveMotorRight2.get()*100.0)/100.0);
    	Robot.gui.sendNumber("drive/Drive Gyro Angle", Math.round(driveGyro.getAngle()*100.0)/100.0);
    	Robot.gui.sendNumber("ahrs/Angle", ahrs.getAngle());
    	Robot.gui.sendNumber("ahrs/CompassHeading", ahrs.getCompassHeading());
    	Robot.gui.sendNumber("ahrs/DisplacementX", ahrs.getDisplacementX());
    	Robot.gui.sendNumber("ahrs/DisplacementY", ahrs.getDisplacementY());
    	Robot.gui.sendNumber("ahrs/DisplacementZ", ahrs.getDisplacementZ());
    	Robot.gui.sendNumber("ahrs/Pitch", ahrs.getPitch());
    	Robot.gui.sendNumber("ahrs/Yaw", ahrs.getYaw());
    	Robot.gui.sendNumber("ahrs/Roll", ahrs.getRoll());
    	Robot.gui.sendNumber("ahrs/VelocityX", ahrs.getVelocityX());
    	Robot.gui.sendNumber("ahrs/VelocityY", ahrs.getVelocityY());
    	Robot.gui.sendNumber("ahrs/VelocityZ", ahrs.getVelocityZ());
    	Robot.gui.sendNumber("ahrs/RawAccelX", ahrs.getRawAccelX());
    	Robot.gui.sendNumber("ahrs/RawAccelY", ahrs.getRawAccelY());
    	Robot.gui.sendNumber("ahrs/RawAccelZ", ahrs.getRawAccelZ());
    	Robot.gui.sendBoolean("ahrs/isMoving", ahrs.isMoving());
    	Robot.gui.sendBoolean("ahrs/isRotating", ahrs.isRotating());
    	Robot.gui.sendBoolean("ahrs/isCalibrating", ahrs.isCalibrating());

//    	Robot.gui.sendNumber("drive/Left Encoder", getRightEnc());
//    	Robot.gui.sendNumber("drive/Right Encoder", getLeftEnc());
    }
}

