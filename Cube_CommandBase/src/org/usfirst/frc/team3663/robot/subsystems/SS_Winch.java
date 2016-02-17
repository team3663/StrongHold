package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.Robot;
import org.usfirst.frc.team3663.robot.commands.C_WinchJoystickUse;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SS_Winch extends Subsystem {

	private CANTalon winchMotor = new CANTalon(Robot.robotMap.winchMotor);
	private Encoder winchEncoder = new Encoder(Robot.robotMap.winchEncoder[0], Robot.robotMap.winchEncoder[1]);
	
	private int maxEncoderTicks = 2000;
	
    public void initDefaultCommand() {
    	setDefaultCommand(new C_WinchJoystickUse());
    }
    
    public void setWinchSpeedNOTSAFE(double pSpeed){
    	winchMotor.set(pSpeed);
    }
    
    public boolean runMotorAutoToTarget(int pTarget, double pSpeed){	//code written to run the winch in auto	
    	if(winchEncoder.getRaw() < pTarget){
    		winchMotor.set(pSpeed);
    		return false;
    	}
    	return true;
    }
    
    public void runMotorTeleop(double pSpeed){							//for human use with built in safty
    	int distValue = winchEncoder.getRaw();
    	if((distValue > maxEncoderTicks && pSpeed < 0) || (distValue < 0 && pSpeed > 0)){
    		winchMotor.set(pSpeed);
    	}
    	else{
    		STOP();
    	}
    }
    
    public void STOP(){													//stops running the motor
    	winchMotor.set(0);
    }
    
    public void updateDashboard(){										//updates the dash board
    	SmartDashboard.putNumber("Winch Encoder : ", winchMotor.getSpeed());
    }
}

