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
    		Robot.visionTable.putBoolean("ShooterShot: ", true);
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
        
    private double topSpeed = 0;
    public void holdTopSpeed(int pSpeed){
    	int vTopSpeed = shooterTop.getEncVelocity();
    	double diffTop = (double)(pSpeed - vTopSpeed)/300000.0;
    	topSpeed-=diffTop;
    	if(topSpeed > 1){
    		topSpeed = 1;
    	}
    	else if(topSpeed < -1){
    		topSpeed = -1;
    	}
    	shooterTop.set(topSpeed);
    	SmartDashboard.putNumber("tops speed", topSpeed);
    }
    
    private double bottomSpeed = 0;    
    public void holdBottomSpeed(int pSpeed){
    	int vBottomSpeed = -shooterBottom.getEncVelocity();
    	double diffBottom = (double)(pSpeed - vBottomSpeed)/300000.0;
    	bottomSpeed-=diffBottom;
    	if(bottomSpeed > 1){
    		bottomSpeed = 1;
    	}
    	else if(bottomSpeed < -1){
    		bottomSpeed = -1;
    	}    
    	shooterBottom.set(bottomSpeed);
    	SmartDashboard.putNumber("bottoms speed", bottomSpeed);
    }
    
    public void holdSpeed(int pSpeed){
    	holdBottomSpeed(pSpeed);
    	holdTopSpeed(pSpeed);
    }
    
    private int topSafeTimes = 3;
    public boolean topInSpeed(int pSpeed){
    	int currentSpeed = shooterTop.getEncVelocity();
    	if(pSpeed+300>currentSpeed&&pSpeed-300<currentSpeed){
    		topSafeTimes--;
    	}
    	else{
    		topSafeTimes = 3;
    	}
    	return topSafeTimes<0;
    }
    
    private int bottomSafeTimes = 3;
    public boolean bottomInSpeed(int pSpeed){
    	int currentSpeed = shooterBottom.getEncVelocity();
    	if(pSpeed+300>currentSpeed&&pSpeed-300<currentSpeed){
    		bottomSafeTimes--;
    	}
    	else{
    		bottomSafeTimes = 3;
    	}
    	return bottomSafeTimes<0;
    }
    
    public boolean atSpeed(int pSpeed){
    	return topInSpeed(pSpeed) && bottomInSpeed(pSpeed);
    }
    
    public void STOP(){
    	shooterBottom.set(0);
    	shooterTop.set(0);
    }
    
    public void updateDashboard(){							//sends a update to the dashboard
    	SmartDashboard.putNumber("Top Shooter Motor Speed : ", shooterTop.getEncVelocity());
    	SmartDashboard.putNumber("Bottom Shooter Motor Speed : ", shooterBottom.getEncVelocity());
    	Robot.gui.sendNumber("shooter/Top Shooter Motor", shooterTop.getEncVelocity());
    	Robot.gui.sendNumber("shooter/Bottom Shooter Motor", shooterBottom.getEncVelocity());
    }
}
