package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.Robot;
import org.usfirst.frc.team3663.robot.commands.C_WinchMove;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * assume that negative is lifting the robot up
 */
public class SS_Winch extends Subsystem {

	private CANTalon winchMotor1 = new CANTalon(Robot.robotMap.winchMotor1);
	private CANTalon winchMotor2 = new CANTalon(Robot.robotMap.winchMotor2);
	
	private int maxEncoderTicks = 2000;
	
    public void initDefaultCommand() {
    	winchMotor1.enableBrakeMode(true);
    	winchMotor2.enableBrakeMode(true);
    	setDefaultCommand(new C_WinchMove());
    }
    
    public void setWinchSpeedNOTSAFE(double pSpeed){
    	setWinchMotor1(pSpeed);
    	setWinchMotor2(pSpeed);
    }
    
    public boolean runMotorAutoToTarget(int pTarget, double pSpeed){	//code written to run the winch in auto	
    	if(0< pTarget){
    		setWinchMotor1(pSpeed);
    		setWinchMotor2(pSpeed);
    		return false;
    	}
    	return true;
    }
    
    public void runMotorTeleop(double pSpeed){							//for human use with built in safety
    	if(pSpeed > .2 || pSpeed < -.2){
    		setWinchMotor1(pSpeed);
    		setWinchMotor2(pSpeed);
    	}
    	else{
	    	STOP();
    	}
    }
    
    public void setWinchMotor1(double pSpeed){
		winchMotor1.set(pSpeed);
    }
    
    public void setWinchMotor2(double pSpeed){
		winchMotor2.set(pSpeed);
    }
    public int grabEncoder(){
    	return winchMotor1.getEncPosition();
    }
    
    public void STOP(){													//stops running the motor
    	winchMotor1.set(0);
		winchMotor2.set(0);
    }
    
    public void updateDashboard(){								
    	Robot.gui.sendNumber("winch/Winch Encoder",winchMotor1.getSpeed());
    }
}

