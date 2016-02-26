package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.Robot;
import org.usfirst.frc.team3663.robot.commands.C_DriveTrain;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;




public class SS_DriveTrain extends Subsystem {
	
	//Motors
	private CANTalon driveMotorLeft1 = new CANTalon(Robot.robotMap.driveLeftMotor1);
	private CANTalon driveMotorLeft2 = new CANTalon(Robot.robotMap.driveLeftMotor2);
	private CANTalon driveMotorRight1 = new CANTalon(Robot.robotMap.driveRightMotor1);
	private CANTalon driveMotorRight2 = new CANTalon(Robot.robotMap.driveRightMotor2);
	
	//DriveTrain
	private RobotDrive driveTrain = new RobotDrive(driveMotorLeft1, driveMotorLeft2, driveMotorRight1, driveMotorRight2);
	
	//Sensors
	private AnalogGyro driveGyro = new AnalogGyro(Robot.robotMap.driveGyro);

	//encoders are now from CANTalons
	
	//Carry values
	private int currentRunNumber = 0;
	private int lastEncoderTicks = -0;
	private int currentSpeed = 0;
	
    public void initDefaultCommand() {
    	setDefaultCommand(new C_DriveTrain());
    	driveGyro.reset();
    }
    
    public void arcadeRobotDrive(double pForwardSpeed, double pTurnSpeed){		//Responsible for driving the robot
    	driveTrain.arcadeDrive(pForwardSpeed, pTurnSpeed);
    }
    
    public void autoArcadeDrive(double pYSpeed, double pXSpeed){
    	driveTrain.arcadeDrive(pYSpeed, pXSpeed);
    }
    
    public void resetGyro(){							//Resets the Gyro
    	driveGyro.reset();
    }
    
    public int getLeftEnc(){
    	return driveMotorLeft1.getEncPosition();
    }
    
    public int getRightEnc(){
    	return driveMotorRight1.getEncPosition();
    }
    
    public boolean spinByGyro(int pDegrees){			//Spins the robot the passed in value returning if the action was complete
    	if(pDegrees > driveGyro.getAngle() && pDegrees > 0){
    		driveTrain.arcadeDrive(-1, 0);
    	}
    	else if(pDegrees < driveGyro.getAngle() && pDegrees < 0){
    		driveTrain.arcadeDrive(1, 0);    		
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
    
    //THIS IS NOT COMPLETE
    public void driveByEncoder(double pMaxSpeed, int pTarget, double pTurnValue){
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
    	return getLeftEnc() > pTarget-10;
    }
    
    public void resetEncoders(){
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
    
    public void updateDashboard(){						//updates the dash board
    	SmartDashboard.putNumber("Left Drive Motor 1 : ", driveMotorLeft1.getSpeed());
    	SmartDashboard.putNumber("Left Drive Motor 2 : ", driveMotorLeft2.getSpeed());
    	SmartDashboard.putNumber("Right Drive Motor 1 : ", driveMotorRight1.getSpeed());
    	SmartDashboard.putNumber("Right Drive Motor 2 : ", driveMotorRight2.getSpeed());
    	SmartDashboard.putNumber("Drive Gyro Angle : ", driveGyro.getAngle());
    	SmartDashboard.putNumber("LeftEncoder : ", driveMotorLeft1.getEncPosition());
    	SmartDashboard.putNumber("RightEncoder : ", driveMotorRight1.getEncPosition());

    	Robot.gui.sendNumber("drive/Left Drive Motor 1 : ", driveMotorLeft1.getSpeed());
    	Robot.gui.sendNumber("drive/Left Drive Motor 2 : ", driveMotorLeft2.getSpeed());
    	Robot.gui.sendNumber("drive/Right Drive Motor 1 : ", driveMotorRight1.getSpeed());
    	Robot.gui.sendNumber("drive/Right Drive Motor 2 : ", driveMotorRight2.getSpeed());
    	Robot.gui.sendNumber("drive/Drive Gyro Angle : ", driveGyro.getAngle());
    	Robot.gui.sendNumber("drive/Left Encoder : ", driveMotorLeft1.getEncPosition());
    	Robot.gui.sendNumber("drive/Right Encoder : ", driveMotorRight1.getEncPosition());
    }
}

