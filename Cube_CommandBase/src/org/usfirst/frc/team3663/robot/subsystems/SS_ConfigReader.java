package org.usfirst.frc.team3663.robot.subsystems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Subsystem;

public class SS_ConfigReader extends Subsystem{
	public SS_ConfigReader(){
		try{
			Path file = Paths.get("/home/lvuser/config.txt");
			try (InputStream in = Files.newInputStream(file);
			    BufferedReader reader = new BufferedReader(new InputStreamReader(in))) 
			{
			    String line;
			    String[] keyValue;
			    while ((line = reader.readLine()) != null){
			    	keyValue = line.split("=");
			    	if(keyValue[0].equals("driveLeftMotor1")){
			    		Robot.robotMap.driveLeftMotor1 = Integer.parseInt(keyValue[1]);
			    	}else if(keyValue[0].equals("driveLeftMotor2")){
			    		Robot.robotMap.driveLeftMotor2 = Integer.parseInt(keyValue[1]);
			    	}else if(keyValue[0].equals("driveRightMotor1")){
			    		Robot.robotMap.driveRightMotor1 = Integer.parseInt(keyValue[1]);
			    	}else if(keyValue[0].equals("driveRightMotor2")){
			    		Robot.robotMap.driveRightMotor2 = Integer.parseInt(keyValue[1]);
			    	}else if(keyValue[0].equals("shooterMotorTop")){
			    		Robot.robotMap.shooterMotorTop = Integer.parseInt(keyValue[1]);
			    	}else if(keyValue[0].equals("shooterMotorBottom")){
			    		Robot.robotMap.shooterMotorBottom = Integer.parseInt(keyValue[1]);
			    	}else if(keyValue[0].equals("pickUpMotor")){
			    		Robot.robotMap.pickUpMotor = Integer.parseInt(keyValue[1]);
			    	}else if(keyValue[0].equals("dartMotor")){
			    		Robot.robotMap.dartMotor = Integer.parseInt(keyValue[1]);
			    	}else if(keyValue[0].equals("winchMotor1")){
			    		Robot.robotMap.winchMotor1 = Integer.parseInt(keyValue[1]);
			    	}else if(keyValue[0].equals("winchMotor2")){
			    		Robot.robotMap.winchMotor2 = Integer.parseInt(keyValue[1]);
			    	}else if(keyValue[0].equals("wheelyBarMotor")){
			    		Robot.robotMap.wheelyBarMotor = Integer.parseInt(keyValue[1]);
			    	}else if(keyValue[0].equals("hookMotor")){
			    		Robot.robotMap.hookMotor = Integer.parseInt(keyValue[1]);
			    	}else if(keyValue[0].equals("dartPotentiometer")){
			    		Robot.robotMap.dartPotentiometer = Integer.parseInt(keyValue[1]);
			    	}else if(keyValue[0].equals("driveGyro")){
			    		Robot.robotMap.driveGyro = Integer.parseInt(keyValue[1]);
			    	}else if(keyValue[0].equals("wheelyBarLimitSwitch")){
			    		Robot.robotMap.wheelyBarLimitSwitch = Integer.parseInt(keyValue[1]);
			    	}else if(keyValue[0].equals("pickupLowerLimit")){
			    		Robot.robotMap.pickupLowerLimit = Integer.parseInt(keyValue[1]);
			    	/*}else if(keyValue[0].equals("autoSwitchs")){
			    		String[] temp = keyValue[1].split(",");
			    		Robot.robotMap.autoSwitchs = new int[]{
			    				Integer.parseInt(temp[0]),
//Trent These no longerExist	Integer.parseInt(temp[1]),
			    				Integer.parseInt(temp[2]),
			    				Integer.parseInt(temp[3])};*/
			    	}else if(keyValue[0].equals("cameraRelay")){
			    		Robot.robotMap.cameraRelay = Integer.parseInt(keyValue[1]);
			    	}else if(keyValue[0].equals("pickUpSolenoid")){
			    		String[] temp = keyValue[1].split(",");
			    		Robot.robotMap.pickUpSolenoid = new int[]{
			    				Integer.parseInt(temp[0]),
			    				Integer.parseInt(temp[1])};
			    	}else if(keyValue[0].equals("shooterSolenoid")){
			    		String[] temp = keyValue[1].split(",");
			    		Robot.robotMap.shooterSolenoid = new int[]{
			    				Integer.parseInt(temp[0]),
			    				Integer.parseInt(temp[1])};
			    	}else if(keyValue[0].equals("hookSolenoid")){
			    		String[] temp = keyValue[1].split(",");
			    		Robot.robotMap.hookSolenoid = new int[]{
			    				Integer.parseInt(temp[0]),
			    				Integer.parseInt(temp[1])};
			    	}else if(keyValue[0].equals("driveAxisForward")){
			    		Robot.robotMap.driveAxisForward = Integer.parseInt(keyValue[1]);
			    	}else if(keyValue[0].equals("driveAxisReverse")){
			    		Robot.robotMap.driveAxisReverse = Integer.parseInt(keyValue[1]);
			    	}else if(keyValue[0].equals("driveAxisTurn")){
			    		Robot.robotMap.driveAxisTurn = Integer.parseInt(keyValue[1]);
			    	}else if(keyValue[0].equals("winchAxis")){
			    		Robot.robotMap.winchAxis = Integer.parseInt(keyValue[1]);
			    	}else if(keyValue[0].equals("hookAxis")){
			    		Robot.robotMap.hookAxis = Integer.parseInt(keyValue[1]);
			    	}else if(keyValue[0].equals("encoderTicksPerInch")){
			    		Robot.robotMap.encoderTicksPerInch = Integer.parseInt(keyValue[1]);
			    	}else if(keyValue[0].equals("maxDistanceValue")){
			    		Robot.robotMap.maxDistanceValue = Integer.parseInt(keyValue[1]);
			    	}else if(keyValue[0].equals("minDistanceValue")){
			    		Robot.robotMap.minDistanceValue = Integer.parseInt(keyValue[1]);
			    	}else if(keyValue[0].equals("soft1")){
			    		Robot.robotMap.soft1 = Integer.parseInt(keyValue[1]);
			    	}else if(keyValue[0].equals("soft2")){
			    		Robot.robotMap.soft2 = Integer.parseInt(keyValue[1]);
			    	}else if(keyValue[0].equals("hard1")){
			    		Robot.robotMap.hard1 = Integer.parseInt(keyValue[1]);
			    	}else if(keyValue[0].equals("hard2")){
			    		Robot.robotMap.hard2 = Integer.parseInt(keyValue[1]);
			    	}else if(keyValue[0].equals("touch1")){
			    		Robot.robotMap.touch1 = Integer.parseInt(keyValue[1]);
			    	}else if(keyValue[0].equals("touch2")){
			    		Robot.robotMap.touch2 = Integer.parseInt(keyValue[1]);
			    	}else if(keyValue[0].equals("isDriveFlipped")){
			    		Robot.robotMap.isDriveFlipped = Boolean.parseBoolean(keyValue[1]);
			    	}
			    }
			} catch (IOException x) {
				System.err.println(x);
			}
		} catch(Exception e){
			System.err.println(e);
		}
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
