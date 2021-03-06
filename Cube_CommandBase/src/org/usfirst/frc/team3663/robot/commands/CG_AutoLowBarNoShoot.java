package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CG_AutoLowBarNoShoot extends CommandGroup {
    
    public  CG_AutoLowBarNoShoot() {
    	addSequential(new C_CameraLightSet(true));
        addSequential(new C_PickupFirePiston(true));
    	addParallel(new C_WheelyBarAutoMove(Robot.ss_WheelyBar.maxDistance(), .5));
    	addSequential(new C_DartAutoMove(Robot.ss_Dart.minDistance()));
        addSequential(new C_DriveBasedTime(0.5, -0.7, 0));
    	addParallel(new C_WheelyBarAutoMove(2046, .7));
    	addSequential(new C_DriveBasedTime(2.6,-0.7, 0));
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
