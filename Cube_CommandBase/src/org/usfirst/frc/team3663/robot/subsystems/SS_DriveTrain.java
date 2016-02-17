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
	
	private Encoder leftEncoder = new Encoder(Robot.robotMap.driveLeftEncoder[0], Robot.robotMap.driveLeftEncoder[1]);
	private Encoder rightEncoder = new Encoder(Robot.robotMap.driveRightEncoder[0], Robot.robotMap.driveRightEncoder[1]);
	
	//Carry values
	private int finalEncoderDistance = 0;	
	
	
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
    
    public boolean spinByGyro(int pDegrees){			//Spins the robot the passed in value returning if the action was complete
    	if(pDegrees > driveGyro.getAngle()){
    		driveTrain.arcadeDrive(0, .5);
    	}
    	else{
    		return true;
    	}
    	return false;
    }
    
    public int setDistanceEncoder(int pInches){		//Sets the distance needed to travel
    	leftEncoder.reset();
    	rightEncoder.reset();
    	return pInches * Robot.robotMap.encoderTicksPerInch;
    }
    
    public boolean checkDistance(){						//Checks if the distance was hit
    	return false;
    }
    
    public int getLeftEncoder()
    {
    	return leftEncoder.get();
    }
    public int getRightEncoder()
    {
    	return rightEncoder.get();
    }
    public void resetEncoders()
    {
    	leftEncoder.reset();
    	rightEncoder.reset();
    }
    
    public void STOP(){									//Stops all of the wheels
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
    }
}

