package org.usfirst.frc.team3663.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CG_PitAutoVisionShooting extends CommandGroup {
    
    public  CG_PitAutoVisionShooting() {

    	addParallel(new C_HoldSpeedToFlag(-3500));
		addSequential(new CG_VisionCenterGoal());
		addSequential(new C_DartPrepareForShot());
		addSequential(new CG_AutoShoot(-3500));//-24000'
		
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
