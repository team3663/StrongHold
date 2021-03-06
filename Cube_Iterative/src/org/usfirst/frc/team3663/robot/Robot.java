package org.usfirst.frc.team3663.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	RobotDrive myRobot;
	Joystick stick;
	CANTalon talon1;
	CANTalon talon2;
	CANTalon talon3;
	CANTalon talon4;
	CANTalon talon5;
	CANTalon talon6;
	CANTalon talon7;
	CANTalon talon8;
	CANTalon talonDart;
	DoubleSolenoid solinoide1;
	DoubleSolenoid solinoide2;
	AnalogInput dart; 
	
	int autoLoopCounter;
	int Flip = 1;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	talon1 = new CANTalon(1);
    	talon2 = new CANTalon(2);
    	talon3 = new CANTalon(3);
    	talon4 = new CANTalon(4);
    	talon5 = new CANTalon(5);
    	talon6 = new CANTalon(6);
    	talon7 = new CANTalon(7);
    	talonDart = new CANTalon(8);
    	dart = new AnalogInput(0);
    	solinoide1 = new DoubleSolenoid(4,5);
    	solinoide2 = new DoubleSolenoid(6,7);
    	
    	talonDart.enableBrakeMode(true);
    	
    	myRobot = new RobotDrive(talon1, talon2, talon3, talon4);
    	stick = new Joystick(0);
    	
    	lastRun = dart.getAverageValue();
    }
    
    
    /**
     * This function is run once each time the robot enters autonomous mode
     */
    public void autonomousInit() {
    }

    /**h
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    }
    
    /**
     * This function is called once each time the robot enters tele-operated mode
     */
    public void teleopInit(){
    }

    /**
     * This function is called periodically during operator control
     */
    public int drop = -1;
    public boolean test1 = false;
    public boolean test2 = false;
    
    public void teleopPeriodic() {
    	myRobot.arcadeDrive(-stick.getRawAxis(0), -stick.getRawAxis(1));
    	
    	driveDart(stick.getRawAxis(3)-stick.getRawAxis(2));    	
    	
    	SmartDashboard.putNumber("Dart raw", dart.getValue());
    	SmartDashboard.putNumber("dart avrage", dart.getAverageValue());
    	
    	int shoot = 0;
    	if(stick.getRawButton(1)){
    		talon7.set(1);
    	}
    	else if(stick.getRawButton(2)){
    		talon7.set(-1);
    	}
    	else{
    		talon7.set(0);
    	}
    	if(stick.getRawButton(4)){
    		talon5.set(-1);
    		talon6.set(1);
    	}
    	else if(stick.getRawButton(3)){
    		talon5.set(.7);
    		talon6.set(-.7);
    	}
    	else{
    		talon5.set(0);
    		talon6.set(0);    		
    	}
    	
    	if(stick.getRawButton(5) && drop < 0){
			solinoide1.set(DoubleSolenoid.Value.kReverse);
			timeCatch = 5;
    	}
    	if(timeCatch == 0){
			solinoide1.set(DoubleSolenoid.Value.kForward);    		
    	}
    	timeCatch--;
    	if(stick.getRawButton(6) && drop < 0){
    		drop = 10;
    		if(test2){
    			solinoide2.set(DoubleSolenoid.Value.kForward);
    			test2 = false;
    		}
    		else{
    			solinoide2.set(DoubleSolenoid.Value.kReverse);   
    			test2 = true;
    		}
    	}
    	drop--;
    	SmartDashboard.putNumber("drop", drop);
    	SmartDashboard.putNumber("lastRun", lastRun);
    	safeCheck();
    }
    
    private int timeCatch = 0;
    
    /**
     * This function is called periodically during test mode
     */
    private int lastRun = 0;
    private int lastSpeed = 1;
    private int diff = 0;
    private int count = 0;
    public void driveDart(double value){
    	diff = dart.getAverageValue() - lastRun;
		SmartDashboard.putNumber(" value switch speed", value);
		SmartDashboard.putNumber(" value switch diff", diff);
    	int speed = lastSpeed;
    	if(!(diff * value < 0) && Math.abs(diff) > 3){
    		speed *= -1;
    	}
    	if(dart.getAverageValue() < 2150 && value < 0){
    		talonDart.set(value*speed);
    	}
    	else if(dart.getAverageValue() > 520 && value > 0){
    		talonDart.set(value*speed);
    	}
    	else{
    		talonDart.set(0);
    	}
    	if(count > 3){
        	lastRun = dart.getAverageValue();   
        	count = 0;
    	}	
    	count++;
    	SmartDashboard.putNumber("vallkasdjflkjlkajsdf", speed);
    	lastSpeed = speed;
    }
    
    int failSafe = 0;
    public void safeCheck(){
    	if((dart.getAverageValue() > 3600 || dart.getAverageValue() < 250)){
    		talonDart.set(0);
    		int a = 0/0;
    	}    	
    	if(((Math.abs(diff) > 2 && Math.abs(stick.getRawAxis(3)-stick.getRawAxis(2)) < .05) || (Math.abs(diff) < 2 && Math.abs(stick.getRawAxis(3)-stick.getRawAxis(2)) > .05))
    			&& dart.getAverageValue() < 2150 && dart.getAverageValue() > 520){
    		failSafe++;
    		if(failSafe > 10){
        		talonDart.set(0);
    			int a = 0/0;
    		}
    	}
    	else{
    		failSafe = 0;
    	}
    }
    
    public void testPeriodic() {
    	LiveWindow.run();
    }
    
}
