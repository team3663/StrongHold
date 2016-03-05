package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.Robot;
import org.usfirst.frc.team3663.robot.commands.C_WinchMove;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
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
    	winchMotor1.set(pSpeed);
		winchMotor2.set(pSpeed);
    }
    
    public boolean runMotorAutoToTarget(int pTarget, double pSpeed){	//code written to run the winch in auto	
    	if(0< pTarget){
    		winchMotor1.set(pSpeed);
    		winchMotor2.set(pSpeed);
    		return false;
    	}
    	return true;
    }
    
    public void runMotorTeleop(double pSpeed){							//for human use with built in safty
    	winchMotor1.set(pSpeed);
    	winchMotor2.set(pSpeed);
    }
    
    public void testSetWinchMotor1(double pSpeed){
		winchMotor1.set(pSpeed);
    }
    
    public void testSetWinchMotor2(double pSpeed){
		winchMotor2.set(pSpeed);
    }
    
    public void STOP(){													//stops running the motor
    	winchMotor1.set(0);
		winchMotor1.set(0);
    }
    
    public void updateDashboard(){								
    	Robot.gui.sendNumber("winch/Winch Encoder",winchMotor1.getSpeed());
    	SmartDashboard.putNumber("Winch Encoder", winchMotor1.getEncPosition());
    }
}

