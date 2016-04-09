package org.usfirst.frc.team3663.robot.commands;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CG_VisionSeeAnyGoal extends CommandGroup {
    
    public  CG_VisionSeeAnyGoal() {
    	
    	addSequential(new C_VisionTurnTime(-0.85,1.25));
    	addSequential(new C_VisionTurnTime(0.85,2.5));
    //	addSequential(new C_VisionDartAutoMove(Robot.robotMap.touch2+350));
    //	addSequential(new C_VisionTurnTime(-0.85,2.6));
        
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
