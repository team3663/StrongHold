package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.Robot;
import org.usfirst.frc.team3663.robot.commands.C_PickupSafteyArm;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;




public class SS_PickupArm extends Subsystem {
	
    //Motors
	private CANTalon pickupMotor = new CANTalon(Robot.robotMap.pickUpMotor);
	
	//Solenoids
	private DoubleSolenoid pickupSolenoid = new DoubleSolenoid(Robot.robotMap.pickUpSolenoid[0], Robot.robotMap.pickUpSolenoid[1]);
	
	//DIO
	private DigitalInput lowerLimit = new DigitalInput(Robot.robotMap.pickupLowerLimit);
	
	//past value
	private boolean stayInSafeZone = true;
	
    public void initDefaultCommand() {						//Sets starts safety check
    	setDefaultCommand(new C_PickupSafteyArm());
    }
    
    public void setPickupSpeed(double speed){				//Sets the speed of the motor
    	Robot.ss_Dart.getMoveArm();
    	pickupMotor.set(speed);
    }
    
    public boolean isDown(){
    	return pickupSolenoid.get() == DoubleSolenoid.Value.kForward && !lowerLimit.get();
    }
    
    public boolean isSafe(){
    	return stayInSafeZone;
    }
    
    public void setSafe(boolean pValue){
    	stayInSafeZone = pValue;
    }
    
    public void togglePickupSolenoid(){						//Acts as a toggle for the solenoid
    	if(pickupSolenoid.get() == DoubleSolenoid.Value.kForward){
    		pickupSolenoid.set(DoubleSolenoid.Value.kReverse);
    	}
    	else{
    		pickupSolenoid.set(DoubleSolenoid.Value.kForward);    		
    	}
    }
    
    public void firePickupSolenoid(boolean pFired){			//Used to set the state of the solenoid
    	if(pFired){
    		pickupSolenoid.set(DoubleSolenoid.Value.kForward);
    	}
    	else{
    		pickupSolenoid.set(DoubleSolenoid.Value.kReverse);    		
    	}
    }
    
    public void STOP(){										//Stops the speed of the motor
    	pickupMotor.set(0);
    }
    
    public void updateDashboard(){							//Updates the dash board
    	SmartDashboard.putNumber("Pickup Speed : ", pickupMotor.getSpeed());
    	SmartDashboard.putBoolean("Pickup Lower Limit : ", !lowerLimit.get());
    	SmartDashboard.putBoolean("Pickup Safe Setting : ", stayInSafeZone);
    	SmartDashboard.putBoolean("is down : ", isDown());
    }
}

