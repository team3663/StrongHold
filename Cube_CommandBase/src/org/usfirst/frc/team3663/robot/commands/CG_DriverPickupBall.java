package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CG_DriverPickupBall extends CommandGroup {
    
    public  CG_DriverPickupBall() {				//runs the motors in to grab the ball off the field
    	addSequential(new C_PickupFirePiston(true));
    	//addSequential(new C_DartAutoMove(Robot.ss_Dart.minDistance()));
    	
    	addParallel(new C_ShooterRunMotors(-1));
    	addParallel(new C_PickupRunMotor(-1));
    }
}
