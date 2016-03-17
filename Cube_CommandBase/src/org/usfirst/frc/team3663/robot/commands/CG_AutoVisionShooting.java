package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class CG_AutoVisionShooting extends CommandGroup {
    
	NetworkTable table = Robot.visionTable;
	
    public  CG_AutoVisionShooting() {
    	
		addSequential(new CG_VisionCenterGoal());
	//	addSequential(new C_DartPrepareForShot()); when got dart, then put in
		addSequential(new CG_AutoShoot(-24000));
		
		//----------------------------------------------
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
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
