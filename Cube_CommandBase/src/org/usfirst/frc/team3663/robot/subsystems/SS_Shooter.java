package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//

public class SS_Shooter extends Subsystem {
	
	//Motors
	private CANTalon shooterTop = new CANTalon(Robot.robotMap.shooterMotorTop);
	private CANTalon shooterBottom = new CANTalon(Robot.robotMap.shooterMotorBottom);
	//Solenoids
	private DoubleSolenoid shooterSolenoid = new DoubleSolenoid(Robot.robotMap.shooterSolenoid[0], Robot.robotMap.shooterSolenoid[1]);
	//Sensors
	
	
    public void initDefaultCommand() {
    	
    }
    
    public boolean aboveWantedSpeed(int pTarget){
    	return (shooterTop.getEncVelocity() > pTarget && shooterBottom.getEncVelocity() > pTarget);
    }
    
    public boolean isMotorRunning(){
    	double value = shooterTop.getSpeed() + shooterBottom.getSpeed();
    	return value == 0;
    }
    
    public void setShooterMotorBottom(double pSpeed){						//sets the speed of the bottom
    	shooterBottom.set(pSpeed);
    }
    
    public void setShooterMotorTop(double pSpeed){							//sets the speed of the top
    	shooterTop.set(pSpeed);
    }
    
    public void setShooterMotorsSpeed(double pSpeed){						//Sets the speed of both motors
    	shooterTop.set(pSpeed);
    	shooterBottom.set(-pSpeed);
    }

    public void toggleShooterSolenoid(){									//Works as a toggle for the shooter piston
    	if(shooterSolenoid.get() == DoubleSolenoid.Value.kForward){
    		shooterSolenoid.set(DoubleSolenoid.Value.kReverse);
    	}
    	else{
    		shooterSolenoid.set(DoubleSolenoid.Value.kForward);    		
    	}
    }
    
    public void fireShooterSolenoid(boolean pFire){							//Manually sets the location of the shooter solenoid
    	if(!pFire){
    		shooterSolenoid.set(DoubleSolenoid.Value.kForward);
    	}
    	else{
    		shooterSolenoid.set(DoubleSolenoid.Value.kReverse);
    	}
    }
    
    public void STOP(){
    	shooterBottom.set(0);
    	shooterTop.set(0);
    }
    
    public void updateDashboard(){							//sends a update to the dashboard
    	SmartDashboard.putNumber("Top Shooter Motor Speed : ", shooterTop.getSpeed());
    	SmartDashboard.putNumber("Bottom Shooter Motor Speed : ", shooterBottom.getSpeed());
    }
}

