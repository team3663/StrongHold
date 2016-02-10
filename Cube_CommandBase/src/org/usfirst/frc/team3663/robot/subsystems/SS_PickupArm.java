package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;




public class SS_PickupArm extends Subsystem {
	
    //Motors
	private CANTalon pickupMotor = new CANTalon(Robot.robotMap.pickUpMotor);
	
	//Solenoids
	private DoubleSolenoid pickupSolenoid = new DoubleSolenoid(Robot.robotMap.pickUpSolenoid[0], Robot.robotMap.pickUpSolenoid[1]);
	
    public void initDefaultCommand() {
    }
    
    public void setPickupSpeed(double speed){				//sets the speed of the motor
    	pickupMotor.set(speed);
    }
    
    public void togglePickupSolenoid(){						//acts as a toggle for the solenoid
    	if(pickupSolenoid.get() == DoubleSolenoid.Value.kForward){
    		pickupSolenoid.set(DoubleSolenoid.Value.kReverse);
    	}
    	else{
    		pickupSolenoid.set(DoubleSolenoid.Value.kForward);    		
    	}
    }
    
    public void firePickupSolenoid(boolean pFired){			//used to set the state of the solenoid
    	if(pFired){
    		pickupSolenoid.set(DoubleSolenoid.Value.kForward);
    	}
    	else{
    		pickupSolenoid.set(DoubleSolenoid.Value.kReverse);    		
    	}
    }
    
    public void updateDashboard(){							//updates the dash board
    	SmartDashboard.putNumber("Pickup Speed : ", pickupMotor.getSpeed());
    }
}

