package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CG_AutoOverDefenceBasic extends CommandGroup {
    
    public  CG_AutoOverDefenceBasic(double driveTime) {
        // Add Commands here:
    	addSequential(new C_PickupFirePiston(true));
    	addSequential(new C_DartAutoMove(1447));
    	addSequential(new C_PickupFirePiston(false));
        addSequential(new C_DriveBasedTime(driveTime/4, -0.7, 0));
    	addParallel(new C_WheelyBarAutoMove(2046, .7));
        addSequential(new C_DriveBasedTime(3*driveTime/4, -0.7, 0));
    	addSequential(new C_PickupFirePiston(true));
    	
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
